package org.carrefour.mvp.mappers.dtos;

import java.util.List;

import org.carrefour.mvp.entities.enums.ModeLivraison;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDTO {
    private Long id;
    private List<ClientDTO> clients;
    private TimeSlotDTO timeSlot;
    private ModeLivraison modeLivraison;
}
