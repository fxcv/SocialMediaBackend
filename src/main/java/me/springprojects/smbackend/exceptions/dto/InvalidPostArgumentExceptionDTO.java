package me.springprojects.smbackend.exceptions.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@AllArgsConstructor
@Getter
public class InvalidPostArgumentExceptionDTO {

    private final String message;
    private final ZonedDateTime date;
    private final HttpStatus httpStatus;
}
