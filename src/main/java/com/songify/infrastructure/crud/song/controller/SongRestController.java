package com.songify.infrastructure.crud.song.controller;

import com.songify.domain.crud.SongifyCrudFacade;
import com.songify.domain.crud.dto.SongDto;
import com.songify.domain.crud.dto.SongRequestDto;
import com.songify.infrastructure.crud.song.controller.dto.request.PartiallyUpdateSongRequestDto;
import com.songify.infrastructure.crud.song.controller.dto.request.UpdateSongRequestDto;
import com.songify.infrastructure.crud.song.controller.dto.response.CreateSongResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.DeleteSongResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.GetAllSongsResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.GetSongResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.PartiallyUpdateSongResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.UpdateSongResponseDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Log4j2
@AllArgsConstructor
@RequestMapping("/songs")
public class SongRestController {


    private final SongifyCrudFacade songFacade;



    /* Wyświetlanie piosenki z id określonym jako Request param czyli w url ?id=<numer id piosenki> */
//    @GetMapping("/songs")
//    public ResponseEntity<SongResponseDto> getAllSongs(@RequestParam Integer id){
//        database.put(1, "shawn mendes song");
//        database.put(2, "ariana grande song");
//        if(id != null){
//            String song = database.get(id);
//            if(song == null){
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//            }
//            SongResponseDto response = new SongResponseDto(Map.of(id,song));
//            return ResponseEntity.ok(response);
//        }
//        SongResponseDto response = new SongResponseDto(database);
//        return ResponseEntity.ok(response);
//    }

    // Wyświetlanie ograniczonej ilości piosenek z bazy danych
    @GetMapping
    public ResponseEntity<GetAllSongsResponseDto> getAllSongs(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        List<SongDto> allSongs = songFacade.findAllSongs(pageable);
        GetAllSongsResponseDto response = SongControllerMapper.mapFromSongToGetAllSongsResponseDto(allSongs);
        return ResponseEntity.ok(response);
    }

//    @GetMapping("/songs/{id}")
//    public ResponseEntity<SingleSongResponseDto> getSongById(@PathVariable Integer id){
//        String song = database.get(id);
//        if(song == null){
//            return ResponseEntity.status(404).build();
//        }
//        SingleSongResponseDto response = new SingleSongResponseDto(song);
//        return ResponseEntity.ok(response);
//    }

    //RequestHeader
    @GetMapping("/{id}")
    public ResponseEntity<GetSongResponseDto> getSongById(@PathVariable Long id, @RequestHeader(required = false) String requestId) {
        log.info(requestId);
        SongDto song = songFacade.findSongDtoById(id);
        GetSongResponseDto body = SongControllerMapper.mapFromSongToGetSongResponseDto(song);
        return ResponseEntity.ok(body);
    }

    @PostMapping
    public ResponseEntity<CreateSongResponseDto> postSong(@RequestBody @Valid SongRequestDto request) {
        SongDto savedSong = songFacade.addSong(request);
        CreateSongResponseDto body = SongControllerMapper.mapFromSongToCreateSongResponseDto(savedSong);
        return ResponseEntity.ok(body);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteSongResponseDto> deleteSongByIdUsingPathVariable(@PathVariable Long id) {
        songFacade.deleteSongById(id);
        DeleteSongResponseDto body = SongControllerMapper.mapFromSongToDeleteSongResponseDto(id);
        return ResponseEntity.ok(body);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateSongResponseDto> update(@PathVariable Long id, @RequestBody @Valid UpdateSongRequestDto request) {
        SongDto newSong = SongControllerMapper.mapFromUpdateSongRequestDtoToSongDto(request);
        songFacade.updateSongById(id, newSong);
        log.info("Updated song with id: " + id + " with old song: " + " for new song: " + newSong);
        UpdateSongResponseDto body = SongControllerMapper.mapFromSongToUpdateSongResponseDto(newSong);
        return ResponseEntity.ok(body);
    }
    @PutMapping("/{songId}/genres/{genreId}")
    ResponseEntity<GetSongResponseDto> assignGenreToSong(
            @PathVariable Long songId,
            @PathVariable Long genreId) {
        final SongDto songDto = songFacade.assignGenreToSong(genreId, songId);
        GetSongResponseDto body = new GetSongResponseDto(songDto);
        return ResponseEntity.ok(body);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PartiallyUpdateSongResponseDto> partiallyUpdate(@PathVariable Long id,
                                                                          @RequestBody PartiallyUpdateSongRequestDto request) {
        SongDto updatedSong = SongControllerMapper.mapFromPartiallyUpdateSongRequestDtoToSongDto(request);
        SongDto savedSong = songFacade.updateSongPartiallyById(id, updatedSong);
        PartiallyUpdateSongResponseDto body = SongControllerMapper.mapFromSongDtoToPartiallyUpdateSongResponseDto(savedSong);
        log.info("Partially updated song with id: " + id + ", old song: " + " new song: " + savedSong);
        return ResponseEntity.ok(body);
    }
}
