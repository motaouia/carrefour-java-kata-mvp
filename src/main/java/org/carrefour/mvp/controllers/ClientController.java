package org.carrefour.mvp.controllers;

import org.carrefour.mvp.mappers.dtos.ClientDTO;
import org.carrefour.mvp.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/clients")
@Tag(name = "Gestion des Clients", description = "APIs liées à la gestion des clients")
public class ClientController {

	private final ClientService clientService;

	public ClientController(ClientService clientService) {
		this.clientService = clientService;
	}

	@PostMapping
	@Operation(summary = "Créer un nouveau client", description = "Crée un nouveau client avec les détails fournis")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Client créé avec succès", content = @Content),
			@ApiResponse(responseCode = "400", description = "Entrée invalide", content = @Content),
			@ApiResponse(responseCode = "500", description = "Erreur interne du serveur", content = @Content) })
	public ResponseEntity<ClientDTO> creerClient(@RequestBody ClientDTO clientDTO) {
		ClientDTO client = clientService.createClient(clientDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(client);
	}

	@GetMapping("/{clientId}")
	@Operation(summary = "Obtenir les détails d'un client", description = "Récupère les informations détaillées d'un client par son ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Client trouvé", content = @Content),
			@ApiResponse(responseCode = "404", description = "Client non trouvé", content = @Content),
			@ApiResponse(responseCode = "500", description = "Erreur interne du serveur", content = @Content) })
	public ResponseEntity<ClientDTO> obtenirClient(@PathVariable Long clientId) {
		ClientDTO client = clientService.getClientById(clientId);
		return ResponseEntity.ok(client);
	}
}
