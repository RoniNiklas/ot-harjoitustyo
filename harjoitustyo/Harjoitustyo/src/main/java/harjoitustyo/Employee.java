/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package harjoitustyo;

import java.util.ArrayList;

/**
 *
 * @author Roni
 */
public class Employee extends User {
    
    private String name;
    private String number;
    private String email;
    private ArrayList<Assignment> assignments;

    public Employee(String name, String number, String email, String un, String pw, String auth) {
        super(un, pw, auth);
        this.name = name;
        this.number = number;
        this.email = email;
        this.assignments = new ArrayList<>();
    }    
}
