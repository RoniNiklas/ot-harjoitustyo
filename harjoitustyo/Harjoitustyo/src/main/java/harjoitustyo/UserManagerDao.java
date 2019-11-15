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
public interface UserManagerDao {

    @Override
    String toString();
    void add(User user);
    void remove(User user);
    public ArrayList<User> getUsers();
    public User getUser(String username);
    public boolean checkLogin(String username, String password);
    
}
