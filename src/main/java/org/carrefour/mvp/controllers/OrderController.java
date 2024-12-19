package org.carrefour.mvp.controllers;

import java.util.List;

import org.carrefour.mvp.entities.enums.ModeLivraison;
import org.carrefour.mvp.mappers.dtos.OrderDTO;
import org.carrefour.mvp.mappers.dtos.TimeSlotDTO;
import org.carrefour.mvp.service.OrderService;
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
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Order Management", description = "APIs related to listing and querying Orders")
public class OrderController {

	private final OrderService orderService;

	@PutMapping("/{orderId}/clients/{clientId}")
	@Operation(summary = "Set delivery mode for an order",
			description = "Sets the delivery mode for a specific order associated with a client")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Delivery mode updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderDTO.class))),
			@ApiResponse(responseCode = "404", description = "Order or Client not found", content = @Content),
			@ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
	public ResponseEntity<OrderDTO> setDeliveryMode(@PathVariable Long orderId, @PathVariable Long clientId,
			@RequestBody OrderDTO orderDTO) {
		OrderDTO updatedOrder = orderService.setDeliveryMode(clientId, orderId, orderDTO.getModeLivraison());
		return ResponseEntity.ok(updatedOrder);
	}

	@PostMapping("/{clientId}")
	@Operation(summary = "Create a new order for a client", description = "Creates a new order associated with the given client ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Order created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderDTO.class))),
			@ApiResponse(responseCode = "404", description = "Client or TimeSlot not found", content = @Content),
			@ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
	public ResponseEntity<OrderDTO> createOrderForClient(@PathVariable Long clientId,
			@Valid @RequestBody OrderDTO orderDTO) {
		OrderDTO order = orderService.createOrderForClient(clientId, orderDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(order);
	}

	@PutMapping("/{clientId}/orders/{orderId}/timeslot")
	@Operation(summary = "Set the time slot of an order", description = "Assigns a specific time slot to the given order")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Time slot updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderDTO.class))),
			@ApiResponse(responseCode = "400", description = "Invalid time slot", content = @Content),
			@ApiResponse(responseCode = "404", description = "Client, Order or Time Slot not found", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
	public ResponseEntity<OrderDTO> setTimeSlot(@PathVariable Long clientId, @PathVariable Long orderId,
			@Valid @RequestBody TimeSlotDTO timeSlotDTO) {
		OrderDTO order = orderService.setTimeSlot(clientId, orderId, timeSlotDTO.getId());
		return ResponseEntity.ok(order);

	}
	
	@GetMapping
	@Operation(summary = "Get all delivery modes", description = "Retrieves a list of all available delivery modes")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Delivery modes retrieved successfully", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
	public ResponseEntity<List<ModeLivraison>> getAllDeliveryModes() {
		List<ModeLivraison> modes = orderService.getAllModeLivraisons();
		return ResponseEntity.ok(modes);
	}
}
