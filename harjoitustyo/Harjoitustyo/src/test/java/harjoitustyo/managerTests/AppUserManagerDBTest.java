/*
 * 
 */
package harjoitustyo.managerTests;

import harjoitustyo.dao.AppUserManagerDB;
import harjoitustyo.domain.AppUser;
import javax.transaction.Transactional;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest()
@TestPropertySource(
        locations = "classpath:application-test.properties")
@Transactional
public class AppUserManagerDBTest {

    @Autowired
    AppUserManagerDB usermanager;

    private AppUser user;

    @Before
    public void initRepo() {
        user = new AppUser();
        user.setAuthorization("authorization");
        user.setPassword("password");
        user.setUsername("name");
        usermanager.add(user);
    }

    @Test
    public void usersCanBeSavedAndRetrieved() {
        AppUser savedUser = usermanager.getUser("name");
        assertNotNull(savedUser);
        assertEquals("name", savedUser.getUsername());
        assertEquals("authorization", savedUser.getAuthorization());
        assertEquals("password", savedUser.getPassword());
    }

    @Test
    public void cantAddMultipleUsersWithSameUsername() {
        AppUser newUser = new AppUser();
        newUser.setUsername("name");
        newUser.setAuthorization("authorization");
        newUser.setPassword("password");
        usermanager.add(newUser);
        assertEquals(usermanager.getUsers().size(), 1);
    }

    @Test
    public void canAddMultipleUsersWithDifferentNames() {
        AppUser newUser = new AppUser();
        newUser.setUsername("name2");
        newUser.setAuthorization("authorization");
        newUser.setPassword("password");
        usermanager.add(newUser);
        assertEquals(usermanager.getUsers().size(), 2);
    }

    @Test
    public void getUsersReturnsFullList() {
        assertEquals(1, usermanager.getUsers().size());
    }

    @Test
    public void checkLoginAllowsCorrect() {
        assertTrue(usermanager.checkLogin("name", "password"));
    }

    @Test
    public void checkLoginDisallowsFaultyPassword() {
        assertFalse(usermanager.checkLogin("name", "wrongPw"));
    }

    @Test
    public void checkLoginDisallowsFaultyUsername() {
        assertFalse(usermanager.checkLogin("wrong name", "password"));
    }

    @Test
    public void canRemoveUsers() {
        usermanager.remove(user);
        assertEquals(0, usermanager.getUsers().size());
    }
}
