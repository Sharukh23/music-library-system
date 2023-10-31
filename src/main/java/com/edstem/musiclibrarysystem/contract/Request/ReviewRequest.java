package com.edstem.musiclibrarysystem.contract.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequest {
    @NotBlank(message = "Reviewer cannot be empty")
    private String reviewer;

    @NotBlank(message = "Comment cannot be empty")
    private String comment;
}
