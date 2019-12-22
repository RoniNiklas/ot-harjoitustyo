/*
 * 
 */
package harjoitustyo.unitTests;

import harjoitustyo.domain.Assignment;
import harjoitustyo.domain.Client;
import harjoitustyo.domain.Employee;
import java.time.LocalDateTime;
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
public class AssignmentTests {

    private Assignment assignment;

    @Before
    public void setUp() {
        Employee employee = new Employee("Teemu", "Työntekijä", "0501234567", "teemutyontekija@gmail.com", "111133-5555", "Tyontekijankatu 1 a");
        Client client = new Client("Aatu", "Asiakas", "0507654321", "aatu.asiakas@gmail.com", "121212-1234", "Asiakkaantie 7 B 22");
        assignment = new Assignment(client, employee, LocalDateTime.of(2019, 12, 24, 21, 00), LocalDateTime.of(2019, 12, 24, 22, 00), "Vie lahjoja ja laula joululauluja", client.getAddress(), client.getNumber(), "", "Assigned");
    }

    @Test
    public void clientNameIsCorrect() {
        assertEquals("Aatu Asiakas", assignment.getClientName());
    }

    @Test
    public void employeeNameIsCorrect() {
        assertEquals("Teemu Työntekijä", assignment.getEmployeeName());
    }

    @Test
    public void startTimeFormatCorrect() {
        assertEquals("24.12.2019 21:00", assignment.getStartTimeString());
    }

    @Test
    public void endTimeFormatCorrect() {
        assertEquals("24.12.2019 22:00", assignment.getEndTimeString());
    }

    @Test
    public void assignmentCanBeFilteredByClientName() {
        assertTrue(assignment.contains("Aatu"));
    }

    @Test
    public void assignmentCanBeFilteredByEmployeeName() {
        assertTrue(assignment.contains("Teemu"));
    }

    @Test
    public void assignmentCanBeFilteredByAddress() {
        assertTrue(assignment.contains("Asiakkaantie"));
    }

    @Test
    public void assignmentCanBeFilteredComplete() {
        System.out.println(assignment);
        assertTrue(assignment.contains("Aatu"));
        assertTrue(assignment.contains("Teemu"));
        assertTrue(assignment.contains("Asiakkaantie"));
        assertTrue(assignment.contains("vie lahjoja"));
        assertTrue(assignment.contains("24.12.2019"));
        assertTrue(assignment.contains("22:00"));
        assertTrue(assignment.contains("Assigned"));
        assertFalse(assignment.contains("Monkeybread"));
    }

    @After
    public void tearDown() {
    }
}
