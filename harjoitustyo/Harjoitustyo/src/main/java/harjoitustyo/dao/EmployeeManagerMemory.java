/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package harjoitustyo.dao;

import harjoitustyo.domain.Assignment;
import harjoitustyo.domain.Employee;
import java.util.ArrayList;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Roni
 */
public class EmployeeManagerMemory implements EmployeeManagerDao {

    private ArrayList<Employee> employees;

    public EmployeeManagerMemory() {
        this.employees = new ArrayList<>();
    }

    @Override
    public ObservableList<Employee> getObservableEmployees(String filter) {
        ObservableList<Employee> returnable = FXCollections.observableArrayList();
        for (int i = 0; i < employees.size(); i++) {
            Employee employee = employees.get(i);
            if (employee.getEmail().contains(filter) || employee.getFirstname().contains(filter) || employee.getLastname().contains(filter) || employee.getFullname().contains(filter) || employee.getNumber().contains(filter)) {
                returnable.add(employees.get(i));
            }
        }
        return returnable;
    }

    @Override
    public Employee add(Employee employee) {
        this.employees.add(employee);
        return employee;
    }

    @Override
    public void remove(Employee employee) {
        this.employees.remove(employee);
    }

    @Override
    public Employee getEmployee(String idNumber) {
        Optional<Employee> returnable = employees.parallelStream().filter(employee -> employee.getIdNumber().equals(idNumber)).findFirst();
        if (returnable.isPresent()) {
            return returnable.get();
        } else {
            throw new IllegalArgumentException("jostain syystä löytää id: " + idNumber + " containssilla, mutta ei getEmployeella");
        }
    }

    @Override
    public boolean contains(String idNumber) {
        return employees.parallelStream().anyMatch(employee -> employee.getIdNumber().equals(idNumber));
    }

    @Override
    public void remove(String idNumber) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void update(Long id, String field, String value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
