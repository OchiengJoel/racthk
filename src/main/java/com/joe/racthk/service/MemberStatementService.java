package com.joe.racthk.service;

import com.joe.racthk.model.Member;
import com.joe.racthk.model.MemberAccountStatement;
import com.joe.racthk.model.MemberStatement;
import com.joe.racthk.repo.MemberRepo;
import com.joe.racthk.repo.MemberStatementRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class MemberStatementService {
    @Autowired
    private final MemberStatementRepo memberStatementRepo;

    @Autowired
    private final MemberRepo memberRepo;

    @Autowired
    public MemberStatementService(MemberStatementRepo memberStatementRepo, MemberRepo memberRepo) {
        this.memberStatementRepo = memberStatementRepo;
        this.memberRepo = memberRepo;
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

    /*public List<MemberStatement> getAllMemberStatements(List<Member> members) {
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
    }*/



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










    public List<MemberStatement> getMemberStatementsForMember(Member member) {
        return memberStatementRepo.findByMember(member);
    }

    public List<MemberAccountStatement> calculateMemberAccountStatements() {
        List<MemberAccountStatement> accountStatements = new ArrayList<>();

        // Fetch distinct members
        List<Member> members = memberStatementRepo.findDistinctMembers();

        // Calculate and populate member account statements
        for (Member member : members) {
            BigDecimal totalExpectedContribution = memberStatementRepo.sumExpectedContributionByMember(member);
            BigDecimal totalAmountContributed = memberStatementRepo.sumAmountContributedByMember(member);

            MemberAccountStatement accountStatement = new MemberAccountStatement();
            accountStatement.setMember(member);
            accountStatement.setTotalExpectedContribution(totalExpectedContribution);
            accountStatement.setTotalAmountContributed(totalAmountContributed);
            accountStatement.setBalance(totalExpectedContribution.subtract(totalAmountContributed));

            accountStatements.add(accountStatement);
        }

        return accountStatements;
    }

    public BigDecimal getTotalExpectedContributionSum() {
        return memberStatementRepo.sumTotalExpectedContribution();
    }

    public BigDecimal getTotalAmountContributedSum() {
        return memberStatementRepo.sumTotalAmountContributed();
    }



}
