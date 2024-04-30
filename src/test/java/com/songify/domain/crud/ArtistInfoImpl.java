package com.songify.domain.crud;

class ArtistInfoImpl implements AlbumInfo.ArtistInfo {
    private final Artist artist;

    ArtistInfoImpl(final Artist artist) {
        this.artist = artist;
    }

    @Override
    public Long getId() {
        return artist.getId();
    }

    @Override
    public String getName() {
        return artist.getName();
    }
}
