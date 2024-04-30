package com.songify.infrastructure.crud.genre;

import com.songify.domain.crud.GenreDto;

import java.util.Set;

public record GetAllGenresResponseDto(Set<GenreDto> genres) {
}
