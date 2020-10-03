package com.mine.users.service;

import com.mine.users.dto.UserDTO;
import com.mine.users.rest.exception.BadParameterException;
import com.sun.istack.NotNull;

import java.util.List;

public interface UserService {

    UserDTO getUser(@NotNull String iban);

    List<UserDTO> getAllUsers();

    String createUser(@NotNull UserDTO userDTO) throws BadParameterException;

    String updateUser(@NotNull UserDTO userDTO) throws BadParameterException;

    void deleteUser(@NotNull String iban);
}
