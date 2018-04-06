package com.LukeHackett;

import java.util.ArrayList;

public class ReviewCrawler extends Thread {

    /*
    Written by LH- fetches all reviews for a given business
     */

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
        Main.reviews.addAll(qControl.reviews(business.getBusiness_id()));//, start, 10));

    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }
}
