package com.songify.infrastructure.crud.song.controller;


import com.songify.domain.crud.dto.SongDto;
import com.songify.infrastructure.crud.song.controller.dto.request.PartiallyUpdateSongRequestDto;
import com.songify.infrastructure.crud.song.controller.dto.request.UpdateSongRequestDto;
import com.songify.infrastructure.crud.song.controller.dto.response.CreateSongResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.DeleteSongResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.GetAllSongsResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.GetSongResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.PartiallyUpdateSongResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.SongControllerResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.UpdateSongResponseDto;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;

public class SongControllerMapper {


    public static CreateSongResponseDto mapFromSongToCreateSongResponseDto(SongDto song) {
        return new CreateSongResponseDto(song);
    }

    public static DeleteSongResponseDto mapFromSongToDeleteSongResponseDto(Long id) {
        return new DeleteSongResponseDto("You deleted song with id: " + id, HttpStatus.OK);
    }

    public static UpdateSongResponseDto mapFromSongToUpdateSongResponseDto(SongDto dto) {
        return new UpdateSongResponseDto(dto.name(), "test");
    }

    public static PartiallyUpdateSongResponseDto mapFromSongDtoToPartiallyUpdateSongResponseDto(SongDto updatedSong) {
        return new PartiallyUpdateSongResponseDto(updatedSong);
    }

    public static GetSongResponseDto mapFromSongToGetSongResponseDto(SongDto song) {
        return new GetSongResponseDto(song);
    }

    public static GetAllSongsResponseDto mapFromSongToGetAllSongsResponseDto(List<SongDto> allSongs) {
        List<SongControllerResponseDto> songControllerResponseDtos = allSongs
                .stream()
                .map(songDto -> SongControllerResponseDto.builder()
                        .id(songDto.id())
                        .name(songDto.name())
                        .build())
                .collect(Collectors.toList());
        return new GetAllSongsResponseDto(songControllerResponseDtos);
    }

    static SongDto mapFromUpdateSongRequestDtoToSongDto(final UpdateSongRequestDto request) {
        return SongDto.builder()
                .name(request.songName())
                .build();
    }

    static SongDto mapFromPartiallyUpdateSongRequestDtoToSongDto(final PartiallyUpdateSongRequestDto request) {
        return SongDto.builder()
                .name(request.songName())
                .build();
    }
}
