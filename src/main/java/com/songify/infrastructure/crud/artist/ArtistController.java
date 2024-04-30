package com.songify.infrastructure.crud.artist;

import com.songify.domain.crud.SongifyCrudFacade;
import com.songify.domain.crud.dto.ArtistDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/artists")
class ArtistController {

    private final SongifyCrudFacade songifyCrudFacade;

    ArtistController(SongifyCrudFacade songifyCrudFacade) {
        this.songifyCrudFacade = songifyCrudFacade;
    }

    @PostMapping
    public ResponseEntity<CreateArtistResponseDto> createArtist(@RequestBody ArtistRequestDto request){
        final ArtistDto artistDto = songifyCrudFacade.addArtist(request);
        CreateArtistResponseDto body = new CreateArtistResponseDto(artistDto.id(),artistDto.name());
        return ResponseEntity.ok(body);
    }

    @PostMapping("/album/song")
    public ResponseEntity<ArtistDto> addArtistWithDefaultAlbumAndSong(@RequestBody ArtistRequestDto requestDto){
        ArtistDto artistDto = songifyCrudFacade.addArtistWithDefaultAlbumAndSong(requestDto);
        return ResponseEntity.ok(artistDto);
    }

    @GetMapping
    public ResponseEntity<GetAllArtistsResponseDto> getAllArtists(@PageableDefault(page = 0,size = 10) Pageable pageable){
        Set<ArtistDto> artistDtos = songifyCrudFacade.findAllArtists(pageable);
        GetAllArtistsResponseDto body = new GetAllArtistsResponseDto(artistDtos);
        return ResponseEntity.ok(body);
    }

    @DeleteMapping("/{artistId}")
    public ResponseEntity<DeleteArtistResponseDto> deleteArtistById(@PathVariable Long artistId){
        songifyCrudFacade.deleteArtistByIdWithSongsAndAlbums(artistId);
        DeleteArtistResponseDto body = new DeleteArtistResponseDto("probably all deleted", HttpStatus.OK);
        return ResponseEntity.ok(body);
    }

    @PutMapping("/{artistId}/albums/{albumId}")
    public ResponseEntity<String> addArtistToAlbum(@PathVariable Long artistId, @PathVariable Long albumId){
        songifyCrudFacade.addArtistToAlbum(artistId,albumId);
        return ResponseEntity.ok("probably assigned artist to album");
    }

    @PatchMapping("/{artistId}")
    public ResponseEntity<ArtistDto> updatePartiallyArtistNameById(@PathVariable Long artistId, @Valid @RequestBody PartiallyUpdatedArtistRequestDto requestDto){
        ArtistDto artistDto = songifyCrudFacade.updateArtistNameById(artistId, requestDto.newArtistName());
        return ResponseEntity.ok(artistDto);
    }
}
