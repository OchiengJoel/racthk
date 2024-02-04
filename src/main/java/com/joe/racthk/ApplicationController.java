package com.joe.racthk;

import com.joe.racthk.enums.Role;
import com.joe.racthk.model.Member;
import com.joe.racthk.model.User;
import com.joe.racthk.repo.AttendanceRepo;
import com.joe.racthk.repo.ClubRepo;
import com.joe.racthk.repo.MemberRepo;
import com.joe.racthk.repo.MemberStatementRepo;
import com.joe.racthk.service.MemberService;
import com.joe.racthk.service.MemberStatementService;
import com.joe.racthk.service.UserService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class ApplicationController {


    private  final Logger logger = LoggerFactory.getLogger(ApplicationController.class);

    private final MemberService memberService;

    @Autowired
    private final MemberRepo memberRepo;

    @Autowired
    private final ClubRepo clubRepo;

   @Autowired
    private final AttendanceRepo attendanceRepo;


   @Autowired
   private final MemberStatementRepo memberStatementRepo;


   private final MemberStatementService memberStatementService;

    public ApplicationController(MemberService memberService, MemberRepo memberRepo, ClubRepo clubRepo, AttendanceRepo attendanceRepo, MemberStatementRepo memberStatementRepo, MemberStatementService memberStatementService, UserService userService) {
        this.memberService = memberService;
        this.memberRepo = memberRepo;
        this.clubRepo = clubRepo;
        this.attendanceRepo = attendanceRepo;
        this.memberStatementRepo = memberStatementRepo;
        this.memberStatementService = memberStatementService;
        this.userService = userService;
    }

   /* @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        List<Role> roles = Arrays.asList(Role.values());
        model.addAttribute("registrationDTO", new MemberRegisterDTO());
        model.addAttribute("roles", roles);
        return "members/add";
    }*/

    /*@PostMapping("/register")
    public String registerMember(@ModelAttribute("registrationDTO") @Valid MemberRegisterDTO registrationDTO,
                                 BindingResult result) {
        if (result.hasErrors()) {
            return "members/add";
        }
        memberService.registerMember(registrationDTO);
        return "redirect:/login?registered";
    }*/

   /* @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
*/
    @GetMapping("/dashboard")
    public String showDashboard() {
        return "dashboard";
    }


    @GetMapping("/")
    public String home(Model model){

        //For Card Count And Bar Graph Representation
        long totalMembers = memberRepo.count();
        long totalClubs = clubRepo.count();
        long totalAttendance = attendanceRepo.count();


        model.addAttribute("totalMembers", totalMembers);
        model.addAttribute("totalClubs", totalClubs);
        model.addAttribute("totalAttendance", totalAttendance);

        //log on terminal
        logger.info("totalMembers: {}", totalMembers );
        logger.info("totalClubs: {}", totalClubs );
        logger.info("totalAttendance: {}", totalAttendance );



        // Total Sum
        BigDecimal totalExpectedContributionSum = memberStatementService.getTotalExpectedContributionSum();
        BigDecimal totalAmountContributedSum = memberStatementService.getTotalAmountContributedSum();

        model.addAttribute("totalExpectedContributionSum", totalExpectedContributionSum);
        model.addAttribute("totalAmountContributedSum", totalAmountContributedSum);

        logger.info("totalExpectedContributionSum: {}", totalExpectedContributionSum);
        logger.info("totalAmountContributedSum: {}", totalAmountContributedSum);

        //End For Card Count


        //For Pie Chart
        // Total Count
        long totalCount = totalMembers + totalClubs + totalAttendance;

        //Calculate Percentages For Pie Chart Representation
        double membersPercentage = (totalMembers / (double) totalCount) * 100;
        double clubsPercentage = (totalClubs / (double) totalCount) * 100;
        double attendancePercentage = (totalAttendance / (double) totalCount) * 100;

        //Set Data In The Model
        model.addAttribute("membersPercentage", membersPercentage);
        model.addAttribute("clubsPercentage", clubsPercentage);
        model.addAttribute("attendancePercentage", attendancePercentage);

        //End For Pie Chart

        List<Long> logMemberIds = attendanceRepo.findMemberLogIds();

        // Calculate the count for each loguser_id
        Map<Long, Long> logMemberCounts = logMemberIds.stream()
                .collect(Collectors.groupingBy(id -> id, Collectors.counting()));

        // Pass the data to the view
        model.addAttribute("logMemberCounts", logMemberCounts);


       return "dashboard";
        //return "login";
    }

    @Autowired
    private final UserService userService;

    @GetMapping("register")
    public String showRegisterForm(Model model){
        model.addAttribute("user", new User());
        model.addAttribute("roles", Role.values());
        return "register";
    }

    /*@PostMapping("/register")
    public String registerUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "register";
        }
        userService.registerUser(user);

        return "redirect:/login";
    }*/

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("member") @Valid Member member, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "register";
        }
        memberService.registerMember(member);
        //userService.registerUser(user);

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String index(){
        return "login";
    }
}
