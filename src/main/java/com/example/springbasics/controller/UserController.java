package com.example.springbasics.controller;

import com.example.springbasics.entity.User;
import com.example.springbasics.service.impl.UserDetailsServiceImpl;

import org.apache.coyote.BadRequestException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    final
    UserDetailsServiceImpl userDetailsService;

    public UserController(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostMapping()
    public ResponseEntity<User> createUser(@RequestBody User user) throws BadRequestException {
        try {
            return new ResponseEntity<>(userDetailsService.createUser(user), HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateKeyException("User ID already exists", e);
        }
    }
}
