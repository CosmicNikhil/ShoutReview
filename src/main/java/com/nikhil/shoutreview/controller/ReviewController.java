package com.nikhil.shoutreview.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nikhil.shoutreview.service.ReviewService;
import com.nikhil.shoutreview.service.request.ReviewRequest;
import com.nikhil.shoutreview.service.response.ReviewResponse;

@RestController
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/add")
    public ResponseEntity<Void> addReview(@RequestBody ReviewRequest reviewRequest) {
        reviewService.addReview(reviewRequest.toReview());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/find")
    public ResponseEntity<ReviewResponse> getReview(@RequestParam Long reviewId) {
        ReviewResponse reviewResponse = reviewService.getReviewById(reviewId);
        if (reviewResponse != null) {
            return new ResponseEntity<>(reviewResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
