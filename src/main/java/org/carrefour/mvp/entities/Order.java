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
@Table(name = "ORDERS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order extends BaseEntity {

	/*@ManyToOne(optional = false)
	@JoinColumn(name = "client_id", nullable = false)
	private Client client;*/
	
	@OneToMany(mappedBy = "order")
    private List<Client> clients;

	@ManyToOne
	@JoinColumn(name = "time_slot_id")
	private TimeSlot timeSlot;

	@Enumerated(EnumType.STRING)
	private ModeLivraison modeLivraison;

}
