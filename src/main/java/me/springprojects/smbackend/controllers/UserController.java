package me.springprojects.smbackend.controllers;

import lombok.AllArgsConstructor;
import me.springprojects.smbackend.entities.dto.UserDTO;
import me.springprojects.smbackend.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(path = "/add")
    public void registerUser(@RequestBody UserDTO userDTO){
        userService.registerUser(userDTO);
    }

    @GetMapping(path = "/all")
    public List<UserDTO> fetchAllUsers(){
        return userService.fetchAllUsers();
    }

    @PutMapping(path = "/update/email")
    public void updateUserEmail(@RequestParam(name = "id") int userId, @RequestParam(name = "email") String email){
        userService.updateUserEmail(userId, email);
    }

    @PutMapping(path = "/update/password")
    public void updateUserPassword(@RequestParam(name = "id") int userId, @RequestParam(name = "pass") String password){
        userService.updateUserPassword(userId, password);
    }
}
