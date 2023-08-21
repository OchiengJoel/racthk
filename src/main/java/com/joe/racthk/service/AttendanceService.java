package com.joe.racthk.service;

import com.joe.racthk.model.Attendance;
import com.joe.racthk.repo.AttendanceRepo;
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

}
