package org.carrefour.mvp.service.impl;

import java.util.Arrays;
import java.util.List;

import org.carrefour.mvp.entities.Client;
import org.carrefour.mvp.entities.Order;
import org.carrefour.mvp.entities.TimeSlot;
import org.carrefour.mvp.entities.enums.ModeLivraison;
import org.carrefour.mvp.mappers.OrderMapper;
import org.carrefour.mvp.mappers.TimeSlotMapper;
import org.carrefour.mvp.mappers.dtos.OrderDTO;
import org.carrefour.mvp.repositories.ClientRepository;
import org.carrefour.mvp.repositories.OrderRepository;
import org.carrefour.mvp.repositories.TimeSlotRepository;
import org.carrefour.mvp.service.OrderService;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;
	private final ClientRepository clientRepository;
	private final TimeSlotRepository timeSlotRepository;
	private final OrderMapper orderMapper;
	//private final TimeSlotMapper timeSlotMapper;

	public OrderServiceImpl(OrderRepository orderRepository, ClientRepository clientRepository,
			TimeSlotRepository timeSlotRepository, OrderMapper orderMapper) {
		this.orderRepository = orderRepository;
		this.clientRepository = clientRepository;
		this.timeSlotRepository = timeSlotRepository;
		this.orderMapper = orderMapper;
	}

	@Override
	public OrderDTO createOrderForClient(Long clientId, OrderDTO orderDTO) {
		log.info("Création d'une nouvelle commande pour le client avec l'ID '{}'", clientId);

		Client client = clientRepository.findById(clientId).orElseThrow(() -> {
			log.warn("Aucun client trouvé pour l'ID '{}'", clientId);
			return new EntityNotFoundException("Client non trouvé pour l'ID: " + clientId);
		});

		Order order = new Order();
		order.getClients().add(client);
		order.setModeLivraison(orderDTO.getModeLivraison());
		//order.setTimeSlot(timeSlotMapper.toEntity(orderDTO.getTimeSlot()));

		Order savedOrder = orderRepository.save(order);
		log.info("Commande créée avec succès. ID de la commande: {}", savedOrder.getId());
		return orderMapper.toDto(savedOrder);
	}

	@Override
	public OrderDTO getClientOrder(Long clientId, Long orderId) {
	    log.info("Récupération de la commande avec l'ID '{}' pour le client '{}'", orderId, clientId);

	    Order order = orderRepository.findById(orderId).filter(o ->
	        o.getClients().stream().anyMatch(client -> client.getId().equals(clientId))
	    ).orElseThrow(() -> {
	        log.warn("Aucune commande trouvée pour le client '{}' et la commande '{}'", clientId, orderId);
	        return new EntityNotFoundException(
	            "Commande non trouvée pour clientId: " + clientId + " et orderId: " + orderId
	        );
	    });

	    log.info("Commande trouvée. ID: {}, Mode Livraison: {}", order.getId(), order.getModeLivraison());
	    return orderMapper.toDto(order);
	}


	@Override
	public OrderDTO setDeliveryMode(Long clientId, Long orderId, ModeLivraison modeLivraison) {
	    log.info("Définition du mode de livraison '{}' pour la commande '{}' du client '{}'", 
	        modeLivraison, orderId, clientId);

	    Order order = orderRepository.findById(orderId).orElseThrow(() -> {
	        log.warn("Aucune commande trouvée pour l'ID '{}'", orderId);
	        return new EntityNotFoundException("Commande non trouvée pour id: " + orderId);
	    });

	    boolean clientFound = order.getClients().stream()
	        .anyMatch(client -> client.getId().equals(clientId));

	    if (!clientFound) {
	        log.warn("La commande '{}' n'est pas associée au client '{}'", orderId, clientId);
	        throw new EntityNotFoundException("Aucune commande " + orderId + " trouvée pour le client " + clientId);
	    }

	    order.setModeLivraison(modeLivraison);
	    Order updatedOrder = orderRepository.save(order);
	    log.info("Mode de livraison défini avec succès. Nouveau mode: {}", updatedOrder.getModeLivraison());
	    return orderMapper.toDto(updatedOrder);
	}

	@Override
	public OrderDTO setTimeSlot(Long clientId, Long orderId, Long timeSlotId) {
	    log.info("Définition du créneau '{}' pour la commande '{}' du client '{}'", timeSlotId, orderId, clientId);

	    Order order = orderRepository.findById(orderId).orElseThrow(() -> {
	        log.warn("Aucune commande trouvée pour l'ID '{}'", orderId);
	        return new EntityNotFoundException("Commande non trouvée pour id: " + orderId);
	    });

	    // Check if the order is associated with the given clientId
	    boolean clientFound = order.getClients().stream()
	        .anyMatch(client -> client.getId().equals(clientId));

	    if (!clientFound) {
	        log.warn("La commande '{}' n'est pas associée au client '{}'", orderId, clientId);
	        throw new EntityNotFoundException("Aucune commande " + orderId + " trouvée pour le client " + clientId);
	    }

	    TimeSlot timeSlot = timeSlotRepository.findById(timeSlotId).orElseThrow(() -> {
	        log.warn("Aucun créneau trouvé pour l'ID '{}'", timeSlotId);
	        return new EntityNotFoundException("Créneau non trouvé pour id: " + timeSlotId);
	    });

	    if (order.getModeLivraison() == null) {
	        log.warn("Tentative de définir un créneau sans mode de livraison préalablement défini sur la commande '{}'",
	                orderId);
	        throw new IllegalStateException(
	                "Impossible de définir un créneau sans définir d'abord un mode de livraison");
	    }

	    if (!order.getModeLivraison().equals(timeSlot.getModeLivraison())) {
	        log.warn("Le mode de livraison du créneau '{}' ne correspond pas à celui de la commande '{}'", timeSlotId,
	                orderId);
	        throw new IllegalArgumentException("Le mode du créneau " + timeSlot.getModeLivraison()
	                + " ne correspond pas au mode de livraison de la commande " + order.getModeLivraison());
	    }

	    order.setTimeSlot(timeSlot);
	    Order updatedOrder = orderRepository.save(order);
	    log.info("Créneau défini avec succès pour la commande '{}'. Créneau: {}", orderId, timeSlotId);
	    return orderMapper.toDto(updatedOrder);
	}


	@Override
	public List<ModeLivraison> getAllModeLivraisons() {
		log.info("Récupération de tous les modes de livraison disponibles");
		List<ModeLivraison> modes = Arrays.asList(ModeLivraison.values());
		log.info("Modes de livraison récupérés avec succès : {}", modes);
		return modes;
	}
}
