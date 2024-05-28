package com.example.springbasics.service.impl;

import com.example.springbasics.entity.User;
import com.example.springbasics.repository.UserRepository;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    static User savedUser;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserDetailsServiceImpl userDetailsService;

    @BeforeAll()
    static void setup() {
        savedUser = new User();
        savedUser.setId(1L);
        savedUser.setUserId("toro.yamada@gmail.com");
        savedUser.setPassword(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("toro.yamada"));
    }

    @Test
    void isEmailFormattedCorrectly() {
        User userToAdd = new User();
        userToAdd.setUserId("toro.yamada(notAt)gmail.com");
        userToAdd.setPassword("toro.yamada");

        when(userRepository.save(any())).thenReturn(savedUser);

        assertThrows(BadRequestException.class, () -> {
            userDetailsService.createUser(userToAdd);
        });

        userToAdd.setUserId("toro.yamada@gmail.com");
        assertDoesNotThrow(() -> {
            userDetailsService.createUser(userToAdd);
        });
    }

    @Test
    void userNotFoundTest() {
        User userToFind = new User();
        userToFind.setUserId("toro.yamada(notAt)gmail.com");
        userToFind.setPassword("toro.yamada");

        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername(userToFind.getUserId());
        });
    }

}