package com.joe.racthk.service;

import com.joe.racthk.model.Member;
import com.joe.racthk.model.MemberStatement;
import com.joe.racthk.repo.MemberStatementRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MemberStatementService {
    @Autowired
    private final MemberStatementRepo memberStatementRepo;

    @Autowired
    public MemberStatementService(MemberStatementRepo memberStatementRepo) {
        this.memberStatementRepo = memberStatementRepo;
    }



   /* public List<MemberStatement> getMemberStatements(){
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
    }*/

   /* public MemberStatement createMemberStatement(MemberStatement memberStatement) {
        // Perform any necessary calculations before saving
        memberStatement.setBalance(memberStatement.getExpectedContribution().subtract(memberStatement.getAmountContributed()));
        return memberStatementRepo.save(memberStatement);
    }*/


    /*public List<MemberStatement> getAllMemberStatements() {
        return memberStatementRepo.findAll();

    }*/

    public List<MemberStatement> getAllMemberStatements(List<Member> members) {
        List<MemberStatement> memberStatements = new ArrayList<>();

        for (Member member : members) {
            MemberStatement statement = memberStatementRepo.findByMember(member);
            if (statement == null) {
                statement = new MemberStatement();
                statement.setMember(member);
                // Set other default values here
            }
            memberStatements.add(statement);
        }

        return memberStatements;
    }



    public MemberStatement getMemberStatementById(Long id) {
        return memberStatementRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Member Statement not found with id: " + id));
    }


    public MemberStatement createMemberStatement(MemberStatement memberStatement) {
        memberStatement.setBalance(memberStatement.getExpectedContribution().subtract(memberStatement.getAmountContributed()));
        return memberStatementRepo.save(memberStatement);
    }



    public MemberStatement saveMemberStatement(MemberStatement memberStatement) {
        return memberStatementRepo.save(memberStatement);
    }

    public void deleteMemberStatement(Long id) {
        memberStatementRepo.deleteById(id);
    }


    public List<MemberStatement> getAllExpectedContributions() {
        return memberStatementRepo.findByTransactionType("Expected Contribution");
    }

    public List<MemberStatement> getAllMemberPaymentsList() {
        return memberStatementRepo.findByTransactionType("Payment");
    }



    public List<MemberStatement> getAllAmountContributed() {
        List<MemberStatement> paymentStatements = memberStatementRepo.findByTransactionType("Payment");
        List<MemberStatement> expectedContributionStatements = memberStatementRepo.findByTransactionType("Expected Contribution");

        // Create a map to store the total expected contributions and total amount contributed for each member
        Map<Long, BigDecimal> totalExpectedContributions = new HashMap<>();
        Map<Long, BigDecimal> totalAmountContributed = new HashMap<>();

        // Calculate the total expected contributions for each member
        for (MemberStatement statement : expectedContributionStatements) {
            Long memberId = statement.getMember().getId();
            BigDecimal contribution = statement.getExpectedContribution();
            totalExpectedContributions.put(memberId, totalExpectedContributions.getOrDefault(memberId, BigDecimal.ZERO).add(contribution));
        }

        // Calculate the total amount contributed for each member
        for (MemberStatement statement : paymentStatements) {
            Long memberId = statement.getMember().getId();
            BigDecimal contribution = statement.getAmountContributed();
            totalAmountContributed.put(memberId, totalAmountContributed.getOrDefault(memberId, BigDecimal.ZERO).add(contribution));
        }

        // Calculate the balance for each member and update the existing statements
        for (MemberStatement statement : paymentStatements) {
            Long memberId = statement.getMember().getId();
            BigDecimal totalExpected = totalExpectedContributions.getOrDefault(memberId, BigDecimal.ZERO);
            BigDecimal totalContributed = totalAmountContributed.getOrDefault(memberId, BigDecimal.ZERO);
            BigDecimal balance = totalExpected.subtract(totalContributed);
            statement.setBalance(balance);
        }

        return paymentStatements;
    }

}
