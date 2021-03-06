package harjoitustyo.dao;

import harjoitustyo.domain.AppUser;
import harjoitustyo.repositories.AppUserRepository;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class AppUserManagerDB implements AppUserManagerDao {

    @Autowired
    private AppUserRepository userrepo;

    @Override
    public AppUser add(AppUser user) {
        if (!userrepo.existsByUsername(user.getUsername())) {
            user = userrepo.save(user);
        }
        return user;
    }

    @Override
    public void remove(AppUser user) {
        userrepo.delete(user);
    }

    @Override
    public ArrayList<AppUser> getUsers() {
        return (ArrayList) userrepo.findAll();
    }

    @Override
    public AppUser getUser(String username) {
        return userrepo.findByUsername(username);
    }

    @Override
    public boolean checkLogin(String username, String password) {
        AppUser user = userrepo.findByUsername(username);
        if (user != null) {
            return user.getPassword().equals(password);
        }
        return false;
    }

}
