package com.carlos.clientserviceapi.models.service;

import com.carlos.clientserviceapi.models.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClientService {

    public List<Client> findAll();

    public Page<Client> findAll(Pageable pageable);

    public Client save(Client client);

    public void delete(Long id);

    public Client findById(Long id);
}
