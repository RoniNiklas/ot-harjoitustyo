/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package harjoitustyo.domain;

import harjoitustyo.domain.Client;
import harjoitustyo.domain.Employee;
import java.time.LocalDateTime;

/**
 *
 * @author Roni
 */
public class Assignment {
    private Client client;
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
