package com.mine.users.rest.controller;

import com.mine.users.dto.UserDTO;
import com.mine.users.rest.exception.BadParameterException;
import com.mine.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping(value = "/{iban}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO getUser(@PathVariable String iban) {
        return service.getUser(iban);
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDTO> getAllUsers() {
        return service.getAllUsers();
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String addUser(@RequestBody UserDTO userDTO) throws BadParameterException {
        return service.createUser(userDTO);
    }

    @DeleteMapping(value = "/{iban}")
    public void deleteUser(@PathVariable String iban) {
        service.deleteUser(iban);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateUser(@RequestBody UserDTO userDTO) throws BadParameterException {
        service.updateUser(userDTO);
    }
}
