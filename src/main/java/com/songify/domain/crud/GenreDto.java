package com.songify.domain.crud;

import lombok.Builder;

@Builder
public record GenreDto(Long id, String name) {
}
