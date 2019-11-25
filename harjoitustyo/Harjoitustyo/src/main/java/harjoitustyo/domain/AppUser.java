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

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import org.springframework.data.jpa.domain.AbstractPersistable;
import static org.springframework.data.jpa.domain.AbstractPersistable_.id;
@Entity
public class AppUser extends AbstractPersistable<Long> {
    @Id
    private Long id;
    private String username;
    private String password;
    private String authorization;
    public AppUser(String un, String pw, String auth) {
        this.password = pw;
        this.username = un;
        this.authorization = auth;
    }

    public AppUser() {
    }
    
    public String toString() {
        return "User: " + username + " has authorization: " + authorization;
    }

    public String getPassword() {
        return password;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public String getUsername() {
        return username;
    }
    
}
