package com.nikhil.shoutreview.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nikhil.shoutreview.domain.Genre;
import com.nikhil.shoutreview.domain.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Long> {
    
    public Movie findByTitle(String title);

    public List<Movie> findByGenre(Genre genre);

}