package com.edstem.musiclibrarysystem.contract.Request;

import com.edstem.musiclibrarysystem.constant.Genre;
import com.edstem.musiclibrarysystem.model.Review;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class SongRequest {
    @NotBlank(message = "Song cannot be empty")
    private String song;
    @Enumerated(EnumType.STRING)
    private Genre genre;
    @NotBlank(message = "Artist cannot be empty")
    private String artist;
    @NotBlank(message = "Album cannot be empty")
    private String album;
    private List<Review> reviews;
}
