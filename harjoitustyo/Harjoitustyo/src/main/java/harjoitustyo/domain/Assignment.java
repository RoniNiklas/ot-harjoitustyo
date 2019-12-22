/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package harjoitustyo.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

//Roni
@Entity
@Getter
@Setter
public class Assignment extends AbstractPersistable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @ManyToOne
    private Client client;
    @ManyToOne
    private Employee employee;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String description;
    private String address;
    private String report;
    private String status;
    private String contact;

    public Assignment(Client client, Employee employee, LocalDateTime start, LocalDateTime end, String description, String address, String contact, String report, String status) {
        this.client = client;
        this.employee = employee;
        this.startTime = start;
        this.endTime = end;
        this.address = address;
        this.contact = contact;
        this.description = description;
        this.report = report;
        this.status = status;
    }

    public Assignment() {
    }
    
    public String getClientName() {
        return this.client.getFullname();
    }
    public String getEmployeeName() {
        return this.employee.getFullname();
    }
    
    public String getStartTimeString() {
        return this.startTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
    }
    public String getEndTimeString() {
        return this.endTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
    }

    public boolean contains(String filterUp) {
        filterUp = filterUp.toUpperCase();
        return this.employee.getFullname().toUpperCase().contains(filterUp) || this.client.getFullname().toUpperCase().contains(filterUp) || this.address.toUpperCase().contains(filterUp) || this.description.toUpperCase().contains(filterUp) || this.getStartTimeString().toUpperCase().contains(filterUp) || this.getEndTimeString().toUpperCase().contains(filterUp) || this.status.toUpperCase().contains(filterUp);
    }    
}
