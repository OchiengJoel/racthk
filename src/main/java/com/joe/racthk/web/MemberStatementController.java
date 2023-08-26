package com.joe.racthk.web;

import com.joe.racthk.model.Member;
import com.joe.racthk.model.MemberStatement;
import com.joe.racthk.service.MemberService;
import com.joe.racthk.service.MemberStatementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
public class MemberStatementController {

    private final MemberStatementService memberStatementService;
    private final MemberService memberService;

    @Autowired
    public MemberStatementController(MemberStatementService memberStatementService, MemberService memberService) {
        this.memberStatementService = memberStatementService;
        this.memberService = memberService;
    }

    @GetMapping("/memberStatement")
    public String showMemberStatements(Model model) {
        List<Member> members = memberService.getAllMembers(); // Retrieve all members
        List<MemberStatement> memberStatements = memberStatementService.getAllMemberStatements(members);

        model.addAttribute("members", members);
        model.addAttribute("memberStatements", memberStatements);

        return "statements/list";
    }

    @GetMapping("/{id}")
    @ResponseBody
    public MemberStatement getMemberStatementDetails(@PathVariable Long id) {
        return memberStatementService.getMemberStatementById(id);
    }

    @PostMapping
    @ResponseBody
    public MemberStatement createMemberStatement(@RequestBody MemberStatement memberStatement) {
        return memberStatementService.createMemberStatement(memberStatement);
    }



    @GetMapping("/contributionForm")
    public String showContributionForm(Model model) {
        List<Member> members = memberService.getAllMembers();
        List<String> periods = Arrays.asList("July-2023/June-2024", "Null-Do Not Select");
        List<MemberStatement> contributions = memberStatementService.getAllExpectedContributions();

        model.addAttribute("members", members);
        model.addAttribute("periods", periods);
        model.addAttribute("contributionForm", new MemberStatement()); // Or create a dedicated ContributionForm class
        model.addAttribute("contributions", contributions);
        return "members/contributionForm";
    }

    @PostMapping("/contributionForm")
    public String submitContributionForm(@ModelAttribute("contributionForm") MemberStatement contributionForm) {
        contributionForm.setTransactionType("Expected Contribution");
        memberStatementService.saveMemberStatement(contributionForm);

        return "redirect:/contributionForm";
    }

    @GetMapping("/paymentForm")
    public String showPaymentForm(Model model) {
        List<Member> members = memberService.getAllMembers();
        List<String> paymentPeriods = Arrays.asList("January", "February", "March", "April", "May", "June", "July", "Aug", "Sept", "Oct", "Nov", "Dec");
        List<String> modeOfPayment = Arrays.asList("Cash", "Bank Transfer", "M-Pesa", "Cheque");
        List<MemberStatement> memberPayments = memberStatementService.getAllMemberPaymentsList();

        model.addAttribute("members", members);
        model.addAttribute("paymentPeriods", paymentPeriods);
        model.addAttribute("modeOfPayment", modeOfPayment);
        model.addAttribute("paymentForm", new MemberStatement()); // Or create a dedicated PaymentForm class
        model.addAttribute("memberPayments", memberPayments);

        return "members/paymentForm";
    }

    @PostMapping("/paymentForm")
    public String submitPaymentForm(@ModelAttribute("paymentForm") MemberStatement paymentForm) {
        paymentForm.setTransactionType("Payment");
        memberStatementService.saveMemberStatement(paymentForm);

        return "redirect:/paymentForm";
    }

    @GetMapping("/deleteContribution/{id}")
    public String deleteContribution(@PathVariable Long id) {
        memberStatementService.deleteMemberStatement(id);
        return "redirect:/contributionForm";
    }

    @GetMapping("/deleteMemberPayment/{id}")
    public String deleteMemberPayment(@PathVariable Long id) {
        memberStatementService.deleteMemberStatement(id);
        return "redirect:/paymentForm";
    }

}
