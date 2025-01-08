package org.carrefour.mvp.controllers;

import java.time.LocalDate;
import java.util.List;

import org.carrefour.mvp.entities.enums.ModeLivraison;
import org.carrefour.mvp.mappers.dtos.CreneauxHoraireDTO;
import org.carrefour.mvp.service.CreneauxHoraireService;
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
@RequestMapping("/api/v1/creneaux-horaires")
@Tag(name = "Gestion des Créneaux Horaires", description = "APIs liées à la liste et à la recherche de créneaux horaires")
@Slf4j
public class CreneauxHoraireController {

    private final CreneauxHoraireService creneauxHoraireService;

    public CreneauxHoraireController(CreneauxHoraireService creneauxHoraireService) {
        this.creneauxHoraireService = creneauxHoraireService;
    }

    @GetMapping("/{mode}/creneaux")
    @Operation(summary = "Obtenir les créneaux horaires pour un mode de livraison à une date spécifique", description = "Récupère une liste des créneaux horaires disponibles pour le mode de livraison et la date spécifiés")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Créneaux récupérés avec succès", content = @Content),
            @ApiResponse(responseCode = "400", description = "Mode ou format de date invalide", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur", content = @Content)
    })
    public ResponseEntity<List<CreneauxHoraireDTO>> obtenirCreneaux(@PathVariable("mode") ModeLivraison mode,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("Récupération des créneaux pour le mode '{}' à la date '{}'", mode, date);
        try {
            List<CreneauxHoraireDTO> creneaux = creneauxHoraireService.getCreneauxHoraires(mode, date);
            log.info("Nombre de créneaux récupérés : {}", creneaux.size());
            return ResponseEntity.ok(creneaux);
        } catch (IllegalArgumentException ex) {
            log.warn("Requête invalide pour le mode '{}' et la date '{}': {}", mode, date, ex.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
