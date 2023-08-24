package com.joe.racthk.service;

import com.joe.racthk.model.Member;
import com.joe.racthk.model.MemberStatement;
import com.joe.racthk.repo.MemberStatementRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;

@Service
public class MemberStatementService {
    @Autowired
    private MemberStatementRepo memberStatementRepo;


    public List<MemberStatement> getMemberStatements(Member member){
        return memberStatementRepo.findAll();
    }

    public void calculateBalance(MemberStatement memberStatement){
        BigDecimal balance = memberStatement.getExpectedContribution().subtract(memberStatement.getAmountContributed());
        memberStatement.setBalance(balance);
    }

    public MemberStatement getMemberStatementById(Long id) {
        return memberStatementRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("MemberStatement not found"));
    }

    public List<MemberStatement> getMemberStatementsForMember(Member member) {
        return memberStatementRepo.findByMemberOrderByPeriodAsc(member);
    }

    public MemberStatement createMemberStatement(MemberStatement memberStatement) {
        // Perform any necessary calculations before saving
        memberStatement.setBalance(memberStatement.getExpectedContribution().subtract(memberStatement.getAmountContributed()));
        return memberStatementRepo.save(memberStatement);
    }


}
