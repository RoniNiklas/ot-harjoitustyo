/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package harjoitustyo.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Client extends AbstractPersistable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    private String firstname;
    private String fullname;
    private String lastname;
    private String number;
    private String email;
    private String idNumber;
    private String address;
    
    @OneToMany(mappedBy="client")
    private Map<Long, Assignment> assignments = new HashMap<Long, Assignment>();

    public Client() {
    }

    public Client(String firstname, String lastname, String number, String email, String idNumber, String address) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.number = number;
        this.email = email;
        this.idNumber = idNumber;
        this.fullname = firstname + " " + lastname;
        this.address = address;
    }

    public Map<Long, Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(Map<Long, Assignment> assignments) {
        this.assignments = assignments;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumber() {
        return number;
    }

    public String getFullname() {
        return fullname;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
        this.fullname = this.firstname + " " + this.lastname;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
        this.fullname = this.firstname + " " + this.lastname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public Long getId() {
        return id;
    }
}
