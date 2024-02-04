package com.joe.racthk.service;

import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.layout.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.joe.racthk.model.Attendance;
import com.joe.racthk.repo.AttendanceRepo;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepo attendanceRepo;


    public List<Attendance> list(){
      return  attendanceRepo.findAll();
    }

    public void saveAttendance(Attendance attendance){
        attendanceRepo.save(attendance);
    }

    public void updateAttendance(Attendance attendance){
        attendanceRepo.save(attendance);
    }

    public Attendance getAttendanceById(Long id){
        return attendanceRepo.findById(id).orElse(null);
    }

    public void deleteById(Long id){
        attendanceRepo.deleteById(id);
    }



    public List<Attendance> getFilteredAttendances(Long memberId, Long clubId, String attendancetype) {
        if (memberId != null && clubId != null && attendancetype != null) {
            return attendanceRepo.findByMemberIdAndClubIdAndAttendancetype(memberId, clubId, attendancetype);
        } else if (memberId != null && clubId != null) {
            return attendanceRepo.findByMemberIdAndClubId(memberId, clubId);
        } else if (memberId != null && attendancetype != null) {
            return attendanceRepo.findByMemberIdAndAttendancetype(memberId, attendancetype);
        } else if (clubId != null && attendancetype != null) {
            return attendanceRepo.findByClubIdAndAttendancetype(clubId, attendancetype);
        } else if (memberId != null) {
            return attendanceRepo.findByMemberId(memberId);
        } else if (clubId != null) {
            return attendanceRepo.findByClubId(clubId);
        } else if (attendancetype != null) {
            return attendanceRepo.findByAttendancetype(attendancetype);
        } else {
            return attendanceRepo.findAll();
        }
    }


    public byte[] exportToExcel(List<Attendance> attendances) {
        try {
            // Create a new Excel workbook and sheet
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Attendance Data");

            // Create header row
            Row headerRow = sheet.createRow(0);
            String[] headers = {"Member", "Club", "From Date", "To Date", "Attendance Type"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            // Populate data rows
            int rowNum = 1;
            for (Attendance attendance : attendances) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(attendance.getMember().getFname() + " " + attendance.getMember().getLname());
                row.createCell(1).setCellValue(attendance.getClub().getName());
                row.createCell(2).setCellValue(attendance.getFromdate().toString());
                row.createCell(3).setCellValue(attendance.getTodate().toString());
                row.createCell(4).setCellValue(attendance.getAttendancetype());
            }

            // Write the workbook content to a ByteArrayOutputStream
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            workbook.close();

            return outputStream.toByteArray();
        } catch (Exception e) {
            // Handle exceptions here
            return null;
        }
    }




}
