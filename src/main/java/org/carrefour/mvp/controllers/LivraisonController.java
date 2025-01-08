package org.carrefour.mvp.controllers;

import java.util.List;

import org.carrefour.mvp.entities.enums.ModeLivraison;
import org.carrefour.mvp.mappers.dtos.CreneauxHoraireDTO;
import org.carrefour.mvp.mappers.dtos.LivraisonDTO;
import org.carrefour.mvp.service.LivraisonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/api/v1/livraisons")
@RequiredArgsConstructor
@Tag(name = "Gestion des Livraisons", description = "APIs liées à la liste et à la gestion des livraisons")
public class LivraisonController {

	private final LivraisonService livraisonService;

	@PutMapping("/{livraisonId}/clients/{clientId}")
	@Operation(summary = "Définir le mode de livraison", description = "Définit le mode de livraison pour une livraison spécifique associée à un client")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Mode de livraison mis à jour avec succès", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivraisonDTO.class))),
			@ApiResponse(responseCode = "404", description = "Livraison ou client introuvable", content = @Content),
			@ApiResponse(responseCode = "400", description = "Données d'entrée invalides", content = @Content),
			@ApiResponse(responseCode = "500", description = "Erreur interne du serveur", content = @Content) })
	public ResponseEntity<LivraisonDTO> definirModeLivraison(@PathVariable Long livraisonId,
			@PathVariable Long clientId, @RequestBody LivraisonDTO livraisonDTO) {
		LivraisonDTO updatedLivraison = livraisonService.setDeliveryMode(clientId, livraisonId,
				livraisonDTO.getModeLivraison());
		return ResponseEntity.ok(updatedLivraison);
	}

	@PostMapping("/{clientId}")
	@Operation(summary = "Créer une nouvelle livraison pour un client", description = "Crée une nouvelle livraison associée à l'identifiant du client fourni")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Livraison créée avec succès", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivraisonDTO.class))),
			@ApiResponse(responseCode = "404", description = "Client ou créneau horaire introuvable", content = @Content),
			@ApiResponse(responseCode = "400", description = "Données d'entrée invalides", content = @Content),
			@ApiResponse(responseCode = "500", description = "Erreur interne du serveur", content = @Content) })
	public ResponseEntity<LivraisonDTO> creerLivraisonPourClient(@PathVariable Long clientId,
			@Valid @RequestBody LivraisonDTO livraisonDTO) {
		LivraisonDTO livraison = livraisonService.createLivraisonForClient(clientId, livraisonDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(livraison);
	}

	@PutMapping("/{clientId}/livraisons/{livraisonId}/creneaux-horaires")
	@Operation(summary = "Définir le créneau horaire d'une livraison", description = "Attribue un créneau horaire spécifique à la livraison donnée")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Créneau horaire mis à jour avec succès", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivraisonDTO.class))),
			@ApiResponse(responseCode = "400", description = "Créneau horaire invalide", content = @Content),
			@ApiResponse(responseCode = "404", description = "Client, livraison ou créneau horaire introuvable", content = @Content),
			@ApiResponse(responseCode = "500", description = "Erreur interne du serveur", content = @Content) })
	public ResponseEntity<LivraisonDTO> definirCreneauxHoraires(@PathVariable Long clientId,
			@PathVariable Long livraisonId, @Valid @RequestBody CreneauxHoraireDTO creneauxHoraireDTO) {
		LivraisonDTO livraison = livraisonService.setCreneauxHoraire(clientId, livraisonId, creneauxHoraireDTO.getId());
		return ResponseEntity.ok(livraison);
	}

	@GetMapping
	@Operation(summary = "Récupérer tous les modes de livraison", description = "Récupère la liste de tous les modes de livraison disponibles")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Modes de livraison récupérés avec succès", content = @Content),
			@ApiResponse(responseCode = "500", description = "Erreur interne du serveur", content = @Content) })
	public ResponseEntity<List<ModeLivraison>> recupererTousLesModesLivraison() {
		List<ModeLivraison> modes = livraisonService.getAllModeLivraisons();
		return ResponseEntity.ok(modes);
	}
}
