/*
 * 
 */
package harjoitustyo.repoTests;

import harjoitustyo.domain.Employee;
import harjoitustyo.repositories.EmployeeRepository;
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
public class EmployeeRepoTest {

    @Autowired
    EmployeeRepository employeerepo;

    @Test
    public void employeeCanBeSavedAndRetrieved() {
        Employee employee = new Employee();
        employee.setAddress("osoite");
        employee.setEmail("email");
        employee.setFirstname("etunimi");
        employee.setLastname("lastname");
        employee.setIdNumber("112233-1234");
        employee.setNumber("555666777");
        Employee savedEmployee = employeerepo.save(employee);
        savedEmployee = employeerepo.getOne(savedEmployee.getId());
        assertEquals(savedEmployee.getAddress(), employee.getAddress());
        assertEquals(savedEmployee.getEmail(), employee.getEmail());
        assertEquals(savedEmployee.getFullname(), employee.getFullname());
        assertEquals(savedEmployee.getNumber(), employee.getNumber());
        assertEquals(savedEmployee.getIdNumber(), employee.getIdNumber());
    }
}
