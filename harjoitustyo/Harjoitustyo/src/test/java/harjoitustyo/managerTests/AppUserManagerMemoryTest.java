package harjoitustyo.managerTests;

import harjoitustyo.dao.AppUserManagerMemory;
import harjoitustyo.domain.AppUser;
import harjoitustyo.*;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

// Roni
public class AppUserManagerMemoryTest {

    private AppUserManagerMemory manager;
    
    @Before
    public void setUp() {
        manager = new AppUserManagerMemory();
        AppUser user = new AppUser("name", "password", "authorization");
        manager.add(user);
    }

    @Test
    public void stubInits() {
        assertNotNull(this.manager);
        assertEquals(1, manager.getUsers().size());
    }

    @Test
    public void canAddAndFindUsers() {
        assertNotNull(manager.getUser("name"));
    }
    @Test 
    public void cantFindNonexistentUsers() {
        assertNull(manager.getUser("Mauno"));
    }
    @Test
    public void canRemoveUsers(){
       AppUser user = manager.getUser("name");
        manager.remove(user);
        assertEquals(0, manager.getUsers().size());
    }
    @Test
    public void canLoginWithCorrectCredentials() {
        assertTrue(manager.checkLogin("name", "password"));
    }
    @Test
    public void cantLoginWithWrongUsername() {
        assertFalse(manager.checkLogin("nameXYZ", "password"));
    }
    @Test
    public void cantLoginWithWrongPassword() {
        assertFalse(manager.checkLogin("name", "passwordXYZ"));
    }
    @Test
    public void tulostaaOikeanListan() {
        assertEquals(manager.toString(),"List of users: \nname has authorization authorization\n");
    }

}
