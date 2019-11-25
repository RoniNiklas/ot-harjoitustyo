/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package harjoitustyo.dao;

import harjoitustyo.domain.AppUser;
import java.util.ArrayList;
import java.util.Optional;

/**
 *
 * @author Roni
 */
public class AppUserManagerMemory implements AppUserManagerDao {

    private ArrayList<AppUser> users;

    public AppUserManagerMemory() {
        this.users = new ArrayList<>();
    }

    ;
    
    @Override
    public String toString() {
        String returnable = "List of users: \n";
        for (int i = 0; i < users.size(); i++) {
            returnable = returnable + users.get(i).getUsername() + " has authorization " + users.get(i).getAuthorization() + "\n";
        }
        return returnable;
    }

    public ArrayList<AppUser> getUsers() {
        return users;
    }

    @Override
    public void add(AppUser user) {
        this.users.add(user);
    }

    @Override
    public void remove(AppUser user) {
        this.users.remove(user);
    }

    @Override
    public AppUser getUser(String username) {
        Optional<AppUser> returnable = users.stream().parallel().filter(user -> user.getUsername().equals(username)).findAny();
        if (returnable.isPresent()) {
            return returnable.get();
        } else {
            return null;
        }
    }

    @Override
    public boolean checkLogin(String username, String password) {
        Optional<AppUser> returnable = users.stream().parallel().filter(user -> user.getUsername().equals(username)).findAny();
        if (returnable.isPresent()) {
            AppUser user = returnable.get();
            if (user.getPassword().equals(password)) {
                return true;
            }
            return false;
        } else {
            return false;
        }
    }
}
