package lanctole.service;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetupTest;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EmailServiceImplTest {

    private GreenMail greenMail;

    @Autowired
    private EmailServiceImpl emailService;

    @BeforeEach
    void setUp() {
        greenMail = new GreenMail(ServerSetupTest.SMTP);
        greenMail.start();

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("localhost");
        mailSender.setPort(3025);
        mailSender.setUsername("");
        mailSender.setPassword("");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "false");
        props.put("mail.smtp.starttls.enable", "false");

        emailService = new EmailServiceImpl(mailSender);
    }

    @AfterEach
    void tearDown() {
        greenMail.stop();
    }

    @Test
    void sendEmail_shouldDeliverMessage() throws Exception {
        emailService.sendEmail("test@localhost", "Test Subject", "Test body");

        greenMail.waitForIncomingEmail(5000, 1);
        MimeMessage message = greenMail.getReceivedMessages()[0];
        assertEquals("Test Subject", message.getSubject());
        assertEquals("Test body", message.getContent().toString().trim());
    }

    @Test
    void sendAccountCreatedEmail_shouldSendWelcomeMessage() throws Exception {
        emailService.sendAccountCreatedEmail("test@localhost");

        assertTrue(greenMail.waitForIncomingEmail(5000, 1));
        var message = greenMail.getReceivedMessages()[0];
        assertEquals("Создание", message.getSubject());
        assertTrue(message.getContent().toString().contains("Здравствуйте! Ваш аккаунт на сайте ваш сайт был успешно создан."));
    }

    @Test
    void sendAccountDeletedEmail_shouldSendDeletionMessage() throws Exception {
        emailService.sendAccountDeletedEmail("test@localhost");

        assertTrue(greenMail.waitForIncomingEmail(5000, 1));
        MimeMessage message = greenMail.getReceivedMessages()[0];
        assertEquals("Удаление", message.getSubject());
        assertTrue(message.getContent().toString().contains("Здравствуйте! Ваш аккаунт был удалён."));
    }
}