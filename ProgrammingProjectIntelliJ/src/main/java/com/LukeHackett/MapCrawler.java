package com.LukeHackett;

import processing.core.PApplet;
import processing.core.PImage;

public class MapCrawler extends Thread{
    private PApplet canvas;
    private Business selectedBusiness;
    private int zoom;

    public MapCrawler(PApplet canvas, Business selectedBusiness, int zoom) {
        this.canvas = canvas;
        this.selectedBusiness = selectedBusiness;
        this.zoom = zoom;
        start();
    }

    public void run(){
        String apiRequest = "https://maps.googleapis.com/maps/api/staticmap?center=" + selectedBusiness.getAddress().replaceAll(" ", "%20") + "," + selectedBusiness.getCity().replaceAll(" ", "%20") +
                "&zoom=" + zoom + "&size=" + Main.SCREEN_X/4 + "x" + Main.SCREEN_X/4 + "&maptype=roadmap&markers=color:red%7C" + selectedBusiness.getLatitude() + "," + selectedBusiness.getLongitude() +
                "&key=AIzaSyCAqZ2BvCbICVrtv-mZpyz914GxnBiyGWY";

        PImage mapImage = canvas.loadImage(apiRequest, "jpg");

        this.selectedBusiness.setMapImage(mapImage);
    }
}
