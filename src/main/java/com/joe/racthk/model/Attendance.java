package com.joe.racthk.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "attendance")
public class Attendance extends BaseEntity {

    @ManyToOne
    private Member member;

    /*@ManyToOne
    private User user;*/

    @ManyToOne
    private Club club;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fromdate")
    private Date fromdate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "todate")
    private Date todate;

    @Column(name = "points")
    private int points = 1;

    @Column(name = "attendancetype")
    private String attendancetype;



    public Attendance() {
    }

    public Attendance(Member member, Club club, Date fromdate, Date todate, String attendancetype) {
        this.member = member;
        this.club = club;
        this.fromdate = fromdate;
        this.todate = todate;
        this.attendancetype = attendancetype;

    }


    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public Date getFromdate() {
        return fromdate;
    }

    public void setFromdate(Date fromdate) {
        this.fromdate = fromdate;
    }

    public Date getTodate() {
        return todate;
    }

    public void setTodate(Date todate) {
        this.todate = todate;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getAttendancetype() {
        return attendancetype;
    }

    public void setAttendancetype(String attendancetype) {
        this.attendancetype = attendancetype;
    }
}
