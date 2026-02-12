package com.yourCarYourWay.api.repositories;

import com.yourCarYourWay.api.models.Operator;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperatorRepository extends CrudRepository<Operator, Long> {
}
