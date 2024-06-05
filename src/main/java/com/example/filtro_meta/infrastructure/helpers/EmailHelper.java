package com.example.filtro_meta.infrastructure.helpers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class EmailHelper {
    @Autowired
    private final JavaMailSender mailSender;

    private String readHTMLTemplate(String description, String title, String date){
        final Path path = Paths.get("src/main/resources/emails/email_template.html");

        try (var lines = Files.lines(path)){
            var html = lines.collect(Collectors.joining());

            return html.replace("{description}", description).replace("{title}", title).replace("{date}", date);
        } catch (IOException e) {
            System.out.println("File could not be read "+e.getMessage());
            throw new RuntimeException();
        }
    }

    public void sendEmail(String destiny, String description, String title, LocalDateTime date){
        MimeMessage message = mailSender.createMimeMessage();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String dateAppointment = date.format(formatter);
        String htmlContent = this.readHTMLTemplate(description, title, dateAppointment);

        try {
            message.setFrom(new InternetAddress("anav.usuga@gmail.com"));
            message.setSubject("Asunto: Creación de encuesta exitosa");

            message.setRecipients(MimeMessage.RecipientType.TO,destiny);
            message.setContent(htmlContent, MediaType.TEXT_HTML_VALUE);
         
            mailSender.send(message);
            System.out.println("Email sent");

        } catch (Exception e) {
            System.out.println("ERROR. Could not send the email " + e.getMessage());

        }
    }
}
