/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package harjoitustyo.dao;

import harjoitustyo.domain.Employee;
import javafx.collections.ObservableList;

/**
 *
 * @author Roni
 */
public interface EmployeeManagerDao {
    boolean contains(String idNumber);
    void add(Employee employee);
    void remove(Employee employee);
    Employee getEmployee(String idNumber);
    ObservableList<Employee> getObservableEmployees(String filter);
    
}
