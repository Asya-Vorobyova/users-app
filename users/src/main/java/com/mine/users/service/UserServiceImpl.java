package com.mine.users.service;

import com.mine.users.dto.UserDTO;
import com.mine.users.model.UserData;
import com.mine.users.model.repository.UserDataRepository;
import com.mine.users.rest.exception.BadParameterException;
import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDataRepository repository;

    public UserDTO getUser(@NotNull String iban) {
        UserData userData = repository.findById(iban).orElse(null);
        return userData == null ? null : transform(userData);
    }

    public List<UserDTO> getAllUsers() {
        return StreamSupport.stream(repository.findAll().spliterator(), false).map(this::transform)
                .collect(Collectors.toList());
    }

    @Transactional
    public String createUser(@NotNull UserDTO userDTO) throws BadParameterException {
        UserData userData = repository.findById(userDTO.getIban()).orElse(null);
        if (userData != null) {
            throw new BadParameterException("Such IBAN already exists");
        } else {
            return repository.save(new UserData(userDTO.getIban(), userDTO.getFirstName(), userDTO.getLastName())).getIban();
        }
    }

    @Transactional
    public String updateUser(@NotNull UserDTO userDTO)throws BadParameterException {
        UserData userData = repository.findById(userDTO.getIban()).orElse(null);
        if (userData != null) {
            return repository.save(new UserData(userDTO.getIban(), userDTO.getFirstName(), userDTO.getLastName())).getIban();
        } else {
            throw new BadParameterException("UserData with this IBAN doesn't exist");
        }
    }

    public void deleteUser(@NotNull String iban) {
        repository.deleteById(iban);
    }

    private UserDTO transform(UserData userData) {
        return new UserDTO(userData.getIban(), userData.getFirstName(), userData.getLastName());
    }
}
