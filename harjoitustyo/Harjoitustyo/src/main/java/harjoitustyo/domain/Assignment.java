/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package harjoitustyo.domain;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 *
 * @author Roni
 */
@Entity
public class Assignment extends AbstractPersistable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @ManyToOne
    private Client client;
    @ManyToOne
    private Employee employee;
    private LocalDateTime start;
    private LocalDateTime end;
    private String description;
    private String report;
    private boolean completed;

    public Assignment(Client client, Employee employee, LocalDateTime start, LocalDateTime end, String description, String report, boolean completed) {
        this.client = client;
        this.employee = employee;
        this.start = start;
        this.end = end;
        this.description = description;
        this.report = report;
        this.completed = completed;
    }
}
