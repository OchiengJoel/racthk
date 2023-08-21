package com.joe.racthk.web;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.joe.racthk.DTO.MemberPointsDTO;
import com.joe.racthk.model.Attendance;
import com.joe.racthk.service.AttendanceService;
import com.joe.racthk.service.ClubService;
import com.joe.racthk.service.MemberService;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private ClubService clubService;


    private void addModelAttribute(Model model) {
        model.addAttribute ("attendances", attendanceService.list());
        model.addAttribute ("members", memberService.getAllMembers());
        model.addAttribute ("clubs", clubService.getClubs());
    }

    @RequestMapping("/attendance")
    public String getAttendanceList(Model model){
        model.addAttribute("attendances", attendanceService.list());
        return "attendance/list";
    }

    @RequestMapping("/addAttendance")
    public String addAttendanceForm(Model model) {
        addModelAttribute(model);
        return "attendance/add";
    }

    @PostMapping("/addAttendance")
    public String save(Attendance attendance){
        attendanceService.saveAttendance(attendance);
        return "redirect:/attendance";

    }

    @RequestMapping("/editAttendance/{id}")
    public String editAttendanceForm(@PathVariable Long id, Model model){
        addModelAttribute(model);
        Attendance attendance = attendanceService.getAttendanceById(id);
        model.addAttribute("attendance", attendance);
        return "attendance/edit";

    }

    @RequestMapping("detailsAttendance/{id}")
    public String detailsAttendance(@PathVariable Long id, Model model){
        addModelAttribute(model);
        Attendance attendance = attendanceService.getAttendanceById(id);
        if (attendance == null) {
            model.addAttribute("errorMessage", "Attendance Not Found");
            return "error";
        }

        model.addAttribute("attendance", attendance);
        return "attendance/detail";
    }

    @RequestMapping(value = "/updateAttendance/{id}", method = {RequestMethod.GET, RequestMethod.PUT})
    public String updateAttendance(Attendance attendance){
        attendanceService.saveAttendance(attendance);
        return "redirect:/attendance";
    }

    @RequestMapping(value = "/deleteAttendance/{id}", method = {RequestMethod.GET, RequestMethod.DELETE})
    public String delete(@PathVariable Long id, Model model){
        Attendance attendance = attendanceService.getAttendanceById(id);
        if (attendance == null) {
            model.addAttribute("errorMessage", "Attendance Not Found");
            return "error";
        }
        attendanceService.deleteById(id);
        return "redirect:/attendance";

    }

    @GetMapping("/memberpoints")
    public String showMemberPoints(Model model) {
        List<MemberPointsDTO> memberPointsList = memberService.getMembersWithTotalPoints();
        model.addAttribute("memberPointsList", memberPointsList);
        return "attendance/memberPoints";
    }

    @GetMapping("/attendance/export/excel2")
    public void exportToExcel2(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=clientLogList.xlsx");

        List<Attendance> attendanceList = attendanceService.list();

        // Create a workbook and sheet
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Members Attendance Fellowship Report");

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
        headerCell.setCellValue("Member");
        headerCell.setCellStyle(headerCellStyle);

        headerCell = headerRow.createCell(1);
        headerCell.setCellValue("Fellowship Club Attended");
        headerCell.setCellStyle(headerCellStyle);

        headerCell = headerRow.createCell(2);
        headerCell.setCellValue("From Date");
        headerCell.setCellStyle(headerCellStyle);

        headerCell = headerRow.createCell(3);
        headerCell.setCellValue("To Date");
        headerCell.setCellStyle(headerCellStyle);

        headerCell = headerRow.createCell(4);
        headerCell.setCellValue("Points Awarded");
        headerCell.setCellStyle(headerCellStyle);

        headerCell = headerRow.createCell(5);
        headerCell.setCellValue("Attendance Type");
        headerCell.setCellStyle(headerCellStyle);

        // Create data rows
        int rowNum = 1;
        for (Attendance attendance : attendanceList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(attendance.getId());
            row.createCell(1).setCellValue(attendance.getMember().getEmail());
            row.createCell(2).setCellValue(attendance.getClub().getName());
            row.createCell(4).setCellValue(attendance.getFromdate().toString());
            row.createCell(3).setCellValue(attendance.getTodate().toString());
            row.createCell(5).setCellValue(attendance.getPoints());
            row.createCell(6).setCellValue(attendance.getAttendancetype());
        }

        workbook.write(response.getOutputStream());
        workbook.close();
    }

    @GetMapping("/attendance/export/pdf")
    public void generatePDF(HttpServletResponse response) throws Exception {
        // Set the response content type
        response.setContentType("application/pdf");

        // Set the headers for the response
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=attendance_report.pdf";
        response.setHeader(headerKey, headerValue);

        List<Attendance> attendanceList = attendanceService.list();

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

        // Load the logo image
        Image logo = Image.getInstance("classpath:static/images/logo.jpg"); // Provide the correct path to your logo image

        // Set the position and size of the logo
        logo.setAlignment(Element.ALIGN_CENTER);
        logo.scaleToFit(100, 100); // Adjust the size as needed

        // Add the logo to the document
        document.add(logo);


        // Create a Font object with Trebuchet font
        Font trebuchetFont = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD, BaseColor.BLACK);


        // Add a title to the document
        Paragraph title = new Paragraph("Members Fellowship Attendance Report", trebuchetFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        // Add a table to the document
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);

        // Add the table header row
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setPadding(5);

        cell.setPhrase(new Phrase("Member", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Fellowship Attended", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
        table.addCell(cell);

        cell.setPhrase(new Phrase("From Date", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
        table.addCell(cell);

        cell.setPhrase(new Phrase("To Date", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Point(s) Awarded", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Attendance Type", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
        table.addCell(cell);

        // Add the table data rows
        for (Attendance attendance : attendanceList) {
            table.addCell(attendance.getMember().getFname() + " " + attendance.getMember().getLname());
            table.addCell(attendance.getClub().getName());
            table.addCell(attendance.getFromdate().toString());
            table.addCell(attendance.getTodate().toString());
            table.addCell(String.valueOf(attendance.getPoints()));
            table.addCell(attendance.getAttendancetype());
        }

        // Add the table to the document
        document.add(table);

        // Close the document
        document.close();
    }


}
