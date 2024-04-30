package com.songify.domain.crud;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

class InMemoryAlbumRepository implements AlbumRepository {
    Map<Long, Album> db = new HashMap<>();
    Long id = 0L;
    @Override
    public Album save(final Album album) {
        db.put(id,album);
        album.setId(id);
        id++;
        return album;
    }

    @Override
    public Optional<Album> findById(final Long id) {
        return Optional.ofNullable(db.get(id));
    }

    @Override
    public Optional<AlbumInfo> findAlbumByIdWithSongsAndArtists(final Long id) {
        Album album = db.get(id);
        AlbumInfoTestImpl albumInfoTest = new AlbumInfoTestImpl(album);
        return Optional.of(albumInfoTest);
    }

    @Override
    public Set<Album> findAllAlbumsByArtistId(final Long id) {
        return db.values().stream()
                .filter(album -> album.getArtists()
                        .stream()
                        .anyMatch(artist -> artist.getId().equals(id)))
                .collect(Collectors.toSet());
    }

    @Override
    public void deleteByIdIn(final Set<Long> ids) {
        ids.forEach(
                id -> db.remove(id)
        );

    }

    @Override
    public Set<Album> findAll() {
        return new HashSet<>(db.values());
    }
}
