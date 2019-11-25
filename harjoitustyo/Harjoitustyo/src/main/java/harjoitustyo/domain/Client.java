/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package harjoitustyo.domain;

/**
 *
 * @author Roni
 */
public class Client {
    private String name;
    private String number;
    private String address;
    private String email;

    public Client() {
    }

    public Client(String name, String number, String address, String email) {
        this.name = name;
        this.number = number;
        this.address = address;
        this.email = email;
    }
    
}
