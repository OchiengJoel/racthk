package com.joe.racthk.DTO;

import com.joe.racthk.model.Member;

public class MemberPointsDTO {

    private Long memberId;
    private String fname;
    private String lname;
    private int totalPoints;

    public MemberPointsDTO(Long memberId, String fname, String lname, int totalPoints) {
        this.memberId = memberId;
        this.fname = fname;
        this.lname = lname;
        this.totalPoints = totalPoints;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }
}