package me.springprojects.smbackend.controllers;

import lombok.AllArgsConstructor;
import me.springprojects.smbackend.entities.dto.UserDTO;
import me.springprojects.smbackend.services.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(path = "/add")
    public void registerUser(@RequestBody UserDTO userDTO){
        userService.registerUser(userDTO);
    }
}
