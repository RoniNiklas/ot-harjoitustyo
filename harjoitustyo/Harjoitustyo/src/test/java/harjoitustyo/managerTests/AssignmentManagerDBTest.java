/*
 * 
 */
package harjoitustyo.managerTests;

import harjoitustyo.dao.AssignmentManagerDB;
import harjoitustyo.dao.ClientManagerDB;
import harjoitustyo.dao.EmployeeManagerDB;
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
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

//Roni
@RunWith(SpringRunner.class)
@SpringBootTest()
@TestPropertySource(
        locations = "classpath:application-test.properties")
public class AssignmentManagerDBTest {

    @Autowired
    AssignmentManagerDB assignmentManager;

    @Autowired
    EmployeeManagerDB employeeManager;

    @Autowired
    ClientManagerDB clientManager;

    private Assignment assignment;

    @Before
    public void initRepo() {
        Employee employee = employeeManager.add(new Employee("Teemu", "Työntekijä", "0501234567", "teemutyontekija@gmail.com", "111133-5555", "Tyontekijankatu 1 a"));
        Client client = clientManager.add(new Client("Aatu", "Asiakas", "0507654321", "aatu.asiakas@gmail.com", "121212-1234", "Asiakkaantie 7 B 22"));
        assignment = new Assignment(client, employee, LocalDateTime.of(2019, 12, 24, 21, 00), LocalDateTime.of(2019, 12, 24, 22, 00), "Vie lahjoja ja laula joululauluja", client.getAddress(), client.getNumber(), "", "Assigned");
        assignment = assignmentManager.add(assignment);
    }

    @Test
    public void assignmentsCanBeAdded() {
        assertTrue(assignmentManager.getObservableAssignments("").size() > 0);
    }

    @Test
    public void assignmentsCanBeRemoved() {
        assertTrue(assignmentManager.getObservableAssignments("").size() > 0);
        assignmentManager.remove(assignment);
        assertTrue(assignmentManager.getObservableAssignments("").size() == 0);
    }

    @Test
    @Transactional
    public void assignmentsCanBeUpdated() {
        assertTrue(assignment.getStatus().equals("Assigned"));
        assignmentManager.update(assignment.getId(), "status", "Completed");
        assignment = assignmentManager.get(assignment.getId());
        assertEquals("Completed", assignment.getStatus());
    }

    @Test
    @Transactional
    public void assignmentsCantBeUpdatedWithWrongField() {
        assertTrue(assignment.getStatus().equals("Assigned"));
        assignmentManager.update(assignment.getId(), "serratus", "Completed");
        assignment = assignmentManager.get(assignment.getId());
        assertEquals("Assigned", assignment.getStatus());
    }

    @Test
    public void assignmentsCanBeValidated() {
        assertTrue(assignmentManager.validateAssignment(
                assignment.getClient(), assignment.getEmployee(),
                assignment.getStartTime().toLocalDate(),
                String.valueOf(assignment.getStartTime().getHour() + 1),
                String.valueOf(assignment.getStartTime().getMinute() + 1),
                assignment.getEndTime().toLocalDate(),
                String.valueOf(assignment.getEndTime().getHour() + 1),
                String.valueOf(assignment.getEndTime().getMinute() + 1),
                assignment.getDescription(),
                assignment.getAddress(),
                assignment.getContact(),
                "", "Assigned"));
    }

