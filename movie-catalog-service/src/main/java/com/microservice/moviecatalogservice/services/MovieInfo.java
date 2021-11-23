package com.microservice.moviecatalogservice.services;

import com.microservice.moviecatalogservice.domains.CatalogItem;
import com.microservice.moviecatalogservice.domains.Movie;
import com.microservice.moviecatalogservice.domains.Rating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieInfo {
    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallbackCatalogItem")
    public CatalogItem getCatalogItem(Rating rating){
        Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
        return new CatalogItem(movie.getName(), movie.getMovieId(), rating.getRating());
    }


    private CatalogItem getFallbackCatalogItem(Rating rating){
        return new CatalogItem("Movie not found", "", 0);
    }

}
