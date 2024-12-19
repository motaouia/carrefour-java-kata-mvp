package org.carrefour.mvp.entities;

import java.time.LocalDate;
import java.time.LocalTime;

import org.carrefour.mvp.entities.enums.ModeLivraison;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TIME_SLOTS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimeSlot extends BaseEntity {

	@NotNull
	private LocalDate date;

	@NotNull
	private LocalTime startTime;

	@NotNull
	private LocalTime endTime;

	@Enumerated(EnumType.STRING)
	@NotNull
	private ModeLivraison modeLivraison;

}
