package lanctole.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lanctole.exception.EmailSendingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendEmail(String to, String subject, String text) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, false);
            mailSender.send(message);
            log.info("Email sent to {}", to);
        } catch (MessagingException e) {
            log.error("Failed to send email to {}", to, e);
            throw new EmailSendingException("Failed to send email to " + to, e);
        }
    }

    @Override
    public void sendAccountCreatedEmail(String email) {
        String subject = "Создание";
        String text = "Здравствуйте! Ваш аккаунт на сайте ваш сайт был успешно создан.";
        sendEmail(email, subject, text);
    }

    @Override
    public void sendAccountDeletedEmail(String email) {
        String subject = "Удаление";
        String text = "Здравствуйте! Ваш аккаунт был удалён.";
        sendEmail(email, subject, text);
    }

}
