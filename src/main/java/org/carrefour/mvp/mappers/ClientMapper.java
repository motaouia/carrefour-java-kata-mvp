package org.carrefour.mvp.mappers;

import org.carrefour.mvp.entities.Client;
import org.carrefour.mvp.mappers.dtos.ClientDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    ClientDTO toDto(Client client);

    Client toEntity(ClientDTO dto);
}
