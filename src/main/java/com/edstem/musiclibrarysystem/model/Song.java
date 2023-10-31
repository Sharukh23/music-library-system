package com.edstem.musiclibrarysystem.model;

import com.edstem.musiclibrarysystem.constant.Genre;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String song;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    private String artist;
    private String album;

    @OneToMany(mappedBy = "song", cascade = CascadeType.ALL)
    private List<Review> reviews;
}
