package com.joe.racthk.service;

import com.joe.racthk.model.Member;
import com.joe.racthk.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServices {
    private final JavaMailSender mailSender;

    @Autowired
    public EmailServices(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

   /* public void sendRegistrationEmail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Registration Successful");
        message.setText("Dear " + user.getUsername() + ",\n\n" + "Your registration was successful. Welcome to our application!" + ",\n\n" + "Your Username is: " + user.getUsername() + "Your Password is: " + user.getPassword());

        mailSender.send(message);
    }*/

    public void sendRegistrationEmail(Member member) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(member.getEmail());
        message.setSubject("Registration Successful");
        message.setText("Dear " + member.getUsername() + ",\n\n" + "Your registration was successful. Welcome to our application!" + ",\n\n" + "Your Username is: " + member.getUsername() + "Your Password is: " + member.getPassword());

        mailSender.send(message);
    }
}
