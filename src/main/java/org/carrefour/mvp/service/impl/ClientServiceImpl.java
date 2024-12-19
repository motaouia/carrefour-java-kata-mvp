package org.carrefour.mvp.service.impl;

import org.carrefour.mvp.entities.Client;
import org.carrefour.mvp.mappers.ClientMapper;
import org.carrefour.mvp.mappers.dtos.ClientDTO;
import org.carrefour.mvp.repositories.ClientRepository;
import org.carrefour.mvp.service.ClientService;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ClientServiceImpl implements ClientService {

	private final ClientRepository clientRepository;
	private final ClientMapper clientMapper;

	public ClientServiceImpl(ClientRepository clientRepository, ClientMapper clientMapper) {
		this.clientRepository = clientRepository;
		this.clientMapper = clientMapper;
	}

	@Override
	public ClientDTO createClient(ClientDTO clientDTO) {
		log.info("Création d'un nouveau client avec le prénom '{}' et le nom '{}'", clientDTO.getFirstName(),
				clientDTO.getLastName());

		Client client = new Client();
		client.setFirstName(clientDTO.getFirstName());
		client.setLastName(clientDTO.getLastName());
		client.setEmail(clientDTO.getEmail());
		Client savedClient = clientRepository.save(client);
		log.info("Client créé avec succès. ID du client: {}", savedClient.getId());
		return clientMapper.toDto(savedClient);
	}

	@Override
	public ClientDTO getClientById(Long clientId) {
		log.info("Récupération du client avec l'ID '{}'", clientId);
		Client client = clientRepository.findById(clientId).orElseThrow(() -> {
			log.warn("Aucun client trouvé pour l'ID '{}'", clientId);
			return new EntityNotFoundException("Client non trouvé pour l'ID: " + clientId);
		});
		log.info("Client trouvé: {} {}", client.getFirstName(), client.getLastName());
		return clientMapper.toDto(client);
	}

}
