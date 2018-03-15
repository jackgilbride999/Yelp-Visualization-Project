package com.LukeHackett;

import processing.core.*;
import controlP5.*;

import java.util.ArrayList;

public class Main extends PApplet {

    public static final int SCREEN_X = 1244;
    public static final int SCREEN_Y = 700;
    public static final int EVENT_NULL = 0;
    public static final int SCROLLBAR_EVENT = 1;

    private final int TEST_EVENT1 = 3;
    private final int TEST_EVENT2 = 4;
    private final int TEST_EVENT3 = 5;
    private final int TEST_EVENT4 = 6;

    private Screen homeScreen;
    private Screen currentScreen;
    private Widget test1, test2, test3, test4;

    private ControlP5 cp5;
    private Textfield searchBar;
    private ScrollableList searchOptions;
    private int selected = 0;

    queries q;

    private PFont searchFont;

    public static void main(String[] args) {
        PApplet.main("com.LukeHackett.Main");
    }

    public void settings() {
        size(SCREEN_X, SCREEN_Y);
    }

    @Override
    public void setup() {
        q = new queries();
        cp5 = new ControlP5(this);
        searchFont = loadFont("C:\\Users\\lukeh\\Documents\\Tortoise-VPN\\CS1013-1718-4(Alt)\\ProgrammingProjectIntelliJ\\src\\main\\java\\data\\CenturyGothic-24.vlw");

        //Control P5 setup
        int searchbarHeight = 40;
        int searchbarWidth = 3 * (SCREEN_X / 4);
        println(searchbarWidth);

        searchBar = cp5.addTextfield("searchBar")
                .setColorBackground(color(255, 255, 255))
                .setPosition(SCREEN_X / 2 - searchbarWidth / 2, 20)
                .setSize(searchbarWidth - 200, searchbarHeight)
                .setFont(searchFont)
                .setFocus(false)
                .setColor(color(0, 0, 0))
                .setColorCursor(color(0, 0, 0))
                .setColor(color(0, 0, 0))
                .setColorActive(color(0, 0, 0))
        ;

        searchOptions = cp5.addScrollableList("Options")
                .addItem("By Category", 0)
                .addItem("By Name", 1)
                .addItem("By City", 2)
                .setFont(searchFont)
                .setColorBackground(color(230, 0, 0))
                .setColorForeground(color(200, 0, 0))
                .setColorActive(color(170, 0, 0))
                .setMouseOver(false)
                .setOpen(false)
                .setHeight(300)
                .setWidth(200)
                .setBarHeight(40)
                .setItemHeight(40)
                .setPosition(SCREEN_X / 2 + 3 * (SCREEN_X / 4) / 2 - 200, 20);

        Label label = searchOptions.getCaptionLabel();
        label.toUpperCase(false);
        label.getStyle()
                .setPaddingLeft(20)
                .setPaddingTop(10);
        label = searchOptions.getValueLabel();
        label.toUpperCase(false);
        label.getStyle()
                .setPaddingLeft(20)
                .setPaddingTop(10);

        //End Control P5 setup


        homeScreen = new Screen(this, color(255)); // setup new white Homescreen

        Scrollbar scrollbar = new Scrollbar(this, 10, SCREEN_Y, color(150), SCROLLBAR_EVENT); // just adding a scrollbar incase we need it, by default it will not scroll and will just take up side of screen
        // the best way I can get the scrollbar to work is to know its index in a screen's widgetList, so for now just always add a scrollbar to any screen as the first element
        //TextWidget searchbar = new TextWidget(SCREEN_X/2-searchbarWidth/2, 20, searchbarWidth, searchbarHeight, "Enter the name of a buisiness...", color(200), searchFont, SEARCHBAR_EVENT, 2);
        test1 = new Widget(this, SCREEN_X / 4 - 25, SCREEN_Y / 2 - 25, 50, 50, "test 1", color(255, 0, 0), searchFont, TEST_EVENT1);
        test2 = new Widget(this, 3 * SCREEN_X / 4 - 25, SCREEN_Y / 2 - 25, 50, 50, "test 2", color(0, 255, 0), searchFont, TEST_EVENT2);
        test3 = new Widget(this, SCREEN_X / 4 - 25, 3 * SCREEN_Y / 4 - 25, 50, 50, "test 3", color(0, 0, 255), searchFont, TEST_EVENT3);
        test4 = new Widget(this, 3 * SCREEN_X / 4 - 25, 3 * SCREEN_Y / 4 - 25, 50, 50, "test 4", color(255, 255, 0), searchFont, TEST_EVENT4);
        homeScreen.add(scrollbar);
        //homeScreen.add(searchbar);
        homeScreen.add(test1);
        homeScreen.add(test2);
        homeScreen.add(test3);
        homeScreen.add(test4);
        currentScreen = homeScreen; // when the program starts, the homeScreen will be the current one
    }

    public void draw() {
        currentScreen.draw();
    }

    public void mousePressed() {
        int event = currentScreen.getEvent(mouseX, mouseY);
        if (event == SCROLLBAR_EVENT)
            currentScreen.pressScrollbar();
        else
            currentScreen.releaseScrollbar();

        switch (event) {
            case TEST_EVENT1:
                println("test button 1 pressed!");
                break;
            case TEST_EVENT2:
                println("test button 2 pressed!");
                break;
            case TEST_EVENT3:
                println("test button 3 pressed!");
                break;
            case TEST_EVENT4:
                println("test button 4 pressed!");
                break;
            default:
        }
    }

    public void mouseMoved() {
        currentScreen.highlightButtons();
    }


    public void mouseReleased() {
        currentScreen.releaseScrollbar();
    }

    public void mouseDragged() {
        currentScreen.dragScrollbar();
    }

    public void searchBar(String text) {
        if (selected == 0) {
            ArrayList<Business> businessesC = q.categorySearch(text, 0, 10);
            if (businessesC.size() != 0) {
                for (Business b : businessesC) {
                    System.out.println(b.toString());
                }
            } else {
                System.out.println("No results");
            }
        } else if (selected == 1) {
            ArrayList<Business> businessesN = q.businessSearch(text, 0, 10);
            if (businessesN.size() != 0) {
                for (Business b : businessesN) {
                    System.out.println(b);
                }
            } else {
                System.out.println("No results");
            }
        } else if (selected == 2) {
            ArrayList<Business> businessesL = q.citySearch(text, 0, 10);
            if (businessesL.size() != 0) {
                for (Business b : businessesL) {
                    System.out.println(b);
                }
            } else {
                System.out.println("No results");
            }
        }
    }

    public void Options(int n) {
        selected = n;
    }
}
