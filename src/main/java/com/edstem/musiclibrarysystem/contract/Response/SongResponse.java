package com.edstem.musiclibrarysystem.contract.Response;

import com.edstem.musiclibrarysystem.constant.Genre;
import lombok.Data;

@Data
public class SongResponse {
    private Long id;
    private String song;
    private Genre genre;
    private String artist;
    private String album;
}
