package com.vistula.aplikacja.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

@Service
public class GmailService {

    private final Logger log = LoggerFactory.getLogger(GmailService.class);

    @Value("${mail.username}")
    private String username;
    @Value("${mail.port}")
    private String port;
    @Value("${mail.hostname}")
    private String hostname;
    @Value("${mail.password}")
    private String password;
    @Value("${mail.aplikacjaEmailAdress}")
    private String aplikacjaEmailAdress;

    public void sendEmailWithAttachedFiles(String host, String port, String username, String password,
                                           String toAddress, String subject, String message, String[] attachedFiles)
        throws MessagingException {
        // SMTP właściwości
        Properties emailProperties = new Properties();
        emailProperties.put("mail.smtp.host", host);
        emailProperties.put("mail.smtp.port", port);
        emailProperties.put("mail.smtp.auth", "true");
        emailProperties.put("mail.smtp.starttls.enable", "true");
        emailProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        emailProperties.put("mail.user", username);
        emailProperties.put("mail.password", password);

        Authenticator authenticator = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        };
        Session session = Session.getInstance(emailProperties, authenticator);

        Message mimeMessage = new MimeMessage(session);

        mimeMessage.setFrom(new InternetAddress(username));
        InternetAddress[] toAddresses = {new InternetAddress(toAddress)};
        mimeMessage.setRecipients(Message.RecipientType.TO, toAddresses);
        mimeMessage.setSubject(subject);
        mimeMessage.setSentDate(new Date());

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(message, "text/html; charset=UTF-8");

        Multipart multipartMessage = new MimeMultipart();
        multipartMessage.addBodyPart(messageBodyPart);

        if (attachedFiles != null && attachedFiles.length > 0) {
            for (String filePath : attachedFiles) {
                MimeBodyPart attachPart = new MimeBodyPart();
                try {
                    attachPart.attachFile(filePath);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                multipartMessage.addBodyPart(attachPart);
            }
        }
        mimeMessage.setContent(multipartMessage);
        Transport.send(mimeMessage);
    }

    /**
     * Wyślij email z gmail wraz z załącznikami.
     */
    public void sendGmailWithAttachments() throws IOException {
        String mailTo = "bulakowska.k@gmail.com";
        String subject = "Aplikacja dietetyczna";

        String message = getBufferedReaderStringMessage("src/main/resources/templates/mail/testMailTemplate.html");

        String[] attachFiles = new String[1];
        attachFiles[0] = ""; //todo wstaw ścieżke do pliku tu rozwiązanie dla 1 załącznika

        try {
            sendEmailWithAttachedFiles(hostname, port, aplikacjaEmailAdress, password, aplikacjaEmailAdress,
                subject, message, attachFiles);
            log.info("Twój email został wysłany!");
        } catch (Exception ex) {
            log.error("Twój email został niewysłany!");
            ex.printStackTrace();
        }
    }

    /**
     * Wysyłka maila z gmaila - dla formularza kontaktowego.
     */
    public void sendEmailWithContactFromUser(String imieNazwisko, String temat, String email, String wiadomosc) throws IOException {

        String message = getBufferedReaderStringMessage("src/main/resources/templates/mail/emailKontaktowyTemplate.html");

        String content = message.toString();
        content = content.replace("#imieNazwisko", imieNazwisko);
        content = content.replace("#temat", temat);
        content = content.replace("#email", email);
        content = content.replace("#wiadomosc", wiadomosc);

        String messageSubject = imieNazwisko + " : " + temat;

        try {
            sendEmailWithAttachedFiles(hostname, port, username, password, aplikacjaEmailAdress,
                messageSubject, content, null);
            log.info("Twój email został wysłany.");
        } catch (Exception ex) {
            log.error("Twój email został niewysłany!");
            ex.printStackTrace();
        }
    }

    private String getBufferedReaderStringMessage(String filePath) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        FileReader fileReader = new FileReader(filePath);

        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String str;
        while ((str = bufferedReader.readLine()) != null) {
            contentBuilder.append(str);
        }
        bufferedReader.close();

        String message = contentBuilder.toString();
        log.info(message);
        return message;
    }
}
