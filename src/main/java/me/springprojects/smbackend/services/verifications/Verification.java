package me.springprojects.smbackend.services.verifications;

import me.springprojects.smbackend.entities.User;
import me.springprojects.smbackend.exceptions.InvalidUserArgumentException;

import java.util.Optional;

public abstract class Verification {

    public void verificateUserExistence(Optional<User> userOptional){
        if(userOptional.isEmpty()) throw new InvalidUserArgumentException("User does not exist.");
    }
}
