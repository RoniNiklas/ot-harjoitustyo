/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package harjoitustyo.domain;

import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javax.persistence.Entity;
import javax.persistence.Id;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 *
 * @author Roni
 */
@Entity
public class Employee extends AbstractPersistable<Long> {
    @Id
    Long id;
    private String firstname;
    private String fullname;
    private String lastname;
    private String number;
    private String email;
    private String idNumber;
    private ArrayList<Assignment> assignments;

    public Employee(String firstname, String lastname, String number, String email, ArrayList<Assignment> assignments, String idNumber) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.number = number;
        this.email = email;
        this.assignments = assignments;
        this.idNumber = idNumber;
        this.fullname = firstname + " " + lastname;
    }

    public Employee() {
        this.firstname = "First name lacking";
        this.lastname = "Last name lacking";
        this.number = "Number lacking";
        this.email = "Email lacking";
        this.assignments = new ArrayList<Assignment>();
        this.idNumber = "Idnumber lacking";
        this.fullname = firstname + " " + lastname;
    }

    public String getFullname() {
        return fullname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
        this.fullname = this.firstname + " " + this.lastname;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
        this.fullname = this.firstname + " " + this.lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAssignments(ArrayList<Assignment> assignments) {
        this.assignments = assignments;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getNumber() {
        return number;
    }
    
    public String getLastname() {
        return lastname;
    }

    public ArrayList<Assignment> getAssignments() {
        return assignments;
    }

}
