package me.springprojects.smbackend.services;

import me.springprojects.smbackend.entities.User;
import me.springprojects.smbackend.entities.dto.UserDTO;
import me.springprojects.smbackend.exceptions.InvalidUserArgumentException;
import me.springprojects.smbackend.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

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

    @Test
    public void checkIfThrowsAnExceptionIfUserByIdHasNotBeenFound(){
        int userId = Integer.MAX_VALUE;

        assertThrows(InvalidUserArgumentException.class, () -> userService.updateUserEmail(userId, ""));
        assertThrows(InvalidUserArgumentException.class, () -> userService.updateUserPassword(userId, ""));
    }

    @Test
    public void checkIfUpdatesAnUser(){
        User user = new User();
        Optional<User> userOptional = Optional.of(user);
        given(userRepository.findById(any())).willReturn(userOptional);

        userService.updateUserEmail(999, "correctemail@example.com");
        userService.updateUserPassword(999, "correctPassword@2");

        verify(userRepository, times(2)).save(any());
        assertEquals("correctemail@example.com", user.getEmail());
        assertEquals("correctPassword@2", user.getPassword());
    }

    @Test
    public void checkIfFetchesUsers(){
        User user1 = new User();
        User user2 = new User();
        user1.setName("user1"); user2.setName("user2");
        user1.setLastname("user1"); user2.setLastname("user2");
        user1.setEmail("email1"); user2.setEmail("email2");
        user1.setPassword("password1"); user2.setPassword("password2");
        given(userRepository.findAll()).willReturn(List.of(user1, user2));

        List<UserDTO> users = userService.fetchAllUsers();

        UserDTO userDTO1 = users.get(0);
        UserDTO userDTO2 = users.get(1);

        assertEquals(user1.getName(), userDTO1.getName());
        assertEquals(user2.getName(), userDTO2.getName());
        assertEquals(user1.getLastname(), userDTO1.getLastname());
        assertEquals(user2.getLastname(), userDTO2.getLastname());
        assertEquals(user1.getEmail(), userDTO1.getEmail());
        assertEquals(user2.getEmail(), userDTO2.getEmail());
        assertEquals(user1.getPassword(), userDTO1.getPassword());
        assertEquals(user2.getPassword(), userDTO2.getPassword());
    }

}