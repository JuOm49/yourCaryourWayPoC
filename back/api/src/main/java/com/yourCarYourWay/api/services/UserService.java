package com.yourCarYourWay.api.services;

import com.yourCarYourWay.api.DTO.UserDto;
import com.yourCarYourWay.api.models.Client;
import com.yourCarYourWay.api.models.Operator;
import com.yourCarYourWay.api.models.User;
import com.yourCarYourWay.api.repositories.ClientRepository;
import com.yourCarYourWay.api.repositories.OperatorRepository;
import com.yourCarYourWay.api.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private final OperatorRepository operatorRepository;

    public UserService(ClientRepository clientRepository, UserRepository userRepository, OperatorRepository operatorRepository) {
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
        this.operatorRepository = operatorRepository;
    }

    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        Optional<Client> client = clientRepository.findById(user.getId());
        if(client.isPresent()){
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setFirstName(user.getFirstName());
            userDto.setLastName(user.getLastName());
            userDto.setGender(user.getGender());
            return userDto;
        }
        else {
            Optional<Operator> operator = operatorRepository.findById(user.getId());

            if (operator.isPresent()) {
                UserDto userDto = new UserDto();
                userDto.setId(user.getId());
                userDto.setFirstName(user.getFirstName());
                userDto.setLastName(user.getLastName());
                userDto.setGender(user.getGender());
                userDto.setRole(operator.get().getRole());
                return userDto;
            }
            else {
                throw new RuntimeException("User not found");
            }
        }

    }

}
