package com.edstem.musiclibrarysystem.service;

import com.edstem.musiclibrarysystem.constant.Genre;
import com.edstem.musiclibrarysystem.contract.Request.ReviewRequest;
import com.edstem.musiclibrarysystem.contract.Request.SongRequest;
import com.edstem.musiclibrarysystem.contract.Response.DeleteSongResponse;
import com.edstem.musiclibrarysystem.contract.Response.ReviewResponse;
import com.edstem.musiclibrarysystem.contract.Response.SongResponse;
import com.edstem.musiclibrarysystem.model.Review;
import com.edstem.musiclibrarysystem.model.Song;
import com.edstem.musiclibrarysystem.repository.ReviewRepository;
import com.edstem.musiclibrarysystem.repository.SongRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SongService {
    private final SongRepository songRepository;
    private final ModelMapper modelMapper;
    private final ReviewRepository reviewRepository;

    public SongResponse addSong(SongRequest request) {
        if (songRepository.existsBySong(request.getSong())) {
            throw new EntityExistsException("Song " + request.getSong() + " already exists");
        }
        Song song = modelMapper.map(request, Song.class);
        Song savedSong = songRepository.save(song);
        return modelMapper.map(savedSong, SongResponse.class);
    }

    public SongResponse viewSongById(Long id) {
        Song song =
                songRepository
                        .findById(id)
                        .orElseThrow(
                                () -> new EntityNotFoundException("Song not found with id " + id));
        return modelMapper.map(song, SongResponse.class);
    }

    public SongResponse updateSongById(Long id, SongRequest request) {
        Song song =
                songRepository
                        .findById(id)
                        .orElseThrow(
                                () -> new EntityNotFoundException("Song not found with id " + id));
        modelMapper.map(request, song);
        Song updatedSong = songRepository.save(song);
        return modelMapper.map(updatedSong, SongResponse.class);
    }

    public DeleteSongResponse deleteSongById(Long id) {
        Song song =
                songRepository
                        .findById(id)
                        .orElseThrow(
                                () -> new EntityNotFoundException("Song not found with id" + id));
        songRepository.delete(song);
        return DeleteSongResponse.builder()
                .message("Song " + song.getSong() + " has been deleted")
                .build();
    }

    public List<SongResponse> viewAllSongs() {
        List<Song> songs = (List<Song>) songRepository.findAll();
        return songs.stream()
                .map(topic -> modelMapper.map(topic, SongResponse.class))
                .collect(Collectors.toList());
    }

    public List<SongResponse> viewSongsByGenre(String genre) {
        List<Song> songs = (List<Song>) songRepository.findByGenre(Genre.valueOf(genre));
        if (songs.isEmpty()) {
            throw new EntityNotFoundException("Entity Genre not found");
        }
        return songs.stream()
                .map(song -> modelMapper.map(song, SongResponse.class))
                .collect(Collectors.toList());
    }

    public List<SongResponse> viewSongsByArtist(String artist) {
        List<Song> songs = (List<Song>) songRepository.findByArtist(artist);
        if (songs.isEmpty()) {
            throw new EntityNotFoundException("Entity Artist not found");
        }
        return songs.stream()
                .map(song -> modelMapper.map(song, SongResponse.class))
                .collect(Collectors.toList());
    }

    public List<SongResponse> viewSongsByAlbum(String album) {
        List<Song> songs = (List<Song>) songRepository.findByAlbum(album);
        if (songs.isEmpty()) {
            throw new EntityNotFoundException("Entity Album not found");
        }
        return songs.stream()
                .map(song -> modelMapper.map(song, SongResponse.class))
                .collect(Collectors.toList());
    }

    public ReviewResponse addReview(Long id, ReviewRequest request) {
        Song song =
                songRepository
                        .findById(id)
                        .orElseThrow(
                                () -> new EntityNotFoundException("Song not found with id " + id));
        Review review =
                Review.builder()
                        .reviewer(request.getReviewer())
                        .comment(request.getComment())
                        .song(song)
                        .build();
        Review savedReview = reviewRepository.save(review);
        return modelMapper.map(savedReview, ReviewResponse.class);
    }

    public List<ReviewResponse> viewAllReviews(Long id) {
        Song song =
                songRepository
                        .findById(id)
                        .orElseThrow(
                                () -> new EntityNotFoundException("Song not found with id " + id));
        List<Review> reviews = reviewRepository.findBySong(song);
        return reviews.stream()
                .map(review -> modelMapper.map(review, ReviewResponse.class))
                .collect(Collectors.toList());
    }
}
