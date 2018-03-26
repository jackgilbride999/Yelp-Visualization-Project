package com.LukeHackett;

public class ReviewCrawler extends Thread {

    Business business;
    queries qControl;
    private int start;

    ReviewCrawler(Business business, queries qControl){
        this.business = business;
        this.qControl = qControl;
        this.start = 0;
        start();
    }

    public void run(){
        qControl.reviews(business.getBusiness_id());//, start, 10));
    }
}
