package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumDto;
import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.SongDto;
import com.songify.domain.crud.dto.SongLanguageDto;
import com.songify.domain.crud.dto.SongRequestDto;
import com.songify.infrastructure.crud.artist.ArtistRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class SongifyCrudFacadeTest {

    SongifyCrudFacade songifyCrudFacade = SongifyCrudFacadeConfiguration.createSongifyCrud(
            new InMemorySongRepository(),
            new InMemoryGenreRepository(),
            new InMemoryArtistRepository(),
            new InMemoryAlbumRepository()
    );


    @Test
    @DisplayName("Should add artist 'Shawn Mendes' when 'Shawn Mendes' was sent")
    public void should_add_artist_shawn_mendes_with_id_when_shawn_mendes_was_sent(){
        //given
        ArtistRequestDto requestDto = ArtistRequestDto.builder()
                .name("shawn mendes")
                .build();

        Set<ArtistDto> allArtists = songifyCrudFacade.findAllArtists(Pageable.unpaged());
        assertThat(allArtists.size()).isEqualTo(0);
        //when
        ArtistDto response = songifyCrudFacade.addArtist(requestDto);
        //then
        assertThat(response.id()).isEqualTo(0L);
        assertThat(response.name()).isEqualTo("shawn mendes");
        int size = songifyCrudFacade.findAllArtists(Pageable.unpaged()).size();
        assertThat(size).isEqualTo(1);
    }

    @Test
    @DisplayName("Should throw exception ArtistNotFound When id: 0")
    public void should_throw_exception_artist_not_found_when_id_was_zero() {
        // given
        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged())).isEmpty();
        // when
        Throwable throwable = catchThrowable(() -> songifyCrudFacade.deleteArtistByIdWithSongsAndAlbums(0L));
        // then
        assertThat(throwable).isInstanceOf(ArtistNotFoundException.class);
        assertThat(throwable.getMessage()).isEqualTo("Artist with id: 0 not found");
    }

    @Test
    @DisplayName("Should delete artist by id when have no albums")
    public void should_delete_artist_by_id_when_have_no_albums(){
        // given
        ArtistRequestDto requestDto = ArtistRequestDto.builder()
                .name("shawn mendes")
                .build();
        Long artistId = songifyCrudFacade.addArtist(requestDto).id();
        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged())).isNotEmpty();
        assertThat(songifyCrudFacade.findAlbumsByArtistId(artistId)).isEmpty();
        // when
        songifyCrudFacade.deleteArtistByIdWithSongsAndAlbums(artistId);
        // then
        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged())).isEmpty();

    }

    @Test
    @DisplayName("Should delete artist by Id when he have one album")
    public void should_delete_artist_with_albums_and_songs_by_id_when_he_had_one_album_and_he_was_the_only_artist_in_this_album(){
        // given
        ArtistRequestDto requestDto = ArtistRequestDto.builder()
                .name("shawn mendes")
                .build();
        Long artistId = songifyCrudFacade.addArtist(requestDto).id();
        // poniższy kod nie musi być pisany, bo sprawdzanie poprawnosci dodawania jest wykonywane w innym teście
        // assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged())).isNotEmpty();

        SongRequestDto song = SongRequestDto.builder()
                .name("song name1")
                .language(SongLanguageDto.ENGLISH)
                .build();
        final SongDto song1 = songifyCrudFacade.addSong(song);
        Long songId = song1.id();

        final AlbumDto albumDto = songifyCrudFacade.addAlbumWithSong(AlbumRequestDto
                .builder()
                .title("album title1")
                .releaseDate(LocalDateTime.now().toInstant(ZoneOffset.UTC))
                .songIds(Set.of(songId))
                .build());

        Long albumId = albumDto.id();
        songifyCrudFacade.addArtistToAlbum(artistId,albumDto.id());
        assertThat(songifyCrudFacade.findAlbumsByArtistId(artistId).size()).isEqualTo(1);
        assertThat(songifyCrudFacade.countArtistsByAlbumId(albumId)).isEqualTo(1L);
        // when
        songifyCrudFacade.deleteArtistByIdWithSongsAndAlbums(artistId);

        // then
        assertThat(songifyCrudFacade.findAlbumsByArtistId(artistId)).isEmpty();
        Throwable throwable = catchThrowable(() -> songifyCrudFacade.findSongDtoById(songId));
        assertThat(throwable).isInstanceOf(SongNotFoundException.class);
        assertThat(throwable.getMessage()).isEqualTo("Song with id 0 not found");
        Throwable throwable2 = catchThrowable(() -> songifyCrudFacade.findAlbumById(albumId));
        assertThat(throwable2).isInstanceOf(AlbumNotFoundException.class);
        assertThat(throwable2.getMessage()).isEqualTo("album with id: 0 not found");

    }


    @Test
    public void should_add_song(){
       //given
        SongRequestDto song = SongRequestDto.builder()
                .name("song name1")
                .language(SongLanguageDto.ENGLISH)
                .build();

        assertThat(songifyCrudFacade.findAllSongs(Pageable.unpaged())).isEmpty();

        // when
        final SongDto song1 = songifyCrudFacade.addSong(song);
        // then
        List<SongDto> allSongs = songifyCrudFacade.findAllSongs(Pageable.unpaged());
        assertThat(allSongs)
                .extracting(SongDto::id)
                .containsExactly(0L);
    }

    @Test
    public void should_add_album_with_song(){
        SongRequestDto songRequestDto = SongRequestDto.builder()
                .name("song1")
                .language(SongLanguageDto.ENGLISH)
                .build();
        SongDto songDto = songifyCrudFacade.addSong(songRequestDto);
        AlbumRequestDto album = AlbumRequestDto
                .builder()
                .songIds(Set.of(songDto.id()))
                .title("album title 1")
                .build();
        assertThat(songifyCrudFacade.findAllAlbums()).isEmpty();
        // when
        AlbumDto albumDto = songifyCrudFacade.addAlbumWithSong(album);
        // then
        assertThat(songifyCrudFacade.findAllAlbums()).isNotEmpty();
        AlbumInfo albumWithSongs = songifyCrudFacade.findAlbumByIdWithArtistsAndSongs(albumDto.id());
        Set<AlbumInfo.SongInfo> songs = albumWithSongs.getSongs();
        assertTrue(songs.stream().anyMatch(song -> song.getId().equals(songDto.id())));
    }
    @Test
    public void should_add_artist_to_album(){
        // given
        ArtistRequestDto artistRequestDto = ArtistRequestDto.builder()
                .name("shawn mendes")
                .build();
        Long artistId = songifyCrudFacade.addArtist(artistRequestDto).id();
        SongRequestDto songRequestDto = SongRequestDto.builder()
                .name("song name1")
                .language(SongLanguageDto.ENGLISH)
                .build();
        final SongDto songDto = songifyCrudFacade.addSong(songRequestDto);
        Long songId = songDto.id();

        AlbumRequestDto albumRequestDto = AlbumRequestDto
                .builder()
                .title("album title1")
                .releaseDate(LocalDateTime.now().toInstant(ZoneOffset.UTC))
                .songIds(Set.of(songId))
                .build();
        final AlbumDto albumDto = songifyCrudFacade.addAlbumWithSong(albumRequestDto);
        Long albumId = albumDto.id();
        assertThat(songifyCrudFacade.countArtistsByAlbumId(albumId)).isEqualTo(0);
        // when
        songifyCrudFacade.addArtistToAlbum(artistId,albumId);
        // then
        assertThat(songifyCrudFacade.countArtistsByAlbumId(albumId)).isEqualTo(1);
        Set<AlbumDto> albumsByArtist = songifyCrudFacade.findAlbumsByArtistId(artistId);
        assertThat(albumsByArtist)
                .extracting(AlbumDto::id)
                .containsExactly(artistId);
    }

    @Test
    public void should_return_album_by_id(){
        // given
        SongRequestDto songRequestDto = SongRequestDto.builder()
                .name("song name1")
                .language(SongLanguageDto.ENGLISH)
                .build();
        final SongDto songDto = songifyCrudFacade.addSong(songRequestDto);
        Long songId = songDto.id();

        AlbumRequestDto albumRequestDto = AlbumRequestDto
                .builder()
                .title("album title1")
                .releaseDate(LocalDateTime.now().toInstant(ZoneOffset.UTC))
                .songIds(Set.of(songId))
                .build();
        final AlbumDto albumDto = songifyCrudFacade.addAlbumWithSong(albumRequestDto);
        Long albumId = albumDto.id();
        // when
        final AlbumDto albumFromDb = songifyCrudFacade.findAlbumById(albumId);
        // then
        assertThat(albumFromDb.title()).isEqualTo("album title1");

        assertThat(albumFromDb)
                .isEqualTo(new AlbumDto(albumId,"album title1",Set.of(songId)));
    }


    @Test
    @DisplayName("should throw exception when album not found by id")
    public void should_throw_exception_when_album_not_found_by_id() {
        // given
        assertThat(songifyCrudFacade.findAllAlbums()).isEmpty();
        // when
        Throwable throwable = catchThrowable(() -> songifyCrudFacade.findAlbumById(0L));
        // then
        assertThat(throwable).isInstanceOf(AlbumNotFoundException.class);
        assertThat(throwable.getMessage()).isEqualTo("album with id: 0 not found");
    }

    @Test
    @DisplayName("should throw exception when song not found by id")
    public void should_throw_exception_when_song_not_found_by_id() {
        // given
        assertThat(songifyCrudFacade.findAllSongs(Pageable.unpaged())).isEmpty();

        // when
        Throwable throwable = catchThrowable(() -> songifyCrudFacade.findSongById(0L));
        // then
        assertThat(throwable).isInstanceOf(SongNotFoundException.class);
        assertThat(throwable.getMessage()).isEqualTo("song with id: 0 not found");
    }

    @Test
    @DisplayName("Should delete only artist from album by id When there were more than 1 artist in album")
    public void should_delete_only_artist_from_album_by_id_when_there_were_more_than_one_artist_in_album() {
        // given
        ArtistRequestDto shawnMendes = ArtistRequestDto.builder()
                .name("shawn mendes")
                .build();
        ArtistRequestDto camilaCabello = ArtistRequestDto.builder()
                .name("Camila Cabello")
                .build();
        Long artistId1 = songifyCrudFacade.addArtist(shawnMendes).id();
        Long artistId2 = songifyCrudFacade.addArtist(camilaCabello).id();
        SongRequestDto songRequestDto = SongRequestDto.builder()
                .name("Seniorita")
                .language(SongLanguageDto.ENGLISH)
                .build();
        final SongDto songDto = songifyCrudFacade.addSong(songRequestDto);
        Long songId = songDto.id();

        AlbumRequestDto albumRequestDto = AlbumRequestDto
                .builder()
                .title("album with Seniorita")
                .releaseDate(LocalDateTime.now().toInstant(ZoneOffset.UTC))
                .songIds(Set.of(songId))
                .build();
        final AlbumDto albumDto = songifyCrudFacade.addAlbumWithSong(albumRequestDto);
        Long albumId = albumDto.id();
        songifyCrudFacade.addArtistToAlbum(artistId1,albumId);
        songifyCrudFacade.addArtistToAlbum(artistId2,albumId);

        assertThat(songifyCrudFacade.countArtistsByAlbumId(albumId)).isEqualTo(2);
        // when
        songifyCrudFacade.deleteArtistByIdWithSongsAndAlbums(artistId1);
        // then
        assertThat(songifyCrudFacade.countArtistsByAlbumId(albumId)).isEqualTo(1);

        AlbumInfo albumInfo = songifyCrudFacade.findAlbumByIdWithArtistsAndSongs(albumId);
        Set<AlbumInfo.ArtistInfo> artists = albumInfo.getArtists();
        assertThat(artists)
                .extracting("id")
                .containsOnly(artistId2);
    }

    @Test
    @DisplayName("should delete artist with all albums and all songs by id when artist was the only artist in albums")
    public void should_delete_artist_with_albums_and_songs_by_id_when_artist_was_the_only_artist_in_albums() {

        // given
        ArtistRequestDto artistRequestDto = ArtistRequestDto.builder()
                .name("shawn mendes")
                .build();
        Long artistId = songifyCrudFacade.addArtist(artistRequestDto).id();
        SongRequestDto songRequestDto = SongRequestDto.builder()
                .name("song name1")
                .language(SongLanguageDto.ENGLISH)
                .build();
        final SongDto songDto = songifyCrudFacade.addSong(songRequestDto);
        Long songId = songDto.id();

        SongRequestDto songRequestDto2 = SongRequestDto.builder()
                .name("song name2")
                .language(SongLanguageDto.ENGLISH)
                .build();
        final SongDto songDto2 = songifyCrudFacade.addSong(songRequestDto2);
        Long songId2 = songDto2.id();

        AlbumRequestDto albumRequestDto1 = AlbumRequestDto
                .builder()
                .title("album title1")
                .releaseDate(LocalDateTime.now().toInstant(ZoneOffset.UTC))
                .songIds(Set.of(songId))
                .build();
        final AlbumDto albumDto1 = songifyCrudFacade.addAlbumWithSong(albumRequestDto1);
        Long albumId1 = albumDto1.id();

        AlbumRequestDto albumRequestDto2 = AlbumRequestDto
                .builder()
                .title("album title2")
                .releaseDate(LocalDateTime.now().toInstant(ZoneOffset.UTC))
                .songIds(Set.of(songId2))
                .build();
        final AlbumDto albumDto2 = songifyCrudFacade.addAlbumWithSong(albumRequestDto2);
        Long albumId2 = albumDto2.id();
        songifyCrudFacade.addArtistToAlbum(artistId,albumId1);
        songifyCrudFacade.addArtistToAlbum(artistId,albumId2);
        assertThat(songifyCrudFacade.countArtistsByAlbumId(albumId1)).isEqualTo(1);
        assertThat(songifyCrudFacade.countArtistsByAlbumId(albumId2)).isEqualTo(1);
        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged()).size()).isEqualTo(1);
        assertThat(songifyCrudFacade.findAlbumsByArtistId(artistId).size()).isEqualTo(2);
        assertThat(songifyCrudFacade.findAllSongs(Pageable.unpaged()).size()).isEqualTo(2);
        // when
        songifyCrudFacade.deleteArtistByIdWithSongsAndAlbums(artistId);
        // then
        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged())).isEmpty();
        assertThat(songifyCrudFacade.findAllAlbums()).isEmpty();
        assertThat(songifyCrudFacade.findAllSongs(Pageable.unpaged())).isEmpty();
    }

    @Test
    @DisplayName("Should retrieve song with genre")
    public void should_retrieve_song_with_genre(){

        // given
        SongRequestDto songRequestDto = SongRequestDto.builder()
                .name("song name1")
                .language(SongLanguageDto.ENGLISH)
                .build();
        final SongDto songDto = songifyCrudFacade.addSong(songRequestDto);
        Long songId = songDto.id();
        // when
        final Song songById = songifyCrudFacade.findSongById(songId);
        // then
        assertThat(songById.getGenre().getId()).isEqualTo(1L);
        assertThat(songById.getGenre().getName()).isEqualTo("default");
        assertThat(songById.getId()).isEqualTo(0L);
        assertThat(songById.getName()).isEqualTo("song name1");
    }


}