package ru.javaops.masterjava.service.mail;

import lombok.extern.slf4j.Slf4j;
import java.util.List;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.EmailException;

@Slf4j
public class MailSender {
    private final MailConfig mailConfig;

    public MailSender(MailConfig mailConfig) {
        this.mailConfig = mailConfig;
    }

    public void sendMail(List<Addressee> to, List<Addressee> cc, String subject, String body) {
        log.info("Send mail to \'" + to + "\' cc \'" + cc + "\' subject \'" + subject + (log.isDebugEnabled() ? "\nbody=" + body : ""));

        try {
            HtmlEmail email = mailConfig.prepareEmail(new HtmlEmail());
            email.setSubject(subject);
            email.setHtmlMsg(body);

            for (Addressee addressee : to) {
                email.addTo(addressee.getEmail(), addressee.getName());
            }
            for (Addressee addressee : cc) {
                email.addCc(addressee.getEmail(), addressee.getName());
            }

            email.setHeaders(ImmutableMap.of("List-Unsubscribe", "<mailto:".concat(mailConfig.getUsername())
                    .concat("?subject=Unsubscribe&body=Unsubscribe>")));

            email.send();
        } catch (EmailException e) {
            log.error(e.getMessage(), e);
        }
    }
}