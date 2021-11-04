package com.microservice.ratingdataservice.controller;

import com.microservice.ratingdataservice.domain.Rating;
import com.microservice.ratingdataservice.domain.UserRating;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/rating")
public class ratingController {
    @GetMapping("/{movieId}")
    public Rating getRating(@PathVariable("movieId") String movieId){
        return new Rating("", 6);
    }


    @GetMapping("/users/{userId}")
    public UserRating getUserRating(@PathVariable String userId){
        List<Rating> ratingList = Arrays.asList(
                new Rating("1234", 4),
                new Rating("6789", 5)
        );

        UserRating userRating = new UserRating(ratingList);
        return  userRating;
    }

}
