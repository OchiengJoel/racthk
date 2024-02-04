package com.joe.racthk.service;

import com.joe.racthk.model.Email;
import com.joe.racthk.repo.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {


    private final EmailRepository emailRepository;

    @Autowired
    public EmailService(EmailRepository emailRepository) {
        this.emailRepository = emailRepository;
    }

    public Email save(Email email) {
        return emailRepository.save(email);
    }

    public List<Email> getAllSentEmails() {
        return emailRepository.findAll();
    }

   /* public List<Email> getAllSentEmails() {
        return emailRepository.findByStatus("SENT");
    }*/

   /* public List<Email> getAllBouncedEmails() {
        return emailRepository.findByStatus("BOUNCED");
    }*/


}
