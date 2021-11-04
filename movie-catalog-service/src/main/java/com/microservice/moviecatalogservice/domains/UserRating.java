package com.microservice.moviecatalogservice.domains;

import java.util.List;

// A copy domain class from rating-data-service,
public class UserRating {
    private List<Rating> ratingList;

    public UserRating() {
    }

    public UserRating(List<Rating> ratingList) {
        this.ratingList = ratingList;
    }

    public List<Rating> getRatingList() {
        return ratingList;
    }

    public void setRatingList(List<Rating> ratingList) {
        this.ratingList = ratingList;
    }
}
