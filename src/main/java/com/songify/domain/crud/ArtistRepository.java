package com.songify.domain.crud;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

interface ArtistRepository extends Repository<Artist, Long> {

    Artist save(Artist artist);

    Set<Artist> findAll(Pageable pageable);


    @Modifying
    @Query("delete from Artist a where a.id = ?1")
    int deleteById(Long id);


    Optional<Artist> findById(Long id);

    @Modifying
    @Query("update Artist a set a.name = :name where a.id = :id")
    int updateNameById(@Param("name") String name, @Param("id") Long id);
}
