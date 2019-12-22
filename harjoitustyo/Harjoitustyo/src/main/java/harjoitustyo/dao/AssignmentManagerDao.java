package harjoitustyo.dao;

//Roni
import harjoitustyo.domain.Assignment;
import harjoitustyo.domain.Client;
import harjoitustyo.domain.Employee;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javafx.collections.ObservableList;

public interface AssignmentManagerDao {

    public boolean contains(Long id);

    public ObservableList getObservableAssignments(String filter);

    public Assignment add(Assignment assignment);

    public void update(Long id, String field, String newValue);

    public void remove(Assignment assignment);
    
    public void removeById(Long id);

    public Assignment get(Long id);

    public boolean validateAssignment(Client client, Employee employee, LocalDate value, String text, String text0, LocalDate value0, String text1, String text2, String text3, String text4, String text5, String string, String assigned);

}
