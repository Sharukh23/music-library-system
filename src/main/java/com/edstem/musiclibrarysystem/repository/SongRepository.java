package com.edstem.musiclibrarysystem.repository;

import com.edstem.musiclibrarysystem.constant.Genre;
import com.edstem.musiclibrarysystem.model.Song;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends CrudRepository<Song, Long> {
    Boolean existsBySong(String song);

    List<Song> findByGenre(Genre genre);

    List<Song> findByArtist(String artist);

    List<Song> findByAlbum(String album);
}
