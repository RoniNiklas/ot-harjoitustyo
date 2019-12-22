/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package harjoitustyo.dao;

import harjoitustyo.domain.Assignment;
import harjoitustyo.domain.Client;
import javafx.collections.ObservableList;

/**
 *
 * @author Roni
 */
public interface ClientManagerDao {

    Client add(Client client);

    boolean contains(String idNumber);

    Client getClient(String idNumber);

    ObservableList<Client> getObservableClients(String filter);

    void remove(String idNumber);

    void update(Long id, String field, String newValue);
}
