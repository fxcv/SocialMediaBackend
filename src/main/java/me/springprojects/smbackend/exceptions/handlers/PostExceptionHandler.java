package me.springprojects.smbackend.exceptions.handlers;

import me.springprojects.smbackend.exceptions.InvalidPostArgumentException;
import me.springprojects.smbackend.exceptions.dto.InvalidPostArgumentExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class PostExceptionHandler {

    @ExceptionHandler(value = {InvalidPostArgumentException.class})
    public ResponseEntity<Object> handleInvalidPostArgumentException(InvalidPostArgumentException postException){
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        InvalidPostArgumentExceptionDTO invalidPostArgumentExceptionDTO = new InvalidPostArgumentExceptionDTO(
                postException.getMessage(),
                ZonedDateTime.now(),
                httpStatus
        );
        return new ResponseEntity<>(invalidPostArgumentExceptionDTO, httpStatus);
    }
}
