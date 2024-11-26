package edu.northwestu.intc3283.datasourcestarter.reports;

import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;

import java.math.BigDecimal;

public class TopDonationReportDTO {


    private String firstName;
    private String lastName;
    private String email;
    private int donationYear;
    private int donationMonth;
    private BigDecimal totalDonationAmount;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getDonationYear() {
        return donationYear;
    }

    public void setDonationYear(int donationYear) {
        this.donationYear = donationYear;
    }

    public int getDonationMonth() {
        return donationMonth;
    }

    public void setDonationMonth(int donationMonth) {
        this.donationMonth = donationMonth;
    }

    public BigDecimal getTotalDonationAmount() {
        return totalDonationAmount;
    }

    public void setTotalDonationAmount(BigDecimal totalDonationAmount) {
        this.totalDonationAmount = totalDonationAmount;
    }
}