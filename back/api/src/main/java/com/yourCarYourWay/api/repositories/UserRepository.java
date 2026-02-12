package com.yourCarYourWay.api.repositories;

import com.yourCarYourWay.api.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
