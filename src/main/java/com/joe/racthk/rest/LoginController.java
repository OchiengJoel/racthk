package com.joe.racthk.rest;

import com.joe.racthk.model.Member;
import com.joe.racthk.repo.MemberRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LoginController {

    private final MemberRepo memberRepo;

    public LoginController(MemberRepo memberRepo) {
        this.memberRepo = memberRepo;
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<String> login(@RequestBody Member loginMember) {
        // Retrieve the user from the database based on the provided username
        Member member = memberRepo.findByUsername(loginMember.getUsername());

        if (member == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }

        // Check if the provided password matches the user's password
        if (!member.getPassword().equals(loginMember.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }

        // User is authenticated, return a success message or JWT token if using JWT for authentication
        return ResponseEntity.ok("Login successful");
    }
}
