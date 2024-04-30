package com.songify.infrastructure.crud.artist;

import lombok.Builder;

@Builder
public record ArtistRequestDto(String name) {
}
