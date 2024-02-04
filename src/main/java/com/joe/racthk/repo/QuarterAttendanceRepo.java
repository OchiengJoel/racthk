package com.joe.racthk.repo;

import com.joe.racthk.model.QuarterAttendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuarterAttendanceRepo extends JpaRepository<QuarterAttendance, Long> {
}
