package org.carrefour.mvp.service;

import java.time.LocalDate;
import java.util.List;

import org.carrefour.mvp.entities.enums.ModeLivraison;
import org.carrefour.mvp.mappers.dtos.CreneauxHoraireDTO;

public interface CreneauxHoraireService {

	List<CreneauxHoraireDTO> getCreneauxHoraires(ModeLivraison mode, LocalDate date);
}
