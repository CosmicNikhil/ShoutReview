package com.nikhil.shoutreview.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.nikhil.shoutreview.domain.Genre;
import com.nikhil.shoutreview.domain.Movie;
import com.nikhil.shoutreview.exception.EntityNotFoundException;
import com.nikhil.shoutreview.exception.InvalidInputException;
import com.nikhil.shoutreview.exception.OperationFailedException;
import com.nikhil.shoutreview.repository.MovieRepository;
import com.nikhil.shoutreview.service.response.MovieResponse;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public MovieResponse findMovie(String title) {
        try {
            Movie movie = movieRepository.findByTitle(title);
            if (Objects.nonNull(movie)) {
                return movie.toMovieResponse();
            }
            throw new EntityNotFoundException("Movie with title " + title + " not found.");
        } catch (DataAccessException e) {
            throw new OperationFailedException("Database error occurred while fetching the movie.", e);
        }
    }

    public List<MovieResponse> findMoviesByGenre(String genre) {
        // Validate genre input
        if (Arrays.stream(Genre.values()).noneMatch(g -> g.toString().equals(genre))) {
            throw new InvalidInputException("Invalid genre: " + genre);
        }

        try {
            List<Movie> movieList = movieRepository.findByGenre(Genre.valueOf(genre));
            if (!CollectionUtils.isEmpty(movieList)) {
                List<MovieResponse> movieResponseList = movieList.stream()
                        .sorted(Comparator.comparing(Movie::getRating, Comparator.reverseOrder()))
                        .map(Movie::toMovieResponse)
                        .collect(Collectors.toList());

                // Return top 5 movies
                if (movieResponseList.size() > 5) {
                    return movieResponseList.subList(0, 5);
                }
                return movieResponseList;
            }
            return new ArrayList<>();
        } catch (DataAccessException e) {
            throw new OperationFailedException("Failed to retrieve movies by genre.", e);
        }
    }
}
