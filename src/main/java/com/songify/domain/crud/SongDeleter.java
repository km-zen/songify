package com.songify.domain.crud;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class SongDeleter {
    private final SongRepository songRepository;
    private final SongRetriever songRetriever;

    public void deleteById(Long id) {
        songRetriever.existById(id);
        songRepository.deleteById(id);
    }

    void deleteAllSongsById(final Set<Long> ids) {
        songRepository.deleteByIdIn(ids);
    }
}
