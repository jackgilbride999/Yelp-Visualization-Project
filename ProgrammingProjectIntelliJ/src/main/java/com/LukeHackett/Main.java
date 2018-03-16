package com.LukeHackett;

import processing.core.*;
import controlP5.*;

import java.util.ArrayList;

public class Main extends PApplet {

    public static final int SCREEN_X = 1244;
    public static final int SCREEN_Y = 800;
    public static final int EVENT_NULL = 0;
    public static final int SCROLLBAR_EVENT = 1;

    public static final int HOME_SCREEN = 0;
    public static final int SEARCH_RESULT_SCREEN = 1;
    public static final int BUSINESS_SCREEN = 2;

    final int BORDER_OFFSET_Y = 10;

    private int currentController;
    ArrayList<Business> businessList;
    int yOffset;

    private ControlP5 homeScreenController;
    private ControlP5 searchResultController;
    private ControlP5 businessScreenController;
    private Textfield searchBar;
    private Button backButton;
    private Button nightLifeButton;
    private Button beautyButton;
    private Button sportsButton;
    private Button automotiveButton;
    private Button retaurantsButton;
    private Button shoppingButton;


    private PImage restaurantImage;
    private PImage beautyImage;
    private PImage sportsImage;
    private PImage nightLifeImage;
    private PImage automotiveImage;
    private PImage shoppingImage;

    private PImage testLogo;


    private ScrollableList searchOptions;
    private int selected = 0;
    private PImage backButtonImage;

    queries qControl;


    private PFont searchFont;

    public static void main(String[] args) {
        PApplet.main("com.LukeHackett.Main");
    }

    public void settings() {
        size(SCREEN_X, SCREEN_Y);
    }

    @Override
    public void setup() {
        qControl = new queries();
        homeScreenController = new ControlP5(this);
        searchResultController = new ControlP5(this);
        businessScreenController = new ControlP5(this);
        searchFont = loadFont("CenturyGothic-24.vlw");

        backButtonImage = loadImage("backButton.png");
        shoppingImage = loadImage("72x72_shopping.png");
        nightLifeImage = loadImage("72x72_nightlife.png");
        sportsImage = loadImage("72x72_active_life.png");
        beautyImage = loadImage("72x72_beauty.png");
        automotiveImage = loadImage("72x72_automotive.png");
        restaurantImage = loadImage("72x72_restaurants.png");
        testLogo = loadImage("testLogo_white_2.png");



        //Control P5 setup
        int searchbarHeight = 40;
        int searchbarWidth = 3 * (SCREEN_X / 4);
        println(searchbarWidth);

        backButton = searchResultController.addButton("backButton")
                .setValue(0)
                .setSize(50, 50)
                .setPosition(10,10);
        backButtonImage.resize(backButton.getWidth(), backButton.getHeight());
        backButton.setImage(backButtonImage);

        beautyButton = homeScreenController.addButton("beautyButton")
                .setSize(110,110)
                .setPosition(SCREEN_X/2 - (72/2) - 250 - 72,SCREEN_Y/2)
                .setImage(beautyImage);

        sportsButton = homeScreenController.addButton("sportsButton")
                .setSize(110,110)
                .setPosition(SCREEN_X/2 - (72/2),SCREEN_Y/2)
                .setImage(sportsImage);

        retaurantsButton = homeScreenController.addButton("restaurantsButton")
                .setSize(110,110)
                .setPosition(SCREEN_X/2 + (72/2) + 250,SCREEN_Y/2)
                .setImage(restaurantImage);


        shoppingButton = homeScreenController.addButton("shoppingButton")
                .setSize(110,110)
                .setPosition(SCREEN_X/2 - (72/2) - 250 - 72,SCREEN_Y/2 + 190)
                .setImage(shoppingImage);


        automotiveButton = homeScreenController.addButton("autoButton")
                .setSize(110,110)
                .setPosition(SCREEN_X/2 - (72/2),SCREEN_Y/2 + 190)
                .setImage(automotiveImage);

        nightLifeButton = homeScreenController.addButton("nightlifeButton")
                .setSize(110,110)
                .setPosition(SCREEN_X/2 + (72/2) + 250,SCREEN_Y/2 + 190)
                .setImage(nightLifeImage);

        searchBar = homeScreenController.addTextfield("searchBar")
                .setCaptionLabel("")
                .setColorBackground(color(255, 255, 255))
                .setPosition(SCREEN_X / 2 - searchbarWidth / 2, 250)
                .setSize(searchbarWidth - 200, searchbarHeight)
                .setFont(searchFont)
                .setFocus(false)
                .setColor(color(0, 0, 0))
                .setColorCursor(color(0, 0, 0))
                .setColor(color(0, 0, 0))
                .setColorActive(color(0, 0, 0))
        ;

        searchOptions = homeScreenController.addScrollableList("Options")
                .addItem("By Category", 0)
                .addItem("By Name", 1)
                .addItem("By City", 2)
                .setFont(searchFont)
                .setColorBackground(color(0, 145, 135))
                .setColorForeground(color(0,135,122))
                .setColorActive(color(0, 100, 100))
                .setMouseOver(false)
                .setOpen(false)
                .setHeight(300)
                .setWidth(200)
                .setBarHeight(40)
                .setItemHeight(40)
                .setPosition(SCREEN_X / 2 + 3 * (SCREEN_X / 4) / 2 - 200, 250);

        // .setImage()

        homeScreenController.setAutoDraw(false);
        searchResultController.setAutoDraw(false);
        businessScreenController.setAutoDraw(false);

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
    }

