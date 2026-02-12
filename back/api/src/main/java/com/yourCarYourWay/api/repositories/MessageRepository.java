package com.yourCarYourWay.api.repositories;

import com.yourCarYourWay.api.models.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Long> {
}
