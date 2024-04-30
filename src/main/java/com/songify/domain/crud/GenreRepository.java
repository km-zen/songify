package com.songify.domain.crud;

import org.springframework.data.repository.Repository;

import java.util.Optional;
import java.util.Set;

interface GenreRepository extends Repository<Genre, Long> {

    Genre save(Genre genre);

    int deleteById(Long genreId);

    Optional<Genre> findById(long genreId);

    Set<Genre> findAll();
}
