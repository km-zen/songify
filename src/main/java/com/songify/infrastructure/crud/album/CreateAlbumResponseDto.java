package com.songify.infrastructure.crud.album;

import java.util.Set;

public record CreateAlbumResponseDto(Long id, String title, Set<Long> songsIds) {
}
