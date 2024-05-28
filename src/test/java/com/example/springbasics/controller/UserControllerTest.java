package com.example.springbasics.controller;

import com.example.springbasics.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void addUser() throws Exception {

        User userToAdd = new User();
        userToAdd.setUserId("toro.yamada@gmail.com");
        userToAdd.setPassword("toro.yamada");

        ObjectMapper mapper = new ObjectMapper();
        String StringUser = mapper.writeValueAsString(userToAdd);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(StringUser))

                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    void addUser_DuplicateUser_Error() throws Exception {
        User userToAdd = new User();
        userToAdd.setUserId("toro2.yamada@gmail.com");
        userToAdd.setPassword("toro.yamada");

        ObjectMapper mapper = new ObjectMapper();
        String StringUser = mapper.writeValueAsString(userToAdd);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(StringUser))
                .andExpect(status().isCreated());

        User userToAdd2 = new User();
        userToAdd2.setUserId("toro2.yamada@gmail.com");
        userToAdd2.setPassword("other.userPassword");

        StringUser = mapper.writeValueAsString(userToAdd2);

        String finalStringUser = StringUser;
        assertThrows(BadRequestException.class, () -> {
            mockMvc.perform(post("/users")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(finalStringUser))
                    .andExpect(jsonPath("$.message").value("Duplicate user found"));
        });
    }

}