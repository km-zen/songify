package com.songify.domain.crud;

class SongifyCrudFacadeConfiguration {

    public static SongifyCrudFacade createSongifyCrud(final SongRepository songRepository,
                                                      final GenreRepository genreRepository,
                                                      final ArtistRepository artistRepository,
                                                      final AlbumRepository albumRepository){
        SongRetriever songRetriever = new SongRetriever(songRepository);
        SongUpdater songUpdater = new SongUpdater(songRepository);
        AlbumAdder albumAdder = new AlbumAdder( albumRepository,songRetriever);
        ArtistRetriever artistRetriever = new ArtistRetriever(artistRepository);
        AlbumRetriever albumRetriever = new AlbumRetriever(albumRepository);
        GenreDeleter genreDeleter = new GenreDeleter(genreRepository);
        SongDeleter songDeleter = new SongDeleter(songRepository, songRetriever);
        GenreRetriever genreRetriever = new GenreRetriever(genreRepository);
        GenreAssigner genreAssigner = new GenreAssigner(songRetriever,genreRetriever);
        SongAdder songAdder = new SongAdder(songRepository,genreAssigner);
        ArtistAdder artistAdder = new ArtistAdder(artistRepository);
        GenreAdder genreAdder = new GenreAdder(genreRepository);
        AlbumDeleter albumDeleter = new AlbumDeleter(albumRepository);
        ArtistDeleter artistDeleter = new ArtistDeleter(artistRepository, artistRetriever, albumRetriever, songDeleter,albumDeleter);
        ArtistAssigner artistAssigner = new ArtistAssigner(artistRetriever, albumRetriever);
        ArtistUpdater artistUpdater = new ArtistUpdater(artistRetriever, artistRepository);
        SongAssigner songAssigner = new SongAssigner(albumRetriever,songRetriever);
        return new SongifyCrudFacade(
                songDeleter,
                songAdder,
                songRetriever,
                songUpdater,
                artistAdder,
                genreAdder,
                albumAdder,
                artistRetriever,
                albumRetriever,
                artistDeleter,
                artistAssigner,
                artistUpdater,
                genreRetriever,
                genreAssigner,
                songAssigner
        );
    }
}
