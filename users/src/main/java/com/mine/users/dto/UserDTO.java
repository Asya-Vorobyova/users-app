package com.mine.users.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
    private String iban;

    private String firstName;

    private String lastName;
}
