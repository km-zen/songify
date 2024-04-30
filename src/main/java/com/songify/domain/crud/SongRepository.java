package com.songify.domain.crud;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

interface SongRepository extends Repository<Song, Long> {

    @Query("SELECT s from Song s")
    List<Song> findAll(Pageable pageable);

    @Query("SELECT s from Song  s WHERE s.id = :id")
    Optional<Song> findById(Long id);

    @Modifying
    @Query("DELETE from Song s WHERE s.id = :id")
    void deleteById(Long id);

    @Modifying
    @Query("UPDATE Song s set s.name = :#{#newSong.name} WHERE s.id = :id")
    void update(Long id, Song newSong);

    boolean existsById(Long id);

    Song save(Song song);

    @Modifying
    @Query("delete from Song s where s.id in :ids")
    void deleteByIdIn(Set<Long> ids);
}
