package org.carrefour.mvp.controllers;

import org.carrefour.mvp.mappers.dtos.ClientDTO;
import org.carrefour.mvp.service.ClientService;
import org.carrefour.mvp.service.OrderService;
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
@RequestMapping("/clients")
@Tag(name = "Client Management", description = "APIs related to client creation and orders")
public class ClientController {

	private final ClientService clientService;
	private final OrderService orderService;

	public ClientController(ClientService clientService, OrderService orderService) {
		this.clientService = clientService;
		this.orderService = orderService;
	}

	@PostMapping
	@Operation(summary = "Create a new client", description = "Creates a new client with provided details")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Client created successfully", content = @Content),
			@ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
	public ResponseEntity<ClientDTO> createClient(@RequestBody ClientDTO clientDTO) {
		ClientDTO client = clientService.createClient(clientDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(client);
	}

	@GetMapping("/{clientId}")
	@Operation(summary = "Get client details", description = "Retrieves detailed information of a client by their ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Client found", content = @Content),
			@ApiResponse(responseCode = "404", description = "Client not found", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
	public ResponseEntity<ClientDTO> getClient(@PathVariable Long clientId) {
		ClientDTO client = clientService.getClientById(clientId);
		return ResponseEntity.ok(client);

	}

}
