package com.edstem.musiclibrarysystem.repository;

import com.edstem.musiclibrarysystem.model.Song;
import org.springframework.data.repository.CrudRepository;

public interface SongRepository extends CrudRepository<Song,Long> {
}
