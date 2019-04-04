package school.cesar.unit;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailClient {

    Collection<EmailAccount> accounts;
    EmailService emailService;

    public EmailClient(){
        this.accounts = new ArrayList<EmailAccount>();
    }

    public void setEmailService(EmailService emailService){
        this.emailService = emailService;
    }

    public boolean isValidAddress(String emailAddress){
        boolean retorno = false;

        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                                          "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = pattern.matcher(emailAddress);

        if(emailAddress != null && matcher.matches()) {
            retorno = true;
        }

        return retorno;
    }

    public boolean isValidEmail(Email email){

        boolean retorno = true;
        if(email != null && email.getCreationDate() != null){

            ArrayList<String> allEmails = new ArrayList<>();
            allEmails.add(email.getFrom());
            allEmails.addAll(email.getTo());
            allEmails.addAll(email.getCc());
            allEmails.addAll(email.getBcc());

            for(String emailAddress : allEmails) {
                if(!isValidAddress(emailAddress)){
                    retorno = false;
                    break;
                }else{
                    retorno = true;
                }
            }
            return retorno;
        }
        return false;
    }

    public Collection<Email> emailList(EmailAccount account){
        if((account.getPassword().length() <= 6 || account.verifyPasswordExpiration())){
            throw new RuntimeException("Erro na senha");
        }
        return emailService.emailList(account);
    }

    public void sendEmail(Email email){
        if(isValidEmail(email)){
            this.emailService.sendEmail(email);
        }
        else {
            throw new RuntimeException("Erro no envio de email");
        }
    }

    public boolean createAccount(EmailAccount account){
        boolean retorno = false;

        if(isValidAddress(account.getUser() + "@" + account.getDomain())){
            if(account.getPassword().length() > 6){
                account.setLastPasswordUpdate(Instant.now());
                this.accounts.add(account);
                retorno = true;
            }
        }
        return retorno;
    }
}
