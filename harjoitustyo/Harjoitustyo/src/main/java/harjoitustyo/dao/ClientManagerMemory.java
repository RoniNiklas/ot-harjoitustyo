/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package harjoitustyo.dao;

import harjoitustyo.dao.ClientManagerDao;
import harjoitustyo.domain.Assignment;
import harjoitustyo.domain.Client;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collector;
import static java.util.stream.Collectors.toList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Roni
 */
public class ClientManagerMemory implements ClientManagerDao {

    private ArrayList<Client> clients;

    public ClientManagerMemory() {
        this.clients = new ArrayList<>();
    }

    @Override
    public ObservableList<Client> getObservableClients(String filter) {
        ObservableList<Client> returnable = FXCollections.observableArrayList();
        System.out.println(clients);
        for (int i = 0; i < clients.size(); i++) {
            Client client = clients.get(i);
            if (client.getEmail().contains(filter) 
                    || client.getFirstname().contains(filter) 
                    || client.getLastname().contains(filter) 
                    || client.getFullname().contains(filter) 
                    || client.getNumber().contains(filter)) {
                returnable.add(clients.get(i));
            }
        }
        return returnable;
    }

    @Override
    public Client add(Client client) {
        this.clients.add(client);
        return client;
    }

    @Override
    public void remove(Client client) {
        this.clients.remove(client);
    }

    @Override
    public Client getClient(String idNumber) {
        Optional<Client> returnable = clients.parallelStream().filter(client -> client.getIdNumber().equals(idNumber)).findFirst();
        if (returnable.isPresent()) {
            return returnable.get();
        } else {
            throw new IllegalArgumentException("jostain syystä löytää id: " + idNumber + " containssilla, mutta ei getClientlla");
        }
    }

    @Override
    public boolean contains(String idNumber) {
        return clients.parallelStream().anyMatch(client -> client.getIdNumber().equals(idNumber));
    }

    @Override
    public void remove(String idNumber) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(Long id, String field, String newValue) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
