package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumDto;
import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.SongDto;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
class AlbumRetriever {
    private final AlbumRepository albumRepository;

    AlbumRetriever(final AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }



    AlbumInfo findAlbumByIdWithArtistsAndSongs(final Long id) {
        return albumRepository.findAlbumByIdWithSongsAndArtists(id)
                .orElseThrow(() -> new AlbumNotFoundException("album with id: " + id + " not found"));

//        AlbumDto albumDto = new AlbumDto(id, album.getTitle());
//        Set<Song> songs = album.getSongs();
//        Set<Artist> artists = album.getArtists();
//
//        Set<SongDto> songsDto = songs.stream()
//                .map(song -> new SongDto(song.getId(), song.getName())).collect(Collectors.toSet());
//        Set<ArtistDto> artistsDto = artists.stream()
//                .map(artist -> new ArtistDto(artist.getId(), artist.getName())).collect(Collectors.toSet());
//
//        return new AlbumWithArtistsAndSongsDto(albumDto, artistsDto, songsDto);
    }

    Long countArtistsByAlbumId(final Long albumId) {
        return (long) findById(albumId)
                .getArtists()
                .size();
    }

    Set<Album> findAlbumsByArtistId(final Long artistId) {
        return albumRepository.findAllAlbumsByArtistId(artistId);
    }

    Set<AlbumDto> findAlbumsDtoByArtistId(final Long artistId) {
        return findAlbumsByArtistId(artistId).stream()
                .map(album -> new AlbumDto(album.getId(),album.getTitle(), album.getSongsIds()))
                .collect(Collectors.toSet());
    }


    Album findById(final Long albumId) {
        return albumRepository.findById(albumId)
                .orElseThrow(() -> new AlbumNotFoundException("album with id: " + albumId + " not found"));
    }

    AlbumDto findAlbumDtoById(final Long albumId) {
        Album album = findById(albumId);
        return new AlbumDto(album.getId(),
                album.getTitle(),album.getSongsIds());
    }


    Set<AlbumDto> findAll() {
        return albumRepository.findAll()
                .stream()
                .map(album -> new AlbumDto(album.getId(), album.getTitle(),album.getSongsIds()))
                .collect(Collectors.toSet());
    }
}
