package me.springprojects.smbackend.exceptions;

public class InvalidUserArgumentException extends RuntimeException {

    public InvalidUserArgumentException(String message){
        super(message);
    }
}
