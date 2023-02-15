package me.springprojects.smbackend.services;

import lombok.AllArgsConstructor;
import me.springprojects.smbackend.entities.User;
import me.springprojects.smbackend.entities.dto.UserDTO;
import me.springprojects.smbackend.repositories.UserRepository;
import me.springprojects.smbackend.services.verifications.UserServiceVerification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserServiceVerification userServiceVerification;

    public List<UserDTO> fetchAllUsers(){
        return userRepository.findAll().stream()
                                       .map(user -> {
                                           UserDTO userDTO = new UserDTO();
                                           userDTO.setName(user.getName());
                                           userDTO.setLastname(user.getLastname());
                                           userDTO.setEmail(user.getEmail());
                                           userDTO.setPassword(user.getPassword());
                                           return userDTO;
                                       })
                                       .collect(Collectors.toList());
    }

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

    public void updateUserEmail(int userId, String email){
        Optional<User> userOptional = userRepository.findById(userId);
        userServiceVerification.verificateUserExistence(userOptional);
        userServiceVerification.changeEmailVerification(email);
        User user = userOptional.get();
        user.setEmail(email);
        userRepository.save(user);
    }

    public void updateUserPassword(int userId, String password){
        Optional<User> userOptional = userRepository.findById(userId);
        userServiceVerification.verificateUserExistence(userOptional);
        userServiceVerification.changePasswordVerification(password);
        User user = userOptional.get();
        user.setPassword(password);
        userRepository.save(user);
    }
}
