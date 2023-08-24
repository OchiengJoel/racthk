package com.joe.racthk.web;

import com.joe.racthk.model.Member;
import com.joe.racthk.model.MemberStatement;
import com.joe.racthk.service.MemberService;
import com.joe.racthk.service.MemberStatementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class MemberStatementController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberStatementService memberStatementService;


    @RequestMapping("/{memberId}")
    public String showMemberStatements(@PathVariable Long memberId, Model model){
        Member member = memberService.getByMemberId(memberId);
        List<MemberStatement> statements = memberStatementService.getMemberStatements(member);
        for (MemberStatement statement: statements){
            memberStatementService.calculateBalance(statement);
        }

        model.addAttribute("member", member);
        model.addAttribute("statements", statements);
        return "member_statements/member_statements_list";
    }
}
