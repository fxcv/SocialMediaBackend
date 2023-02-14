package me.springprojects.smbackend.exceptions.handlers;

import me.springprojects.smbackend.exceptions.InvalidUserArgumentException;
import me.springprojects.smbackend.exceptions.dto.InvalidUserArgumentExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(value = {InvalidUserArgumentException.class})
    public ResponseEntity<Object> handleInvalidUserArgumentException(InvalidUserArgumentException userException){
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        InvalidUserArgumentExceptionDTO invalidUserArgumentExceptionDTO = new InvalidUserArgumentExceptionDTO(
                userException.getMessage(),
                ZonedDateTime.now(),
                httpStatus
        );
        return new ResponseEntity<>(invalidUserArgumentExceptionDTO, httpStatus);
    }

}
