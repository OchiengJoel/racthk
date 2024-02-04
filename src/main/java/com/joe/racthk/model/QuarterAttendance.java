package com.joe.racthk.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "qattendance")
public class QuarterAttendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /*@ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;*/

    @Column(name = "quarter")
    private String quarter;


    @OneToMany(mappedBy = "quarterAttendance", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClubAttendance> clubAttendances = new ArrayList<>();

    public QuarterAttendance() {

    }

    public void addClubAttendance(ClubAttendance clubAttendance) {
        clubAttendances.add(clubAttendance);
        clubAttendance.setQuarterAttendance(this);
    }

    public List<ClubAttendance> getClubAttendances() {
        return clubAttendances;
    }

    public QuarterAttendance(Long id, String quarter, List<ClubAttendance> clubAttendances) {
        this.id = id;
        this.quarter = quarter;
        this.clubAttendances = clubAttendances;
    }


    @Override
    public String toString() {
        return "QuarterAttendance{" +
                "id=" + id +
                ", quarter='" + quarter + '\'' +
                ", clubAttendances=" + clubAttendances +
                '}';
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuarter() {
        return quarter;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }

    public void setClubAttendances(List<ClubAttendance> clubAttendances) {
        this.clubAttendances = clubAttendances;
    }
}
