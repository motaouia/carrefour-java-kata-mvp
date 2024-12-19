package org.carrefour.mvp.service;

import java.util.List;

import org.carrefour.mvp.entities.enums.ModeLivraison;
import org.carrefour.mvp.mappers.dtos.OrderDTO;

public interface OrderService {

	OrderDTO createOrderForClient(Long clientId, OrderDTO orderDTO);

	OrderDTO getClientOrder(Long clientId, Long orderId);

	OrderDTO setDeliveryMode(Long clientId, Long orderId, ModeLivraison modeLivraison);

	OrderDTO setTimeSlot(Long clientId, Long orderId, Long timeSlotId);

	List<ModeLivraison> getAllModeLivraisons();
}
