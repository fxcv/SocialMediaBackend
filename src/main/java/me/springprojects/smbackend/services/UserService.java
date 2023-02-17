package me.springprojects.smbackend.services;

import lombok.AllArgsConstructor;
import me.springprojects.smbackend.entities.SecurityAuthority;
import me.springprojects.smbackend.entities.User;
import me.springprojects.smbackend.entities.dto.UserDTO;
import me.springprojects.smbackend.repositories.SecurityAuthorityRepository;
import me.springprojects.smbackend.repositories.UserRepository;
import me.springprojects.smbackend.services.verifications.UserServiceVerification;
import me.springprojects.smbackend.utility.UserUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserServiceVerification userServiceVerification;
    private final SecurityAuthorityRepository securityAuthorityRepository;
    private final UserUtil userUtil;

    public List<UserDTO> fetchAllUsers(){
        return userRepository.findAll().stream()
                                       .map(user -> {
                                           UserDTO userDTO = new UserDTO();
                                           userDTO.setUsername(user.getUsername());
                                           userDTO.setEmail(user.getEmail());
                                           userDTO.setPassword(user.getPassword());
                                           return userDTO;
                                       })
                                       .collect(Collectors.toList());
    }

    public void registerUser(UserDTO userDTO){
        userServiceVerification.registerUserVerification(userDTO);
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setPostsCreated(new ArrayList<>());
        user.setAuthorities(new ArrayList<>());
        SecurityAuthority sc = securityAuthorityRepository.findAuthorityByName("USER");
        sc.getUsers().add(user);
        user.getAuthorities().add(sc);
        securityAuthorityRepository.save(sc);
        userRepository.save(user);
    }

    public void updateUserEmail(String email){
        userServiceVerification.changeEmailVerification(email);
        User user = userUtil.getUser();
        user.setEmail(email);
        userRepository.save(user);
    }

    public void updateUserPassword(String password){
        userServiceVerification.changePasswordVerification(password);
        User user = userUtil.getUser();
        user.setPassword(password);
        userRepository.save(user);
    }
}
