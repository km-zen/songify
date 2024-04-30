package com.songify.infrastructure.crud.song.controller.error;

import com.songify.infrastructure.crud.song.controller.SongRestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = SongRestController.class)
public class HttpMediaTypeExceptionHandler {

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public String handleException(HttpMediaTypeNotAcceptableException exception) {
        return new ErrorSongResponseDto(exception.getMessage(), HttpStatus.NOT_ACCEPTABLE).toString();
    }
}
