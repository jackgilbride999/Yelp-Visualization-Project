package com.LukeHackett;

import processing.core.PApplet;
import processing.core.PImage;

public class MapCrawler extends Thread{
    /*
    Written by LH -loads google static map on different thread
     */

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
        String apiKey1 = "AIzaSyCAqZ2BvCbICVrtv-mZpyz914GxnBiyGWY";
        String apiKey2 = "AIzaSyAdNw5lE_KJe9uhRRb2fKi2U8Ex63HfYL8";
        String apiKey3 = "AIzaSyAI1gmMQChQjNW5A2ye5h0tIx_c1kQLNc0";

        String apiRequest = "https://maps.googleapis.com/maps/api/staticmap?center=" + selectedBusiness.getAddress().replaceAll(" ", "%20") + "," + selectedBusiness.getCity().replaceAll(" ", "%20") +
                "&zoom=" + zoom + "&size=" + Main.SCREEN_X/4 + "x" + Main.SCREEN_X/4 + "&maptype=roadmap&markers=color:red%7C" + selectedBusiness.getLatitude() + "," + selectedBusiness.getLongitude() +
                "&key=" + apiKey3;

        PImage mapImage = canvas.loadImage(apiRequest, "jpg");

        this.selectedBusiness.setMapImage(mapImage);
    }
}
