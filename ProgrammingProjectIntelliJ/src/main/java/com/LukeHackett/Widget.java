package com.LukeHackett;

import processing.core.*;

class Widget {
    PApplet canvas;
    int x, y, width, height;
    String label;
    int event;
    int widgetColor, labelColor, borderColor;
    PFont widgetFont;

    Widget(PApplet canvas, int x, int y, int width, int height, String label,
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

    void draw(int subFromY) {
        canvas.stroke(borderColor);
        canvas.fill(widgetColor);
        canvas.rect(x, y - subFromY, width, height);
        canvas.fill(labelColor);
        canvas.text(label, x + 10, y + height - 10 - subFromY);
    }

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
    int ratio;

    Scrollbar(PApplet canvas, int width, int totalHeightOfPage,
              int widgetColor, int event) {
        super(canvas, Main.SCREEN_X - width, 0, width, 0, "", widgetColor, null, event); // again the super constructor for the class is called to stop code duplication
        this.ratio = totalHeightOfPage / Main.SCREEN_Y;
        if(this.ratio == 0){
            setHeight(Main.SCREEN_Y);
        }else {
            setHeight(Main.SCREEN_Y / ratio);
        }
    }

    public int getRatio() {
        return this.ratio;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getHeight() {
        return height;
    }

    public void draw(int subFromY) { // method overriding draw so the scrollbar doesn't "drag itself"
        super.draw(0);
    }
}