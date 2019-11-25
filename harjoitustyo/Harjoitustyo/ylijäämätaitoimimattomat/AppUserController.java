/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package harjoitustyo;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Roni
 */
@Controller
public class AppUserController {
    
    @Autowired
    AppUserRepository userrepo;
    
    @GetMapping("/users")
    private List<AppUser> getUsers() {
        return userrepo.findAll();
    }
    @PostMapping("/users")
    private void addUser(@RequestParam AppUser user) {
        if (!userrepo.existsByUsername(user.getUsername())) {
            userrepo.save(user);
        }
    }
    
}
