package com.LukeHackett;

import processing.core.PImage;
import processing.core.PApplet;
import static com.LukeHackett.Main.*;

public class Animation {
    PImage[] images;
    PApplet canvas;
    int imageCount;
    int frame;
    int frameRate = 6;
    int currentFrame;

    Animation(String imagePrefix, int count, PApplet canvas) {
        this.canvas = canvas;
        imageCount = count;
        images = new PImage[imageCount];

        for(int i = 0; i < images.length; i++)
        {
            String filename = imagePrefix + "_0" + (i+1) + ".gif";
            images[i] = canvas.loadImage(filename);
            System.out.println("Loaded: " + filename);
        }
    }

    public void displayAnimation(int xpos, int ypos) {
        currentFrame++;
        if((currentFrame) % frameRate == 0)
        {
            frame = frame < images.length - 1 ? frame+1: 0;
        }
        canvas.image(images[frame], xpos, ypos);
    }
}


