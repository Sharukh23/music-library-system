package com.edstem.musiclibrarysystem.contract.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequest {
    @NotBlank(message = "Reviewer cannot be empty")
    private String reviewer;
    @NotBlank(message = "Comment cannot be empty")
    private String comment;
}
