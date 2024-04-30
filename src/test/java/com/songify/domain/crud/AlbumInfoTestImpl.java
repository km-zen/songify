package com.songify.domain.crud;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

class AlbumInfoTestImpl implements AlbumInfo{

    private final Album album;

    AlbumInfoTestImpl(Album album) {
        this.album = album;
    }

    @Override
    public Long getId() {
        return album.getId();
    }

    @Override
    public String getTitle() {
        return album.getTitle();
    }

    @Override
    public Instant getReleaseDate() {
        return null;
    }

    @Override
    public Set<SongInfo> getSongs() {
        return album.getSongs().stream()
                .map(SongInfoTestImpl::new)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<ArtistInfo> getArtists() {
        return album.getArtists()
                .stream()
                .map(ArtistInfoImpl::new)
                .collect(Collectors.toSet());
    }
}
