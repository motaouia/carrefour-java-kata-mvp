package org.carrefour.mvp.service;

import java.time.LocalDate;
import java.util.List;

import org.carrefour.mvp.entities.enums.ModeLivraison;
import org.carrefour.mvp.mappers.dtos.TimeSlotDTO;

public interface TimeSlotService {

	List<TimeSlotDTO> getTimeSlots(ModeLivraison mode, LocalDate date);
}
