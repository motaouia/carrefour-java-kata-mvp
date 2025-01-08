package org.carrefour.mvp.repositories;

import java.time.LocalDate;
import java.util.List;

import org.carrefour.mvp.entities.CreneauxHoraire;
import org.carrefour.mvp.entities.enums.ModeLivraison;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreneauxHoraireRepository extends JpaRepository<CreneauxHoraire, Long> {

	List<CreneauxHoraire> findByModeLivraisonAndDate(ModeLivraison mode, LocalDate date);

}
