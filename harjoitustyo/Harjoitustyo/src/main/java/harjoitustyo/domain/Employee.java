package harjoitustyo.domain;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Getter
@Setter
public class Employee extends AbstractPersistable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    private String firstname;
    private String fullname;
    private String lastname;
    private String number;
    private String email;
    private String idNumber;
    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Assignment> assignments = new HashSet<Assignment>();
    private String address;

    public Employee() {

    }

    public Employee(String firstname, String lastname, String number, String email, String idNumber, String address) {
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

    public void addAssignment(Assignment assignment) {
        this.assignments.add(assignment);
    }

    public void removeAssignment(Assignment assignment) {
        this.assignments.remove(assignment);
    }

    public void clearAssignments() {
        this.assignments.stream().forEach(assignment -> assignment.getClient().removeAssignment(assignment));
        this.assignments.clear();
    }

    @Override
    public String toString() {
        return this.fullname + " " + this.idNumber;
    }
}
