package org.carrefour.mvp.entities;

import java.util.List;

import org.carrefour.mvp.entities.enums.ModeLivraison;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "LIVRAISONS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Livraison extends BaseEntity {

	@OneToMany(mappedBy = "livraison")
	private List<Client> clients;

	@ManyToOne
	@JoinColumn(name = "time_slot_id")
	private CreneauxHoraire creneauxHoraire;

	@Enumerated(EnumType.STRING)
	private ModeLivraison modeLivraison;

}
