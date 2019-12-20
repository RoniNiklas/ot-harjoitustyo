package harjoitustyo.dao;

//Roni
import harjoitustyo.domain.Assignment;
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
import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@Transactional
public class AssignmentManagerDB implements AssignmentManagerDao {

    @Autowired
    private AssignmentRepository assignmentRepo;
    @Autowired
    private ClientRepository clientRepo;
    @Autowired
    private EmployeeRepository employeeRepo;

    @Override
    public ObservableList getObservableAssignments(String filter) {
        final String filterUp = filter.toUpperCase().trim();
        ObservableList<Assignment> returnable = FXCollections.observableArrayList();
        assignmentRepo.findAll().parallelStream()
                .filter(assignment -> assignment.contains(filterUp))
                .forEach(remainingAssignment -> returnable.add(remainingAssignment));
        return returnable;
    }

    @Override
    public Assignment add(Assignment assignment) {
        return assignmentRepo.save(assignment);
    }

    @Override
    public void update(Long id, String field, String value) {
        try {
            Assignment assignment = assignmentRepo.findById(id).get();
            String methodName = "set" + Character.toUpperCase(field.charAt(0)) + field.substring(1, field.length());
            Method method = assignment.getClass().getMethod(methodName, String.class);
            method.invoke(assignment, value);
            assignmentRepo.save(assignment);
        } catch (Exception e) {
            System.out.println("update doesn't work with field " + field + " and value: " + value + " error: " + e);
        }
    }

    @Override
    public void connect(Employee employee, Assignment assignment) {
        employee.addAssignment(assignment);
        assignment.setEmployee(employee);
        employeeRepo.save(employee);
        assignmentRepo.save(assignment);
    }

    @Override
    public void connect(Client client, Assignment assignment) {
        client.addAssignment(assignment);
        assignment.setClient(client);
        clientRepo.save(client);
        assignmentRepo.save(assignment);
    }

    @Override
    public boolean validateAssignment(Client client, Employee employee, LocalDate startDate, String startHour, String startMinutes, LocalDate endDate, String endHour, String endMinutes, String description, String address, String contact, String report, String assigned) {
        if (client == null) {
            throw new IllegalArgumentException("Client hasn't been chosen yet.");
        }
        if (employee == null) {
            throw new IllegalArgumentException("Employee hasn't been chosen yet.");
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
        LocalDateTime endDateActual = LocalDateTime.of(startDate, LocalTime.of(Integer.parseInt(endHour), Integer.parseInt(endMinutes)));
        if (startDateActual.isAfter(endDateActual)) {
            throw new IllegalArgumentException("Starting time and date can't be after ending time and date.");
        }
        return true;
    }
}
