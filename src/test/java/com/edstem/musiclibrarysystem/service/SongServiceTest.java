package com.edstem.musiclibrarysystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.edstem.musiclibrarysystem.constant.Genre;
import com.edstem.musiclibrarysystem.contract.Request.ReviewRequest;
import com.edstem.musiclibrarysystem.contract.Request.SongRequest;
import com.edstem.musiclibrarysystem.contract.Response.ReviewResponse;
import com.edstem.musiclibrarysystem.contract.Response.SongResponse;
import com.edstem.musiclibrarysystem.model.Review;
import com.edstem.musiclibrarysystem.model.Song;
import com.edstem.musiclibrarysystem.repository.ReviewRepository;
import com.edstem.musiclibrarysystem.repository.SongRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
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
    public void testViewSongById() {
        Long id = 1L;
        Song song =
                new Song(
                        id,
                        "Test Song",
                        Genre.ROCK,
                        "Test Artist",
                        "Test Album",
                        new ArrayList<>());
        SongResponse expectedResponse = new ModelMapper().map(song, SongResponse.class);

        when(songRepository.findById(id)).thenReturn(Optional.of(song));

        SongResponse actualResponse = songService.viewSongById(id);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testUpdateSongById() {
        Long id = 1L;
        Song song =
                new Song(
                        id,
                        "Original Song",
                        Genre.ROCK,
                        "Original Artist",
                        "Original Album",
                        new ArrayList<>());
        SongRequest songRequest =
                new SongRequest("Updated Song", Genre.ROCK, "Updated Artist", "Updated Album");
        Song updatedSong =
                new Song(
                        id,
                        "Updated Song",
                        Genre.ROCK,
                        "Updated Artist",
                        "Updated Album",
                        new ArrayList<>());
        SongResponse expectedResponse = new ModelMapper().map(updatedSong, SongResponse.class);

        when(songRepository.findById(id)).thenReturn(Optional.of(song));
        when(songRepository.save(any(Song.class))).thenReturn(updatedSong);

        SongResponse actualResponse = songService.updateSongById(id, songRequest);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void testDeleteSongById() {
        Long id = 1L;
        Song song =
                new Song(
                        id,
                        "Original Song",
                        Genre.ROCK,
                        "Original Artist",
                        "Original Album",
                        new ArrayList<>());

        when(songRepository.findById(any())).thenReturn(Optional.of(song));
        doNothing().when(songRepository).delete(any());

        String message = songService.deleteSongById(song.getId());

        assertEquals(message, "Song " + song.getSong() + " has been deleted");
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
    public void testAddReview() {
        Long id = 1L;
        Song song =
                new Song(
                        id,
                        "Original Song",
                        Genre.ROCK,
                        "Original Artist",
                        "Original Album",
                        new ArrayList<>());
        ReviewRequest reviewRequest = new ReviewRequest("John Doe", "Great song!");
        Review review =
                Review.builder()
                        .id(id)
                        .reviewer("John Doe")
                        .comment("Great song!")
                        .song(song)
                        .build();
        ReviewResponse expectedResponse = new ModelMapper().map(review, ReviewResponse.class);

        when(songRepository.findById(id)).thenReturn(Optional.of(song));
        when(reviewRepository.save(any(Review.class))).thenReturn(review);

        ReviewResponse actualResponse = songService.addReview(id, reviewRequest);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testViewAllReviews() {
        Long id = 1L;
        Song song =
                new Song(
                        id,
                        "Original Song",
                        Genre.ROCK,
                        "Original Artist",
                        "Original Album",
                        new ArrayList<>());
        Review review =
                Review.builder()
                        .id(id)
                        .reviewer("John Doe")
                        .comment("Great song!")
                        .song(song)
                        .build();
        List<Review> reviews = Arrays.asList(review);
        ReviewResponse expectedResponse = new ModelMapper().map(review, ReviewResponse.class);
        List<ReviewResponse> expectedResponses = Arrays.asList(expectedResponse);

        when(songRepository.findById(id)).thenReturn(Optional.of(song));
        when(reviewRepository.findBySong(song)).thenReturn(reviews);

        List<ReviewResponse> actualResponses = songService.viewAllReviews(id);

        assertEquals(expectedResponses, actualResponses);
    }

    @Test
    public void testHashCode() {
        ReviewResponse review1 = new ReviewResponse(1L, "John Doe", "Great song!", "Original Song");
        ReviewResponse review2 = new ReviewResponse(1L, "John Doe", "Great song!", "Original Song");
        SongResponse song1 =
                new SongResponse(
                        1L, "Original Song", Genre.ROCK, "Original Artist", "Original Album");
        SongResponse song2 =
                new SongResponse(
                        1L, "Original Song", Genre.ROCK, "Original Artist", "Original Album");

        HashSet<ReviewResponse> reviewSet = new HashSet<>();
        reviewSet.add(review1);
        reviewSet.add(review2);

        HashSet<SongResponse> songSet = new HashSet<>();
        songSet.add(song1);
        songSet.add(song2);

        assertEquals(1, reviewSet.size());
        assertEquals(1, songSet.size());
    }
}
