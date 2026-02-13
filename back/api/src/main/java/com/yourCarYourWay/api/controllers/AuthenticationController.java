package com.yourCarYourWay.api.controllers;

import com.yourCarYourWay.api.DTO.LoginDto;
import com.yourCarYourWay.api.DTO.UserDto;

import com.yourCarYourWay.api.services.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginDto loginDto) {
        UserDto userDto =  this.authenticationService.login(loginDto);
        if(userDto == null){
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(userDto);
    }
}
