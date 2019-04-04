package school.cesar.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import school.cesar.unit.utils.EmailAccountBuilder;
import school.cesar.unit.utils.EmailBuilder;
import school.cesar.unit.utils.MockitoExtesion;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.time.temporal.ChronoUnit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtesion.class)
public class EmailClientTest {

    @Mock EmailService service;
    @InjectMocks EmailClient client;
    EmailBuilder emailBuilder;
    EmailAccountBuilder accountBuilder;

    private Collection<String> to;
    private Collection<String> cc;
    private Collection<String> bcc;

    @BeforeEach
    public void beforeEach(){
        this.to = new ArrayList<>();
        this.cc = new ArrayList<>();
        this.bcc = new ArrayList<>();

        this.emailBuilder = new EmailBuilder();
        this.accountBuilder = new EmailAccountBuilder(){
            @Override
            public boolean verifyPasswordExpiration() {
                return false;
            }
        };
    }

    @Test
    public void isValidAddressTest(){
        Assertions.assertTrue(client.isValidAddress("paulo@gmail.com"));
    }

    @Test
    public void isValidAddressTest2(){
        Assertions.assertFalse(client.isValidAddress("paulo@gmail.com."));
    }

    @Test
    public void isValidAddressTest3(){
        Assertions.assertFalse(client.isValidAddress(""));
    }

    @Test
    public void isValidEmailTest(){
        this.to.add("teste@gmail.com");
        this.cc.add("teste@gmail.com");
        this.bcc.add("teste@gmail.com");
        Email email = emailBuilder
                .setTo(to)
                .setBcc(bcc)
                .setCc(cc)
                .setFrom("paulo@gmail.com")
                .setCreationDate(Instant.now())
                .setMessage("Mensagem")
                .setSubject("Assunto")
                .build();
        Assertions.assertTrue(client.isValidEmail(email));
    }

    @Test
    public void isValidEmailTest2(){
        this.to.add("");
        this.cc.add("teste@gmail.com");
        this.bcc.add("teste@gmail.com");
        Email email = emailBuilder
                .setTo(to)
                .setBcc(bcc)
                .setCc(cc)
                .setFrom("paulo@gmail.com")
                .setCreationDate(Instant.now())
                .setMessage("Mensagem")
                .setSubject("Assunto")
                .build();
        Assertions.assertFalse(client.isValidEmail(email));
    }

    @Test
    public void isValidEmailTest3(){
        this.to.add("teste@gmail.com");
        this.cc.add("");
        this.bcc.add("teste@gmail.com");
        Email email = emailBuilder
                .setTo(to)
                .setBcc(bcc)
                .setCc(cc)
                .setFrom("paulo@gmail.com")
                .setCreationDate(Instant.now())
                .setMessage("Mensagem")
                .setSubject("Assunto")
                .build();
        Assertions.assertFalse(client.isValidEmail(email));
    }

    @Test
    public void isValidEmailTest4(){
        this.to.add("teste@gmail.com");
        this.cc.add("teste@gmail.com");
        this.bcc.add("");
        Email email = emailBuilder
                .setTo(to)
                .setBcc(bcc)
                .setCc(cc)
                .setFrom("paulo@gmail.com")
                .setCreationDate(Instant.now())
                .setMessage("Mensagem")
                .setSubject("Assunto")
                .build();
        Assertions.assertFalse(client.isValidEmail(email));
    }

    @Test
    public void isValidEmailTest5(){
        this.to.add("teste@gmail.com");
        this.cc.add("teste@gmail.com");
        this.bcc.add("teste@gmail.com");
        Email email = emailBuilder
                .setTo(to)
                .setBcc(bcc)
                .setCc(cc)
                .setFrom("")
                .setCreationDate(Instant.now())
                .setMessage("Mensagem")
                .setSubject("Assunto")
                .build();
        Assertions.assertFalse(client.isValidEmail(email));
    }

    @Test
    public void isValidEmailTest6(){
        this.to.add("teste@gmail.com");
        this.cc.add("teste@gmail.com");
        this.bcc.add("teste@gmail.com");
        Email email = emailBuilder
                .setTo(to)
                .setBcc(bcc)
                .setCc(cc)
                .setFrom("paulo@gmail.com")
                .setCreationDate(null)
                .setMessage("Mensagem")
                .setSubject("Assunto")
                .build();
        Assertions.assertFalse(client.isValidEmail(email));
    }

    @Test
    public void isValidEmailTest7(){
        this.to.add("");
        this.cc.add("");
        this.bcc.add("");
        Email email = emailBuilder
                .setTo(to)
                .setBcc(bcc)
                .setCc(cc)
                .setFrom("")
                .setCreationDate(null)
                .setMessage("Mensagem")
                .setSubject("Assunto")
                .build();
        Assertions.assertFalse(client.isValidEmail(email));
    }

