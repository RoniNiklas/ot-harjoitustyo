/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import harjoitustyo.domain.Assignment;
import harjoitustyo.domain.Employee;
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
public class EmployeeUnitTest {

    public EmployeeUnitTest() {
    }

    @Test
    public void EmployeeCanBeCreatedFullConstructor() {
        Employee employee = new Employee("Make", "Makenen", "555-666-777", "email@email.com", new ArrayList<>(), "111", "kotitie 1a");
        assertNotNull(employee);
        assertEquals(employee.getFirstname(), "Make");
        assertEquals(employee.getLastname(), "Makenen");
        assertEquals(employee.getNumber(), "555-666-777");
        assertEquals(employee.getEmail(), "email@email.com");
        assertEquals(employee.getIdNumber(), "111");
        assertEquals(employee.getAssignments().size(), 0);
        assertEquals(employee.getAddress(), "kotitie 1a");
    }

    @Test
    public void EmployeeAttributesCanBeSetAndGot() {
        Employee employee = new Employee();
        employee.setFirstname("Make");
        employee.setLastname("Makenen");
        employee.setEmail("email@email.com");
        employee.setNumber("555-666-777");
        employee.setIdNumber("111");
        employee.setAssignments(new ArrayList<>());
        employee.setAddress("kotitie 1a");
        assertEquals(employee.getFirstname(), "Make");
        assertEquals(employee.getLastname(), "Makenen");
        assertEquals(employee.getNumber(), "555-666-777");
        assertEquals(employee.getEmail(), "email@email.com");
        assertEquals(employee.getIdNumber(), "111");
        assertEquals(employee.getAssignments().size(), 0);
        assertEquals(employee.getFullname(), "Make Makenen");
        assertEquals(employee.getAddress(), "kotitie 1a");
    }
}
