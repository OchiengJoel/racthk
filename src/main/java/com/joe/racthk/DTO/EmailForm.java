package com.joe.racthk.DTO;

import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class EmailForm {

    private List<String> recipients = new ArrayList<>();
    private String extraEmail;
    private String subject;
    private String body;
    private MultipartFile attachment;

    public List<String> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<String> recipients) {
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

    public MultipartFile getAttachment() {
        return attachment;
    }

    public void setAttachment(MultipartFile attachment) {
        this.attachment = attachment;
    }
}
