package com.LukeHackett;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import processing.core.PApplet;
import processing.core.PImage;

import processing.data.XML;

import java.io.IOException;


public class ImageCrawler extends Thread {
    private final Business business;
    private final PApplet canvas;

    ImageCrawler(PApplet canvas, Business business) {
        this.business = business;
        this.canvas = canvas;
        start();
    }

    public void run() {
        //if(business.getImage() != Main.placeHolderImage) {
        business.setImage(findPhoto(business));
        //}
    }

    PImage findPhoto(Business b) {
        String urlPhotoBing = null;//imageSearch("86b3dc772e3b470e8b100d151a0e0da2", "3f01e0d67a13451c80d265d8e5b3d55d", b);
        if (urlPhotoBing != null) {
            System.out.println("Binged it!");
            return canvas.loadImage(urlPhotoBing);
        } else {
            String urlPhotoGoogle = googleImageAttempt(b);
            System.out.println("Googled it!");
            return canvas.loadImage(urlPhotoGoogle, "jpg");
        }
    }

    public Business getBusiness() {
        return business;
    }
/*
    public ImageSearchAPIImpl getClient(final String subscriptionKey) {
        return new ImageSearchAPIImpl("https://api.cognitive.microsoft.com/bing/v7.0/",
                new ServiceClientCredentials() {
                    public void applyCredentialsFilter(OkHttpClient.Builder builder) {
                        builder.addNetworkInterceptor(
                                new Interceptor() {
                                    public Response intercept(Chain chain) throws IOException {
                                        Request request = null;
                                        Request original = chain.request();
                                        // Request customization: add request headers
                                        Request.Builder requestBuilder = original.newBuilder()
                                                .addHeader("Ocp-Apim-Subscription-Key", subscriptionKey);
                                        request = requestBuilder.build();
                                        return chain.proceed(request);
                                    }
                                });
                    }
                });
    }

    public String imageSearch(String subscriptionKey, String subscriptionKeyAlt, Business b) {
        //TODO Add category searching if you cant find a business image

        String subKeyToUse = subscriptionKey;
        boolean done = false;
        while (!done) {
            ImageSearchAPIImpl client = getClient(subKeyToUse);
            try {
                ImagesInner imageResults = client.searchs().list(b.getName() + " " + "yelp");

                if (imageResults != null && imageResults.value().size() > 0) {
                    ImageObject firstImageResult = imageResults.value().get(0);
                    return firstImageResult.contentUrl();

                }
            } catch (Exception e) {
                if (subKeyToUse.equals(subscriptionKeyAlt)) {
                    done = true;
                } else {
                    subKeyToUse = subscriptionKeyAlt;
                }
            }
            if (subKeyToUse.equals(subscriptionKeyAlt)) {
                done = true;
            }
        }
        return null;
    }
*/
    public String googleImageAttempt(Business b) {
        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/xml?"
                + "location=" + b.getLatitude() + ',' + b.getLongitude()
                + "&name=" + b.getName().replaceAll(" ", "%20").replaceAll("'", "")
                + "&radius=500&key=AIzaSyAdNw5lE_KJe9uhRRb2fKi2U8Ex63HfYL8";

        //Make XML object and parse photo reference and load the image
        XML xmlPlace = canvas.loadXML(url);
        String urlPhoto = "businessPlaceholder.png";
        if (xmlPlace.getChild("status").getContent().equals("OK")) {
            String photoReference;
            try {
                if ((photoReference = xmlPlace.getChild("result").getChild("photo").getChild("photo_reference").getContent()) != null)
                    urlPhoto = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=180&maxheight=180&photoreference=" + photoReference + "&key=AIzaSyAdNw5lE_KJe9uhRRb2fKi2U8Ex63HfYL8";
            } catch (Exception e) {
                urlPhoto = xmlPlace.getChild("result").getChild("icon").getContent();
            }
            return urlPhoto;
        } else {
            return urlPhoto;
        }
    }
}
