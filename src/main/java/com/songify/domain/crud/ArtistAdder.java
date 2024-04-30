package com.songify.domain.crud;

import com.songify.domain.crud.dto.ArtistDto;
import com.songify.infrastructure.crud.artist.ArtistRequestDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Set;
import java.util.UUID;

@Service
class ArtistAdder {
    private final ArtistRepository artistRepository;

    ArtistAdder(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    ArtistDto addArtist(final String name) {
        Artist artist = new Artist(name);
        Artist save = artistRepository.save(artist);
        return new ArtistDto(save.getId(), save.getName());
    }

    ArtistDto addArtistWithDefaultAlbumAndSong(final ArtistRequestDto dto) {
        String artistName = dto.name();
        Artist artist = saveArtistWithDefaultAlbumAndSong(artistName);
        return new ArtistDto(artist.getId(), artist.getName());
    }

    private Artist saveArtistWithDefaultAlbumAndSong(final String name) {
        Artist artist = new Artist(name);
        Album album = new Album();
        album.setTitle("default title" + UUID.randomUUID());
        album.setReleaseDate(LocalDateTime.now().toInstant(ZoneOffset.UTC));
        Song song = new Song("default song" + UUID.randomUUID());
        album.addSongToAlbum(song);
        artist.setAlbums(Set.of(album));
        return artistRepository.save(artist);
    }
}
