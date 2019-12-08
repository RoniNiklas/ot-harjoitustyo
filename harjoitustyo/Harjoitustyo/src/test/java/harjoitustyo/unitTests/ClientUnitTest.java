package harjoitustyo.unitTests;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import harjoitustyo.domain.Assignment;
import harjoitustyo.domain.Client;
import java.util.ArrayList;

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
public class ClientUnitTest {

    public ClientUnitTest() {
    }

    @Test
    public void ClientCanBeCreatedFullConstructor() {
        Client client = new Client("Make", "Makenen", "555-666-777", "email@email.com", "111", "kotitie 1A");
        assertNotNull(client);
        assertEquals(client.getFirstname(), "Make");
        assertEquals(client.getLastname(), "Makenen");
        assertEquals(client.getNumber(), "555-666-777");
        assertEquals(client.getEmail(), "email@email.com");
        assertEquals(client.getIdNumber(), "111");
        assertEquals(client.getAssignments().size(), 0);
        assertEquals(client.getAddress(), "kotitie 1A");
    }

    @Test
    public void ClientAttributesCanBeSetAndGot() {
        Client client = new Client();
        client.setFirstname("Make");
        client.setLastname("Makenen");
        client.setEmail("email@email.com");
        client.setNumber("555-666-777");
        client.setIdNumber("111");
        client.setAddress("kotitie 1A");
        assertEquals(client.getFirstname(), "Make");
        assertEquals(client.getLastname(), "Makenen");
        assertEquals(client.getNumber(), "555-666-777");
        assertEquals(client.getEmail(), "email@email.com");
        assertEquals(client.getIdNumber(), "111");
        assertEquals(client.getAssignments().size(), 0);
        assertEquals(client.getFullname(), "Make Makenen");
        assertEquals(client.getAddress(), "kotitie 1A");
    }
}
