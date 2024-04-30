package com.songify.domain.crud;

import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

class InMemoryArtistRepository implements ArtistRepository{

    Map<Long,Artist> db = new HashMap<>();
    private Long id = 0L;
    @Override
    public Artist save(final Artist artist) {
        db.put(id, artist);
        artist.setId(id);
        id++;
        return artist;
    }

    @Override
    public Set<Artist> findAll(final Pageable pageable) {
        return new HashSet<>(db.values());
    }

    @Override
    public int deleteById(final Long id) {
        db.remove(id);
        return id.intValue();
    }

    @Override
    public Optional<Artist> findById(final Long id) {
        return Optional.ofNullable(db.get(id));
    }

    @Override
    public int updateNameById(final String name, final Long id) {
        return 0;
    }
}
