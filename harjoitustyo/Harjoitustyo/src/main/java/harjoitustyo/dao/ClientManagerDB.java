package harjoitustyo.dao;

import harjoitustyo.domain.Client;
import harjoitustyo.repositories.ClientRepository;
import java.lang.reflect.Method;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Controller
public class ClientManagerDB implements ClientManagerDao {

    @Autowired
    ClientRepository clientrepo;

    @Override
    public ObservableList<Client> getObservableClients(String filter) {
        final String filterUp = filter.toUpperCase().trim();
        ObservableList<Client> returnable = FXCollections.observableArrayList();
        clientrepo.findAll().parallelStream().filter(clientX -> clientX.getAddress().toUpperCase().contains(filterUp)
                || clientX.getFullname().toUpperCase().contains(filterUp)
                || clientX.getNumber().toUpperCase().contains(filterUp)
                || clientX.getEmail().toUpperCase().contains(filterUp)
                || clientX.getIdNumber().toUpperCase().contains(filterUp))
                .forEach(client -> returnable.add(client));
        return returnable;
    }

    @Override
    @Transactional
    public Client add(Client client) {
        if (!clientrepo.existsByIdNumber(client.getIdNumber())) {
            client = clientrepo.save(client);
        }
        return client;
    }

    @Override
    public Client getClient(String idNumber) {
        return clientrepo.findByIdNumber(idNumber);
    }

    @Override
    public boolean contains(String idNumber) {
        return clientrepo.existsByIdNumber(idNumber);
    }

    @Override
    @Transactional
    public void remove(String idNumber) {
        clientrepo.deleteByIdNumber(idNumber);
    }

    @Override
    @Transactional
    public void update(Long id, String field, String value) {
        try {
            Client client = clientrepo.findById(id).get();
            String methodName = "set" + Character.toUpperCase(field.charAt(0)) + field.substring(1, field.length());
            Method method = client.getClass().getMethod(methodName, String.class);
            method.invoke(client, value);
            clientrepo.save(client);
        } catch (Exception e) {
            System.out.println("update doesn't work with field " + field + " and value: " + value + " error: " + e);
        }
    }

    public void remove(Client client) {
        clientrepo.delete(client);
    }
}
