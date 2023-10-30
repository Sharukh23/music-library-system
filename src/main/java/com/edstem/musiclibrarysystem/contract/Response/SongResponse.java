package com.edstem.musiclibrarysystem.contract.Response;

import com.edstem.musiclibrarysystem.constant.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SongResponse {
    private Long id;
    private String song;
    private Genre genre;
    private String artist;
    private String album;
}