    @Test
    public void emailListTest(){
        EmailAccount account = accountBuilder
                .setUser("paulo")
                .setDomain("gmail.com")
                .setPassword("123456789")
                .setLastPasswordUpdate(Instant.now())
                .build();

        ArrayList<Email> list = new ArrayList<>();
        when(service.emailList(any(EmailAccount.class))).thenReturn(list);
        Assertions.assertEquals(list, client.emailList(account));
    }

    @Test
    public void emailListTest2() throws RuntimeException {
        EmailAccount account = accountBuilder
                .setUser("paulo")
                .setDomain("gmail.com")
                .setPassword("123")
                .setLastPasswordUpdate(Instant.now())
                .build();

        ArrayList<Email> list = new ArrayList<>();
        when(service.emailList(any(EmailAccount.class))).thenReturn(list);
        Assertions.assertThrows(RuntimeException.class, () -> {
            client.emailList(account);
        });
    }

    @Test
    public void emailListTest3() throws RuntimeException {
        EmailAccount account = accountBuilder
                .setUser("paulo")
                .setDomain("gmail.com")
                .setPassword("123456789")
                .setLastPasswordUpdate(Instant.now().plus(+95, ChronoUnit.DAYS))
                .build();

        ArrayList<Email> list = new ArrayList<>();
        when(service.emailList(any(EmailAccount.class))).thenReturn(list);
        Assertions.assertThrows(RuntimeException.class, () -> {
            client.emailList(account);
        });
    }

    @Test
    public void emailListTest4() throws RuntimeException {
        EmailAccount account = accountBuilder
                .setUser("paulo")
                .setDomain("gmail.com")
                .setPassword("123")
                .setLastPasswordUpdate(Instant.now().plus(95, ChronoUnit.DAYS))
                .build();
        ArrayList<Email> list = new ArrayList<>();
        when(service.emailList(any(EmailAccount.class))).thenReturn(list);
        Assertions.assertThrows(RuntimeException.class, () -> {
            client.emailList(account);
        });
    }

    @Test
    public void sendEmailTest(){
        this.to.add("teste@gmail.com");
        this.cc.add("teste@gmail.com");
        this.bcc.add("teste@gmail.com");
        Email email = emailBuilder
                .setTo(to)
                .setBcc(bcc)
                .setCc(cc)
                .setFrom("paulo@gmail.com")
                .setCreationDate(Instant.now())
                .setMessage("Mensagem")
                .setSubject("Assunto")
                .build();

        when(service.sendEmail(any(Email.class))).thenReturn(true);
        client.setEmailService(service);
        Assertions.assertDoesNotThrow( () -> {
            client.sendEmail(email);
        });
    }

    @Test
    public void sendEmailTest2() throws RuntimeException {
        this.to.add("teste@gmail.com......");
        this.cc.add("teste@gmail.com");
        this.bcc.add("teste@gmail.com");
        Email email = emailBuilder
                .setTo(to)
                .setBcc(bcc)
                .setCc(cc)
                .setFrom("paulo@gmail.com")
                .setCreationDate(Instant.now())
                .setMessage("Mensagem")
                .setSubject("Assunto")
                .build();

        Assertions.assertThrows(RuntimeException.class, () -> {
            client.sendEmail(email);
        });
    }

    @Test
    public void createAccountTest(){
        EmailAccount account = accountBuilder
                .setUser("paulo")
                .setDomain("gmail.com")
                .setPassword("123456789")
                .setLastPasswordUpdate(Instant.now())
                .build();

       Assertions.assertTrue(client.createAccount(account));
    }

    @Test
    public void createAccountTest2(){
        EmailAccount account = accountBuilder
                .setUser("paulo!@#$%¨&*")
                .setDomain("gmail.com")
                .setPassword("123456789")
                .setLastPasswordUpdate(Instant.now())
                .build();

        Assertions.assertFalse(client.createAccount(account));
    }

    @Test
    public void createAccountTest3(){
        EmailAccount account = accountBuilder
                .setUser("paulo")
                .setDomain("gmail.com...")
                .setPassword("123456789")
                .setLastPasswordUpdate(Instant.now())
                .build();

        Assertions.assertFalse(client.createAccount(account));
    }

    @Test
    public void createAccountTest4(){
        EmailAccount account = accountBuilder
                .setUser("paulo")
                .setDomain("gmail.com")
                .setPassword("123")
                .setLastPasswordUpdate(Instant.now())
                .build();

        Assertions.assertFalse(client.createAccount(account));
    }

}