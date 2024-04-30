package com.songify.domain.crud;

import com.songify.domain.crud.dto.ArtistDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
class ArtistUpdater {
    private final ArtistRetriever artistRetriever;
    private final ArtistRepository artistRepository;
    ArtistDto updateArtistNameById(final Long artistId, final String name) {
        Artist artist = artistRetriever.findById(artistId);
        artist.setName(name);
        return new ArtistDto(artist.getId(),name);
    }

    ArtistDto updateArtistNameByIdQuery(final Long artistId, final String name){
        artistRepository.updateNameById(name, artistId);
        Artist updatedArtist = artistRetriever.findById(artistId);
        return new ArtistDto(updatedArtist.getId(), updatedArtist.getName());
    }
}
