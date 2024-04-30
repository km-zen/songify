package com.songify.domain.crud;

import com.songify.domain.crud.dto.SongDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
@Transactional
class GenreAssigner {

    private final SongRetriever songRetriever;
    private final GenreRetriever genreRetriever;

    void assignDefaultGenreToSong(Long songId) {
        Song song = songRetriever.findSongById(songId);
        Genre genre = genreRetriever.findGenreById(1L);
        song.setGenre(genre);
    }

    SongDto assignGenreToSong(final Long genreId, final Long songId) {
        Song song = songRetriever.findSongById(songId);
        Genre genre = genreRetriever.findGenreById(genreId);
        song.setGenre(genre);
        return new SongDto(song.getId(),song.getName(),new GenreDto(song.getGenre().getId(),song.getGenre().getName()));
    }

}
