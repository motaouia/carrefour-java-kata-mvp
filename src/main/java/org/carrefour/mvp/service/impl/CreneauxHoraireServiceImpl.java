package org.carrefour.mvp.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.carrefour.mvp.entities.CreneauxHoraire;
import org.carrefour.mvp.entities.enums.ModeLivraison;
import org.carrefour.mvp.mappers.CreneauxHoraireMapper;
import org.carrefour.mvp.mappers.dtos.CreneauxHoraireDTO;
import org.carrefour.mvp.repositories.CreneauxHoraireRepository;
import org.carrefour.mvp.service.CreneauxHoraireService;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CreneauxHoraireServiceImpl implements CreneauxHoraireService {

	private final CreneauxHoraireRepository creneauxHoraireRepository;
	private final CreneauxHoraireMapper creneauxHoraireMapper;

	@Override
	public List<CreneauxHoraireDTO> getCreneauxHoraires(ModeLivraison mode, LocalDate date) {
		log.info("Récupération des créneaux pour le mode '{}' à la date '{}'", mode, date);
		List<CreneauxHoraire> slots = creneauxHoraireRepository.findByModeLivraisonAndDate(mode, date);

		if (slots.isEmpty()) {
			log.warn("Aucun créneau trouvé pour le mode '{}' à la date '{}'", mode, date);
			throw new EntityNotFoundException("Aucun créneau trouvé pour le mode " + mode + " à la date " + date);
		}

		log.info("Nombre de créneaux trouvés: {}", slots.size());

		return creneauxHoraireMapper.toDtoList(slots);
	}
}