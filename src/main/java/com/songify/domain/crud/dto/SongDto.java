package com.songify.domain.crud.dto;

import com.songify.domain.crud.GenreDto;
import lombok.Builder;

@Builder
public record SongDto(
        Long id,
        String name,
        GenreDto genre) {
}
