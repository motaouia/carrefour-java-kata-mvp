package org.carrefour.mvp.service;

import org.carrefour.mvp.mappers.dtos.ClientDTO;

public interface ClientService {

	ClientDTO createClient(ClientDTO clientDTO);

	ClientDTO getClientById(Long clientId);
}
