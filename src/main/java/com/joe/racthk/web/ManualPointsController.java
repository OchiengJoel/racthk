package com.joe.racthk.web;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.joe.racthk.model.ManualPoints;
import com.joe.racthk.model.Member;
import com.joe.racthk.service.ManualPointService;
import com.joe.racthk.service.MemberService;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

@Controller
//@RequestMapping("/api/v1/manualpoints")
public class ManualPointsController {

    @Autowired
    private final ManualPointService manualPointService;

    @Autowired
    private final MemberService memberService;


    public ManualPointsController(ManualPointService manualPointService, MemberService memberService) {
        this.manualPointService = manualPointService;
        this.memberService = memberService;
    }

    private void addModelAttribute(Model model){
        model.addAttribute("members", memberService.getAllMembers());
        model.addAttribute("manualpoints", manualPointService.manualPointsList());
    }

//    @RequestMapping("/points")
//    public String getPointsList(Model model){
//        model.addAttribute("manualpoints", manualPointService.manualPointsList());
//        return "points/list";
//    }

    @RequestMapping("/points")
    public String getPointsList(Model model, @RequestParam(defaultValue = "desc") String order) {
        // Fetch manual points from the service
        List<ManualPoints> manualPoints = manualPointService.manualPointsList();

        // Sort the manual points based on the points column
        if ("desc".equalsIgnoreCase(order)) {
            manualPoints.sort(Comparator.comparing(ManualPoints::getPoint).reversed());
        } else {
            manualPoints.sort(Comparator.comparing(ManualPoints::getPoint));
        }

        // Add sorted manual points to the model
        model.addAttribute("manualpoints", manualPoints);

        return "points/list";
    }

    @RequestMapping("/addPoints")
    public String addPointsForm(Model model) {
        addModelAttribute(model);
        return "points/add";
    }

    @PostMapping("/addPoints")
    public String save(ManualPoints manualPoints){
        manualPointService.createManualPoints(manualPoints);
        return "redirect:/points";

    }

    @RequestMapping("/editPoints/{id}")
    public String editPointsForm(@PathVariable Long id, Model model){
        addModelAttribute(model);
        ManualPoints manualPoints = manualPointService.getManualPointsById(id);
        model.addAttribute("manualPoints", manualPoints);
        return "points/edit";

    }

    @RequestMapping("detailsPoints/{id}")
    public String detailsPoints(@PathVariable Long id, Model model){
        addModelAttribute(model);
        ManualPoints manualPoints = manualPointService.getManualPointsById(id);
        if (manualPoints == null) {
            model.addAttribute("errorMessage", "Record Not Found");
            return "error";
        }

        model.addAttribute("manualPoints", manualPoints);
        return "points/detail";
    }

    @RequestMapping(value = "/updatePoints/{id}", method = {RequestMethod.GET, RequestMethod.PUT})
    public String updatePoints(ManualPoints manualPoints){
        manualPointService.updateManualPoints(manualPoints);
        return "redirect:/points";
    }

    @RequestMapping(value = "/deletePoints/{id}", method = {RequestMethod.GET, RequestMethod.DELETE})
    public String delete(@PathVariable Long id, Model model){
        ManualPoints manualPoints = manualPointService.getManualPointsById(id);
        if (manualPoints == null) {
            model.addAttribute("errorMessage", "Record Not Found");
            return "error";
        }
        manualPointService.deleteManualPointsById(id);
        return "redirect:/points";

    }


    @GetMapping("/points/export/excel2")
    public void exportToExcel2(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=membersList.xls");

        //List<Member> memberList = memberService.getAllMembers();
        List<ManualPoints> manualPointsList = manualPointService.manualPointsList();
        // Sort the manual points based on the points column
        manualPointsList.sort(Comparator.comparing(ManualPoints::getPoint));


        // Create a workbook and sheet
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Club Members Points List Report");

        // Create a bold font
        XSSFFont headerFont = workbook.createFont();
        headerFont.setBold(true);

        // Create a cell style with the bold font
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        // Create a header row
        XSSFRow headerRow = sheet.createRow(0);

        // Set the header row cells with the cell style
        XSSFCell headerCell = ((XSSFRow) headerRow).createCell(0);
        headerCell.setCellValue("S/No.");
        headerCell.setCellStyle(headerCellStyle);

        headerCell = headerRow.createCell(1);
        headerCell.setCellValue("First Name");
        headerCell.setCellStyle(headerCellStyle);

        headerCell = headerRow.createCell(2);
        headerCell.setCellValue("Last Name");
        headerCell.setCellStyle(headerCellStyle);

        headerCell = headerRow.createCell(3);
        headerCell.setCellValue("Email");
        headerCell.setCellStyle(headerCellStyle);

        headerCell = headerRow.createCell(4);
        headerCell.setCellValue("Phone Contact");
        headerCell.setCellStyle(headerCellStyle);

        headerCell = headerRow.createCell(4);
        headerCell.setCellValue("Points");
        headerCell.setCellStyle(headerCellStyle);


        // Create data rows
        int rowNum = 1;
        for (ManualPoints manualPoints : manualPointsList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(manualPoints.getMember().getFname());
            row.createCell(1).setCellValue(manualPoints.getMember().getLname());
            row.createCell(2).setCellValue(manualPoints.getMember().getEmail());
            row.createCell(3).setCellValue(manualPoints.getMember().getContact());
            row.createCell(4).setCellValue(manualPoints.getPoint());


        }

        workbook.write(response.getOutputStream());
        workbook.close();
    }


    @GetMapping("/points/export/pdf")
    public void generatePDF(HttpServletResponse response) throws Exception {
        // Set the response content type
        response.setContentType("application/pdf");

        // Set the headers for the response
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=members_report.pdf";
        response.setHeader(headerKey, headerValue);

       // List<Member> memberList = memberService.getAllMembers();
        List<ManualPoints> manualPointsList = manualPointService.manualPointsList();
        // Sort the manual points based on the points column
        manualPointsList.sort(Comparator.comparing(ManualPoints::getPoint));


        // Create a new PDF document
        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());

        // Open the document
        document.open();

        // Add image to the PDF report
        /*InputStream logo = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("static/images/logo.png");
        ImageDataFactory imageFactory = ImageDataFactory.create(logo);
        Image image = new Image(imageFactory);
        doc.add(image.setAutoScale(true).setFixedPosition(0, 0));*/

        /*File logo = new File("static/images/logo.png");
        Image image = Image.getInstance(logo.getAbsolutePath());
        image.setAutoScale(true);
        image.setFixedPosition(0, 0);
        doc.add(image);*/



        // Add a title to the document
        Paragraph title = new Paragraph("Club Members Points List Report");
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        // Add a table to the document
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);

        // Add the table header row
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setPadding(5);

        cell.setPhrase(new Phrase("First Name", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Last Name", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Email", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Phone Contact", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Member Classification", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
        table.addCell(cell);


        // Add the table data rows
        for (ManualPoints manualPoints : manualPointsList) {
            table.addCell(manualPoints.getMember().getFname());
            table.addCell(manualPoints.getMember().getLname());
            table.addCell(manualPoints.getMember().getEmail());
            table.addCell(manualPoints.getMember().getContact());
            table.addCell(manualPoints.getPoint().toString());
        }

        // Add the table to the document
        document.add(table);

        // Close the document
        document.close();
    }



}
