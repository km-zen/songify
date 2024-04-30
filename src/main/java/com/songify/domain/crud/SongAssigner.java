package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
class SongAssigner {

    private final AlbumRetriever albumRetriever;
    private final SongRetriever songRetriever;

    AlbumDto assignSongToAlbum(Long albumId, Long songId) {
        Album album = albumRetriever.findById(albumId);
        Song songById = songRetriever.findSongById(songId);
        album.addSongToAlbum(songById);
        return new AlbumDto(
                album.getId(),
                album.getTitle(),
                album.getSongsIds()
        );
    }
}
