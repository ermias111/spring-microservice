package com.microservice.moviecatalogservice.controllers;

import com.microservice.moviecatalogservice.domains.CatalogItem;
import com.microservice.moviecatalogservice.domains.Movie;
import com.microservice.moviecatalogservice.domains.Rating;
import com.microservice.moviecatalogservice.domains.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogController {
    @Autowired
    private RestTemplate restTemplate;

    // Using Web Client
//    @Autowired
//    private WebClient.Builder webClientBuilder;

    @GetMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){



        UserRating userRating =  restTemplate.getForObject("http://rating-data-service/rating/users/4", UserRating.class);



        return userRating.getRatingList().stream().map(rating -> {
            // Using WebClient
//            Movie movie = webClientBuilder
//                    .build()
//                    .get()
//                    .uri("http://localhost:8081/movies/" + rating.getMovieId())
//                    .retrieve()
//                    .bodyToMono(Movie.class)
//                    .block();

            Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);

            return new CatalogItem(movie.getName(), movie.getMovieId(), rating.getRating());
        }).collect(Collectors.toList());
    }

}
