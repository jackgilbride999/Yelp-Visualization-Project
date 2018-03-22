package com.LukeHackett;

import processing.core.PApplet;
import processing.core.PImage;
import processing.data.XML;

public class ImageCrawler extends Thread {
    private final Business business;
    private final PApplet canvas;

    ImageCrawler(PApplet canvas, Business business){
        this.business = business;
        this.canvas = canvas;
        start();
    }

    public void run(){
        business.setImage(findPhoto(business));
    }

    PImage findPhoto(Business b) {
        //Construct URL
        //https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=500&type=restaurant&keyword=cruise&key=YOUR_API_KEY
/*
        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/xml?"
                + "location=" + b.getLatitude() + ',' + b.getLongitude()
                + "&name=" + b.getName().replaceAll(" ", "%20").replaceAll("'", "")
                + "&radius=500&key=AIzaSyAI1gmMQChQjNW5A2ye5h0tIx_c1kQLNc0";

        //Make XML object and parse photo reference and load the image
        XML xmlPlace = canvas.loadXML(url);
        String urlPhoto = "businessPlaceholder.png";
        if (xmlPlace.getChild("status").getContent().equals("OK")) {
            String photoReference;
            try {
                if ((photoReference = xmlPlace.getChild("result").getChild("photo").getChild("photo_reference").getContent()) != null)
                    urlPhoto = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=180&maxheight=180&photoreference=" + photoReference + "&key=AIzaSyAI1gmMQChQjNW5A2ye5h0tIx_c1kQLNc0";
            } catch (Exception e){
                urlPhoto = xmlPlace.getChild("result").getChild("icon").getContent();
            }
            return canvas.loadImage(urlPhoto, "jpg");
        } else {
            return canvas.loadImage(urlPhoto);
        }
*/
        String urlPhoto = "businessPlaceholder.png";
        return canvas.loadImage(urlPhoto);
    }

    public Business getBusiness() {
        return business;
    }
}
