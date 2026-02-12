package com.yourCarYourWay.api.repositories;

import com.yourCarYourWay.api.models.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {
}
