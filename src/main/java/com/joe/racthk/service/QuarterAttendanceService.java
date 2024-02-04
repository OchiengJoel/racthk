package com.joe.racthk.service;

import com.joe.racthk.model.QuarterAttendance;
import com.joe.racthk.repo.QuarterAttendanceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuarterAttendanceService {

    @Autowired
    private QuarterAttendanceRepo quarterAttendanceRepo;

    public List<QuarterAttendance> quarterAttendanceList(){
        return quarterAttendanceRepo.findAll();
    }

    public QuarterAttendance createQuarterAttendance (QuarterAttendance quarterAttendance){
        return quarterAttendanceRepo.save(quarterAttendance);
    }

    public QuarterAttendance getQrattendanceById(Long id){
        return quarterAttendanceRepo.findById(id).orElseThrow(null);
    }

    public QuarterAttendance updateById(QuarterAttendance quarterAttendance){
        return quarterAttendanceRepo.save(quarterAttendance);
    }

    public void deleteQuarterAttendanceById(Long id){
        quarterAttendanceRepo.deleteById(id);
    }



}


