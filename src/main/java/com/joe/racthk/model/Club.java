package com.joe.racthk.model;

import org.hibernate.annotations.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "club")
@SQLDelete( sql = "UPDATE club SET deleted = true WHERE id=?")
//@Where(clause = "deleted = false")
@FilterDef(name = "deletedClubFilter", parameters = @ParamDef(name = "isDeleted", type = "boolean"))
@Filter(name = "deletedClubFilter", condition = "delete = :isDeleted")
public class Club extends BaseEntity{

    /*@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;*/

    @Column(name = "name")
    private String name;

    @Column(name = "country")
    private String country;


    @Column(name = "location")
    private String location;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "datefounded")
    private Date datefounded;


    public Club() {
    }


    public Club( String name, String country, String location, Date datefounded) {
        this.name = name;
        this.country = country;
        this.location = location;
        this.datefounded = datefounded;
    }

    @Override
    public String toString() {
        return "Club{" +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", location='" + location + '\'' +
                ", datefounded=" + datefounded +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getDatefounded() {
        return datefounded;
    }

    public void setDatefounded(Date datefounded) {
        this.datefounded = datefounded;
    }
}
