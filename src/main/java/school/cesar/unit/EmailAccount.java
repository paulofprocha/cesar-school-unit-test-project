package school.cesar.unit;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailAccount {

    private String user;
    private String domain;
    private String password;
    private Instant lastPasswordUpdate;

    public void setUser(String user) {
        this.user = user;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLastPasswordUpdate(Instant lastPasswordUpdate) {
        this.lastPasswordUpdate = lastPasswordUpdate;
    }

    public String getUser() {
        return user;
    }

    public String getDomain() {
        return domain;
    }

    public String getPassword() {
        return password;
    }

    public Instant getLastPasswordUpdate() {
        return lastPasswordUpdate;
    }

    public boolean verifyUser(){
        boolean retorno = false;

        Pattern pattern = Pattern.compile(".*([a-zA-Z0-9\\-_.]+).*");
        Matcher matcher = pattern.matcher(user);

        if(user != null && matcher.matches()) {
            retorno = true;
        }
        return retorno;
    }

    public boolean  verifyDomain(){
        boolean retorno = true;

        Pattern pattern = Pattern.compile(".*([a-zA-Z0-9\\.]+).*");
        Matcher matcher = pattern.matcher(domain);

        if(domain != null && matcher.matches() && (domain.charAt(0) == '.' || domain.charAt(domain.length() - 1) == '.' || domain.contains(".."))){
            retorno = false;
        }
        return retorno;
    }

    public boolean verifyPasswordExpiration() {
        boolean retorno = true;

        if(lastPasswordUpdate != null && lastPasswordUpdate.isBefore(lastPasswordUpdate.plus(90, ChronoUnit.DAYS))) {
            retorno = false;
        }
        return retorno;
    }
}