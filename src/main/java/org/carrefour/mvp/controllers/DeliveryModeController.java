package org.carrefour.mvp.controllers;

import java.time.LocalDate;
import java.util.List;

import org.carrefour.mvp.entities.enums.ModeLivraison;
import org.carrefour.mvp.mappers.dtos.TimeSlotDTO;
import org.carrefour.mvp.service.TimeSlotService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/time-slots")
@Tag(name = "Time-slots Management", description = "APIs related to listing and querying time-slots")
@Slf4j
public class DeliveryModeController {

	private final TimeSlotService timeSlotService;

	public DeliveryModeController(TimeSlotService timeSlotService) {
		this.timeSlotService = timeSlotService;
	}

	@GetMapping("/{mode}/timeslots")
	@Operation(summary = "Get timeslots for a delivery mode on a specific date", description = "Retrieves a list of available time slots for the specified delivery mode and date")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Timeslots retrieved successfully", content = @Content),
			@ApiResponse(responseCode = "400", description = "Invalid mode or date format", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
	public ResponseEntity<List<TimeSlotDTO>> getTimeSlots(@PathVariable("mode") ModeLivraison mode,
			@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
		log.info("Récupération des créneaux pour le mode '{}' à la date '{}'", mode, date);
		try {
			List<TimeSlotDTO> slots = timeSlotService.getTimeSlots(mode, date);
			log.info("Nombre de créneaux récupérés: {}", slots.size());
			return ResponseEntity.ok(slots);
		} catch (IllegalArgumentException ex) {
			log.warn("Requête invalide pour le mode '{}' et la date '{}': {}", mode, date, ex.getMessage());
			return ResponseEntity.badRequest().build();
		}
	}
}
