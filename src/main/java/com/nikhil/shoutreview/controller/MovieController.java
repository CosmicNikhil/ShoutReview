package com.nikhil.shoutreview.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nikhil.shoutreview.service.MovieService;
import com.nikhil.shoutreview.service.response.MovieResponse;

@RestController
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/title")
    public ResponseEntity<MovieResponse> findMovie(@RequestParam String title) {
        MovieResponse movieResponse = movieService.findMovie(title);
        return new ResponseEntity<>(movieResponse, HttpStatus.OK);
    }

    @GetMapping("/genre")
    public ResponseEntity<List<MovieResponse>> findMovieByGenre(@RequestParam String genre) {
        List<MovieResponse> movieResponses = movieService.findMoviesByGenre(genre);
        return new ResponseEntity<>(movieResponses, HttpStatus.OK);
    }

}
