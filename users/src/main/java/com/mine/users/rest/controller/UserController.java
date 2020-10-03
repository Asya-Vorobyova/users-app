package com.mine.users.rest.controller;

import com.mine.users.dto.UserDTO;
import com.mine.users.rest.exception.BadParameterException;
import com.mine.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping(value = "/users/{iban}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO getUser(@PathVariable String iban) {
        return service.getUser(iban);
    }

    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDTO> getAllUsers() {
        return service.getAllUsers();
    }

    @PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String addUser(@Valid @RequestBody UserDTO userDTO) throws BadParameterException {
        return service.createUser(userDTO);
    }

    @DeleteMapping(value = "/users/{iban}")
    public void deleteUser(@PathVariable String iban) {
        service.deleteUser(iban);
    }

    @PutMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateUser(@Valid @RequestBody UserDTO userDTO) throws BadParameterException {
        service.updateUser(userDTO);
    }
}
