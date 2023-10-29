package com.edstem.musiclibrarysystem.controller;

import com.edstem.musiclibrarysystem.contract.Request.ReviewRequest;
import com.edstem.musiclibrarysystem.contract.Request.SongRequest;
import com.edstem.musiclibrarysystem.contract.Response.DeleteSongResponse;
import com.edstem.musiclibrarysystem.contract.Response.ReviewResponse;
import com.edstem.musiclibrarysystem.contract.Response.SongResponse;
import com.edstem.musiclibrarysystem.service.SongService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/songs")
@RequiredArgsConstructor
public class SongController {

    private final SongService songService;

    @PostMapping
    public SongResponse addSong(@Valid @RequestBody SongRequest request) {
        return songService.addSong(request);
    }

    @GetMapping("/{id}")
    public SongResponse viewSongById(@PathVariable Long id) {
        return songService.viewSongById(id);
    }

    @PutMapping("/{id}")
    public SongResponse updateSongById(@PathVariable Long id, @Valid @RequestBody SongRequest request) {
        return songService.updateSongById(id, request);
    }

    @DeleteMapping("/{id}")
    public DeleteSongResponse deleteSongById(@PathVariable Long id) {
        return songService.deleteSongById(id);
    }

    @GetMapping
    public List<SongResponse> viewAllSongs() {
        return songService.viewAllSongs();
    }

    @GetMapping("/genre/{genre}")
    public List<SongResponse> viewSongsByGenre(@PathVariable String genre) {
        return songService.viewSongsByGenre(genre);
    }

    @GetMapping("/artist/{artist}")
    public List<SongResponse> viewSongsByArtist(@PathVariable String artist) {
        return songService.viewSongsByArtist(artist);
    }

    @GetMapping("/album/{album}")
    public List<SongResponse> viewSongsByAlbum(@PathVariable String album) {
        return songService.viewSongsByAlbum(album);
    }

    @PostMapping("/{id}/reviews")
    public ReviewResponse addReview(@PathVariable Long id, @Valid @RequestBody ReviewRequest review) {
        return songService.addReview(id, review);
    }

    @GetMapping("/{id}/reviews")
    public List<ReviewResponse> viewAllReviews(@PathVariable Long id) {
        return songService.viewAllReviews(id);
    }
}
