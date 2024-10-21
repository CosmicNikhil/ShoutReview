package com.nikhil.shoutreview.service;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import com.nikhil.shoutreview.domain.Movie;
import com.nikhil.shoutreview.repository.MovieRepository;
import com.nikhil.shoutreview.exception.InvalidInputException;
import com.nikhil.shoutreview.exception.OperationFailedException;

@Service
public class AdminService {

    private final MovieRepository movieRepository;

    // Constructor injection
    public AdminService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Movie addMovie(Movie movie) {
        // Validate the input to avoid invalid movie objects
        if (movie == null || movie.getTitle() == null || movie.getTitle().isEmpty()) {
            throw new InvalidInputException("Invalid input: Movie details are missing or incomplete.");
        }

        try {
            return movieRepository.save(movie);
        } catch (DataAccessException e) {
            // Throw a custom exception to handle operation failures more meaningfully
            throw new OperationFailedException("Failed to save the movie. Please try again.", e);
        }
    }
}
