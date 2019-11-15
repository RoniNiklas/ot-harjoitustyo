/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package harjoitustyo;

import java.util.ArrayList;
import java.util.Optional;

/**
 *
 * @author Roni
 */
public class UserManagerMemory implements UserManagerDao {
    private ArrayList<User> users;
    public UserManagerMemory() {
        this.users = new ArrayList<>();
    };
    
    @Override
    public String toString() {
        String returnable = "List of users: \n";
        for (int i = 0; i < users.size(); i++) {
            returnable = returnable + users.get(i).getUsername() + " has authorization " + users.get(i).getAuthorization() + "\n";
        }
        return returnable;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    @Override
    public void add(User user) {
        this.users.add(user);
    }

    @Override
    public void remove(User user) {
        this.users.remove(user);
    }

    @Override
    public User getUser(String username) {
        Optional<User> returnable = users.stream().parallel().filter(user -> user.getUsername().equals(username)).findAny();
        if (returnable.isPresent()) {
            return returnable.get();
        } else {
            return null;
        }
    }

    @Override
    public boolean checkLogin(String username, String password) {
        Optional<User> returnable = users.stream().parallel().filter(user -> user.getUsername().equals(username)).findAny();
        if (returnable.isPresent()) {
            User user = returnable.get();
            if (user.getPassword().equals(password)) {
                return true;
            }
            return false;
        } else {
            return false;
        }
    }
}
