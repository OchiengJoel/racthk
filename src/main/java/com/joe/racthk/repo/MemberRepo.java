package com.joe.racthk.repo;

import com.joe.racthk.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepo extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    Member findByUsername(String username);
}
