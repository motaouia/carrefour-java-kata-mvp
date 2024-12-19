package org.carrefour.mvp.mappers;

import java.util.List;

import org.carrefour.mvp.entities.TimeSlot;
import org.carrefour.mvp.mappers.dtos.TimeSlotDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TimeSlotMapper {
	TimeSlotMapper INSTANCE = Mappers.getMapper(TimeSlotMapper.class);

	TimeSlotDTO toDto(TimeSlot timeSlot);

	TimeSlot toEntity(TimeSlotDTO timeSlotDto);

	List<TimeSlotDTO> toDtoList(List<TimeSlot> timeSlots);

	List<TimeSlot> toEntityList(List<TimeSlotDTO> timeSlotDtos);
}
