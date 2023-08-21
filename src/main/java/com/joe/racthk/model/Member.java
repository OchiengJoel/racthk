package com.joe.racthk.model;

import com.joe.racthk.enums.Role;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fname")
    private String fname;

    @Column(name = "lname")
    private String lname;

    @Column(name = "uname")
    private String username;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "contact")
    private String contact;

    @Column(name = "classification")
    private String classification;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "dateofbirth")
    private Date dateofbirth;

    @Column(name = "inducted")
    private boolean inducted;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "datejoined")
    private Date datejoined;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Attendance> attendances = new HashSet<>();

    public Member() {
    }

    public Member(Long id, String fname, String lname, String username, String email, String contact, String classification, Date dateofbirth, boolean inducted, Date datejoined, Set<Attendance> attendances) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.username = username;
        this.email = email;
        this.contact = contact;
        this.classification= classification;
        this.dateofbirth = dateofbirth;
        this.inducted = inducted;
        this.datejoined = datejoined;
        this.attendances = attendances;
    }

    public Member(String password, Role role) {
        this.password = password;
        this.role = role;
    }



    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", contact='" + contact + '\'' +
                ", classification='" + classification + '\'' +
                ", dateofbirth=" + dateofbirth +
                ", inducted=" + inducted +
                ", datejoined=" + datejoined +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", attendances=" + attendances +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public Date getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(Date dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public boolean isInducted() {
        return inducted;
    }

    public void setInducted(boolean inducted) {
        this.inducted = inducted;
    }

    public Date getDatejoined() {
        return datejoined;
    }

    public void setDatejoined(Date datejoined) {
        this.datejoined = datejoined;
    }

    public Set<Attendance> getAttendances() {
        return attendances;
    }

    public void setAttendances(Set<Attendance> attendances) {
        this.attendances = attendances;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
