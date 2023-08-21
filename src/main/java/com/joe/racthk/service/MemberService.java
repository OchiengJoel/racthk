package com.joe.racthk.service;

import com.joe.racthk.DTO.MemberPointsDTO;
import com.joe.racthk.DTO.MemberRegisterDTO;
import com.joe.racthk.enums.Role;
import com.joe.racthk.model.Attendance;
import com.joe.racthk.model.Member;
import com.joe.racthk.repo.MemberRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class MemberService {

    @Autowired
    private final JavaMailSender javaMailSender;

    private final PasswordEncoder passwordEncoder;
    private final EmailServices emailService;

    private final MemberRepo memberRepo;

    public MemberService(MemberRepo memberRepo, JavaMailSender javaMailSender, PasswordEncoder passwordEncoder, EmailServices emailService) {
        this.memberRepo = memberRepo;
        this.javaMailSender = javaMailSender;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    public List<Member> getAllMembers(){
        return memberRepo.findAll();
    }

    public void saveMembers(Member member){
        memberRepo.save(member);
        // Send Email to the member
        //sendRegistrationEmail(member, password);
    }
    public void updateMember(Member member){
        memberRepo.save(member);
    }

    public Member getByMemberId(Long id) {
        return memberRepo.findById(id).orElse(null);
    }

    public void deleteByMemberId(Long id){
         memberRepo.deleteById(id);
    }


    /*public void registerMember(MemberRegisterDTO registrationDTO) {
        // Check if email already exists
        Optional<Member> existingMember = memberRepo.findByEmail(registrationDTO.getEmail());
        if (existingMember.isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        // Generate a random password
        String password = generateRandomPassword();

        // Create a new member
        Member member = new Member();
        member.setFname(registrationDTO.getFname());
        member.setLname(registrationDTO.getLname());
        member.setUsername(registrationDTO.getUsername());
        member.setEmail(registrationDTO.getEmail());
        member.setPassword(password);
        member.setRole(registrationDTO.getRole());

        // Save the member
        memberRepo.save(member);

        // Send Email to the member
        sendRegistrationEmail(member, password);
    }*/

    private String generateRandomPassword() {
        String allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
        int passwordLength = 10;

        Random random = new Random();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < passwordLength; i++) {
            int index = random.nextInt(allowedChars.length());
            password.append(allowedChars.charAt(index));
        }

        return password.toString();
    }

    private void sendRegistrationEmail(Member member, String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(member.getEmail());
        message.setSubject("Registration Confirmation");
        message.setText("Username: " + member.getEmail() + "\nPassword: " + password);

        javaMailSender.send(message);
    }


    //@Override
   /* public void onApplicationEvent(ApplicationReadyEvent event) {
        // Create and save the default Admin member when the application starts
        createDefaultAdminMember();
    }

    private void createDefaultAdminMember() {
        Optional<Member> existingAdmin = memberRepo.findByEmail("admin@admin.com");
        if (!existingAdmin.isPresent()) {
            Member admin = new Member();
            admin.setFname("John");
            admin.setLname("Doe");
            admin.setEmail("admin@admin.com");
            admin.setPassword(passwordEncoder.encode("Demo123")); // Set your desired default password
            admin.setRole(MemberRole.ADMIN); // Set the default role as Admin

            memberRepo.save(admin);
        }
    }*/


    public List<MemberPointsDTO> getMembersWithTotalPoints() {
        List<Member> members = memberRepo.findAll();
        List<MemberPointsDTO> memberPointsList = new ArrayList<>();

        for (Member member : members) {
            int totalPoints = calculateTotalPoints(member);
            MemberPointsDTO memberPointsDTO = new MemberPointsDTO(member.getId(), member.getFname(), member.getLname(), totalPoints);
            memberPointsList.add(memberPointsDTO);
        }

            // Sort the memberPointsList based on totalPoints in ascending order
        //Collections.sort(memberPointsList, Comparator.comparingInt(MemberPointsDTO::getTotalPoints));

        // Sort the memberPointsList based on totalPoints in descending order
        Collections.sort(memberPointsList, Comparator.comparingInt(MemberPointsDTO::getTotalPoints).reversed());

        return memberPointsList;
    }

    private int calculateTotalPoints(Member member) {
        int totalPoints = 0;
        for (Attendance attendance : member.getAttendances()) {
            totalPoints += attendance.getPoints();
        }
        return totalPoints;
    }


    public List<String> getAllMemberEmails() {
        List<Member> members = memberRepo.findAll();
        return members.stream().map(Member::getEmail).collect(Collectors.toList());
    }


    public void registerMember(Member member) {
        // Set the user's role
        member.setRole(Role.USER);

        // Encrypt the password
        member.setPassword(passwordEncoder.encode(member.getPassword()));

        // Save the user to the database
        memberRepo.save(member);

        // Send email to the user
        emailService.sendRegistrationEmail(member);
    }
}
