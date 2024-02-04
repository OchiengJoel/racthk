package com.joe.racthk.web;

import com.joe.racthk.DTO.EmailForm;
import com.joe.racthk.model.Email;
import com.joe.racthk.repo.EmailRepository;
import com.joe.racthk.service.EmailService;
import com.joe.racthk.service.MemberService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.List;

@Controller
public class EmailController {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private EmailService emailService;

    private final EmailRepository emailRepository;

    @Autowired
    private MemberService memberService;

    public EmailController(JavaMailSender javaMailSender, EmailService emailService, EmailRepository emailRepository, MemberService memberService) {
        this.javaMailSender = javaMailSender;
        this.emailService = emailService;
        this.emailRepository = emailRepository;
        this.memberService = memberService;
    }

    @GetMapping("/email")
    public String showEmailForm(Model model) {
        List<String> memberEmails = memberService.getAllMemberEmails();
        model.addAttribute("memberEmails", memberEmails);
        model.addAttribute("emailForm", new EmailForm());

        // Fetch the list of sent emails from the database
        List<Email> sentEmails = emailService.getAllSentEmails();
        model.addAttribute("sentEmails", sentEmails);
        return "email-form";
    }

    @PostMapping("/email/send")
    public String sendEmail(EmailForm emailForm, RedirectAttributes redirectAttributes) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            // Set recipient(s)
            List<String> recipients = emailForm.getRecipients();
            if (!StringUtils.isEmpty(emailForm.getExtraEmail())) {
                recipients.add(emailForm.getExtraEmail());
            }
            helper.setTo(recipients.toArray(new String[0]));

            // Set subject and body
            helper.setSubject(emailForm.getSubject());
            helper.setText(emailForm.getBody(), true);

            // Attach file if provided
            MultipartFile attachment = emailForm.getAttachment();
            if (attachment != null && !attachment.isEmpty()) {
                String filename = attachment.getOriginalFilename();
                if (StringUtils.isEmpty(filename)) {
                    filename = "attachment";
                }
                helper.addAttachment(filename, new ByteArrayResource(attachment.getBytes()));
            }

            // Save the email to the database (if required)
            // emailService.save(email);
            // Save the email to the database (if required)
            Email email = new Email();
            email.setRecipients(String.join(",", emailForm.getRecipients()));
            email.setExtraEmail(emailForm.getExtraEmail());
            email.setSubject(emailForm.getSubject());
            email.setBody(emailForm.getBody());
            email.setAttachment(attachment.getBytes());
            emailService.save(email);

            // Send email
            javaMailSender.send(message);

            redirectAttributes.addFlashAttribute("successMessage", "Email sent successfully.");
        } catch (MessagingException | IOException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to send email: " + e.getMessage());
        }

        return "redirect:/email";
    }

}
