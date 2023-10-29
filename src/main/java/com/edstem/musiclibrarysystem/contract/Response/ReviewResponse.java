package com.edstem.musiclibrarysystem.contract.Response;

import lombok.Data;

@Data
public class ReviewResponse {
    private Long id;
    private String reviewer;
    private String comment;
    private String song;
}
