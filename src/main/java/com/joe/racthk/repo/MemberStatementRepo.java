package com.joe.racthk.repo;

import com.joe.racthk.model.Member;
import com.joe.racthk.model.MemberStatement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberStatementRepo extends JpaRepository<MemberStatement, Long> {
    List<MemberStatement> findByMemberOrderByPeriodAsc(Member member);

}
