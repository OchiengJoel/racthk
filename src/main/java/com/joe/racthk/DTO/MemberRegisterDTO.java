package com.joe.racthk.DTO;

import com.joe.racthk.enums.Role;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class MemberRegisterDTO {


    @NotEmpty(message = "Name is required")
    private String fname;

    @NotEmpty(message = "Name is required")
    private String lname;

    @NotEmpty(message = "Name is required")
    private String username;

    @NotEmpty(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    private Role role;

    public MemberRegisterDTO() {

    }
    public MemberRegisterDTO(String fname, String lname, String username, String email, Role role) {
        this.fname = fname;
        this.lname = lname;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String name) {
        this.fname = name;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String name) {
        this.lname = name;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
