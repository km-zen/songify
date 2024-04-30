package com.songify.infrastructure.crud.genre;

import com.songify.domain.crud.GenreDto;
import com.songify.domain.crud.GenreRequestDto;
import com.songify.domain.crud.SongifyCrudFacade;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/genres")
class GenreController {

    private final SongifyCrudFacade songifyCrudFacade;


    @PostMapping
    public ResponseEntity<CreateGenreResponseDto> createGenre(@RequestBody GenreRequestDto dto){
        final GenreDto genreDto = songifyCrudFacade.addGenre(dto);
        CreateGenreResponseDto body = new CreateGenreResponseDto(genreDto.id(), genreDto.name());
        return ResponseEntity.ok(body);
    }

    @GetMapping
    ResponseEntity<GetAllGenresResponseDto> getGenres() {
        Set<GenreDto> genreDto = songifyCrudFacade.retrieveGenres();
        return ResponseEntity.ok(new GetAllGenresResponseDto(genreDto));
    }
}
