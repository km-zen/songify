package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumDto;
import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.SongDto;

import java.util.Set;

public record AlbumWithArtistsAndSongsDto(
        AlbumDto album,
        Set<ArtistDto> artists,
        Set<SongDto> songs
) {
}
