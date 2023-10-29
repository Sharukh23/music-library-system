package com.edstem.musiclibrarysystem.repository;

import com.edstem.musiclibrarysystem.model.Review;
import com.edstem.musiclibrarysystem.model.Song;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long> {
    List<Review> findBySong(Song song);
}
