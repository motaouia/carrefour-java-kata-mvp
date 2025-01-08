package org.carrefour.mvp.service;

import java.util.List;

import org.carrefour.mvp.entities.enums.ModeLivraison;
import org.carrefour.mvp.mappers.dtos.LivraisonDTO;

public interface LivraisonService {

	LivraisonDTO createLivraisonForClient(Long clientId, LivraisonDTO livraisonDTO);

	LivraisonDTO getClientLivraison(Long clientId, Long livraisonId);

	LivraisonDTO setDeliveryMode(Long clientId, Long livraisonId, ModeLivraison modeLivraison);

	LivraisonDTO setCreneauxHoraire(Long clientId, Long livraisonId, Long creneauxHoraireId);

	List<ModeLivraison> getAllModeLivraisons();
}
