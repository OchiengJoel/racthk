package com.joe.racthk.service;


import com.joe.racthk.model.ManualPoints;
import com.joe.racthk.repo.ManualPointsRepo;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManualPointService {

    @Autowired
    ManualPointsRepo manualPointsRepo;

    public List<ManualPoints> manualPointsList(){
      return manualPointsRepo.findAll();
    };

    public ManualPoints createManualPoints(ManualPoints manualPoints){
        return manualPointsRepo.save(manualPoints);
    }

    public ManualPoints getManualPointsById(Long id){
        return manualPointsRepo.findById(id).orElse(null);
    }

    public ManualPoints updateManualPoints(ManualPoints manualPoints){
        return manualPointsRepo.save(manualPoints);
    }

    public void deleteManualPointsById(Long id){
        manualPointsRepo.deleteById(id);
    }
}
