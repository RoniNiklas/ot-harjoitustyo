package harjoitustyo.dao;

//Roni
import harjoitustyo.domain.Assignment;
import harjoitustyo.domain.Client;
import harjoitustyo.domain.Employee;
import harjoitustyo.repositories.AssignmentRepository;
import harjoitustyo.repositories.ClientRepository;
import harjoitustyo.repositories.EmployeeRepository;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

@Controller
public class AssignmentManagerDB implements AssignmentManagerDao {

    @Autowired
    private AssignmentRepository assignmentrepo;
    @Autowired
    private ClientRepository clientrepo;
    @Autowired
    private EmployeeRepository employeerepo;

    @Override
    public ObservableList getObservableAssignments(String filter) {
        ObservableList<Assignment> returnable = FXCollections.observableArrayList();
        assignmentrepo.findAll().parallelStream()
                .filter(assignment -> assignment.contains(filter))
                .forEach(remainingAssignment -> returnable.add(remainingAssignment));
        return returnable;
    }

    @Override
    @Transactional
    public Assignment add(Assignment assignment) {
        assignment = assignmentrepo.save(assignment);
        assignment.getClient().addAssignment(assignment);
        clientrepo.save(assignment.getClient());
        assignment.getEmployee().addAssignment(assignment);
        employeerepo.save(assignment.getEmployee());
        return assignment;
    }

    @Override
    @Transactional
    public void update(Long id, String field, String value) {
        try {
            Assignment assignment = assignmentrepo.getOne(id);
            String methodName = "set" + Character.toUpperCase(field.charAt(0)) + field.substring(1, field.length());
            Method method = assignment.getClass().getMethod(methodName, String.class);
            method.invoke(assignment, value);
            assignmentrepo.save(assignment);
        } catch (Exception e) {
            System.out.println("update doesn't work with field " + field + " and value: " + value + " error: " + e);
        }
    }

    @Override
    public boolean contains(Long id) {
        return assignmentrepo.existsById(id);
    }

    @Override
    public Assignment get(Long id) {
        return assignmentrepo.getOne(id);
    }

    @Override
    public void removeById(Long id) {
        assignmentrepo.deleteById(id);
    }

    @Override
    public void remove(Assignment assignment) {
        assignmentrepo.delete(assignment);
    }

    @Override
    public boolean validateAssignment(Client client, Employee employee, LocalDate startDate, String startHour, String startMinutes, LocalDate endDate, String endHour, String endMinutes, String description, String address, String contact, String report, String assigned) {
        if (client == null) {
            throw new IllegalArgumentException("Client hasn't been chosen yet.");
        }
        if (employee == null) {
            throw new IllegalArgumentException("Employee hasn't been chosen yet.");
        }
        if (description.equals("")) {
            throw new IllegalArgumentException("The assignment needs a description");
        }
        if (address.equals("")) {
            throw new IllegalArgumentException("The assignment needs an address");
        }
        if (contact.equals("")) {
            throw new IllegalArgumentException("The assignment needs the client's phone number");
        }
        if (startDate == null) {
            throw new IllegalArgumentException("Start date hasn't been chosen yet.");
        }
        if (endDate == null) {
            throw new IllegalArgumentException("End date hasn't been chosen yet.");
        }

        try {
            int hour = Integer.parseInt(startHour);
            if (hour < 0 || hour > 23) {
                throw new IllegalArgumentException("Starting hour hasn't been chosen yet or is badly formatted.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Starting hour hasn't been chosen yet or is badly formatted.");
        }
        try {
            int minute = Integer.parseInt(startMinutes);
            if (minute < 0 || minute > 59) {
                throw new IllegalArgumentException("Starting minute hasn't been chosen yet or is badly formatted.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Starting minute hasn't been chosen yet or is badly formatted.");
        }
        try {
            int hour = Integer.parseInt(endHour);
            if (hour < 0 || hour > 23) {
                throw new IllegalArgumentException("Ending hour hasn't been chosen yet or is badly formatted.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Ending hour hasn't been chosen yet or is badly formatted.");
        }
        try {
            int minute = Integer.parseInt(endMinutes);
            if (minute < 0 || minute > 59) {
                throw new IllegalArgumentException("Ending minute hasn't been chosen yet or is badly formatted.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Ending minute hasn't been chosen yet or is badly formatted.");
        }
        LocalDateTime startDateActual = LocalDateTime.of(startDate, LocalTime.of(Integer.parseInt(startHour), Integer.parseInt(startMinutes)));
        LocalDateTime endDateActual = LocalDateTime.of(endDate, LocalTime.of(Integer.parseInt(endHour), Integer.parseInt(endMinutes)));
        if (startDateActual.isAfter(endDateActual)) {
            throw new IllegalArgumentException("Starting time and date can't be after ending time and date.");
        }
        if (employee.getAssignments().stream().anyMatch(
            assignment -> startDateActual.isBefore(assignment.getEndTime()) && assignment.getStartTime().isBefore(endDateActual)
        )) {
            throw new IllegalArgumentException("Employee is busy with a different assignment at that time.");
        }
        return true;
    }
}
