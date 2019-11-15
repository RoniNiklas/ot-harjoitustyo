/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package harjoitustyo;

import java.time.LocalDateTime;

/**
 *
 * @author Roni
 */
public class Assignment {
    private Customer customer;
    private Employee employee;
    private LocalDateTime start;
    private LocalDateTime end;
    private String description;
    private String report;
    private boolean done;

    public Assignment(Customer customer, Employee employee, LocalDateTime start, LocalDateTime end, String description, String report, boolean done) {
        this.customer = customer;
        this.employee = employee;
        this.start = start;
        this.end = end;
        this.description = description;
        this.report = report;
        this.done = done;
    }
}
