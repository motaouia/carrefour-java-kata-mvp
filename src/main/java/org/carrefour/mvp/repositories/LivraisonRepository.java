package org.carrefour.mvp.repositories;

import org.carrefour.mvp.entities.Livraison;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivraisonRepository extends JpaRepository<Livraison, Long> {

}
