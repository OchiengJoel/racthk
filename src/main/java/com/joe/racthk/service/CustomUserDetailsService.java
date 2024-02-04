package com.joe.racthk.service;


import com.joe.racthk.model.Member;
import com.joe.racthk.repo.MemberRepo;
import com.joe.racthk.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepo userRepo;

    private final MemberRepo memberRepo;

    @Autowired
    public CustomUserDetailsService(UserRepo userRepo, MemberRepo memberRepo) {
        this.userRepo = userRepo;
        this.memberRepo = memberRepo;
    }

/*    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().toString()))
        );
    }*/

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepo.findByUsername(username);
        if (member == null) {
            throw new UsernameNotFoundException("Member not found");
        }
        return new org.springframework.security.core.userdetails.User(
                member.getUsername(),
                member.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN" + member.getRole().toString()))
        );
    }
}
