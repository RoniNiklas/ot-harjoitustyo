/*
 * 
 */
package harjoitustyo.repoTests;

import harjoitustyo.domain.Client;
import harjoitustyo.repositories.ClientRepository;
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
public class ClientRepoTest {

    @Autowired
    ClientRepository clientrepo;

    @Test
    public void clientCanBeSavedAndRetrieved() {
        Client client = new Client();
        client.setAddress("osoite");
        client.setEmail("email");
        client.setFirstname("etunimi");
        client.setLastname("lastname");
        client.setIdNumber("112233-1234");
        client.setNumber("555666777");
        Client savedClient = clientrepo.save(client);
        savedClient = clientrepo.getOne(savedClient.getId());
        assertEquals(savedClient.getAddress(), client.getAddress());
        assertEquals(savedClient.getEmail(), client.getEmail());
        assertEquals(savedClient.getFullname(), client.getFullname());
        assertEquals(savedClient.getNumber(), client.getNumber());
        assertEquals(savedClient.getIdNumber(), client.getIdNumber());
    }
}
