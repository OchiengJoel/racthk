package com.joe.racthk.rest;

import com.joe.racthk.model.Member;
import com.joe.racthk.repo.MemberRepo;
import com.joe.racthk.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberControllerA {

    @Autowired
    private MemberRepo memberRepo;

    @Autowired
    private MemberService memberService;


    @GetMapping("/{id}")
    public Member getMemberById(@PathVariable Long id) {
        return memberRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + id));
    }

    @PostMapping
    public Member createMember(@RequestBody Member member) {
        return memberRepo.save(member);
    }

    @GetMapping("/")
    public List<Member> getAllMembers(){
        return memberService.getAllMembers();
    }

    @PutMapping("/{id}")
    public Member updateMember(@PathVariable Long id, @RequestBody Member memberDetails) {
        Member member = memberRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + id));

        member.setFname(memberDetails.getFname());
        member.setLname(memberDetails.getLname());
        member.setEmail(memberDetails.getEmail());
        member.setDateofbirth(memberDetails.getDateofbirth());
        //member.setStatus(memberDetails.getStatus());

        return memberRepo.save(member);
    }

    @DeleteMapping("/{id}")
    public void deleteMember(@PathVariable Long id) {
        memberRepo.deleteById(id);
    }
}
