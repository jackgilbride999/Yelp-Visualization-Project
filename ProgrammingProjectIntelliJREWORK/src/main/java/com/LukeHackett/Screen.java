package com.LukeHackett;

import processing.core.PApplet;

import java.util.ArrayList;

class Screen {
    PApplet canvas;
    ArrayList screenWidgets;
    int screenColor;
    int previousMouseY;            // added for scrollbar
    int mouseDifference;           // scrollbar
    boolean scrollbarPressed;      //    ""
    int scrollOffsetFromTop;       //    ""
    int totalHeight;               //    ""
    int ratio;                     //    ""

    Screen(PApplet canvas, int in_color) {
        this.canvas = canvas;
        screenWidgets = new ArrayList();
        screenColor = in_color;
    }

    void add(Widget w) {
        screenWidgets.add(w);
    }

    void draw() {
        canvas.background(screenColor);

        for (int i = 0; i < screenWidgets.size(); i++) {
            Widget aWidget = (Widget) screenWidgets.get(i);
            aWidget.draw(ratio * scrollOffsetFromTop);
        }
        previousMouseY = canvas.mouseY;
        Scrollbar scroll = (Scrollbar) screenWidgets.get(0);
        ratio = scroll.getRatio();
        scrollOffsetFromTop = scroll.getY();
    }

    int getEvent(int mx, int my) {
        for (int i = 0; i < screenWidgets.size(); i++) {
            Widget aWidget = (Widget) screenWidgets.get(i);
            int event = aWidget.getEvent(canvas.mouseX, canvas.mouseY);
            if (event != Main.EVENT_NULL) {
                return event;
            }
        }
        return Main.EVENT_NULL;
    }

    void highlightButtons() {    // added this in, better checking just the widgets on a screen than every button in the program
        int event;
        for (int i = 0; i < screenWidgets.size(); i++) {
            Widget aWidget = (Widget) screenWidgets.get(i);
            event = aWidget.getEvent(canvas.mouseX, canvas.mouseY);
            if (event != Main.EVENT_NULL) {
                aWidget.mouseOver();
            } else
                aWidget.mouseNotOver();
        }
    }

    ArrayList getWidgets() {    // added this method as it was needed or the code in the slides
        return screenWidgets;
    }

    void pressScrollbar() {      // added this method for the scrollbar
        Scrollbar scrollbar = (Scrollbar) screenWidgets.get(0);
        if (scrollbar.getEvent(canvas.mouseX, canvas.mouseY) == Main.SCROLLBAR_EVENT) {
            scrollbarPressed = true;
            mouseDifference = canvas.mouseY - scrollbar.getY();
        }
    }

    void releaseScrollbar() {    // added this method for the scrollbar
        scrollbarPressed = false;
    }

    void dragScrollbar() {      // added this method for the scrollbar
        Scrollbar scrollbar = (Scrollbar) screenWidgets.get(0);
        if (scrollbarPressed) {
            scrollbar.setY(canvas.mouseY - mouseDifference);
            if (scrollbar.getY() < 0)
                scrollbar.setY(0);
            else if (scrollbar.getY() + scrollbar.getHeight() > Main.SCREEN_Y)
                scrollbar.setY(Main.SCREEN_Y - scrollbar.getHeight());
        }
    }
}