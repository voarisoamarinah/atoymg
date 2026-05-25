package org.atoy.atoymg.services.interfaces;

import org.atoy.atoymg.models.Client;
import org.atoy.atoymg.models.dto.ClientSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;    

public interface ClientService {
    Page<Client> getAllClient(Pageable pageable);

    Page<Client> getAllClient(Pageable pageable, ClientSearch object);

    Client getClientById(Long id);

    

    Client createClient(Client client);

    Client updateClient(Long id, Client client);

    void deleteClient(Long id);
    
}
