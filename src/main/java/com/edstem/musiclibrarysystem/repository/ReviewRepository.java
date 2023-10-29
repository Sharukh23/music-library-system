package com.edstem.musiclibrarysystem.repository;

import com.edstem.musiclibrarysystem.model.Review;
import com.edstem.musiclibrarysystem.model.Song;
import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends CrudRepository<Review,Long> {
}
