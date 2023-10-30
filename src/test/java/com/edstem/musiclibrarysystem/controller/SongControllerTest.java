package com.edstem.musiclibrarysystem.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.edstem.musiclibrarysystem.constant.Genre;
import com.edstem.musiclibrarysystem.contract.Request.ReviewRequest;
import com.edstem.musiclibrarysystem.contract.Request.SongRequest;
import com.edstem.musiclibrarysystem.contract.Response.DeleteSongResponse;
import com.edstem.musiclibrarysystem.contract.Response.ReviewResponse;
import com.edstem.musiclibrarysystem.contract.Response.SongResponse;
import com.edstem.musiclibrarysystem.service.SongService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class SongControllerTest {
    @Autowired private MockMvc mockMvc;

    @MockBean private SongService songService;

    @Test
    void testAddSong() throws Exception {
        SongRequest request = new SongRequest("Test Song", Genre.ROCK, "Test Artist", "Test Album");
        SongResponse expectedResponse =
                new SongResponse(1L, "Test Song", Genre.ROCK, "Test Artist", "Test Album");

        when(songService.addSong(any(SongRequest.class))).thenReturn(expectedResponse);

        mockMvc.perform(
                        post("/songs")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(expectedResponse)));
    }

    @Test
    void testViewSongById() throws Exception {
        Long id = 1L;
        SongResponse songResponse =
                new SongResponse(id, "Test Song", Genre.ROCK, "Test Artist", "Test Album");

        when(songService.viewSongById(id)).thenReturn(songResponse);

        mockMvc.perform(get("/songs/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(songResponse)));
    }

    @Test
    void testUpdateSongById() throws Exception {
        Long id = 1L;
        SongRequest songRequest =
                new SongRequest("Updated Song", Genre.ROCK, "Updated Artist", "Updated Album");
        SongResponse songResponse =
                new SongResponse(id, "Updated Song", Genre.ROCK, "Updated Artist", "Updated Album");

        when(songService.updateSongById(id, songRequest)).thenReturn(songResponse);

        mockMvc.perform(
                        put("/songs/" + id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(songRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(songResponse)));
    }

    @Test
    void testDeleteSongById() throws Exception {
        Long id = 1L;
        DeleteSongResponse deleteSongResponse =
                new DeleteSongResponse("Song with id " + id + " deleted successfully.");

        when(songService.deleteSongById(id)).thenReturn(deleteSongResponse);

        mockMvc.perform(delete("/songs/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(
                        content().json(new ObjectMapper().writeValueAsString(deleteSongResponse)));
    }

    @Test
    void testViewAllSongs() throws Exception {
        List<SongResponse> songResponses = new ArrayList<>();
        songResponses.add(
                new SongResponse(1L, "Test Song 1", Genre.ROCK, "Test Artist 1", "Test Album 1"));
        songResponses.add(
                new SongResponse(2L, "Test Song 2", Genre.POP, "Test Artist 2", "Test Album 2"));

        when(songService.viewAllSongs()).thenReturn(songResponses);

        mockMvc.perform(get("/songs"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(songResponses)));
    }

    @Test
    void testViewSongsByGenre() throws Exception {
        String genre = "ROCK";
        List<SongResponse> songResponses = new ArrayList<>();
        songResponses.add(
                new SongResponse(
                        1L, "Test Song 1", Genre.valueOf(genre), "Test Artist 1", "Test Album 1"));
        songResponses.add(
                new SongResponse(
                        2L, "Test Song 2", Genre.valueOf(genre), "Test Artist 2", "Test Album 2"));

        when(songService.viewSongsByGenre(genre)).thenReturn(songResponses);

        mockMvc.perform(get("/songs/genre/" + genre))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(songResponses)));
    }

    @Test
    void testViewSongsByArtist() throws Exception {
        String artist = "Test Artist";
        List<SongResponse> songResponses = new ArrayList<>();
        songResponses.add(new SongResponse(1L, "Test Song 1", Genre.ROCK, artist, "Test Album 1"));
        songResponses.add(new SongResponse(2L, "Test Song 2", Genre.POP, artist, "Test Album 2"));

        when(songService.viewSongsByArtist(artist)).thenReturn(songResponses);

        mockMvc.perform(get("/songs/artist/" + artist))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(songResponses)));
    }

    @Test
    void testViewSongsByAlbum() throws Exception {
        String album = "Test Album";
        List<SongResponse> songResponses = new ArrayList<>();
        songResponses.add(new SongResponse(1L, "Test Song 1", Genre.ROCK, "Test Artist 1", album));
        songResponses.add(new SongResponse(2L, "Test Song 2", Genre.POP, "Test Artist 2", album));

        when(songService.viewSongsByAlbum(album)).thenReturn(songResponses);

        mockMvc.perform(get("/songs/album/" + album))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(songResponses)));
    }

    @Test
    void testAddReview() throws Exception {
        Long id = 1L;
        ReviewRequest reviewRequest = new ReviewRequest("Test Review", "Test Comment");
        ReviewResponse reviewResponse =
                new ReviewResponse(id, "Test Review", "Test Comment", "Test Song");

        when(songService.addReview(id, reviewRequest)).thenReturn(reviewResponse);

        mockMvc.perform(
                        post("/songs/" + id + "/reviews")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(reviewRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(reviewResponse)));
    }

    @Test
    void testViewAllReviews() throws Exception {
        Long id = 1L;
        List<ReviewResponse> reviewResponses =
                Arrays.asList(
                        new ReviewResponse(id, "Test Review 1", "Test Comment 1", "Test Song 1"),
                        new ReviewResponse(id, "Test Review 2", "Test Comment 2", "Test Song 2"));

        when(songService.viewAllReviews(id)).thenReturn(reviewResponses);

        mockMvc.perform(get("/songs/" + id + "/reviews").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(reviewResponses)));
    }
}
