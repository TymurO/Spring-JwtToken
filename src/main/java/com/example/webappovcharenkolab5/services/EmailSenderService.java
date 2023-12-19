package com.example.webappovcharenkolab5.services;

import com.example.webappovcharenkolab5.models.StudentDopInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String toEmail,
                          String subject,
                          String body) throws MessagingException, IOException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);

        message.setFrom("0966282541zxzxzx@gmail.com");
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);
        Path path = Paths.get("/info.json");
        byte[] contentInfo = Files.readAllBytes(path);
        path = Paths.get("/students.json");
        byte[] contentStudents = Files.readAllBytes(path);
        message.addAttachment("info.json", new ByteArrayResource(contentInfo));
        message.addAttachment("students.json", new ByteArrayResource(contentStudents));

        mailSender.send(mimeMessage);
    }
}
