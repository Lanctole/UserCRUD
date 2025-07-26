package lanctole.service;

public interface EmailService {
    void sendEmail(String to, String subject, String text);
    void sendAccountCreatedEmail(String email);
    void sendAccountDeletedEmail(String email);
}
