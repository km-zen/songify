package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumDto;
import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.SongDto;
import com.songify.domain.crud.dto.SongRequestDto;
import com.songify.infrastructure.crud.artist.ArtistRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class SongifyCrudFacade {

    private final SongDeleter songDeleter;
    private final SongAdder songAdder;
    private final SongRetriever songRetriever;
    private final SongUpdater songUpdater;
    private final ArtistAdder artistAdder;
    private final GenreAdder genreAdder;
    private final AlbumAdder albumAdder;
    private final ArtistRetriever artistRetriever;
    private final AlbumRetriever albumRetriever;
    private final ArtistDeleter artistDeleter;
    private final ArtistAssigner artistAssigner;
    private final ArtistUpdater artistUpdater;
    private final GenreRetriever genreRetriever;
    private final GenreAssigner genreAssigner;
    private final SongAssigner songAssigner;


    public ArtistDto addArtist(ArtistRequestDto dto) {
        return artistAdder.addArtist(dto.name());
    }

    public GenreDto addGenre(GenreRequestDto dto) {
        return genreAdder.addGenre(dto.name());
    }

    public AlbumDto addAlbumWithSong(AlbumRequestDto dto) {
        return albumAdder.addAlbum(dto.songIds(), dto.title(), dto.releaseDate());
    }

    public void addArtistToAlbum(Long artistId, Long albumId){
        artistAssigner.addArtistToAlbum(artistId,albumId);
    }

    public ArtistDto addArtistWithDefaultAlbumAndSong(ArtistRequestDto dto){
        return artistAdder.addArtistWithDefaultAlbumAndSong(dto);
    }

    public ArtistDto updateArtistNameById(Long artistId, String name){
        return artistUpdater.updateArtistNameById(artistId, name);
    }


    public AlbumInfo findAlbumByIdWithArtistsAndSongs(Long id){
        return albumRetriever.findAlbumByIdWithArtistsAndSongs(id);
    }



    public Set<ArtistDto> findAllArtists(Pageable pageable) {
        return artistRetriever.findAll(pageable);
    }

    public SongDto updateSongPartiallyById(final Long id, final SongDto songFromRequest) {
        songRetriever.existById(id);
        Song songFromDatabase = songRetriever.findSongById(id);
        Song toSave = new Song();

        if (songFromRequest.name() != null) {
            toSave.setName(songFromRequest.name());
        } else {
            toSave.setName(songFromDatabase.getName());
        }
        songUpdater.updateById(id, toSave);
        return SongDto.builder()
                .name(toSave.getName())
                .id(toSave.getId())
                .build();
    }

    public void updateSongById(final Long id, final SongDto newSongDto) {
        songRetriever.existById(id);
        Song songValidatedAndReady = new Song(newSongDto.name());
        songUpdater.updateById(id, songValidatedAndReady);
    }

    public void deleteSongById(final Long id) {
        songDeleter.deleteById(id);
    }

    public SongDto addSong(final SongRequestDto dto) {
        return songAdder.addSong(dto);
    }

    public SongDto findSongDtoById(Long id) {
        return songRetriever.findSongDtoById(id);
    }

    Song findSongById(final Long id) {
        return songRetriever.findSongById(id);
    }

    public List<SongDto> findAllSongs(final Pageable pageable) {
        return songRetriever.findAll(pageable);
    }

    public void deleteArtistByIdWithSongsAndAlbums(final Long artistId) {
        artistDeleter.deleteArtistByIdWithSongsAndAlbums(artistId);
    }

    public Set<AlbumDto> findAlbumsByArtistId(final long artistId) {
        return  albumRetriever.findAlbumsDtoByArtistId(artistId);

    }

    public Long countArtistsByAlbumId(final Long albumId) {
        return albumRetriever.countArtistsByAlbumId(albumId);
    }

    AlbumDto findAlbumById(final Long albumId) {
        return albumRetriever.findAlbumDtoById(albumId);
    }

    public Set<AlbumDto> findAllAlbums() {
        return albumRetriever.findAll();
    }

    public Set<GenreDto> retrieveGenres() {
        return genreRetriever.findAll();
    }

    public SongDto assignGenreToSong(final Long genreId, final Long songId) {
       return genreAssigner.assignGenreToSong(genreId,songId);
    }

    public AlbumDto addSongToAlbum(final Long albumId, final Long songId) {
        return songAssigner.assignSongToAlbum(albumId, songId);
    }
}
