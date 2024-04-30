package com.songify.domain.crud;

import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

class InMemorySongRepository implements SongRepository{

    Map<Long,Song> db = new HashMap<>();
    Long id = 0L;
    @Override
    public List<Song> findAll(final Pageable pageable) {
        return db.values().stream().toList();
    }

    @Override
    public Optional<Song> findById(final Long id) {
        Song value = db.get(id);
        return Optional.ofNullable(value);
    }

    @Override
    public void deleteById(final Long id) {
        db.remove(id);
    }

    @Override
    public void update(final Long id, final Song newSong) {

    }

    @Override
    public boolean existsById(final Long id) {
        return false;
    }

    @Override
    public Song save(final Song song) {
        db.put(id,song);
        song.setId(id);
        id++;
        return song;
    }

    @Override
    public void deleteByIdIn(final Set<Long> ids) {
        ids.forEach(
                id -> db.remove(id)
        );
    }
}
