package ru.javaops.masterjava.service.mail;

import com.typesafe.config.Config;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import javax.mail.Authenticator;

/**
 * MailConfig.
 *
 * @author Stanislav (376825@gmail.com)
 * @since 20.06.2018
 */
public class MailConfig {
    private String host;
    private int port;
    private String username;
    private Authenticator auth;
    private boolean useSSL;
    private boolean useTLS;
    private boolean debug;
    private String fromName;

    public MailConfig(Config config) {
        this.host = config.getString("host");
        this.port = config.getInt("port");
        this.username = config.getString("username");
        this.auth = new DefaultAuthenticator(username, config.getString("auth"));
        this.useSSL = config.getBoolean("useSSL");
        this.useTLS = config.getBoolean("useTLS");
        this.debug = config.getBoolean("debug");
        this.fromName = config.getString("fromName");
    }

    public <T extends Email> T prepareEmail(T email) throws EmailException {
        email.setFrom(username, fromName);
        email.setHostName(host);
        email.setSSLOnConnect(useSSL);

        if (useSSL) {
            email.setSslSmtpPort(String.valueOf(port));
        } else {
            email.setSmtpPort(port);
        }

        email.setStartTLSEnabled(useTLS);
        email.setDebug(debug);
        email.setAuthenticator(auth);
        email.setCharset("UTF_8");

        return email;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "MailConfig{" +
                "host='" + host + '\'' +
                ", port=" + port +
                ", useSSL=" + useSSL +
                ", useTLS=" + useTLS +
                ", debug=" + debug +
                ", username='" + username + '\'' +
                ", fromName='" + fromName + '\'' +
                '}';
    }
}