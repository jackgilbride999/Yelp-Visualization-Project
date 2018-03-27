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
        switch(Main.selectedFiter) {
            case 0:
                reviews.addAll(qControl.reviews(business.getBusiness_id()));//, start, 10));
                break;
            case 1:
                reviews.addAll(qControl.reviewsFilteredByStars(business.getBusiness_id(), 5));
                break;
            case 2:
                reviews.addAll(qControl.reviewsFilteredByStars(business.getBusiness_id(), 4));
                break;
            case 3:
                reviews.addAll(qControl.reviewsFilteredByStars(business.getBusiness_id(), 3));
                break;
            case 4:
                reviews.addAll(qControl.reviewsFilteredByStars(business.getBusiness_id(), 2));
                break;
            case 5:
                reviews.addAll(qControl.reviewsFilteredByStars(business.getBusiness_id(), 1));
                break;
        }
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }
}
