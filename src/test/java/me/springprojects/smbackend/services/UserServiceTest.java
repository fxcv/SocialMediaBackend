package me.springprojects.smbackend.services;

import me.springprojects.smbackend.entities.SecurityAuthority;
import me.springprojects.smbackend.entities.User;
import me.springprojects.smbackend.entities.dto.UserDTO;
import me.springprojects.smbackend.exceptions.InvalidUserArgumentException;
import me.springprojects.smbackend.repositories.SecurityAuthorityRepository;
import me.springprojects.smbackend.repositories.UserRepository;
import me.springprojects.smbackend.utility.UserUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private SecurityAuthorityRepository securityAuthorityRepository;
    @MockBean
    private UserUtil userUtil;

    @Test
    public void checkIfRegistersAnUser(){
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("Alice");
        userDTO.setEmail("name@gmail.com");
        userDTO.setPassword("Password@2");
        SecurityAuthority sc = new SecurityAuthority();
        sc.setUsers(new ArrayList<>());
        given(securityAuthorityRepository.findAuthorityByName("USER")).willReturn(sc);

        userService.registerUser(userDTO);

        verify(userRepository, times(1)).save(any());
        verify(securityAuthorityRepository, times(1)).save(any());;
    }

    @Test
    public void checkIfThrowsAnExceptionWhenNotEnoughRegisteringInformation(){
        UserDTO userDTO = new UserDTO();

        assertThrows(InvalidUserArgumentException.class, () -> userService.registerUser(userDTO));
    }

    @Test
    public void checkIfThrowsAnExceptionWhenIncorrectUsernameHasBeenProvided(){
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("tes");
        userDTO.setEmail("name@gmail.com");
        userDTO.setPassword("Password@2");

        assertThrows(InvalidUserArgumentException.class, () -> userService.registerUser(userDTO));
    }

    @Test
    public void checkIfThrowsAnExceptionWhenIncorrectUsernameHasBeenProvided2(){
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("test#");
        userDTO.setEmail("name@gmail.com");
        userDTO.setPassword("Password@2");

        assertThrows(InvalidUserArgumentException.class, () -> userService.registerUser(userDTO));
    }

    @Test
    public void checkIfThrowsAnExceptionWhenIncorrectUsernameHasBeenProvided3(){
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("test ");
        userDTO.setEmail("name@gmail.com");
        userDTO.setPassword("Password@2");

        assertThrows(InvalidUserArgumentException.class, () -> userService.registerUser(userDTO));
    }

    @Test
    public void checkIfThrowsAnExceptionWhenIncorrectEmailHasBeenProvided(){
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("Alice");
        userDTO.setEmail("test");
        userDTO.setPassword("Password@2");

        assertThrows(InvalidUserArgumentException.class, () -> userService.registerUser(userDTO));
    }

    @Test
    public void checkIfThrowsAnExceptionWhenIncorrectPasswordHasBeenProvided(){
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("Alice");
        userDTO.setEmail("name@gmail.com");
        userDTO.setPassword("test");

        assertThrows(InvalidUserArgumentException.class, () -> userService.registerUser(userDTO));
    }

    @Test
    public void checkIfFetchesUsers(){
        User user1 = new User();
        User user2 = new User();
        user1.setUsername("user1"); user2.setUsername("user2");
        user1.setEmail("email1"); user2.setEmail("email2");
        user1.setPassword("password1"); user2.setPassword("password2");
        given(userRepository.findAll()).willReturn(List.of(user1, user2));

        List<UserDTO> users = userService.fetchAllUsers();

        UserDTO userDTO1 = users.get(0);
        UserDTO userDTO2 = users.get(1);

        assertEquals(user1.getUsername(), userDTO1.getUsername());
        assertEquals(user2.getUsername(), userDTO2.getUsername());
        assertEquals(user1.getEmail(), userDTO1.getEmail());
        assertEquals(user2.getEmail(), userDTO2.getEmail());
        assertEquals(user1.getPassword(), userDTO1.getPassword());
        assertEquals(user2.getPassword(), userDTO2.getPassword());
    }

    @Test
    public void checkIfUpdatesAnEmail(){
        String email = "updatedEmail@gmail.com";
        User user = new User();
        given(userUtil.getUser()).willReturn(user);

        userService.updateUserEmail(email);

        assertEquals(email, user.getEmail());
    }

    @Test
    public void checkIfUpdatesThePassword(){
        String password = "updatedPassword@2";
        User user = new User();
        given(userUtil.getUser()).willReturn(user);

        userService.updateUserPassword(password);

        assertEquals(password, user.getPassword());
    }

}