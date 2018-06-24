package ru.javaops.masterjava.service.mail;

import ru.javaops.masterjava.config.Configs;
import javax.jws.WebService;
import java.util.List;

@WebService(endpointInterface = "ru.javaops.masterjava.service.mail.MailService")
public class MailServiceImpl implements MailService {
    private final MailSender mailSender = new MailSender(
            new MailConfig(Configs.getConfig("mail.conf", "mail")));

    public void sendMail(List<Addressee> to, List<Addressee> cc, String subject, String body) {
        mailSender.sendMail(to, cc, subject, body);
    }
}