    public void draw() {
        background(255);
        if(currentController == HOME_SCREEN)
        {
            fill(0,169,154);
            noStroke();
            rect(0,0,SCREEN_X, 340);
            image(testLogo,SCREEN_X/2 - 400, -90);
        }
        switch (currentController) {
            case HOME_SCREEN:
                homeScreenController.draw();
                break;
            case SEARCH_RESULT_SCREEN:
                searchResultController.draw();
                break;
            case BUSINESS_SCREEN:
                businessScreenController.draw();
                break;
        }
    }

    public void searchBar(String text) {

        currentController = SEARCH_RESULT_SCREEN;
        if (selected == 0) {
            ArrayList<Business> businessesC = qControl.categorySearch(text, 0, 10);
            businessList = businessesC;
            buttonBusinessList(businessList);
            if (businessesC.size() != 0) {
                for (Business b : businessesC) {
                    System.out.println(b.toString());
                }
            } else {
                System.out.println("No results");
            }
        } else if (selected == 1) {
            ArrayList<Business> businessesN = qControl.businessSearch(text, 0, 10);
            businessList = businessesN;
            buttonBusinessList(businessList);
            if (businessesN.size() != 0) {
                for (Business b : businessesN) {
                    System.out.println(b);
                }
            } else {
                System.out.println("No results");
            }
        } else if (selected == 2) {
            ArrayList<Business> businessesL = qControl.citySearch(text, 0, 10);
            businessList = businessesL;
            buttonBusinessList(businessList);
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

    public void backButton() {
        currentController = HOME_SCREEN;
    }
    public void beautyButton() {
        currentController = SEARCH_RESULT_SCREEN;
        businessList = qControl.businessSearch("Beauty", 0, 10);
        buttonBusinessList(businessList);
    }
    public void autoButton() {
        currentController = SEARCH_RESULT_SCREEN;
        businessList = qControl.businessSearch("Automotive", 0, 10);
        buttonBusinessList(businessList);
    }
    public void shoppingButton() {
        currentController = SEARCH_RESULT_SCREEN;
        businessList = qControl.businessSearch("Shopping", 0, 10);
        buttonBusinessList(businessList);
    }
    public void nightlifeButton() {
        currentController = SEARCH_RESULT_SCREEN;
        businessList = qControl.businessSearch("nightlife", 0, 10);
        buttonBusinessList(businessList);
    }
    public void restaurantsButton() {
        currentController = SEARCH_RESULT_SCREEN;
        businessList = qControl.businessSearch("Restaurant", 0, 10);
        buttonBusinessList(businessList);
    }
    public void sportsButton() {
        currentController = SEARCH_RESULT_SCREEN;
        businessList = qControl.businessSearch("sports", 0, 10);
        buttonBusinessList(businessList);
    }

    void buttonBusinessList(ArrayList<Business> businesses) {
      //  searchResultController = new ControlP5(this);
       // background(255);
        if (businessList != null) {
            for (Business b : businessList) {
                searchResultController.addButton(b.getBusiness_id())
                        .setLabel(b.getName() + ", " + b.getNeighborhood() + ", " + b.getCity() + "              Stars : " + b.getStars())
                        .setValue(10)
                        .setPosition((float) SCREEN_X / 2 - 500, (float) yOffset + 80)
                        .setSize(1000, 50)
                        .setFont(searchFont)
                        .setColorBackground(color(0, 169, 154))
                        .setColorForeground(color(0,135,122))
                        .setColorActive(color(0, 100, 100));

                yOffset = yOffset + 50 + BORDER_OFFSET_Y;
            }
            yOffset = 0;
        }
    }

    public void controlEvent(ControlEvent event) {
        if(event.getValue() == 10){
            String business = event.getLabel().split(",")[0];
            Business b = qControl.getBusinessInfo(qControl.getBusinessID(business));
            System.out.println(b);
        }
    }
}
