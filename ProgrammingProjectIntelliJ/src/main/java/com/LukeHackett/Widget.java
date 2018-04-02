package com.LukeHackett;

import processing.core.*;

abstract class Widget {
    PApplet canvas;
    float x, y, width, height;
    String label;
    int event;
    int widgetColor, labelColor, borderColor;
    PFont widgetFont;

    Widget(PApplet canvas, float x, float y, float width, float height, String label,
           int widgetColor, PFont widgetFont, int event) {
        this.canvas = canvas;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.label = label;
        this.event = event;
        this.widgetColor = widgetColor;
        this.widgetFont = widgetFont;
        labelColor = (0);
        borderColor = (0);
    }

    abstract void draw();

    int getEvent(int mX, int mY) {
        if (mX > x && mX < x + width && mY > y && mY < y + height) {
            return event;
        }
        return Main.EVENT_NULL;
    }

    void mouseOver() {      // added these two methods for the sample code from slides to work
        borderColor = (255);
    }

    void mouseNotOver() {
        borderColor = (0);
    }

}

class Scrollbar extends Widget {
    float ratio;

    Scrollbar(PApplet canvas, float width, float totalHeightOfPage,
              int widgetColor, int event) {
        super(canvas, Main.SCREEN_X - width, 0, width, 0, "", widgetColor, null, event); // again the super constructor for the class is called to stop code duplication
        this.ratio = totalHeightOfPage / Main.SCREEN_Y;
        if(this.ratio == 0){
            setHeight(Main.SCREEN_Y);
        }else {
            setHeight(Main.SCREEN_Y / ratio);
        }
    }

    public float getRatio() {
        return this.ratio;
    }

    public void setHeight(float height) {
        this.height = (int)height;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = (int)y;
    }

    public float getHeight() {
        return height;
    }

    public void draw() {
        canvas.noStroke();
        canvas.fill(widgetColor);
        canvas.rect(x, y, width, height);
        canvas.fill(labelColor);
        canvas.text(label, x + 10, y + height - 10);
    }
}