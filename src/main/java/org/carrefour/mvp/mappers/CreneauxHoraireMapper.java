package org.carrefour.mvp.mappers;

import java.util.List;

import org.carrefour.mvp.entities.CreneauxHoraire;
import org.carrefour.mvp.mappers.dtos.CreneauxHoraireDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = BaseMapper.class)
public interface CreneauxHoraireMapper {
	CreneauxHoraireMapper INSTANCE = Mappers.getMapper(CreneauxHoraireMapper.class);

	CreneauxHoraireDTO toDto(CreneauxHoraire creneauxHoraire);

	CreneauxHoraire toEntity(CreneauxHoraireDTO creneauxHoraireDto);

	List<CreneauxHoraireDTO> toDtoList(List<CreneauxHoraire> creneauxHoraires);

	List<CreneauxHoraire> toEntityList(List<CreneauxHoraireDTO> creneauxHoraireDtos);
}
