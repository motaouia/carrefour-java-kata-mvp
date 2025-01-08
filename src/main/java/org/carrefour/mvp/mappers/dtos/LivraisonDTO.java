package org.carrefour.mvp.mappers.dtos;

import java.util.List;

import org.carrefour.mvp.entities.enums.ModeLivraison;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LivraisonDTO extends BaseDTO {
    private List<ClientDTO> clients;
    private CreneauxHoraireDTO creneauxHoraire;
    private ModeLivraison modeLivraison;
}
