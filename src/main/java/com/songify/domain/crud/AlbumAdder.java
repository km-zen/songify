package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
class AlbumAdder {
    private final AlbumRepository albumRepository;
    private final SongRetriever songRetriever;

    AlbumAdder(final AlbumRepository albumRepository, SongRetriever songRetriever) {
        this.albumRepository = albumRepository;
        this.songRetriever = songRetriever;
    }

    AlbumDto addAlbum(final Set<Long> songIds, final String title, final Instant releaseDate) {
        Set<Song> songs = songIds.stream()
                .map(songRetriever::findSongById)
                .collect(Collectors.toSet());

        Album album = new Album();
        album.setTitle(title);
        album.addSongsToAlbum(songs);
        album.setReleaseDate(releaseDate);
        Album savedAlbum = albumRepository.save(album);
        return new AlbumDto(savedAlbum.getId(), savedAlbum.getTitle(), savedAlbum.getSongsIds());
    }
}
