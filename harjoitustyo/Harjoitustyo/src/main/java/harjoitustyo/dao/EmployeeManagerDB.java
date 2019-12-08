/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package harjoitustyo.dao;

import harjoitustyo.domain.Employee;
import harjoitustyo.repositories.EmployeeRepository;
import java.lang.reflect.Method;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class EmployeeManagerDB implements EmployeeManagerDao {

    @Autowired
    private EmployeeRepository employeerepo;

    @Override
    public ObservableList<Employee> getObservableEmployees(String filter) {
        final String filterUp = filter.toUpperCase().trim();
        ObservableList<Employee> returnable = FXCollections.observableArrayList();
        employeerepo.findAll().parallelStream()
                .filter(employeeX -> employeeX.getFullname().toUpperCase().contains(filterUp)
                || employeeX.getIdNumber().toUpperCase().contains(filterUp)
                || employeeX.getEmail().toUpperCase().contains(filterUp)
                || employeeX.getNumber().toUpperCase().contains(filterUp)
                || employeeX.getAddress().toUpperCase().contains(filterUp))
                .forEach(employee -> returnable.add(employee));
        return returnable;
    }

    @Override
    public void add(Employee employee) {
        if (!employeerepo.existsByIdNumber(employee.getIdNumber())) {
            employeerepo.save(employee);
        }
    }

    @Override
    @Transactional
    public void remove(String idNumber) {
        employeerepo.deleteByIdNumber(idNumber);
    }

    @Override
    public Employee getEmployee(String idNumber) {
        return employeerepo.findByIdNumber(idNumber);
    }

    @Override
    public boolean contains(String idNumber) {
        return employeerepo.existsByIdNumber(idNumber);
    }

    @Override
    @Transactional
    public void remove(Employee employee) {
        employeerepo.delete(employee);
    }

    @Transactional
    public void update(Long id, String field, String value) {
        try {
            Employee employee = employeerepo.findById(id).get();
            String methodName = "set" + Character.toUpperCase(field.charAt(0)) + field.substring(1, field.length());
            System.out.println("methodname: " + methodName);
            Method method = employee.getClass().getMethod(methodName, String.class);
            System.out.println("method: " + method);
            method.invoke(employee, value);
            employeerepo.save(employee);
        } catch (Exception e) {
            System.out.println("update doesn't work with field " + field + " and value: " + value + " error: " + e);
        }
    }
}
