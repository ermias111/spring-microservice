package com.microservice.moviecatalogservice.controllers;

import com.microservice.moviecatalogservice.domains.CatalogItem;
import com.microservice.moviecatalogservice.domains.Movie;
import com.microservice.moviecatalogservice.domains.Rating;
import com.microservice.moviecatalogservice.domains.UserRating;
import com.microservice.moviecatalogservice.services.MovieInfo;
import com.microservice.moviecatalogservice.services.UserRatingInfo;
import com.netflix.discovery.converters.Auto;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.catalina.User;
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



// we need to copy it so that we can map the coming
// rating data in rating class, this might be redundant, but we need it because this is how microservice
// application works. we get a lot of benefits along with it's downsides.


// This class consumes all the other services
@RestController
@RequestMapping("/catalog")
public class MovieCatalogController {

    @Autowired
    MovieInfo movieInfo;

    @Autowired
    UserRatingInfo userRatingInfo;

    // A rest client library from springframework, It is used to make a rest call to other services
    // so that it can get the required data. Since we only need it to be created once in the our service
    // we create a bean and inject it wherever we need it.
    @Autowired
    private RestTemplate restTemplate;

    // Using Web Client, It is another way to create a rest client. It is more recent and it uses reactive programming( asynchronous )
//    @Autowired
//    private WebClient.Builder webClientBuilder;

    @GetMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){


        // Call rating-data-service with it's service name and map the resulting list of ratings to the class UserRating.
        // In this kind of call, eureka-server looks for the service name and it provides the actual url and port number of the specified
        // service instance.
        UserRating userRating =  userRatingInfo.getUserRating(userId);



        return userRating.getRatingList().stream().map(rating -> {
            // Using WebClient
//            Movie movie = webClientBuilder
//                    .build()
//                    .get()
//                    .uri("http://localhost:8081/movies/" + rating.getMovieId())
//                    .retrieve()
//                    .bodyToMono(Movie.class)
//                    .block();


            return movieInfo.getCatalogItem(rating);
        }).collect(Collectors.toList());
    }


    public List<CatalogItem> getFallbackCatalog(@PathVariable("userId") String string){
        return Arrays.asList(new CatalogItem("No movie", "", 0));
    }

}
