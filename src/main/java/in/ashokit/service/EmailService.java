package in.ashokit.service;

public interface EmailService {

    public boolean sendEmail(String to, String subject, String body);
}
