package org.carrefour.mvp.mappers.dtos;

import java.time.LocalDate;
import java.time.LocalTime;

import org.carrefour.mvp.entities.enums.ModeLivraison;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreneauxHoraireDTO extends BaseDTO {

	private LocalDate date;
	private LocalTime startTime;
	private LocalTime endTime;
	private ModeLivraison modeLivraison;
}
