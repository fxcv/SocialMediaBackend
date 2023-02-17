package me.springprojects.smbackend.utility;

import lombok.AllArgsConstructor;
import me.springprojects.smbackend.entities.User;
import me.springprojects.smbackend.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class UserUtil {

    private final UserRepository userRepository;

    public User getUser(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> userOptional = userRepository.findUserByUsername(username);
        return userOptional.orElseThrow(() -> new IllegalStateException("Something went wrong."));
    }
}
