package com.microservice.movieinfoservice.controllers;

import com.microservice.movieinfoservice.domains.Movie;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/movies")
public class movieController {
    @GetMapping("/{movieId}")
    public Movie getCatalog(@PathVariable("movieId") String movieId){
        return new Movie("1", "Transformer");
    }

}
