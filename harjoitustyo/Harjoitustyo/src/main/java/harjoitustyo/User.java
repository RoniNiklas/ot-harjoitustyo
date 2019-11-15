/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package harjoitustyo;

/**
 *
 * @author Roni
 */
public class User {
    private String username;
    private String password;
    private String authorization;
    public User(String un, String pw, String auth) {
        this.password = pw;
        this.username = un;
        this.authorization = auth;
    }
    @Override
    public String toString(){
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
