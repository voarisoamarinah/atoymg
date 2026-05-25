package org.atoy.atoymg.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;      
import org.springframework.data.jpa.domain.Specification;
import org.atoy.atoymg.models.Client;
import org.atoy.atoymg.models.dto.ClientSearch;
import org.springframework.stereotype.Service;
import org.atoy.atoymg.repositories.ClientRepository;
import java.util.Optional;
import org.atoy.atoymg.services.interfaces.ClientService;
import org.atoy.atoymg.specification.ClientSpecification;
import org.atoy.atoymg.exception.ResourceNotFoundException;
import org.atoy.atoymg.exception.InternalServerErrorException;

@Service
public class DefaultClientService implements ClientService {
	private final ClientRepository clientRepository;

	public DefaultClientService(ClientRepository clientRepository) {
	   this.clientRepository = clientRepository;
	}

	@Override
	public Page<Client> getAllClient(Pageable pageable) {
	    try {
	        return clientRepository.findAll(pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving client", ex);
	    }
	}

	@Override
	public Page<Client> getAllClient(Pageable pageable, ClientSearch object) {
	    try {
	        Specification<Client> spec=ClientSpecification.filter(object);
	        return clientRepository.findAll(spec, pageable);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while retrieving client", ex);
	    }
	}

	@Override
	public Client getClientById(Long id) {
	    Optional<Client> client = clientRepository.findById(id);
	    if (client.isPresent()) {
	        return client.get();
	    } else {
	        throw new ResourceNotFoundException("Client not found with id : " + id);
	    }
	}

 

	@Override
	public Client createClient(Client client) {
	    try {
	        return clientRepository.save(client);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while creating client", ex);
	    }
	}

	@Override
	public Client updateClient(Long id, Client client) {
	    Optional<Client> existingClient = clientRepository.findById(id);
	    if (existingClient.isPresent()) {
	        client.setId(id);
	        try {
	            return clientRepository.save(client);
	        } catch (Exception ex) {
	            throw new InternalServerErrorException("Error while updating client", ex);
	        }
	    } else {
	        throw new ResourceNotFoundException("Client not found with id : " + id);
	    }
	}

	@Override
	public void deleteClient(Long id) {
	    try {
	        clientRepository.deleteById(id);
	    } catch (Exception ex) {
	        throw new InternalServerErrorException("Error while deleting client", ex);
	    }
	}


}
