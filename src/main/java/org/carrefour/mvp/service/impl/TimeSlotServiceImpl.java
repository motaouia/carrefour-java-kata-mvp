package org.carrefour.mvp.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.carrefour.mvp.entities.TimeSlot;
import org.carrefour.mvp.entities.enums.ModeLivraison;
import org.carrefour.mvp.mappers.TimeSlotMapper;
import org.carrefour.mvp.mappers.dtos.TimeSlotDTO;
import org.carrefour.mvp.repositories.TimeSlotRepository;
import org.carrefour.mvp.service.TimeSlotService;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class TimeSlotServiceImpl implements TimeSlotService {

	private final TimeSlotRepository timeSlotRepository;
	private final TimeSlotMapper timeSlotMapper;

	@Override
	public List<TimeSlotDTO> getTimeSlots(ModeLivraison mode, LocalDate date) {
		log.info("Récupération des créneaux pour le mode '{}' à la date '{}'", mode, date);
		List<TimeSlot> slots = timeSlotRepository.findByModeLivraisonAndDate(mode, date);

		if (slots.isEmpty()) {
			log.warn("Aucun créneau trouvé pour le mode '{}' à la date '{}'", mode, date);
			throw new EntityNotFoundException("Aucun créneau trouvé pour le mode " + mode + " à la date " + date);
		}

		log.info("Nombre de créneaux trouvés: {}", slots.size());

		return timeSlotMapper.toDtoList(slots);
	}
}