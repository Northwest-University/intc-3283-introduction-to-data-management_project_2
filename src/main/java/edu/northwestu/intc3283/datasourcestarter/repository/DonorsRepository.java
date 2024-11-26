package edu.northwestu.intc3283.datasourcestarter.repository;

import edu.northwestu.intc3283.datasourcestarter.entity.Donor;
import edu.northwestu.intc3283.datasourcestarter.reports.*;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface DonorsRepository extends CrudRepository<Donor, Long> {

    List<Donor> findByFirstNameContainingOrLastNameContaining(String firstName, String lastName);

    List<Donor> findTop10ByOrderByCreatedAtDesc();

    @Query("""
            
                        SELECT
                              donors.first_name,
                              donors.last_name,
                              donors.email,
                              YEAR(donations.created_at) AS donation_year,
                              MONTH(donations.created_at) AS donation_month,
                              SUM(donations.amount) AS total_donation_amount
                          FROM
                              donors
                          JOIN
                              donations ON donors.id = donations.donor_id
                          GROUP BY
                              donors.id,
                              donation_year,
                              donation_month
                          ORDER BY
                              donation_year DESC,
                              donation_month DESC,
                              SUM(donations.amount) DESC
                        LIMIT :limit;
            """)
    List<TopDonationReportDTO> findTopDonors(Integer limit);

    @Query(
            """

SELECT
    DATE_FORMAT(created_at, '%Y-%u') as donation_week,
    SUM(amount) as          total_donated
FROM donations
WHERE
    created_at BETWEEN :started_at AND :ended_at
GROUP BY donation_week
Order By donation_week ASC;        
"""
    )
    List<WeeklyDonationRow> weeklyDonationReport(@Param("started_at") LocalDate startedAt, @Param("ended_at") LocalDate endedAt);


@Query(
  """      
                    
SELECT
DATE_FORMAT(created_at, '%Y-%m') as donation_month,
SUM(amount) as          total_donated
FROM donations
WHERE
created_at BETWEEN :started_at AND :ended_at
GROUP BY donation_month
Order By donation_month ASC;        
"""
)

    List<MonthlyDonationRow> monthlyDonationReport(@Param("started_at") LocalDate startedAt, @Param("ended_at") LocalDate endedAt);

    @Query(
            """
               SELECT
                   donors.id AS donorId,
                   donors.first_name,
                   donors.last_name,
                   donors.phone,
                   donors.email,
                   donors.address1,
                   donors.address2,
                   donors.city,
                   donors.state,
                   donors.zip_Code,
                   SUM(donations.amount) AS totalAmount,
                   MAX(donations.created_at) AS lastDonationDate
               FROM
                   donors
                       JOIN
                   donations ON donors.id = donations.donor_Id
               WHERE ( donors.address1 = ''
                   OR  donors.city = ''
                   OR  donors.zip_code = '')
                 AND donors.phone IS NOT NULL
               GROUP BY donors.id, donors.first_name, donors.last_name, donors.phone,
                        donors.email, donors.address1, donors.address2, donors.city, donors.state, donors.zip_Code
               ORDER BY lastDonationDate DESC;
            """
    )
   
   List<LarryReportRow> findDonorsMissingAddressWithPhone(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);



@Query(
        """

SELECT
    donors.created_at,
    donors.donor_id,
    donors.FirstName,
    donors.LastName,
    donors.address1,
    donors.address2,
    donors.city,
    donors.state,
    donors.zip_Code,
    MIN(donation.created_at) AS first_donation_date,
    MIN(donation.amount) AS first_donation_amount
FROM
    donors donors
    JOIN donations donation ON donors.id = donation.donor_id
WHERE
    donors.address1 IS NOT NULL AND donors.address1 != ''  -- Ensures a valid mailing address
GROUP BY
    donors.id, donors.FirstName, donors.LastName, donors.address1, donors.address2, donors.city, donors.state, donors.zip_Code
HAVING
    first_donation_date = MIN(donation.created_at)  -- Filters to the first donation only
ORDER BY
    first_donation_date DESC;
""")
    List<JaniceReportRow> findFirstTimeDonorsWithAddress(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}