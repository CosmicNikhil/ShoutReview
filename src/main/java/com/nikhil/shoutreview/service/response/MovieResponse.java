package com.nikhil.shoutreview.service.response;

import java.util.List;

import com.nikhil.shoutreview.domain.Genre;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieResponse {

    private String title;
    private Genre genre;
    private Double rating;
    private List<ReviewResponse> reviews;


}