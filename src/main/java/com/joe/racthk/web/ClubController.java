package com.joe.racthk.web;

import com.joe.racthk.model.Club;
import com.joe.racthk.service.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ClubController {

    @Autowired
    private ClubService clubService;


    @RequestMapping("/clubs")
    public String clubList(Model model){
        model.addAttribute("clubs", clubService.getClubs());
        return "clubs/list";
    }

    /*@RequestMapping("/clubs")
    public String clubList(@RequestParam(value = "isDeleted", required = false, defaultValue = "false") boolean isDeleted, Model  model){
        Iterable<Club> clubs = clubService.getClubs(isDeleted);
        model.addAttribute("clubs", clubs);
        model.addAttribute("isDeleted", isDeleted); // Add the parameter to the model for use in your view
        return "clubs/list"; // Replace "clubs/list" with the actual view name
    }*/

    @RequestMapping("/addClub")
    public String addClubForm(){
        return "clubs/add";
    }

    @PostMapping("/addClub")
    public String save(Club club){
        clubService.saveClub(club);
        return "redirect:clubs";
    }

    @RequestMapping("/editClub/{id}")
    public String editClubForm(@PathVariable Long id, Model model){
        Club club = clubService.getClubById(id);
        if (club == null) {
            model.addAttribute("errorMessage", "Club Not Found");
            return "error";
        }
        model.addAttribute("club", club);
        return "clubs/edit";
    }

    @RequestMapping("detailsClub/{id}")
    public String detailsClubForm(@PathVariable Long id, Model model){
        Club club = clubService.getClubById(id);
        if (club == null) {
            model.addAttribute("errorMessage", "Club Not Found");
            return "error";
        }
        model.addAttribute("club", club);
        return "clubs/detail";
    }

    @RequestMapping(value = "/updateClub/{id}", method = {RequestMethod.GET, RequestMethod.PUT})
    public String updateClub(Club club){
        clubService.saveClub(club);
        return "redirect:/clubs";
    }

    @RequestMapping(value = "/deleteClub/{id}", method = {RequestMethod.GET, RequestMethod.DELETE})
    public String deleteClub(@PathVariable Long id, Model model){
        Club club = clubService.getClubById(id);
        if (club == null) {
            model.addAttribute("error", "Club not found");
            return "error";
        }

        clubService.deleteById(id);
        return "redirect:/clubs";
    }

}
