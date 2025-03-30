package tools;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailSender {

    public static void sendEmail(String to, String subject, String messageText) {
        final String username = "ntanh.dhti15a6hn@sv.uneti.edu.vn";  // Email gửi
        final String password = "dmby yexi xvqx ngld";               // Mật khẩu ứng dụng

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username, "MixiShop", "UTF-8"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(MimeUtility.encodeText(subject, "UTF-8", "B"));  // ✅ Đảm bảo UTF-8

            // ✅ Định dạng email với UTF-8
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(messageText, "text/html; charset=UTF-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);
            message.setContent(multipart);

            // Gửi email
            Transport.send(message);
            System.out.println("✅ Email đã được gửi thành công!");

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("❌ Gửi email thất bại! Lỗi: " + e.getMessage());
        }
    }
}
