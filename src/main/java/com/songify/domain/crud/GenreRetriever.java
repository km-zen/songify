package com.songify.domain.crud;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
class GenreRetriever {

    private final GenreRepository genreRepository;
    Genre findGenreById(final long genreId) {
       return genreRepository.findById(genreId)
               .orElseThrow(() -> new GenreNotFoundException("Genre with id: " + genreId + " not found"));
    }

    Set<GenreDto> findAll() {
        return genreRepository.findAll().stream()
                .map(genre -> GenreDto.builder()
                        .id(genre.getId())
                        .name(genre.getName())
                        .build())
                .collect(Collectors.toSet());
    }
}
