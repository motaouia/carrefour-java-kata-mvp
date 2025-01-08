package org.carrefour.mvp.service.impl;

import java.util.Arrays;
import java.util.List;

import org.carrefour.mvp.entities.Client;
import org.carrefour.mvp.entities.CreneauxHoraire;
import org.carrefour.mvp.entities.Livraison;
import org.carrefour.mvp.entities.enums.ModeLivraison;
import org.carrefour.mvp.mappers.LivraisonMapper;
import org.carrefour.mvp.mappers.dtos.LivraisonDTO;
import org.carrefour.mvp.repositories.ClientRepository;
import org.carrefour.mvp.repositories.CreneauxHoraireRepository;
import org.carrefour.mvp.repositories.LivraisonRepository;
import org.carrefour.mvp.service.LivraisonService;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LivraisonServiceImpl implements LivraisonService {

	private final LivraisonRepository livraisonRepository;
	private final ClientRepository clientRepository;
	private final CreneauxHoraireRepository creneauxHoraireRepository;
	private final LivraisonMapper livraisonMapper;
	// private final CreneauxHoraireMapper creneauxHoraireMapper;

	public LivraisonServiceImpl(LivraisonRepository livraisonRepository, ClientRepository clientRepository,
			CreneauxHoraireRepository creneauxHoraireRepository, LivraisonMapper livraisonMapper) {
		this.livraisonRepository = livraisonRepository;
		this.clientRepository = clientRepository;
		this.creneauxHoraireRepository = creneauxHoraireRepository;
		this.livraisonMapper = livraisonMapper;
	}

	@Override
	public LivraisonDTO createLivraisonForClient(Long clientId, LivraisonDTO livraisonDTO) {
		log.info("Création d'une nouvelle commande pour le client avec l'ID '{}'", clientId);

		Client client = clientRepository.findById(clientId).orElseThrow(() -> {
			log.warn("Aucun client trouvé pour l'ID '{}'", clientId);
			return new EntityNotFoundException("Client non trouvé pour l'ID: " + clientId);
		});

		Livraison livraison = new Livraison();
		livraison.getClients().add(client);
		livraison.setModeLivraison(livraisonDTO.getModeLivraison());
		// livraison.setCreneauxHoraire(creneauxHoraireMapper.toEntity(livraisonDTO.getCreneauxHoraire()));

		Livraison savedLivraison = livraisonRepository.save(livraison);
		log.info("Commande créée avec succès. ID de la commande: {}", savedLivraison.getId());
		return livraisonMapper.toDto(savedLivraison);
	}

	@Override
	public LivraisonDTO getClientLivraison(Long clientId, Long livraisonId) {
		log.info("Récupération de la commande avec l'ID '{}' pour le client '{}'", livraisonId, clientId);

		Livraison livraison = livraisonRepository.findById(livraisonId)
				.filter(o -> o.getClients().stream().anyMatch(client -> client.getId().equals(clientId)))
				.orElseThrow(() -> {
					log.warn("Aucune commande trouvée pour le client '{}' et la commande '{}'", clientId, livraisonId);
					return new EntityNotFoundException(
							"Commande non trouvée pour clientId: " + clientId + " et livraisonId: " + livraisonId);
				});

		log.info("Commande trouvée. ID: {}, Mode Livraison: {}", livraison.getId(), livraison.getModeLivraison());
		return livraisonMapper.toDto(livraison);
	}

	@Override
	public LivraisonDTO setDeliveryMode(Long clientId, Long livraisonId, ModeLivraison modeLivraison) {
		log.info("Définition du mode de livraison '{}' pour la commande '{}' du client '{}'",
				modeLivraison, livraisonId, clientId);

		Livraison livraison = livraisonRepository.findById(livraisonId).orElseThrow(() -> {
			log.warn("Aucune commande trouvée pour l'ID '{}'", livraisonId);
			return new EntityNotFoundException("Commande non trouvée pour id: " + livraisonId);
		});

		boolean clientFound = livraison.getClients().stream()
				.anyMatch(client -> client.getId().equals(clientId));

		if (!clientFound) {
			log.warn("La commande '{}' n'est pas associée au client '{}'", livraisonId, clientId);
			throw new EntityNotFoundException("Aucune commande " + livraisonId + " trouvée pour le client " + clientId);
		}

		livraison.setModeLivraison(modeLivraison);
		Livraison updatedLivraison = livraisonRepository.save(livraison);
		log.info("Mode de livraison défini avec succès. Nouveau mode: {}", updatedLivraison.getModeLivraison());
		return livraisonMapper.toDto(updatedLivraison);
	}

	@Override
	public LivraisonDTO setCreneauxHoraire(Long clientId, Long livraisonId, Long creneauxHoraireId) {
		log.info("Définition du créneau '{}' pour la commande '{}' du client '{}'", creneauxHoraireId, livraisonId,
				clientId);

		Livraison livraison = livraisonRepository.findById(livraisonId).orElseThrow(() -> {
			log.warn("Aucune commande trouvée pour l'ID '{}'", livraisonId);
			return new EntityNotFoundException("Commande non trouvée pour id: " + livraisonId);
		});

		// Check if the livraison is associated with the given clientId
		boolean clientFound = livraison.getClients().stream()
				.anyMatch(client -> client.getId().equals(clientId));

		if (!clientFound) {
			log.warn("La commande '{}' n'est pas associée au client '{}'", livraisonId, clientId);
			throw new EntityNotFoundException("Aucune commande " + livraisonId + " trouvée pour le client " + clientId);
		}

		CreneauxHoraire creneauxHoraire = creneauxHoraireRepository.findById(creneauxHoraireId).orElseThrow(() -> {
			log.warn("Aucun créneau trouvé pour l'ID '{}'", creneauxHoraireId);
			return new EntityNotFoundException("Créneau non trouvé pour id: " + creneauxHoraireId);
		});

		if (livraison.getModeLivraison() == null) {
			log.warn("Tentative de définir un créneau sans mode de livraison préalablement défini sur la commande '{}'",
					livraisonId);
			throw new IllegalStateException(
					"Impossible de définir un créneau sans définir d'abord un mode de livraison");
		}

		if (!livraison.getModeLivraison().equals(creneauxHoraire.getModeLivraison())) {
			log.warn("Le mode de livraison du créneau '{}' ne correspond pas à celui de la commande '{}'",
					creneauxHoraireId,
					livraisonId);
			throw new IllegalArgumentException("Le mode du créneau " + creneauxHoraire.getModeLivraison()
					+ " ne correspond pas au mode de livraison de la commande " + livraison.getModeLivraison());
		}

		livraison.setCreneauxHoraire(creneauxHoraire);
		Livraison updatedLivraison = livraisonRepository.save(livraison);
		log.info("Créneau défini avec succès pour la commande '{}'. Créneau: {}", livraisonId, creneauxHoraireId);
		return livraisonMapper.toDto(updatedLivraison);
	}

	@Override
	public List<ModeLivraison> getAllModeLivraisons() {
		log.info("Récupération de tous les modes de livraison disponibles");
		List<ModeLivraison> modes = Arrays.asList(ModeLivraison.values());
		log.info("Modes de livraison récupérés avec succès : {}", modes);
		return modes;
	}
}
