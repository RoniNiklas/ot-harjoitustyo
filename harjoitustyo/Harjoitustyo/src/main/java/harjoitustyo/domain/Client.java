/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package harjoitustyo.domain;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.transaction.Transactional;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Getter
@Setter
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
    @OneToMany(mappedBy = "client", fetch=FetchType.LAZY, orphanRemoval=true)
    private Set<Assignment> assignments = new HashSet<Assignment>();

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

    public void setLastname(String lastname) {
        this.lastname = lastname;
        this.fullname = this.firstname + " " + this.lastname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
        this.fullname = this.firstname + " " + this.lastname;
    }

    public Long getId() {
        return id;
    }
    
    public void addAssignment(Assignment assignment) {
        this.assignments.add(assignment);
    }
    
    public void removeAssignment(Assignment assignment) {
        this.assignments.remove(assignment);
    }

    public void clearAssignments() {
        this.assignments.stream().forEach(assignment -> assignment.getEmployee().removeAssignment(assignment));
        this.assignments.clear();
    }
    
    @Override
    public String toString() {
        return this.fullname + " " + this.idNumber;
    }
}
