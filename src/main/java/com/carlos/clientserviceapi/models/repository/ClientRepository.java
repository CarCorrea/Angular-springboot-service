package com.carlos.clientserviceapi.models.repository;

import com.carlos.clientserviceapi.models.entity.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {
}
