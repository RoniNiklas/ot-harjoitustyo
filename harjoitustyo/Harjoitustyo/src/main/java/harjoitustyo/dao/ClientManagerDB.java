package harjoitustyo.dao;

import harjoitustyo.domain.Client;
import harjoitustyo.repositories.ClientRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

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
    public void add(Client client) {
        if (!clientrepo.existsByIdNumber(client.getIdNumber())) {
            clientrepo.save(client);
        }
    }

    @Override
    @Transactional
    public void remove(Client client) {
        clientrepo.delete(client);
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

}
