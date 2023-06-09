package org.example.mail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import java.util.Properties;

/**
 * The class introduce sending a mail to user.
 */
public class MailSender {

    protected String username = System.getenv("EMAIL");
    protected String password = System.getenv("EMAIL_PASSWORD");;


    private String sender;
    private String recipient;
    private String subject;
    private String content;


//    public MailSender(String username, String password){
//        this.username = username;
//        this.password = password;
//
//    }

    /**
     * Setting a sender of mail.
     * @param sender - sender's mail
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * Setting a recipient of mail.
     * @param recipient - recipient's mail
     */
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    /**
     * Setting a subject of mail.
     * @param subject - mail's subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Setting a content of mial.
     * @param content - content of mail.
     */
    public void setContent(String content) {
        this.content = content;
    }


    /**
     * The method introduce a sending message to recipient.
     */
    public void send() {
        System.out.println(username + "to nazwa");
        System.out.println(password + "a to nie");
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.host", "smtp.poczta.onet.pl");
        properties.put("mail.smtp.host", "smtp.poczta.onet.pl");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.socketFactory.fallback", "false");

        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            System.out.println(password);
            System.out.println(username);
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sender));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(subject);
            message.setText(content);
//            BodyPart messageBodyPart = new MimeBodyPart();
//            messageBodyPart.setText(content);

//            Multipart multipart = new MimeMultipart();
//            multipart.addBodyPart(messageBodyPart);
//            message.setContent(multipart);
            Transport.send(message);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

