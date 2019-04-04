package school.cesar.unit.utils;

import school.cesar.unit.EmailAccount;

import java.time.Instant;

public abstract class EmailAccountBuilder {

    private String user;
    private String domain;
    private String password;
    private Instant lastPasswordUpdate;


    public EmailAccountBuilder setUser(String user) {
        this.user = user;
        return this;
    }

    public EmailAccountBuilder setDomain(String domain) {
        this.domain = domain;
        return this;
    }

    public EmailAccountBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public EmailAccountBuilder setLastPasswordUpdate(Instant lastPasswordUpdate) {
        this.lastPasswordUpdate = lastPasswordUpdate;
        return this;
    }

    public EmailAccount build(){
        EmailAccount emailAccount = new EmailAccount();
        emailAccount.setUser(this.user);
        emailAccount.setDomain(this.domain);
        emailAccount.setPassword(this.password);
        emailAccount.setLastPasswordUpdate(this.lastPasswordUpdate);
        return emailAccount;
    }

    public abstract boolean verifyPasswordExpiration();
}
