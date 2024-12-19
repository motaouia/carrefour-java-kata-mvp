package org.carrefour.mvp.repositories;

import org.carrefour.mvp.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ClientRepository extends JpaRepository<Client, Long> {

}
