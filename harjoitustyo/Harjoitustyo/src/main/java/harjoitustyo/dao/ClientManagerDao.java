/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package harjoitustyo.dao;

import harjoitustyo.domain.Client;
import javafx.collections.ObservableList;

/**
 *
 * @author Roni
 */
public interface ClientManagerDao {

    void add(Client client);

    boolean contains(String idNumber);

    Client getClient(String idNumber);

    ObservableList<Client> getObservableClients(String filter);

    void remove(Client client);
    void remove(String idNumber);

    public void update(Long id, String field, String newValue);
}
