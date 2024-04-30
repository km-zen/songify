package com.songify.domain.crud.dto;

import org.springframework.http.HttpStatus;

public record ErrorAlbumResponseDto(String message, HttpStatus status) {
}
