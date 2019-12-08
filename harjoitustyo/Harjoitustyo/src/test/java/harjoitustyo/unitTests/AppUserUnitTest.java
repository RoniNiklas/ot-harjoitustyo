package harjoitustyo.unitTests;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import harjoitustyo.domain.AppUser;
import harjoitustyo.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Roni
 */
public class AppUserUnitTest {
    
    public AppUserUnitTest() {
    }
    @Test 
    public void AppUserCanBeCreatedFullConstructor() {
        AppUser user = new AppUser("name", "password", "authorization");
        assertNotNull(user);
        assertEquals(user.getAuthorization(), "authorization");
        assertEquals(user.getPassword(), "password");
        assertEquals(user.getUsername(), "name");
    }
    @Test
    public void AppUserAttributesCanBeSetAndGot() {
        AppUser user = new AppUser();
        user.setAuthorization("authorization");
        user.setPassword("password");
        user.setUsername("name");
        assertEquals(user.getAuthorization(), "authorization");
        assertEquals(user.getPassword(), "password");
        assertEquals(user.getUsername(), "name");
    }
    @Test
    public void toStringTest() {
        AppUser user = new AppUser();
        user.setAuthorization("authorization");
        user.setPassword("password");
        user.setUsername("name");
        assertEquals(user.toString(), "User: name has authorization: authorization");
    }
}
