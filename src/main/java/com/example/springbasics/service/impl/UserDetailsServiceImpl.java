package com.example.springbasics.service.impl;

import com.example.springbasics.entity.User;
import com.example.springbasics.repository.UserRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    final
    UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * get's called by Spring security's UserDetailsService in order to check if username exists
     * @param username
     * @return user entity if found
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> foundUser = userRepository.findByUserId(username);
        return foundUser.orElseThrow(() -> new UsernameNotFoundException("user not found."));
    }

    /**
     * Create and insert a user and encrypt his password
     * @param user to insert in DB
     * @return ok from jpa insert query
     * @throws BadRequestException
     */
    public User createUser(User user) throws BadRequestException {
        if (!isValidEmail(user.getUserId())) {
            throw new BadRequestException("no valid email.");
        }
        String encryptedPW = PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(user.getPassword());
        user.setPassword(encryptedPW);
        return userRepository.save(user);
    }

    /**
     * Check if email is a valid email pattern
     * @param email string
     * @return true or false
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }

        // Regular expression pattern for validating email addresses
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

}
