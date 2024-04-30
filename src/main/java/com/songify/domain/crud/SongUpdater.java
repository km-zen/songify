package com.songify.domain.crud;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class SongUpdater {
    private final SongRepository songRepository;

    void updateById(Long id, Song song) {
        songRepository.update(id, song);
    }
//
//    Song updatePartiallyById(Long id, Song updatedSong) {
//        Song songFromDatabase = songRetriever.findSongById(id);
//        Song.SongBuilder builder = Song.builder();
//        if (updatedSong.getName() != null) {
//            builder.name(updatedSong.getName());
//        } else {
//            builder.name(songFromDatabase.getName());
//        }
//
//
////        if (updatedSong.getArtist() != null) {
////            builder.artist(updatedSong.getArtist());
////        } else {
////            builder.artist(songFromDatabase.getArtist());
////        }
//
//        Song songToSave = builder.build();
//        updateById(id, songToSave);
//        return songToSave;
//    }
}
