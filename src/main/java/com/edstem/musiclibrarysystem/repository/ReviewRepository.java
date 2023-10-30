package com.edstem.musiclibrarysystem.repository;

import com.edstem.musiclibrarysystem.model.Review;
import com.edstem.musiclibrarysystem.model.Song;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long> {
    List<Review> findBySong(Song song);
}
