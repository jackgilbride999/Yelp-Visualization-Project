package com.LukeHackett;

import processing.core.PApplet;
import processing.core.PImage;
import com.microsoft.azure.cognitiveservices.imagesearch.ImageObject;
import com.microsoft.azure.cognitiveservices.imagesearch.implementation.ImageSearchAPIImpl;
import com.microsoft.azure.cognitiveservices.imagesearch.implementation.ImagesInner;


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
        String urlPhoto = imageSearch("86b3dc772e3b470e8b100d151a0e0da2", b);

        return canvas.loadImage(urlPhoto);
    }

    public Business getBusiness() {
        return business;
    }

    public static String imageSearch(String subscriptionKey, Business b)
    {
        ImageSearchAPIImpl client = Experiment.getClient(subscriptionKey);

        try
        {
            ImagesInner imageResults = client.searchs().list(b.getName() + " yelp");
            System.out.println("\r\nSearch images for query " + b.getName());

            if (imageResults == null)
            {
                System.out.println("No image result data.");
            }
            else
            {
                // Image results
                if (imageResults.value().size() > 0)
                {
                    ImageObject firstImageResult = imageResults.value().get(0);

                    System.out.println(String.format("Image result count: %d", imageResults.value().size()));
                    System.out.println(String.format("First image insights token: %s", firstImageResult.imageInsightsToken()));
                    System.out.println(String.format("First image thumbnail url: %s", firstImageResult.thumbnailUrl()));
                    System.out.println(String.format("First image content url: %s", firstImageResult.contentUrl()));

                    return firstImageResult.contentUrl();
                }
                else
                {
                    System.out.println("Couldn't find image results!");
                }
            }
        }

        catch (Exception e)
        {
            System.out.println("HRMMMM");
        }
        return "businessPlaceholder.png";
    }
}
