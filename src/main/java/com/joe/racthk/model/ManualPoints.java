package com.joe.racthk.model;

import javax.persistence.*;

@Entity
@Table(name = "manual_points")
public class ManualPoints {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    private Member member;

    @Column
    private Long point;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ManualPoints() {
    }

    public ManualPoints(Long id, Member member, Long point) {
        this.id = id;
        this.member = member;
        this.point = point;
    }

    @Override
    public String toString() {
        return "ManualPoints{" +
                "id=" + id +
                ", member=" + member +
                ", point=" + point +
                '}';
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Long getPoint() {
        return point;
    }

    public void setPoint(Long point) {
        this.point = point;
    }
}
