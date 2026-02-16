package com.yourCarYourWay.api.services;

import com.yourCarYourWay.api.DTO.LoginDto;
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
public class AuthenticationService {
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final OperatorRepository operatorRepository;

    public AuthenticationService(UserRepository userRepository, ClientRepository clientRepository, OperatorRepository operatorRepository) {
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
        this.operatorRepository = operatorRepository;
    }

    public UserDto login(LoginDto loginDto) {
        User user = userRepository.findByEmail(
                loginDto.getEmail()).orElseThrow(() -> new RuntimeException("User not found")
        );

        if (user.getPassword().equals(loginDto.getPassword())) {
            Optional<Client> client = clientRepository.findById(user.getId());

            if (client.isPresent()) {
                UserDto userDto = new UserDto();
                userDto.setId(user.getId());
                userDto.setEmail(user.getEmail());
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
                    userDto.setEmail(user.getEmail());
                    userDto.setFirstName(user.getFirstName());
                    userDto.setLastName(user.getLastName());
                    userDto.setGender(user.getGender());
                    userDto.setRole(operator.get().getRole());

                    operator.get().setAvailability(true);

                    userDto.setAvailability(operator.get().getAvailability());

                    operatorRepository.save(operator.get());

                    return userDto;
                }
            }

        } else {
            throw new RuntimeException("Invalid password");
        }
        return null;
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void logout(Long userId) {
        Optional<Operator> operator = operatorRepository.findById(userId);
        if (operator.isPresent()) {
            operator.get().setAvailability(false);
            operatorRepository.save(operator.get());
        }
    }

}
