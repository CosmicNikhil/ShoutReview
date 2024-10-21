package com.nikhil.shoutreview.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.nikhil.shoutreview.domain.Movie;
import com.nikhil.shoutreview.domain.Review;
import com.nikhil.shoutreview.exception.EntityNotFoundException;
import com.nikhil.shoutreview.exception.OperationFailedException;
import com.nikhil.shoutreview.repository.MovieRepository;
import com.nikhil.shoutreview.repository.ReviewRepository;
import com.nikhil.shoutreview.service.response.ReviewResponse;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MovieRepository movieRepository;

    public void addReview(Review review) {
        try {
            // Check if the movie exists for the review
            Movie movie = movieRepository.findById(review.getMovie().getId())
                .orElseThrow(() -> new EntityNotFoundException("Movie with ID " + review.getMovie().getId() + " not found."));

            // Save the review and update the movie rating
            reviewRepository.save(review);
            
            Double average = reviewRepository.getReviewAverage(movie.getId());
            movie.setRating(average);
            movieRepository.save(movie);
        } catch (DataAccessException e) {
            throw new OperationFailedException("Failed to save review or update movie rating.", e);
        }
    }

    public ReviewResponse getReviewById(Long reviewId) {
        try {
            // Fetch the review by ID
            Optional<Review> review = reviewRepository.findById(reviewId);
            return review.map(Review::toReviewResponse)
                .orElseThrow(() -> new EntityNotFoundException("Review with ID " + reviewId + " not found."));
        } catch (DataAccessException e) {
            throw new OperationFailedException("Failed to retrieve review from the database.", e);
        }
    }
}
