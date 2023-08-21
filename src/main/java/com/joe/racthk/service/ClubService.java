package com.joe.racthk.service;

import com.joe.racthk.model.Club;
import com.joe.racthk.repo.ClubRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class ClubService {

    @Autowired
    private ClubRepo clubRepo;


    public List<Club> getClubs(){
        return clubRepo.findAll();
    }
    public void saveClub(Club club){
        clubRepo.save(club);
    }

    public void updateClub(Club club){
        clubRepo.save(club);
    }

    public Club getClubById(Long id){
        return clubRepo.findById(id).orElse(null);
    }

    public void deleteById(Long id){
        clubRepo.deleteById(id);
    }
}
