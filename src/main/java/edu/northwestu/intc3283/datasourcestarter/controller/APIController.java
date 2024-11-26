package edu.northwestu.intc3283.datasourcestarter.controller;

import edu.northwestu.intc3283.datasourcestarter.reports.MonthlyDonationRow;
import edu.northwestu.intc3283.datasourcestarter.repository.DonorsRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
@RestController
public class APIController {
    private final DonorsRepository donorsRepository;

    public APIController(DonorsRepository donorsRepository) {
        this.donorsRepository = donorsRepository;
    }

    @GetMapping("/monthly-donations")
    public List<MonthlyDonationRow> monthlyDonationReport() {

        LocalDate startDate = LocalDate.now().minusWeeks(6);
        LocalDate endDate = LocalDate.now();

        return this.donorsRepository.monthlyDonationReport(startDate, endDate);
    }


}



