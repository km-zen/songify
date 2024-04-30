package com.songify.infrastructure.crud.artist;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record PartiallyUpdatedArtistRequestDto(@NotEmpty(message = "new artist name must not be empty")
                                               @NotNull(message = "new artist name must not be null")
                                               String newArtistName) {
}
