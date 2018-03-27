package com.LukeHackett;

import java.util.ArrayList;

public class ReviewCrawler extends Thread {

    Business business;
    queries qControl;
    ArrayList<Review> reviews;
    private int start;

    ReviewCrawler(Business business, queries qControl){
        this.business = business;
        this.qControl = qControl;
        this.reviews = new ArrayList<Review>();
        this.start = 0;
        start();
    }

    public void run(){
        reviews.addAll(qControl.reviewsFilteredByStars(business.getBusiness_id(),5));//, start, 10));
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }
}
