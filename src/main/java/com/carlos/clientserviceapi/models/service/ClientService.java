package com.carlos.clientserviceapi.models.service;

import com.carlos.clientserviceapi.models.entity.Client;

import java.util.List;

public interface ClientService {

    public List<Client> findAll();

    public Client save(Client client);

    public void delete(Long id);

    public Client findById(Long id);
}
