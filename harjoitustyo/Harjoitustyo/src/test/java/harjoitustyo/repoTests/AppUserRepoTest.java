/*
 * 
 */
package harjoitustyo.repoTests;

import harjoitustyo.domain.AppUser;
import harjoitustyo.repositories.AppUserRepository;
import javax.transaction.Transactional;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest()
@TestPropertySource(
        locations = "classpath:application-test.properties")
@Transactional
public class AppUserRepoTest {

    @Autowired
    AppUserRepository userrepo;

    @Test
    public void userCanBeSavedAndRetrieved() {
        AppUser user = new AppUser();
        user.setAuthorization("authorization");
        user.setPassword("password");
        user.setUsername("name");
        AppUser savedUser = userrepo.save(user);
        savedUser = userrepo.getOne(savedUser.getId());
        assertEquals(user.getAuthorization(), savedUser.getAuthorization());
        assertEquals(user.getUsername(), savedUser.getUsername());
        assertEquals(user.getPassword(), savedUser.getPassword());
    }
}
