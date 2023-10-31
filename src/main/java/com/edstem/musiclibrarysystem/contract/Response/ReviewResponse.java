package com.edstem.musiclibrarysystem.contract.Response;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
public class ReviewResponse {
    private Long id;
    private String reviewer;
    private String comment;
    private String song;
}
