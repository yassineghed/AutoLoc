package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.domain.Client;

public interface IClientService {

    Client create(Client client);

    Client findById(Long id);

    List<Client> findAll();

    long count();

    boolean existsById(Long id);

    Client update(Long id, Client client);

    void deleteById(Long id);
}
