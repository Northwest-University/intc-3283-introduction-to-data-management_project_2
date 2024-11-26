package edu.northwestu.intc3283.datasourcestarter.controller;

import edu.northwestu.intc3283.datasourcestarter.entity.Donor;
import edu.northwestu.intc3283.datasourcestarter.reports.LarryReportRow;
import edu.northwestu.intc3283.datasourcestarter.reports.MonthlyDonationRow;
import edu.northwestu.intc3283.datasourcestarter.reports.TopDonationReportDTO;
import edu.northwestu.intc3283.datasourcestarter.reports.WeeklyDonationRow;
import edu.northwestu.intc3283.datasourcestarter.repository.DonorsRepository;
import edu.northwestu.intc3283.datasourcestarter.util.DataGeneratorService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/")
public class DonorsController {

    private final DonorsRepository donorRepository;
    private final DataGeneratorService dataGeneratorService;
    private final DonorsRepository donorsRepository;

    public DonorsController(DonorsRepository donorRepository, DataGeneratorService dataGeneratorService, DonorsRepository donorsRepository) {
        this.donorRepository = donorRepository;
        this.dataGeneratorService = dataGeneratorService;
        this.donorsRepository = donorsRepository;
    }

    @GetMapping("/search")
    @ResponseBody
    public ResponseEntity<List<Donor>> searchDonors(@RequestParam("searchTerm") String searchTerm) {
        List<Donor> donors = donorRepository.findByFirstNameContainingOrLastNameContaining(searchTerm, searchTerm);

        if (donors.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(donors);
        }
    }


    @GetMapping("")
    public String getAll(@RequestParam(value = "searchTerm", required = false) String searchTerm, Model model) {
        List<Donor> donors;


        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            donors = donorRepository.findByFirstNameContainingOrLastNameContaining(searchTerm, searchTerm);
        } else {

            donors = donorRepository.findTop10ByOrderByCreatedAtDesc();
        }

        model.addAttribute("donors", donors);
        model.addAttribute("searchTerm", searchTerm);
        return "donors/index";
    }

    @GetMapping("/report")
    public String topDonorsReport(Model model, @RequestParam(value = "limit", required = false) Integer limit,
                                  @RequestParam(value = "startDate", required = false) String startDate) {
        if (limit == null || limit < 1) {
            limit = 5;
        }

        LocalDate localDate = LocalDate.parse(startDate);
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        model.addAttribute("startDate", startDate);
        model.addAttribute("topDonors", donorRepository.findTopDonors(limit));
        return "donors/report";
    }
    @GetMapping("/report/janice")
    public String topDonorsReportJanice(Model model) {
        model.addAttribute("JaniceInfo", donorRepository.findFirstTimeDonorsWithAddress(LocalDate.now().minusWeeks(5),LocalDate.now()));
        return "Janice reports";

    }
    @GetMapping("/report/Larry")
    public String topDonorsReportLarry(Model model) {
        List<LarryReportRow> missingAddressDonors = donorsRepository.findDonorsMissingAddressWithPhone(LocalDate.now().minusWeeks(5),LocalDate.now());
        model.addAttribute("LarrysInfo", missingAddressDonors);
        return "Larry reports";
    }


    @GetMapping("/report/Jennifer")
    public String topDonorsReportJennifer(Model model) {
        List<TopDonationReportDTO> monthlyReport = donorsRepository.findTopDonors(5);
        model.addAttribute("TopDonors",monthlyReport);

        List<MonthlyDonationRow> monthlyDonationReport = donorsRepository.monthlyDonationReport(LocalDate.now().minusWeeks(5),LocalDate.now());
        model.addAttribute("MonthlyDonationReport",monthlyReport);

        List<WeeklyDonationRow> weeklyReport = donorsRepository.weeklyDonationReport(LocalDate.now().minusWeeks(5),LocalDate.now());
        model.addAttribute("WeeklyDonationReport", weeklyReport);
        return "Jennifer reports";

    }
    @GetMapping("/donors/random")
    public String generateRandomDonors(@RequestParam("numDonors") int numDonors, @RequestParam("maxDonationsPerDonor") int maxDonationsPerDonor) {
        this.dataGeneratorService.generateRandomDonorsAndDonations(numDonors, maxDonationsPerDonor);
        return "redirect:/";
    }

}
