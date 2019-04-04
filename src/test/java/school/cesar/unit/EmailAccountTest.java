package school.cesar.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import school.cesar.unit.utils.EmailAccountBuilder;

import java.time.Instant;
import java.time.ZonedDateTime;

public class EmailAccountTest {

    Email email;
    EmailClient client;
    EmailAccountBuilder accountBuilder;

    @BeforeEach
    public void beforeEach(){
        this.accountBuilder = new EmailAccountBuilder() {
            @Override
            public boolean verifyPasswordExpiration() {
                return false;
            }
        };
    }

    @Test
    public void verifyUserTest(){
        EmailAccount account = accountBuilder
                .setUser("paulo")
                .setDomain("gmail.com")
                .setPassword("123456789")
                .setLastPasswordUpdate(Instant.now())
                .build();
        Assertions.assertTrue(account.verifyUser());
    }

    @Test
    public void verifyUserTest2(){
        EmailAccount account = accountBuilder
                .setUser("")
                .setDomain("gmail.com")
                .setPassword("123456789")
                .setLastPasswordUpdate(Instant.now())
                .build();
        Assertions.assertFalse(account.verifyUser());
    }

    @Test
    public void verifyDomainTest() {
        EmailAccount account = accountBuilder
                .setUser("paulo")
                .setDomain("gmail.com")
                .setPassword("123456789")
                .setLastPasswordUpdate(Instant.now())
                .build();
        Assertions.assertTrue(account.verifyDomain());
    }

    @Test
    public void verifyDomainTest2() {
        EmailAccount account = accountBuilder
                .setUser("paulo")
                .setDomain("gmail.com.")
                .setPassword("123456789")
                .setLastPasswordUpdate(Instant.now())
                .build();
        Assertions.assertFalse(account.verifyDomain());
    }

    @Test
    public void verifyPasswordExpirationTest() {
        EmailAccount account = accountBuilder
                .setUser("paulo")
                .setDomain("gmail.com")
                .setPassword("123456789")
                .setLastPasswordUpdate(Instant.now())
                .build();
        Assertions.assertFalse(account.verifyPasswordExpiration());
    }

    @Test
    public void verifyPasswordExpirationTest2() {
        EmailAccount account = accountBuilder
                .setUser("paulo")
                .setDomain("gmail.com")
                .setPassword("123456789")
                .setLastPasswordUpdate(ZonedDateTime.now().plusDays(100).toInstant())
                .build();
        Assertions.assertTrue(account.verifyPasswordExpiration());
    }
}