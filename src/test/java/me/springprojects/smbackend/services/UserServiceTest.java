package me.springprojects.smbackend.services;

import lombok.AllArgsConstructor;
import me.springprojects.smbackend.entities.dto.UserDTO;
import me.springprojects.smbackend.exceptions.InvalidUserArgumentException;
import me.springprojects.smbackend.repositories.UserRepository;
import me.springprojects.smbackend.services.verifications.UserServiceVerification;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;
    @MockBean
    private UserRepository userRepository;

    @Test
    public void checkIfRegistersAnUser(){
        UserDTO userDTO = new UserDTO();
        userDTO.setName("Alice");
        userDTO.setLastname("Johnson");
        userDTO.setEmail("name@gmail.com");
        userDTO.setPassword("Password@2");

        userService.registerUser(userDTO);

        verify(userRepository, times(1)).save(any());
    }

    @Test
    public void checkIfThrowsAnExceptionWhenNotEnoughRegisteringInformation(){
        UserDTO userDTO = new UserDTO();

        assertThrows(InvalidUserArgumentException.class, () -> userService.registerUser(userDTO));
    }

    @Test
    public void checkIfThrowsAnExceptionWhenIncorrectNameHasBeenProvided(){
        UserDTO userDTO = new UserDTO();
        userDTO.setName("test");
        userDTO.setLastname("Johnson");
        userDTO.setEmail("name@gmail.com");
        userDTO.setPassword("Password@2");

        assertThrows(InvalidUserArgumentException.class, () -> userService.registerUser(userDTO));
    }

    @Test
    public void checkIfThrowsAnExceptionWhenIncorrectLastnameHasBeenProvided(){
        UserDTO userDTO = new UserDTO();
        userDTO.setName("Alice");
        userDTO.setLastname("test");
        userDTO.setEmail("name@gmail.com");
        userDTO.setPassword("Password@2");

        assertThrows(InvalidUserArgumentException.class, () -> userService.registerUser(userDTO));
    }

    @Test
    public void checkIfThrowsAnExceptionWhenIncorrectEmailHasBeenProvided(){
        UserDTO userDTO = new UserDTO();
        userDTO.setName("Alice");
        userDTO.setLastname("Johnson");
        userDTO.setEmail("test");
        userDTO.setPassword("Password@2");

        assertThrows(InvalidUserArgumentException.class, () -> userService.registerUser(userDTO));
    }

    @Test
    public void checkIfThrowsAnExceptionWhenIncorrectPasswordHasBeenProvided(){
        UserDTO userDTO = new UserDTO();
        userDTO.setName("Alice");
        userDTO.setLastname("Johnson");
        userDTO.setEmail("name@gmail.com");
        userDTO.setPassword("test");

        assertThrows(InvalidUserArgumentException.class, () -> userService.registerUser(userDTO));
    }

}