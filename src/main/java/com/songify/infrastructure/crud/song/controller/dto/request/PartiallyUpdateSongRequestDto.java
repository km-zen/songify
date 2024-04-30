package com.songify.infrastructure.crud.song.controller.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record PartiallyUpdateSongRequestDto(String songName,
                                            String artist) {
}
