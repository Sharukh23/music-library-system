package com.edstem.musiclibrarysystem.contract.Response;

import com.edstem.musiclibrarysystem.constant.Genre;
import com.edstem.musiclibrarysystem.model.Review;
import lombok.Data;
import java.util.List;

@Data
public class SongResponse {
    private String song;
    private Genre genre;
    private String artist;
    private String album;
    private List<Review> reviews;
}
