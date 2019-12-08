/*
 * 
 */
package harjoitustyo.managerTests;

import harjoitustyo.dao.ClientManagerDB;
import harjoitustyo.domain.Client;
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
public class ClientManagerDBTest {

    @Autowired
    ClientManagerDB clientmanager;

    private Client client;

    @Before
    public void init() {
        client = new Client();
        client.setAddress("osoite");
        client.setEmail("email");
        client.setFirstname("etunimi");
        client.setLastname("sukunimi");
        client.setIdNumber("112233-1234");
        client.setNumber("555666777");
        clientmanager.add(client);

    }

    @Test
    public void clientCanBeGot() {
        Client client = clientmanager.getClient("112233-1234");
        assertNotNull(client);
        assertEquals("osoite", client.getAddress());
        assertEquals("email", client.getEmail());
        assertEquals("osoite", client.getAddress());
        assertEquals("etunimi", client.getFirstname());
        assertEquals("sukunimi", client.getLastname());
        assertNotNull(client.getId());
    }

    @Test
    public void listOfClientsCanBeGot() {
        assertEquals(1, clientmanager.getObservableClients("").size());
        assertTrue(clientmanager.getObservableClients("").stream().anyMatch(client -> client.getIdNumber().equals("112233-1234")));
    }

    @Test
    public void cantAddMultipleGuysWithSameIdNumber() {
        Client duplicate = new Client();
        duplicate.setIdNumber("112233-1234");
        clientmanager.add(duplicate);
        assertEquals(1, clientmanager.getObservableClients("").size());
    }

    @Test
    public void canRemoveGuysWithIdNumber() {
        clientmanager.remove("112233-1234");
        assertEquals(0, clientmanager.getObservableClients("").size());
    }

    @Test
    public void canRemoveGuysWithFullClient() {
        clientmanager.remove(client);
        assertEquals(0, clientmanager.getObservableClients("").size());
    }

    @Test
    public void canCheckIfDBContainsAGuy() {
        assertTrue(clientmanager.contains("112233-1234"));
        assertFalse(clientmanager.contains("221144-4321"));
    }

    @Test
    public void canUpdateClientInfo() {
        client = clientmanager.getClient("112233-1234");
        Long id = client.getId();
        clientmanager.update(id, "firstname", "newname");
        clientmanager.update(id, "lastname", "newlastname");
        clientmanager.update(id, "address", "newaddress");
        clientmanager.update(id, "number", "newnumber");
        clientmanager.update(id, "email", "newemail");
        clientmanager.update(id, "idNumber", "newIdNumber");
        Client updatedClient = clientmanager.getClient("newIdNumber");
        assertEquals("newname", updatedClient.getFirstname());
        assertEquals("newlastname", updatedClient.getLastname());
        assertEquals("newaddress", updatedClient.getAddress());
        assertEquals("newnumber", updatedClient.getNumber());
        assertEquals("newemail", updatedClient.getEmail());
    }

    @Test
    public void aUselessTestForTestikattavuus() {
        client = clientmanager.getClient("112233-1234");
        Long id = client.getId();
        clientmanager.update(id, "notafield", "nonvalue");
        Client updatedClient = clientmanager.getClient("112233-1234");
        assertEquals(client, updatedClient);
    }

    @Test
    public void canFilterByName() {
        assertEquals(1, clientmanager.getObservableClients("etunimi").size());
        assertEquals(1, clientmanager.getObservableClients("ETUNIMI").size());
        assertEquals(0, clientmanager.getObservableClients("vääräetunimi").size());
    }

    @Test
    public void canFilterByLastname() {
        assertEquals(1, clientmanager.getObservableClients("sukunimi").size());
        assertEquals(1, clientmanager.getObservableClients("SUKUNIMI").size());
        assertEquals(0, clientmanager.getObservableClients("vääräsukunimi").size());
    }

    @Test
    public void canFilterByIdNumber() {
        assertEquals(1, clientmanager.getObservableClients("112233-1234").size());
        assertEquals(0, clientmanager.getObservableClients("332211-4321").size());
    }

    @Test
    public void canFilterByAddress() {
        assertEquals(1, clientmanager.getObservableClients("osoite").size());
        assertEquals(1, clientmanager.getObservableClients("OSOITE").size());
        assertEquals(0, clientmanager.getObservableClients("väärä osoite").size());
    }

    @Test
    public void canFilterByNumber() {
        assertEquals(1, clientmanager.getObservableClients("555666777").size());
        assertEquals(0, clientmanager.getObservableClients("123456789").size());
    }

    @Test
    public void canFilterByEmail() {
        assertEquals(1, clientmanager.getObservableClients("email").size());
        assertEquals(1, clientmanager.getObservableClients("EMAIL").size());
        assertEquals(0, clientmanager.getObservableClients("email@hotmail.com").size());
    }
}
