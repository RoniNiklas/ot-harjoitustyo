/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Roni
 */
package harjoitustyo.repositories;
import harjoitustyo.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByIdNumber(String idNumber);
    boolean existsByIdNumber(String idNumber);
    void deleteByIdNumber(String idNumber);
}