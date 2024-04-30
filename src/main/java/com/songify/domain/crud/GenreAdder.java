package com.songify.domain.crud;

import org.springframework.stereotype.Service;

@Service
class GenreAdder {
    private final GenreRepository genreRepository;

    GenreAdder(final GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    GenreDto addGenre(String name){
        Genre genre = new Genre(name);
        Genre save = genreRepository.save(genre);
        return new GenreDto(save.getId(), save.getName());
    }
}
