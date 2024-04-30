package com.songify.infrastructure.crud.song.controller.error;

import com.songify.infrastructure.crud.song.controller.SongRestController;
import com.songify.domain.crud.SongNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice(assignableTypes = SongRestController.class)
@Log4j2
public class SongErrorHandler {

    @ExceptionHandler(SongNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorSongResponseDto handleException(SongNotFoundException exception) {
        log.warn("SongNotFoundException error while accesing song");
        return new ErrorSongResponseDto(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
}
