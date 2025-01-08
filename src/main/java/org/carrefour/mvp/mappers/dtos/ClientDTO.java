package org.carrefour.mvp.mappers.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class ClientDTO extends BaseDTO {
	private String firstName;
	private String lastName;
	private String email;
}
