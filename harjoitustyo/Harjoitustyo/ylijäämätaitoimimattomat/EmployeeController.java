/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package harjoitustyo;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Roni
 */
@Controller
public class EmployeeController {
    
    @Autowired
    EmployeeRepository employeerepo;
    
    @GetMapping("/employees")
    private List<Employee> getEmployees() {
        return employeerepo.findAll();
    }
    @PostMapping("/employees")
    private void addEmployee(@RequestParam Employee employee) {
        if (!employeerepo.existsByFirstnameAndLastname(employee.getFirstname(), employee.getLastname())) {
            employeerepo.save(employee);
        }
    }
    
}
