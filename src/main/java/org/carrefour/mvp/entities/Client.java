package org.carrefour.mvp.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "CLIENTS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client extends BaseEntity {
	
	private String firstName;
	private String lastName;
	
	@Email
	@NotBlank
	private String email;
	
	@ManyToOne(optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

}
