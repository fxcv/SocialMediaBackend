package me.springprojects.smbackend.services;

import lombok.AllArgsConstructor;
import me.springprojects.smbackend.entities.User;
import me.springprojects.smbackend.entities.dto.UserDTO;
import me.springprojects.smbackend.repositories.UserRepository;
import me.springprojects.smbackend.services.verifications.UserServiceVerification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserServiceVerification userServiceVerification;

    public void registerUser(UserDTO userDTO){
        userServiceVerification.registerUserVerification(userDTO);

        User user = new User();
        user.setName(userDTO.getName());
        user.setLastname(userDTO.getLastname());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setGroupsCreated(new ArrayList<>());
        user.setGroupsJoined(new ArrayList<>());
        user.setPostsCreated(new ArrayList<>());
        userRepository.save(user);
    }
}
