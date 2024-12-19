package org.carrefour.mvp.mappers;

import org.carrefour.mvp.entities.Order;
import org.carrefour.mvp.mappers.dtos.OrderDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { ClientMapper.class })
public interface OrderMapper {
	@Mapping(source = "clients", target = "clients")
	@Mapping(source = "timeSlot", target = "timeSlot")
	OrderDTO toDto(Order order);

	@Mapping(source = "clients", target = "clients")
	@Mapping(source = "timeSlot", target = "timeSlot")
	Order toEntity(OrderDTO orderDto);
}
