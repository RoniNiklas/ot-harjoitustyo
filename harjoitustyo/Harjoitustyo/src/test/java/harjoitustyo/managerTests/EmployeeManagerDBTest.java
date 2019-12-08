/*
 * 
 */
package harjoitustyo.managerTests;

import harjoitustyo.dao.EmployeeManagerDB;
import harjoitustyo.domain.Employee;
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
public class EmployeeManagerDBTest {

    @Autowired
    EmployeeManagerDB employeemanager;

    private Employee employee;

    @Before
    public void init() {
        employee = new Employee();
        employee.setAddress("osoite");
        employee.setEmail("email");
        employee.setFirstname("etunimi");
        employee.setLastname("sukunimi");
        employee.setIdNumber("112233-1234");
        employee.setNumber("555666777");
        employeemanager.add(employee);

    }

    @Test
    public void employeeCanBeGot() {
        Employee employee = employeemanager.getEmployee("112233-1234");
        assertNotNull(employee);
        assertEquals("osoite", employee.getAddress());
        assertEquals("email", employee.getEmail());
        assertEquals("osoite", employee.getAddress());
        assertEquals("etunimi", employee.getFirstname());
        assertEquals("sukunimi", employee.getLastname());
        assertNotNull(employee.getId());
    }

    @Test
    public void listOfEmployeesCanBeGot() {
        assertEquals(1, employeemanager.getObservableEmployees("").size());
        assertTrue(employeemanager.getObservableEmployees("").stream().anyMatch(employee -> employee.getIdNumber().equals("112233-1234")));
    }

    @Test
    public void cantAddMultipleGuysWithSameIdNumber() {
        Employee duplicate = new Employee();
        duplicate.setIdNumber("112233-1234");
        employeemanager.add(duplicate);
        assertEquals(1, employeemanager.getObservableEmployees("").size());
    }

    @Test
    public void canRemoveGuysWithIdNumber() {
        employeemanager.remove("112233-1234");
        assertEquals(0, employeemanager.getObservableEmployees("").size());
    }

    @Test
    public void canRemoveGuysWithFullEmployee() {
        employeemanager.remove(employee);
        assertEquals(0, employeemanager.getObservableEmployees("").size());
    }

    @Test
    public void canCheckIfDBContainsAGuy() {
        assertTrue(employeemanager.contains("112233-1234"));
        assertFalse(employeemanager.contains("221144-4321"));
    }

    @Test
    public void canUpdateEmployeeInfo() {
        employee = employeemanager.getEmployee("112233-1234");
        Long id = employee.getId();
        employeemanager.update(id, "firstname", "newname");
        employeemanager.update(id, "lastname", "newlastname");
        employeemanager.update(id, "address", "newaddress");
        employeemanager.update(id, "number", "newnumber");
        employeemanager.update(id, "email", "newemail");
        employeemanager.update(id, "idNumber", "newIdNumber");
        Employee updatedEmployee = employeemanager.getEmployee("newIdNumber");
        assertEquals("newname", updatedEmployee.getFirstname());
        assertEquals("newlastname", updatedEmployee.getLastname());
        assertEquals("newaddress", updatedEmployee.getAddress());
        assertEquals("newnumber", updatedEmployee.getNumber());
        assertEquals("newemail", updatedEmployee.getEmail());
    }

    @Test
    public void aUselessTestForTestikattavuus() {
        employee = employeemanager.getEmployee("112233-1234");
        Long id = employee.getId();
        employeemanager.update(id, "notafield", "nonvalue");
        Employee updatedEmployee = employeemanager.getEmployee("112233-1234");
        assertEquals(employee, updatedEmployee);
    }

    @Test
    public void canFilterByName() {
        assertEquals(1, employeemanager.getObservableEmployees("etunimi").size());
        assertEquals(1, employeemanager.getObservableEmployees("ETUNIMI").size());
        assertEquals(0, employeemanager.getObservableEmployees("vääräetunimi").size());
    }

    @Test
    public void canFilterByLastname() {
        assertEquals(1, employeemanager.getObservableEmployees("sukunimi").size());
        assertEquals(1, employeemanager.getObservableEmployees("SUKUNIMI").size());
        assertEquals(0, employeemanager.getObservableEmployees("vääräsukunimi").size());
    }

    @Test
    public void canFilterByIdNumber() {
        assertEquals(1, employeemanager.getObservableEmployees("112233-1234").size());
        assertEquals(0, employeemanager.getObservableEmployees("332211-4321").size());
    }

    @Test
    public void canFilterByAddress() {
        assertEquals(1, employeemanager.getObservableEmployees("osoite").size());
        assertEquals(1, employeemanager.getObservableEmployees("OSOITE").size());
        assertEquals(0, employeemanager.getObservableEmployees("väärä osoite").size());
    }

    @Test
    public void canFilterByNumber() {
        assertEquals(1, employeemanager.getObservableEmployees("555666777").size());
        assertEquals(0, employeemanager.getObservableEmployees("123456789").size());
    }

    @Test
    public void canFilterByEmail() {
        assertEquals(1, employeemanager.getObservableEmployees("email").size());
        assertEquals(1, employeemanager.getObservableEmployees("EMAIL").size());
        assertEquals(0, employeemanager.getObservableEmployees("email@hotmail.com").size());
    }
}
