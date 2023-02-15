package me.springprojects.smbackend.exceptions;

public class InvalidPostArgumentException extends RuntimeException{

    public InvalidPostArgumentException(String message){
        super(message);
    }
}
