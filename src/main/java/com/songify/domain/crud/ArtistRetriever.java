package com.songify.domain.crud;

import com.songify.domain.crud.dto.ArtistDto;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
class ArtistRetriever {
    private final ArtistRepository artistRepository;

    ArtistRetriever(final ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    Set<ArtistDto> findAll(Pageable pageable){
        return artistRepository.findAll(pageable)
                .stream()
                .map(artist-> new ArtistDto(
                        artist.getId(),
                        artist.getName()
                )).collect(Collectors.toSet());
    }

    Artist findById(final Long artistId) {
        return artistRepository.findById(artistId)
                .orElseThrow(() -> new ArtistNotFoundException("Artist with id: " + artistId + " not found"));
    }
}
