package com.edstem.musiclibrarysystem.repository;

import com.edstem.musiclibrarysystem.constant.Genre;
import com.edstem.musiclibrarysystem.model.Song;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongRepository extends CrudRepository<Song, Long> {
    Boolean existsBySong(String song);

    List<Song> findByGenre(Genre genre);

    List<Song> findByArtist(String artist);

    List<Song> findByAlbum(String album);
}
