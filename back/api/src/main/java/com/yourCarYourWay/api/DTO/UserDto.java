package com.yourCarYourWay.api.DTO;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String gender;
    private String lastName;
    private String firstName;
    private String email;
    private String role;
    private Boolean availability;
}
