package com.joe.racthk.repo;

import com.joe.racthk.model.Member;
import com.joe.racthk.model.MemberStatement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface MemberStatementRepo extends JpaRepository<MemberStatement, Long> {

    List<MemberStatement> findByMember(Member member);

    List<MemberStatement> findByTransactionType(String transactionType);
    //List<MemberStatement> findByMemberOrderByPeriodAsc(Member member);

/*    @Query("SELECT DISTINCT ms.member FROM MemberStatement ms")
    List<Member> findDistinctMembers();

    @Query("SELECT SUM(ms.expectedContribution) FROM MemberStatement ms WHERE ms.member = :member")
    BigDecimal sumExpectedContributionByMember(Member member);

    @Query("SELECT SUM(ms.amountContributed) FROM MemberStatement ms WHERE ms.member = :member")
    BigDecimal sumAmountContributedByMember(Member member);*/



    @Query("SELECT DISTINCT ms.member FROM MemberStatement ms")
    List<Member> findDistinctMembers();

    @Query("SELECT COALESCE(SUM(ms.expectedContribution), 0) FROM MemberStatement ms WHERE ms.member = :member")
    BigDecimal sumExpectedContributionByMember(@Param("member") Member member);

    @Query("SELECT COALESCE(SUM(ms.amountContributed), 0) FROM MemberStatement ms WHERE ms.member = :member")
    BigDecimal sumAmountContributedByMember(@Param("member") Member member);



}



