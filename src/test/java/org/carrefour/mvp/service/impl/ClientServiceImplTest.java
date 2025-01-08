package org.carrefour.mvp.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.carrefour.mvp.entities.Client;
import org.carrefour.mvp.mappers.ClientMapper;
import org.carrefour.mvp.mappers.dtos.ClientDTO;
import org.carrefour.mvp.repositories.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jakarta.persistence.EntityNotFoundException;

public class ClientServiceImplTest {

	@Mock
	private ClientRepository clientRepository;

	@Mock
	private ClientMapper clientMapper;

	@InjectMocks
	private ClientServiceImpl clientService;

	private ClientDTO clientDTO;
	private Client client;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		clientDTO = new ClientDTO();
		clientDTO.setLastName("ALILASTNAME");
		clientDTO.setFirstName("ALIFIRSTNAME");
		clientDTO.setEmail("alif.alil@example.com");
		client = new Client();
		client.setId(1L);
		client.setFirstName("ALIFIRSTNAME");
		client.setLastName("ALILASTNAME");
		client.setEmail("alif.alil@example.com");
	}

	@Test
	void testCreateClient_Success() {
		when(clientRepository.save(any(Client.class))).thenReturn(client);
		when(clientMapper.toDto(any(Client.class))).thenReturn(clientDTO);

		ClientDTO result = clientService.createClient(clientDTO);

		assertNotNull(result);
		assertEquals("ALIFIRSTNAME", result.getFirstName());
		assertEquals("ALILASTNAME", result.getLastName());
		assertEquals("alif.alil@example.com", result.getEmail());

		verify(clientRepository, times(1)).save(any(Client.class));
		verify(clientMapper, times(1)).toDto(any(Client.class));
	}

	@Test
	void testCreateClient_RepositoryFailure() {
		when(clientRepository.save(any(Client.class))).thenThrow(new RuntimeException("Database error"));

		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			clientService.createClient(clientDTO);
		});
		assertEquals("Database error", exception.getMessage());

		verify(clientRepository, times(1)).save(any(Client.class));
	}

	@Test
	void testGetClientById_ClientFound() {
		Long clientId = 1L;
		client.setId(clientId);
		
		when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
		when(clientMapper.toDto(client)).thenReturn(clientDTO);

		ClientDTO result = clientService.getClientById(clientId);

		assertNotNull(result);
		assertEquals(clientId, result.getId());
		assertEquals("ALIFIRSTNAME", result.getFirstName());
		assertEquals("ALILASTNAME", result.getLastName());
		assertEquals("alif.alil@example.com", result.getEmail());

		verify(clientRepository, times(1)).findById(clientId);
		verify(clientMapper, times(1)).toDto(client);
	}

	@Test
	void testGetClientById_ClientNotFound() {
		Long clientId = 1L;

		when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

		EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
			clientService.getClientById(clientId);
		});

		assertEquals("Client non trouvé pour l'ID: " + clientId, exception.getMessage());

		verify(clientRepository, times(1)).findById(clientId);
		verify(clientMapper, times(0)).toDto(any());
	}
}
