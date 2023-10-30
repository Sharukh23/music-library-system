package com.edstem.musiclibrarysystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

public class SongServiceTest {
    private SongRepository songRepository;
    private ModelMapper modelMapper;
    private SongService songService;
    private ReviewRepository reviewRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        songRepository = Mockito.mock(SongRepository.class);
        modelMapper = new ModelMapper();
        reviewRepository = Mockito.mock(ReviewRepository.class);
        songService = new SongService(songRepository, modelMapper, reviewRepository);
    }

    @Test
    void testAddSong() {
        SongRequest request = new SongRequest("Test Song", Genre.ROCK, "Test Artist", "Test Album");
        Song song = modelMapper.map(request, Song.class);
        SongResponse expectedResponse = modelMapper.map(song, SongResponse.class);

        when(songRepository.existsBySong(request.getSong())).thenReturn(false);
        when(songRepository.save(any())).thenReturn(song);

        SongResponse actualResponse = songService.addSong(request);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void testViewSongById() {
        Long id = 1L;
        Song song =
                new Song(
                        id,
                        "Test Song",
                        Genre.ROCK,
                        "Test Artist",
                        "Test Album",
                        new ArrayList<>());

        SongResponse expectedResponse = modelMapper.map(song, SongResponse.class);

        when(songRepository.findById(id)).thenReturn(Optional.of(song));

        SongResponse actualResponse = songService.viewSongById(id);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void testUpdateSongById() {
        Long id = 1L;
        Song originalSong =
                new Song(
                        id,
                        "Original Song",
                        Genre.ROCK,
                        "Original Artist",
                        "Original Album",
                        new ArrayList<>());
        SongRequest request =
                new SongRequest("Updated Song", Genre.POP, "Updated Artist", "Updated Album");

        Song updatedSong =
                new Song(
                        id,
                        request.getSong(),
                        request.getGenre(),
                        request.getArtist(),
                        request.getAlbum(),
                        new ArrayList<>());
        SongResponse expectedResponse = modelMapper.map(updatedSong, SongResponse.class);

        when(songRepository.findById(id)).thenReturn(Optional.of(originalSong));
        when(songRepository.save(any(Song.class))).thenReturn(updatedSong);

        SongResponse actualResponse = songService.updateSongById(id, request);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void testDeleteSongById() {
        Long id = 1L;
        Song song =
                new Song(
                        id,
                        "Test Song",
                        Genre.ROCK,
                        "Test Artist",
                        "Test Album",
                        new ArrayList<>());

        DeleteSongResponse expectedResponse =
                DeleteSongResponse.builder()
                        .message("Song " + song.getSong() + " has been deleted")
                        .build();

        when(songRepository.findById(id)).thenReturn(Optional.of(song));
        doNothing().when(songRepository).delete(song);

        DeleteSongResponse actualResponse = songService.deleteSongById(id);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void testViewAllSongs() {
        Song song1 =
                new Song(
                        1L,
                        "Test Song 1",
                        Genre.ROCK,
                        "Test Artist 1",
                        "Test Album 1",
                        new ArrayList<>());
        Song song2 =
                new Song(
                        2L,
                        "Test Song 2",
                        Genre.POP,
                        "Test Artist 2",
                        "Test Album 2",
                        new ArrayList<>());

        List<Song> songs = Arrays.asList(song1, song2);
        List<SongResponse> expectedResponse =
                songs.stream()
                        .map(song -> modelMapper.map(song, SongResponse.class))
                        .collect(Collectors.toList());

        when(songRepository.findAll()).thenReturn(songs);

        List<SongResponse> actualResponse = songService.viewAllSongs();

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void testViewSongsByGenre() {
        String genre = "ROCK";
        Song song1 =
                new Song(
                        1L,
                        "Test Song 1",
                        Genre.valueOf(genre),
                        "Test Artist 1",
                        "Test Album 1",
                        new ArrayList<>());
        Song song2 =
                new Song(
                        2L,
                        "Test Song 2",
                        Genre.valueOf(genre),
                        "Test Artist 2",
                        "Test Album 2",
                        new ArrayList<>());

        List<Song> songs = Arrays.asList(song1, song2);
        List<SongResponse> expectedResponse =
                songs.stream()
                        .map(song -> modelMapper.map(song, SongResponse.class))
                        .collect(Collectors.toList());

        when(songRepository.findByGenre(Genre.valueOf(genre))).thenReturn(songs);

        List<SongResponse> actualResponse = songService.viewSongsByGenre(genre);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void testViewSongsByArtist() {
        String artist = "Test Artist";
        Song song1 =
                new Song(1L, "Test Song 1", Genre.ROCK, artist, "Test Album 1", new ArrayList<>());
        Song song2 =
                new Song(2L, "Test Song 2", Genre.POP, artist, "Test Album 2", new ArrayList<>());

        List<Song> songs = Arrays.asList(song1, song2);
        List<SongResponse> expectedResponse =
                songs.stream()
                        .map(song -> modelMapper.map(song, SongResponse.class))
                        .collect(Collectors.toList());

        when(songRepository.findByArtist(artist)).thenReturn(songs);

        List<SongResponse> actualResponse = songService.viewSongsByArtist(artist);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void testViewSongsByAlbum() {
        String album = "Test Album";
        Song song1 =
                new Song(1L, "Test Song 1", Genre.ROCK, "Test Artist 1", album, new ArrayList<>());
        Song song2 =
                new Song(2L, "Test Song 2", Genre.POP, "Test Artist 2", album, new ArrayList<>());

        List<Song> songs = Arrays.asList(song1, song2);
        List<SongResponse> expectedResponse =
                songs.stream()
                        .map(song -> modelMapper.map(song, SongResponse.class))
                        .collect(Collectors.toList());

        when(songRepository.findByAlbum(album)).thenReturn(songs);

        List<SongResponse> actualResponse = songService.viewSongsByAlbum(album);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void testAddReview() {
        Long id = 1L;
        ReviewRequest request = new ReviewRequest("Test Reviewer", "Test Comment");
        Song song =
                new Song(
                        id,
                        "Test Song",
                        Genre.ROCK,
                        "Test Artist",
                        "Test Album",
                        new ArrayList<>());
        Review review =
                Review.builder()
                        .reviewer(request.getReviewer())
                        .comment(request.getComment())
                        .song(song)
                        .build();

        ReviewResponse expectedResponse = modelMapper.map(review, ReviewResponse.class);

        when(songRepository.findById(id)).thenReturn(Optional.of(song));
        when(reviewRepository.save(any(Review.class))).thenReturn(review);

        ReviewResponse actualResponse = songService.addReview(id, request);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void testViewAllReviews() {
        Long id = 1L;
        Song song =
                new Song(
                        id,
                        "Test Song",
                        Genre.ROCK,
                        "Test Artist",
                        "Test Album",
                        new ArrayList<>());
        Review review1 =
                Review.builder().reviewer("Reviewer 1").comment("Comment 1").song(song).build();
        Review review2 =
                Review.builder().reviewer("Reviewer 2").comment("Comment 2").song(song).build();

        List<Review> reviews = Arrays.asList(review1, review2);
        List<ReviewResponse> expectedResponse =
                reviews.stream()
                        .map(review -> modelMapper.map(review, ReviewResponse.class))
                        .collect(Collectors.toList());

        when(songRepository.findById(id)).thenReturn(Optional.of(song));
        when(reviewRepository.findBySong(song)).thenReturn(reviews);

        List<ReviewResponse> actualResponse = songService.viewAllReviews(id);

        assertEquals(expectedResponse, actualResponse);
    }
}
