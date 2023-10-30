package com.edstem.musiclibrarysystem.contract.Request;

import com.edstem.musiclibrarysystem.constant.Genre;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SongRequest {
    @NotBlank(message = "Song cannot be empty")
    private String song;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    @NotBlank(message = "Artist cannot be empty")
    private String artist;

    @NotBlank(message = "Album cannot be empty")
    private String album;
}
