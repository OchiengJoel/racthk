package com.joe.racthk.service;


import com.joe.racthk.enums.Role;
import com.joe.racthk.model.Member;
import com.joe.racthk.model.User;
import com.joe.racthk.repo.MemberRepo;
import com.joe.racthk.repo.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService  {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final EmailServices emailService;

    private final MemberRepo memberRepo;

    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder, EmailServices emailService, MemberRepo memberRepo) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.memberRepo = memberRepo;
    }


   /* public void registerUser(User user) {
        // Set the user's role
        user.setRole(Role.USER);

        // Encrypt the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save the user to the database
        userRepo.save(user);

        // Send email to the user
        emailService.sendRegistrationEmail(user);
    }*/

    public void registerUser(Member member) {
        // Set the user's role
        member.setRole(Role.USER);

        // Encrypt the password
        member.setPassword(passwordEncoder.encode(member.getPassword()));

        // Save the user to the database
        memberRepo.save(member);

        // Send email to the user
        emailService.sendRegistrationEmail(member);
    }


  /*  @Override
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
    }
*/
    /*@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                // Add user roles here if necessary
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().toString()))
        );
    }*/

}
