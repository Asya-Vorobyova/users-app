package com.mine.users.service;

import com.mine.users.dto.UserDTO;
import com.mine.users.model.User;
import com.mine.users.model.repository.UserRepository;
import com.mine.users.rest.exception.BadParameterException;
import com.sun.istack.NotNull;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserService {

    private UserRepository repository;

    public UserDTO getUser(@NotNull String iban) {
        User user = repository.findById(iban).orElse(null);
        return user == null ? null : transform(user);
    }

    public List<UserDTO> getAllUsers() {
        return StreamSupport.stream(repository.findAll().spliterator(), false).map(this::transform)
                .collect(Collectors.toList());
    }

    @Transactional
    public String createUser(@NotNull UserDTO userDTO) throws BadParameterException {
        User user = repository.findById(userDTO.getIban()).orElse(null);
        if (user != null) {
            throw new BadParameterException("Such IBAN already exists");
        } else {
            return repository.save(new User(userDTO.getIban(), userDTO.getFirstName(), userDTO.getLastName())).getIban();
        }
    }

    @Transactional
    public String updateUser(@NotNull UserDTO userDTO)throws BadParameterException {
        User user = repository.findById(userDTO.getIban()).orElse(null);
        if (user != null) {
            return repository.save(new User(userDTO.getIban(), userDTO.getFirstName(), userDTO.getLastName())).getIban();
        } else {
            throw new BadParameterException("User with this IBAN doesn't exist");
        }
    }

    public void deleteUser(@NotNull String iban) {
        repository.deleteById(iban);
    }

    private UserDTO transform(User user) {
        return new UserDTO(user.getIban(), user.getFirstName(), user.getLastName());
    }
}
