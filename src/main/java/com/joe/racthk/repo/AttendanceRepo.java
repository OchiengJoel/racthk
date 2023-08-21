package com.joe.racthk.repo;

import com.joe.racthk.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceRepo extends JpaRepository<Attendance, Long> {

    @Query(value = "SELECT at.member_id from attendance at", nativeQuery = true)
    List<Long> findMemberLogIds();
}
