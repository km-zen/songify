package com.songify.domain.crud;

import org.springframework.stereotype.Service;

import java.util.Set;

@Service
class AlbumDeleter {

    private final AlbumRepository albumRepository;

    AlbumDeleter(final AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    void deleteAllAlbumsByIds(Set<Long> ids){
        albumRepository.deleteByIdIn(ids);
    }
}
