package com.songify.infrastructure.crud.album;

import com.songify.domain.crud.AlbumInfo;
import com.songify.domain.crud.AlbumWithArtistsAndSongsDto;
import com.songify.domain.crud.dto.AlbumDto;
import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.SongDto;

import java.util.Set;

public record GetAlbumsWithArtistsAndSongsResponseDto(AlbumInfo albumInfo) {
}
