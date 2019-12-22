package harjoitustyo.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;
import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Entity
@Getter
@Setter
public class AppUser extends AbstractPersistable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
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
}
