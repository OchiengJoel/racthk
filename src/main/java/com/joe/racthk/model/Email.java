package com.joe.racthk.model;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "email")
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "recipients")
    private String recipients;

    @Column(name = "extra_email")
    private String extraEmail;

    @Column(name = "subject")
    private String subject;

    @Column(name = "body")
    private String body;

    @Lob
    @Column(name = "attachment")
    private byte[] attachment;

    public Email() {
    }

    public Email(Long id, String recipients, String extraEmail, String subject, String body, byte[] attachment) {
        this.id = id;
        this.recipients = recipients;
        this.extraEmail = extraEmail;
        this.subject = subject;
        this.body = body;
        this.attachment = attachment;
    }

    @Override
    public String toString() {
        return "Email{" +
                "id=" + id +
                ", recipients='" + recipients + '\'' +
                ", extraEmail='" + extraEmail + '\'' +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                ", attachment=" + Arrays.toString(attachment) +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRecipients() {
        return recipients;
    }

    public void setRecipients(String recipients) {
        this.recipients = recipients;
    }

    public String getExtraEmail() {
        return extraEmail;
    }

    public void setExtraEmail(String extraEmail) {
        this.extraEmail = extraEmail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public byte[] getAttachment() {
        return attachment;
    }

    public void setAttachment(byte[] attachment) {
        this.attachment = attachment;
    }
}
