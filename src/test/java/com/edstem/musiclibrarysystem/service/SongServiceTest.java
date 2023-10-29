package com.edstem.musiclibrarysystem.service;

import com.edstem.musiclibrarysystem.constant.Genre;
import com.edstem.musiclibrarysystem.contract.Request.SongRequest;
import com.edstem.musiclibrarysystem.contract.Response.SongResponse;
import com.edstem.musiclibrarysystem.model.Song;
import com.edstem.musiclibrarysystem.repository.ReviewRepository;
import com.edstem.musiclibrarysystem.repository.SongRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class SongServiceTest {
    private SongRepository songRepository;
    private ModelMapper modelMapper;
    private SongService songService;
    private ReviewRepository reviewRepository;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
        songRepository = Mockito.mock(SongRepository.class);
        modelMapper = new ModelMapper();
        songService = new SongService(songRepository, modelMapper,reviewRepository);
    }

    @Test
    void testAddSong(){
        SongRequest request = new SongRequest("Test Song", Genre.ROCK, "Test Artist", "Test Album");
        Song song = modelMapper.map(request, Song.class);
        song.setId(1L);
        SongResponse expectedResponse = modelMapper.map(song, SongResponse.class);

        when(songRepository.existsBySong(request.getSong())).thenReturn(false);
        when(songRepository.save(any())).thenReturn(song);

        SongResponse actualResponse = songService.addSong(request);

        assertEquals(expectedResponse, actualResponse);
    }
}
