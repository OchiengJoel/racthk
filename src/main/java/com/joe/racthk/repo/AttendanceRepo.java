package com.joe.racthk.repo;

import com.joe.racthk.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceRepo extends JpaRepository<Attendance, Long> {

    @Query(value = "SELECT at.member_id from attendance at", nativeQuery = true)
    List<Long> findMemberLogIds();

    @Query("SELECT a FROM Attendance a WHERE a.member.id = :memberId AND a.club.id = :clubId AND a.attendancetype = :attendancetype")
    List<Attendance> findByMemberIdAndClubIdAndAttendancetype(@Param("memberId") Long memberId, @Param("clubId") Long clubId, @Param("attendancetype") String attendancetype);

    @Query("SELECT a FROM Attendance a WHERE a.member.id = :memberId AND a.club.id = :clubId")
    List<Attendance> findByMemberIdAndClubId(@Param("memberId") Long memberId, @Param("clubId") Long clubId);

    @Query("SELECT a FROM Attendance a WHERE a.member.id = :memberId AND a.attendancetype = :attendancetype")
    List<Attendance> findByMemberIdAndAttendancetype(@Param("memberId") Long memberId, @Param("attendancetype") String attendancetype);

    @Query("SELECT a FROM Attendance a WHERE a.club.id = :clubId AND a.attendancetype = :attendancetype")
    List<Attendance> findByClubIdAndAttendancetype(@Param("clubId") Long clubId, @Param("attendancetype") String attendancetype);


    List<Attendance> findByMemberId(Long memberId);

    List<Attendance> findByClubId(Long clubId);

    List<Attendance> findByAttendancetype(String attendancetype);
}
