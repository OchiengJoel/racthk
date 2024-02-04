package com.joe.racthk.model;

import javax.persistence.*;

@Entity
@Table(name = "club_attendance")
public class ClubAttendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;

    @ManyToOne
    @JoinColumn(name = "quarter_attendance_id")
    private QuarterAttendance quarterAttendance;

    public ClubAttendance() {
    }

    public ClubAttendance(Long id, Club club, QuarterAttendance quarterAttendance) {
        this.id = id;
        this.club = club;
        this.quarterAttendance = quarterAttendance;
    }

    @Override
    public String toString() {
        return "ClubAttendance{" +
                "id=" + id +
                ", club=" + club +
                ", quarterAttendance=" + quarterAttendance +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public QuarterAttendance getQuarterAttendance() {
        return quarterAttendance;
    }

    public void setQuarterAttendance(QuarterAttendance quarterAttendance) {
        this.quarterAttendance = quarterAttendance;
    }
}
