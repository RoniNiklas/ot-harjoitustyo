
package harjoitustyo.dao;

//Roni
import harjoitustyo.repositories.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class AssignmentManagerDB implements AssignmentManagerDao {

    @Autowired
    private AssignmentRepository assignmentRepo;
    
}
