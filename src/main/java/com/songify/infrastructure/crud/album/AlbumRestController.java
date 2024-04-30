package com.songify.infrastructure.crud.album;

import com.songify.domain.crud.AlbumInfo;
import com.songify.domain.crud.AlbumRequestDto;
import com.songify.domain.crud.SongifyCrudFacade;
import com.songify.domain.crud.dto.AlbumDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/albums")
class AlbumRestController {

    private final SongifyCrudFacade songifyCrudFacade;

    AlbumRestController(SongifyCrudFacade songifyCrudFacade) {
        this.songifyCrudFacade = songifyCrudFacade;
    }

    @PostMapping
    public ResponseEntity<CreateAlbumResponseDto> createAlbum(@RequestBody AlbumRequestDto requestDto){
        AlbumDto albumDto = songifyCrudFacade.addAlbumWithSong(requestDto);
        CreateAlbumResponseDto body = new CreateAlbumResponseDto(albumDto.id(),albumDto.title(),albumDto.songsIds());
        return ResponseEntity.ok(body);
    }

    @GetMapping("/{albumId}")
    public ResponseEntity<GetAlbumsWithArtistsAndSongsResponseDto> getAlbums(@PathVariable Long albumId){
        AlbumInfo albumWithArtistsAndSongsDto = songifyCrudFacade.findAlbumByIdWithArtistsAndSongs(albumId);
        GetAlbumsWithArtistsAndSongsResponseDto body = new GetAlbumsWithArtistsAndSongsResponseDto(albumWithArtistsAndSongsDto);
        return ResponseEntity.ok(body);
    }

    @GetMapping
    ResponseEntity<GetAllAlbumsResponseDto> getAllAlbums() {
        Set<AlbumDto> allAlbums = songifyCrudFacade.findAllAlbums();
        GetAllAlbumsResponseDto response = new GetAllAlbumsResponseDto(allAlbums);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{albumId}/songs/{songId}")
    ResponseEntity<AlbumDto> addSongToAlbum(@PathVariable Long albumId, @PathVariable Long songId) {
        AlbumDto albumDto = songifyCrudFacade.addSongToAlbum(albumId, songId);
        return ResponseEntity.ok(albumDto);
    }
}
