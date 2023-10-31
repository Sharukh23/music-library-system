package com.edstem.musiclibrarysystem.contract.Response;

import com.edstem.musiclibrarysystem.constant.Genre;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class SongResponse {
    private Long id;
    private String song;
    private Genre genre;
    private String artist;
    private String album;
}
