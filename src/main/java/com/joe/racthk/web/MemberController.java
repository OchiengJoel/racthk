package com.joe.racthk.web;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.joe.racthk.enums.Role;
import com.joe.racthk.model.Attendance;
import com.joe.racthk.model.Member;
import com.joe.racthk.repo.MemberRepo;
import com.joe.racthk.service.MemberService;
import com.joe.racthk.service.UserService;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class MemberController {

    @Autowired
    private MemberRepo memberRepo;

    @Autowired
    private MemberService memberService;

    @Autowired
    private UserService userService;

    public MemberController() {
    }


    public Model addModelAttribute(Model model){
        model.addAttribute("members", memberService.getAllMembers());
        model.addAttribute("roles", Role.values());
        return model;
    }


    @RequestMapping("/members")
    public String memberList(Model model) {
        model.addAttribute("members", memberService.getAllMembers());
        return "members/list";
    }

    @RequestMapping("/addMember")
    public String createMemberForm() {
        return "members/add";
    }

    @PostMapping("/addMember")
    public String save(Member member) {
        memberService.saveMembers(member);
        return "redirect:/members";
    }

    @RequestMapping("/editMember/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        Member member = memberRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID: " + id));
        model.addAttribute("member", member);
        return "members/edit";
    }

    @RequestMapping(value = "/updateMember/{id}", method = {RequestMethod.GET, RequestMethod.PUT})
    public String update(Member member){
        memberService.saveMembers(member);
        return "redirect:/members";
    }

    @GetMapping("/memberDetails/{id}")
    public String memberDetails(@PathVariable Long id, Model model){
        //addModelAttribute(model);
        Member memberDetails = memberService.getByMemberId(id);
        model.addAttribute("member", memberDetails);
        return "members/detail";
    }

    @RequestMapping(value = "/deleteMember/{id}", method = {RequestMethod.GET, RequestMethod.DELETE})
    public String delete (@PathVariable Long id){
        memberRepo.deleteById(id);
        return "redirect:/members";
    }

    @GetMapping("/member/export/excel2")
    public void exportToExcel2(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=membersList.xls");

        List<Member> memberList = memberService.getAllMembers();

        // Create a workbook and sheet
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Club Members List Report");

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
        headerCell.setCellValue("Member Classification");
        headerCell.setCellStyle(headerCellStyle);


        // Create data rows
        int rowNum = 1;
        for (Member member : memberList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(member.getId());
            row.createCell(1).setCellValue(member.getFname());
            row.createCell(2).setCellValue(member.getLname());
            row.createCell(3).setCellValue(member.getEmail());
            row.createCell(4).setCellValue(member.getContact());
            row.createCell(4).setCellValue(member.getClassification());

        }

        workbook.write(response.getOutputStream());
        workbook.close();
    }

    @GetMapping("/member/export/pdf")
    public void generatePDF(HttpServletResponse response) throws Exception {
        // Set the response content type
        response.setContentType("application/pdf");

        // Set the headers for the response
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=members_report.pdf";
        response.setHeader(headerKey, headerValue);

        List<Member> memberList = memberService.getAllMembers();

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
        Paragraph title = new Paragraph("Club Members List Report");
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
        for (Member member : memberList) {
            table.addCell(member.getFname());
            table.addCell(member.getLname());
            table.addCell(member.getEmail());
            table.addCell(member.getContact());
            table.addCell(member.getClassification());
        }

        // Add the table to the document
        document.add(table);

        // Close the document
        document.close();
    }





}
