package com.mine.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mine.users.dto.UserDTO;
import com.mine.users.rest.controller.UserController;
import com.mine.users.rest.exception.BadParameterException;
import com.mine.users.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UserController.class)
public class UserControllerTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    @WithMockUser(username = "admin", password = "adminPass", roles = "ADMIN")
    void whenInputIsOk_thenReturnsStatus200() throws Exception {
        UserDTO userDTO = new UserDTO("123", "Jim", "Morrison");
        String body = objectMapper.writeValueAsString(userDTO);

        mvc.perform(post("/users")
                .contentType("application/json")
                .content(body))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", password = "adminPass", roles = "ADMIN")
    void whenInputIsInvalid_thenReturnsStatus403() throws Exception {
        UserDTO userDTO = new UserDTO("123", "", "Morrison");
        String body = objectMapper.writeValueAsString(userDTO);

        mvc.perform(post("/users")
                .contentType("application/json")
                .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "admin", password = "adminPass", roles = "ADMIN")
    void whenUserExists__thenReturnsStatus400() throws Exception {
        UserDTO userDTO = new UserDTO("123", "Jim", "Morrison");
        String body = objectMapper.writeValueAsString(userDTO);
        when(userService.createUser(any())).thenThrow(new BadParameterException(""));

        mvc.perform(post("/users")
                .contentType("application/json")
                .content(body))
                .andExpect(status().isBadRequest());
    }
}