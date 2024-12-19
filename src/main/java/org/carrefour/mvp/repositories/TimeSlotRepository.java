package org.carrefour.mvp.repositories;

import java.time.LocalDate;
import java.util.List;

import org.carrefour.mvp.entities.TimeSlot;
import org.carrefour.mvp.entities.enums.ModeLivraison;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long>{
	
	  List<TimeSlot> findByModeLivraisonAndDate(ModeLivraison mode, LocalDate date);

}
