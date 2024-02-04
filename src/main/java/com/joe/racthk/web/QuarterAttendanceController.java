package com.joe.racthk.web;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.joe.racthk.model.Attendance;
import com.joe.racthk.model.Club;
import com.joe.racthk.model.ClubAttendance;
import com.joe.racthk.model.QuarterAttendance;
import com.joe.racthk.service.ClubService;
import com.joe.racthk.service.QuarterAttendanceService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class QuarterAttendanceController {

    @Autowired
    private final ClubService clubService;

    @Autowired
    private  final QuarterAttendanceService quarterAttendanceService;

    public QuarterAttendanceController(ClubService clubService, QuarterAttendanceService quarterAttendanceService) {
        this.clubService = clubService;
        this.quarterAttendanceService = quarterAttendanceService;
    }

    public void addModelAttribute(Model model){
        model.addAttribute("clubs", clubService.getClubs());
        model.addAttribute("quarterAttendances", quarterAttendanceService.quarterAttendanceList());
    }

    @RequestMapping("/qrattendance")
    public String getQuarterAttendanceList(Model model){
        model.addAttribute("quarterAttendances", quarterAttendanceService.quarterAttendanceList());
        return "qrattendance/list";
    }

    @RequestMapping("/addQrattendance")
    public String addQuarterAttendanceForm(Model model) {
        addModelAttribute(model);
        return "qrattendance/add";
    }

  /*  @PostMapping("/addQrattendance")
    public String save(QuarterAttendance quarterAttendance){

        // Assuming that the name attribute of the <select> for clubs is "club.id"
        // Set the selected quarter and club in the QuarterAttendance object
        quarterAttendance.setQuarter(quarterAttendance.getQuarter());
        // Retrieve the Club object based on the selected club id
        Long clubId = quarterAttendance.getClub().getId();
        Club club = clubService.getClubById(clubId);
        // Set the retrieved club in QuarterAttendance
        quarterAttendance.setClub(club);
        // Save the QuarterAttendance instance
        quarterAttendanceService.createQuarterAttendance(quarterAttendance);
        return "redirect:/qrattendance";

       *//* quarterAttendanceService.createQuarterAttendance(quarterAttendance);
        return "redirect:/qrattendance";*//*
    }

    @PostMapping("/addQrattendance")
    public String save(@RequestParam("quarter") String quarter, @RequestParam("clubIds") List<Long> clubIds) {
        // Create a QuarterAttendance instance
        QuarterAttendance quarterAttendance = new QuarterAttendance();
        quarterAttendance.setQuarter(quarter);

        // Retrieve and set the selected clubs
        List<Club> selectedClubs = clubService.getClubsByIds(clubIds);
        quarterAttendance.setClub(selectedClubs);

        // Save the QuarterAttendance instance
        quarterAttendanceService.createQuarterAttendance(quarterAttendance);

        return "redirect:/qrattendance";
    }*/

   /* @PostMapping("/addQrattendance")
    public String save(@RequestParam("quarter") String quarter, @RequestParam("clubIds") List<Long> clubIds) {
        for (Long clubId : clubIds) {
            Club club = clubService.getClubById(clubId);
            QuarterAttendance quarterAttendance = new QuarterAttendance();
            quarterAttendance.setQuarter(quarter);
            quarterAttendance.setClub(club);
            quarterAttendanceService.createQuarterAttendance(quarterAttendance);
        }
        return "redirect:/qrattendance";
    }*/

    @PostMapping("/addQrattendance")
    public String save(@RequestParam("quarter") String quarter, @RequestParam("clubIds") List<Long> clubIds) {
        QuarterAttendance quarterAttendance = new QuarterAttendance();
        quarterAttendance.setQuarter(quarter);

        for (Long clubId : clubIds) {
            Club club = clubService.getClubById(clubId);
            ClubAttendance clubAttendance = new ClubAttendance();
            clubAttendance.setClub(club);
            quarterAttendance.addClubAttendance(clubAttendance);
        }

        quarterAttendanceService.createQuarterAttendance(quarterAttendance);
        return "redirect:/qrattendance";
    }

    @RequestMapping("/editQrattendance/{id}")
    public String editQuarterAttendanceForm(@PathVariable Long id, Model model) {
        QuarterAttendance quarterAttendance = quarterAttendanceService.getQrattendanceById(id);
        if (quarterAttendance == null) {
            model.addAttribute("errorMessage", "Quarter Attendance Not Found");
            return "error";
        }
        model.addAttribute("quarterAttendance", quarterAttendance);
        return "qrattendance/edit";
    }

   /* @RequestMapping("/editQrattendance/{id}")
    public String editQuarterAttendanceForm(@PathVariable Long id, Model model){
        addModelAttribute(model);
        QuarterAttendance quarterAttendance = quarterAttendanceService.getQrattendanceById(id);
        model.addAttribute("quarterAttendance", quarterAttendance);
        return "qrattendance/edit";

    }*/

   // @PostMapping("/update/{id}")
    @RequestMapping("detailsQrattendance/{id}")
    public String updateQuarterAttendance(@PathVariable Long id, QuarterAttendance updatedQuarterAttendance) {
        QuarterAttendance existingQuarterAttendance = quarterAttendanceService.getQrattendanceById(id);
        if (existingQuarterAttendance == null) {
            // Handle the case where the quarter attendance is not found
            return "error";
        }

        // Update the properties of the existing quarter attendance
        existingQuarterAttendance.setQuarter(updatedQuarterAttendance.getQuarter());
        // Update other properties as needed

        // Save the updated quarter attendance
        quarterAttendanceService.updateById(existingQuarterAttendance);

        return "redirect:/qrattendance/list";
    }

    //@GetMapping("/details/{id}")
    @RequestMapping(value = "/updateQrattendance/{id}", method = {RequestMethod.GET, RequestMethod.PUT})
    public String viewQuarterAttendanceDetails(@PathVariable Long id, Model model) {
        QuarterAttendance quarterAttendance = quarterAttendanceService.getQrattendanceById(id);
        if (quarterAttendance == null) {
            model.addAttribute("errorMessage", "Quarter Attendance Not Found");
            return "error";
        }
        model.addAttribute("quarterAttendance", quarterAttendance);
        return "qrattendance/detail";
    }



   /* @RequestMapping("detailsQrattendance/{id}")
    public String detailsQuarterAttendance(@PathVariable Long id, Model model){
        addModelAttribute(model);
        QuarterAttendance quarterAttendance = quarterAttendanceService.getQrattendanceById(id);
        if (quarterAttendance == null) {
            model.addAttribute("errorMessage", "Record Not Found");
            return "error";
        }

        model.addAttribute("quarterAttendance", quarterAttendance);
        return "qrattendance/detail";
    }

    @RequestMapping(value = "/updateQrattendance/{id}", method = {RequestMethod.GET, RequestMethod.PUT})
    public String updateQrattendance(QuarterAttendance quarterAttendance){
        quarterAttendanceService.createQuarterAttendance(quarterAttendance);
        return "redirect:/qrattendance";
    }*/

    @RequestMapping(value = "/deleteQrattendance/{id}", method = {RequestMethod.GET, RequestMethod.DELETE})
    public String delete(@PathVariable Long id, Model model){
        QuarterAttendance quarterAttendance = quarterAttendanceService.getQrattendanceById(id);
        if (quarterAttendance == null) {
            model.addAttribute("errorMessage", "Record Not Found");
            return "error";
        }
        quarterAttendanceService.deleteQuarterAttendanceById(id);
        return "redirect:/qrattendance";

    }


    @GetMapping("/qrattendance/export/excel2")
    public void exportToExcel(HttpServletResponse response) throws IOException, IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=attendanceData.xlsx");

        List<QuarterAttendance> quarterAttendances = quarterAttendanceService.quarterAttendanceList(); // Fetch your data

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Attendance Data");

        // Create header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("S/No.");
        headerRow.createCell(1).setCellValue("Quarter");
        headerRow.createCell(2).setCellValue("Fellowship Clubs Attended");

        // Create data rows
        int rowNum = 1;
        for (QuarterAttendance qa : quarterAttendances) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(rowNum - 1);
            row.createCell(1).setCellValue(qa.getQuarter());

            // Concatenate club names into a single cell
            Cell cell = row.createCell(2);
            cell.setCellValue(qa.getClubAttendances().stream().map(ca -> ca.getClub().getName()).collect(Collectors.joining(", ")));
        }

        // Write the workbook content to the response
        workbook.write(response.getOutputStream());
        workbook.close();
    }


    @GetMapping("/qrattendance/export/pdf")
    public void exportToPDF(HttpServletResponse response) throws IOException, DocumentException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=attendanceData.pdf");

        List<QuarterAttendance> quarterAttendances = quarterAttendanceService.quarterAttendanceList(); // Fetch your data

        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        // Add title
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLACK);
        Paragraph title = new Paragraph("Attendance Data", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        // Add empty line
        document.add(Chunk.NEWLINE);

        // Add table
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);

        // Add table headers
        PdfPCell cell1 = new PdfPCell(new Phrase("S/No."));
        PdfPCell cell2 = new PdfPCell(new Phrase("Quarter"));
        PdfPCell cell3 = new PdfPCell(new Phrase("Fellowship Clubs Attended"));

        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);

        // Add table data
        int rowNum = 1;
        for (QuarterAttendance qa : quarterAttendances) {
            table.addCell(String.valueOf(rowNum));
            table.addCell(qa.getQuarter());

            // Concatenate club names into a single cell
            String clubNames = qa.getClubAttendances().stream().map(ca -> ca.getClub().getName()).collect(Collectors.joining(", "));
            table.addCell(clubNames);

            rowNum++;
        }

        document.add(table);
        document.close();
    }
}
