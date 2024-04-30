package com.songify.domain.crud;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

class InMemoryGenreRepository implements GenreRepository{
    Map<Long,Genre> db = new HashMap<>();
    private Long id = 1L;

    InMemoryGenreRepository() {
        save(new Genre(1L,"default"));
    }

    @Override
    public Genre save(final Genre genre) {
        db.put(id,genre);
        id++;
        return genre;
    }

    @Override
    public int deleteById(final Long genreId) {
        db.remove(genreId);
        return 0;
    }

    @Override
    public Optional<Genre> findById(final long genreId) {
        return Optional.ofNullable(db.get(genreId));
    }

    @Override
    public Set<Genre> findAll() {
        return new HashSet<>(db.values());
    }
}
