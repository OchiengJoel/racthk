package com.joe.racthk.model;

import com.joe.racthk.enums.Role;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uname")
    private String username;

    @Column(name = "fname")
    private String fname;

    @Column(name = "lname")
    private String lname;

    @Column(name = "email")
    private String email;

    @Column(name = "contact")
    private String contact;

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

   /* @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Attendance> attendances = new HashSet<>();*/

    @Enumerated(EnumType.STRING)
    private Role role;

    public User() {
    }

    public User(Long id, String username, String fname, String lname, String email, String contact, Date dateofbirth, boolean inducted, Date datejoined, String password, Role role) {
        this.id = id;
        this.username = username;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.contact = contact;
        this.dateofbirth = dateofbirth;
        this.inducted = inducted;
        this.datejoined = datejoined;
        this.password = password;

        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", email='" + email + '\'' +
                ", contact='" + contact + '\'' +
                ", dateofbirth=" + dateofbirth +
                ", inducted=" + inducted +
                ", datejoined=" + datejoined +
                ", password='" + password + '\'' +

                ", role=" + role +
                '}';
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
