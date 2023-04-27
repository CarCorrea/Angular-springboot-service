package com.carlos.clientserviceapi.models.repository;

import com.carlos.clientserviceapi.models.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}