    @Test
    public void assignmentsCanBeValidatedClient() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            assignmentManager.validateAssignment(
                    null, assignment.getEmployee(),
                    assignment.getStartTime().toLocalDate(),
                    String.valueOf(assignment.getStartTime().getHour() + 1),
                    String.valueOf(assignment.getStartTime().getMinute() + 1),
                    assignment.getEndTime().toLocalDate(),
                    String.valueOf(assignment.getEndTime().getHour() + 1),
                    String.valueOf(assignment.getEndTime().getMinute() + 1),
                    assignment.getDescription(),
                    assignment.getAddress(),
                    assignment.getContact(),
                    "", "Assigned");
        });
    }

    @Test
    public void assignmentsCanBeValidatedEmployee() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            assignmentManager.validateAssignment(
                    assignment.getClient(), null,
                    assignment.getStartTime().toLocalDate(),
                    String.valueOf(assignment.getStartTime().getHour() + 1),
                    String.valueOf(assignment.getStartTime().getMinute() + 1),
                    assignment.getEndTime().toLocalDate(),
                    String.valueOf(assignment.getEndTime().getHour() + 1),
                    String.valueOf(assignment.getEndTime().getMinute() + 1),
                    assignment.getDescription(),
                    assignment.getAddress(),
                    assignment.getContact(),
                    "", "Assigned");
        });
    }

    @Test
    public void assignmentsCanBeValidatedStartDate() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            assignmentManager.validateAssignment(
                    assignment.getClient(), assignment.getEmployee(),
                    null,
                    String.valueOf(assignment.getStartTime().getHour() + 1),
                    String.valueOf(assignment.getStartTime().getMinute() + 1),
                    assignment.getEndTime().toLocalDate(),
                    String.valueOf(assignment.getEndTime().getHour() + 1),
                    String.valueOf(assignment.getEndTime().getMinute() + 1),
                    assignment.getDescription(),
                    assignment.getAddress(),
                    assignment.getContact(),
                    "", "Assigned");
        });
    }

    @Test
    public void assignmentsCanBeValidatedStartHour() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            assignmentManager.validateAssignment(
                    assignment.getClient(), assignment.getEmployee(),
                    assignment.getStartTime().toLocalDate(),
                    "",
                    String.valueOf(assignment.getStartTime().getMinute() + 1),
                    assignment.getEndTime().toLocalDate(),
                    String.valueOf(assignment.getEndTime().getHour() + 1),
                    String.valueOf(assignment.getEndTime().getMinute() + 1),
                    assignment.getDescription(),
                    assignment.getAddress(),
                    assignment.getContact(),
                    "", "Assigned");
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            assignmentManager.validateAssignment(
                    assignment.getClient(), assignment.getEmployee(),
                    assignment.getStartTime().toLocalDate(),
                    "24",
                    String.valueOf(assignment.getStartTime().getMinute() + 1),
                    assignment.getEndTime().toLocalDate(),
                    String.valueOf(assignment.getEndTime().getHour() + 1),
                    String.valueOf(assignment.getEndTime().getMinute() + 1),
                    assignment.getDescription(),
                    assignment.getAddress(),
                    assignment.getContact(),
                    "", "Assigned");
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            assignmentManager.validateAssignment(
                    assignment.getClient(), assignment.getEmployee(),
                    assignment.getStartTime().toLocalDate(),
                    "-1",
                    String.valueOf(assignment.getStartTime().getMinute() + 1),
                    assignment.getEndTime().toLocalDate(),
                    String.valueOf(assignment.getEndTime().getHour() + 1),
                    String.valueOf(assignment.getEndTime().getMinute() + 1),
                    assignment.getDescription(),
                    assignment.getAddress(),
                    assignment.getContact(),
                    "", "Assigned");
        });
    }

    @Test
    public void assignmentsCanBeValidatedStartMinute() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            assignmentManager.validateAssignment(
                    assignment.getClient(), assignment.getEmployee(),
                    assignment.getStartTime().toLocalDate(),
                    String.valueOf(assignment.getStartTime().getHour() + 1),
                    "",
                    assignment.getEndTime().toLocalDate(),
                    String.valueOf(assignment.getEndTime().getHour() + 1),
                    String.valueOf(assignment.getEndTime().getMinute() + 1),
                    assignment.getDescription(),
                    assignment.getAddress(),
                    assignment.getContact(),
                    "", "Assigned");
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            assignmentManager.validateAssignment(
                    assignment.getClient(), assignment.getEmployee(),
                    assignment.getStartTime().toLocalDate(),
                    String.valueOf(assignment.getStartTime().getHour() + 1),
                    "60",
                    assignment.getEndTime().toLocalDate(),
                    String.valueOf(assignment.getEndTime().getHour() + 1),
                    String.valueOf(assignment.getEndTime().getMinute() + 1),
                    assignment.getDescription(),
                    assignment.getAddress(),
                    assignment.getContact(),
                    "", "Assigned");
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            assignmentManager.validateAssignment(
                    assignment.getClient(), assignment.getEmployee(),
                    assignment.getStartTime().toLocalDate(),
                    String.valueOf(assignment.getStartTime().getHour() + 1),
                    "-1",
                    assignment.getEndTime().toLocalDate(),
                    String.valueOf(assignment.getEndTime().getHour() + 1),
                    String.valueOf(assignment.getEndTime().getMinute() + 1),
                    assignment.getDescription(),
                    assignment.getAddress(),
                    assignment.getContact(),
                    "", "Assigned");
        });
    }

    @Test
    public void assignmentsCanBeValidatedEndDate() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            assignmentManager.validateAssignment(
                    assignment.getClient(), assignment.getEmployee(),
                    assignment.getStartTime().toLocalDate(),
                    String.valueOf(assignment.getStartTime().getHour() + 1),
                    String.valueOf(assignment.getStartTime().getMinute() + 1),
                    null,
                    String.valueOf(assignment.getEndTime().getHour() + 1),
                    String.valueOf(assignment.getEndTime().getMinute() + 1),
                    assignment.getDescription(),
                    assignment.getAddress(),
                    assignment.getContact(),
                    "", "Assigned");
        });
    }

    @Test
    public void assignmentsCanBeValidatedEndHour() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            assignmentManager.validateAssignment(
                    assignment.getClient(), assignment.getEmployee(),
                    assignment.getStartTime().toLocalDate(),
                    String.valueOf(assignment.getStartTime().getHour() + 1),
                    String.valueOf(assignment.getStartTime().getMinute() + 1),
                    assignment.getEndTime().toLocalDate(),
                    "",
                    String.valueOf(assignment.getEndTime().getMinute() + 1),
                    assignment.getDescription(),
                    assignment.getAddress(),
                    assignment.getContact(),
                    "", "Assigned");
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            assignmentManager.validateAssignment(
                    assignment.getClient(), assignment.getEmployee(),
                    assignment.getStartTime().toLocalDate(),
                    String.valueOf(assignment.getStartTime().getHour() + 1),
                    String.valueOf(assignment.getStartTime().getMinute() + 1),
                    assignment.getEndTime().toLocalDate(),
                    "24",
                    String.valueOf(assignment.getEndTime().getMinute() + 1),
                    assignment.getDescription(),
                    assignment.getAddress(),
                    assignment.getContact(),
                    "", "Assigned");
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            assignmentManager.validateAssignment(
                    assignment.getClient(), assignment.getEmployee(),
                    assignment.getStartTime().toLocalDate(),
                    String.valueOf(assignment.getStartTime().getHour() + 1),
                    String.valueOf(assignment.getStartTime().getMinute() + 1),
                    assignment.getEndTime().toLocalDate(),
                    "-1",
                    String.valueOf(assignment.getEndTime().getMinute() + 1),
                    assignment.getDescription(),
                    assignment.getAddress(),
                    assignment.getContact(),
                    "", "Assigned");
        });
    }

    @Test
    public void assignmentsCanBeValidatedEndMinute() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            assignmentManager.validateAssignment(
                    assignment.getClient(), assignment.getEmployee(),
                    assignment.getStartTime().toLocalDate(),
                    String.valueOf(assignment.getStartTime().getHour() + 1),
                    String.valueOf(assignment.getStartTime().getMinute() + 1),
                    assignment.getEndTime().toLocalDate(),
                    String.valueOf(assignment.getEndTime().getHour() + 1),
                    "",
                    assignment.getDescription(),
                    assignment.getAddress(),
                    assignment.getContact(),
                    "", "Assigned");
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            assignmentManager.validateAssignment(
                    assignment.getClient(), assignment.getEmployee(),
                    assignment.getStartTime().toLocalDate(),
                    String.valueOf(assignment.getStartTime().getHour() + 1),
                    String.valueOf(assignment.getStartTime().getMinute() + 1),
                    assignment.getEndTime().toLocalDate(),
                    String.valueOf(assignment.getEndTime().getHour() + 1),
                    "60",
                    assignment.getDescription(),
                    assignment.getAddress(),
                    assignment.getContact(),
                    "", "Assigned");
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            assignmentManager.validateAssignment(
                    assignment.getClient(), assignment.getEmployee(),
                    assignment.getStartTime().toLocalDate(),
                    String.valueOf(assignment.getStartTime().getHour() + 1),
                    String.valueOf(assignment.getStartTime().getMinute() + 1),
                    assignment.getEndTime().toLocalDate(),
                    String.valueOf(assignment.getEndTime().getHour() + 1),
                    "-1",
                    assignment.getDescription(),
                    assignment.getAddress(),
                    assignment.getContact(),
                    "", "Assigned");
        });
    }

    @Test
    public void assignmentsCanBeValidatedDescription() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            assignmentManager.validateAssignment(
                    assignment.getClient(), assignment.getEmployee(),
                    assignment.getStartTime().toLocalDate(),
                    String.valueOf(assignment.getStartTime().getHour() + 1),
                    String.valueOf(assignment.getStartTime().getMinute() + 1),
                    assignment.getEndTime().toLocalDate(),
                    String.valueOf(assignment.getEndTime().getHour() + 1),
                    String.valueOf(assignment.getEndTime().getMinute() + 1),
                    "",
                    assignment.getAddress(),
                    assignment.getContact(),
                    "", "Assigned");
        });
    }

    @Test
    public void assignmentsCanBeValidatedAddress() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            assignmentManager.validateAssignment(
                    assignment.getClient(), assignment.getEmployee(),
                    assignment.getStartTime().toLocalDate(),
                    String.valueOf(assignment.getStartTime().getHour() + 1),
                    String.valueOf(assignment.getStartTime().getMinute() + 1),
                    assignment.getEndTime().toLocalDate(),
                    String.valueOf(assignment.getEndTime().getHour() + 1),
                    String.valueOf(assignment.getEndTime().getMinute() + 1),
                    assignment.getDescription(),
                    "",
                    assignment.getContact(),
                    "", "Assigned");
        });
    }

    @Test
    public void assignmentsCanBeValidatedStartPhoneNumber() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            assignmentManager.validateAssignment(
                    assignment.getClient(), assignment.getEmployee(),
                    assignment.getStartTime().toLocalDate(),
                    String.valueOf(assignment.getStartTime().getHour() + 1),
                    String.valueOf(assignment.getStartTime().getMinute() + 1),
                    assignment.getEndTime().toLocalDate(),
                    String.valueOf(assignment.getEndTime().getHour() + 1),
                    String.valueOf(assignment.getEndTime().getMinute() + 1),
                    assignment.getDescription(),
                    assignment.getAddress(),
                    "",
                    "", "Assigned");
        });
    }

    @Test
    public void assignmentsCanBeValidatedEmployeeOverlap() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            assignmentManager.validateAssignment(
                    assignment.getClient(), assignment.getEmployee(),
                    assignment.getStartTime().toLocalDate(),
                    String.valueOf(assignment.getStartTime().getHour() - 1),
                    String.valueOf(assignment.getStartTime().getMinute() - 1),
                    assignment.getEndTime().toLocalDate(),
                    String.valueOf(assignment.getEndTime().getHour() + 1),
                    String.valueOf(assignment.getEndTime().getMinute() + 1),
                    assignment.getDescription(),
                    assignment.getAddress(),
                    assignment.getContact(),
                    "", "Assigned");
        });
    }

    @Test
    public void assignmentsCanBeValidatedStartDateAfterEndDate() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            assignmentManager.validateAssignment(
                    assignment.getClient(), assignment.getEmployee(),
                    assignment.getEndTime().toLocalDate(),
                    String.valueOf(assignment.getEndTime().getHour() + 1),
                    String.valueOf(assignment.getEndTime().getMinute() + 1),
                    assignment.getStartTime().toLocalDate(),
                    String.valueOf(assignment.getStartTime().getHour() +1),
                    String.valueOf(assignment.getStartTime().getMinute() +1),
                    assignment.getDescription(),
                    assignment.getAddress(),
                    assignment.getContact(),
                    "", "Assigned");
        });
    }

    @After
    public void clearManagers() {
        employeeManager.getObservableEmployees("").stream().forEach(employee -> employeeManager.remove(employee));
        clientManager.getObservableClients("").stream().forEach(client -> clientManager.remove(client));
    }
}
