package com.songify.domain.crud;

import com.songify.domain.crud.dto.SongDto;
import com.songify.domain.crud.dto.SongLanguageDto;
import com.songify.domain.crud.dto.SongRequestDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
@Transactional
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class SongAdder {
    private final SongRepository songRepository;
    private final GenreAssigner genreAssigner;

    public SongDto addSong(SongRequestDto dto){
    SongLanguageDto songLanguageDto = dto.language();
        SongLanguage language = SongLanguage.valueOf(songLanguageDto.name());
        Song song = new Song(dto.name(),dto.releaseDate(),dto.duration(),language);
        log.info("adding new song: " + song);
        Song save = songRepository.save(song);
        genreAssigner.assignDefaultGenreToSong(song.getId());
        return new SongDto(save.getId(), save.getName(), new GenreDto(save.getGenre().getId(), save.getGenre().getName()));

    }

}
