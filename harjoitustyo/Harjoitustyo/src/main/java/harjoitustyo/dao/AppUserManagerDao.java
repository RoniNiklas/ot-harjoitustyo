/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package harjoitustyo.dao;

import harjoitustyo.domain.AppUser;
import java.util.ArrayList;

/**
 *
 * @author Roni
 */
public interface AppUserManagerDao {
    @Override
    String toString();
    void add(AppUser user);
    void remove(AppUser user);
    public ArrayList<AppUser> getUsers();
    public AppUser getUser(String username);
    public boolean checkLogin(String username, String password);
}
