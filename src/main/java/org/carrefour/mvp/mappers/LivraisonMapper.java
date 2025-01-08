package org.carrefour.mvp.mappers;

import org.carrefour.mvp.entities.Livraison;
import org.carrefour.mvp.mappers.dtos.LivraisonDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { BaseMapper.class, ClientMapper.class })
public interface LivraisonMapper {
	@Mapping(source = "clients", target = "clients")
	@Mapping(source = "creneauxHoraire", target = "creneauxHoraire")
	LivraisonDTO toDto(Livraison livraison);

	@Mapping(source = "clients", target = "clients")
	@Mapping(source = "creneauxHoraire", target = "creneauxHoraire")
	Livraison toEntity(LivraisonDTO livraisonDto);
}
