package com.LukeHackett;

import controlP5.*;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

//import de.fhpotsdam.unfolding.*;
//import de.fhpotsdam.unfolding.geo.*;
//import de.fhpotsdam.unfolding.ui.*;
//import de.fhpotsdam.unfolding.providers.OpenStreetMap;

import java.util.ArrayList;

public class Main extends PApplet {

    public static final int SCREEN_X = 1244;
    public static final int SCREEN_Y = 800;
    public static final int EVENT_NULL = 0;
    public static final int SCROLLBAR_EVENT = 1;
    public static final int HOME_SCREEN = 0;
    public static final int SEARCH_RESULT_SCREEN = 1;
    public static final int BUSINESS_SCREEN = 2;
    public static final int BORDER_OFFSET_Y = 10;
    public static final int LINE_LENGTH = 170;

    public static int currentController;
    public static int currentSearch = 0;
    public static int selected = 0;
    public static int selectedFiter = 0;
    public static int selectedFilter = 0;
    public static int yOffset;
    public static int offsetFromTop = 0;

    public static String searchString;
    public static String spaceFromEdge;

    public static boolean connected = false;

    public static ControlP5 homeScreenController;
    public static ControlP5 searchResultController;
    public static ControlP5 searchResultHeaders;
    public static ControlP5 businessScreenController;

    public static Textfield searchBar;
    public static Textfield searchBarSearch;
    public static ScrollableList searchOptions;
    public static ScrollableList searchOptionsSearch;
    public static ScrollableList reviewFilterOptions;


    public static Button backButton;
    public static Button forwardButton;
    public static Button homeButton;
    public static Button backButtonBusiness;
    public static Button nightLifeButton;
    public static Button beautyButton;
    public static Button sportsButton;
    public static Button automotiveButton;
    public static Button restaurantsButton;
    public static Button shoppingButton;
    public static Button[] searchResultButtons;
    public static Button graphForward;
    public static Button graphBackward;

    public static PImage restaurantImage;
    public static PImage beautyImage;
    public static PImage sportsImage;
    public static PImage nightLifeImage;
    public static PImage automotiveImage;
    public static PImage shoppingImage;
    public static PImage testLogo;
    public static PImage backButtonImage;
    public static PImage forwardButtonImage;
    public static PImage homeButtonImage;
    public static PImage fullStar;
    public static PImage halfStar;
    public static PImage emptyStar;
    public static PImage placeHolderImage;
    public static PImage noParking;

    public static ImageCrawler[] businessesSearch;
    public static Scrollbar searchScroll;
    public static Scrollbar reviewScroll;
    public static Business selectedBusiness;
    public static ArrayList<Review> reviews;

    public static ArrayList<Float> starsList;
    public static StarBarChart starChart;
    public static ArrayList<Float> visitorsList;
    public static CheckinsBarChart chart;

    public static ImageCrawler businessImages;
    public static ReviewCrawler reviewCrawler;
    public static UI UI;
    public static Drawable draws;
    public static queries qControl;
    public static GraphScreen graphScreen;

    public static PFont searchFont;
    public static float searchMouseDifference;
    public static boolean searchScrollPressed;
    public static float reviewMouseDifference;
    public static boolean reviewScrollPressed;
    public static boolean emptyReview;

//    public UnfoldingMap map;


    public static void main(String[] args) {
        PApplet.main("com.LukeHackett.Main");
    }

    public void settings() {
        size(SCREEN_X, SCREEN_Y);
    }

    @Override
    public void setup() {
        qControl = null;
        UI = new UI(this);
        draws = new Drawable(this);
        searchFont = createFont("OpenSans-Regular", 22);
        spaceFromEdge = " ";
        while (textWidth(spaceFromEdge) < 120) {
            spaceFromEdge += " ";
        }

        //   map = null;

        homeScreenController = new ControlP5(this);
        searchResultController = new ControlP5(this);
        searchResultHeaders = new ControlP5(this);
        businessScreenController = new ControlP5(this);

        background(0, 169, 154);
        textFont(searchFont);
        text("loading...", SCREEN_X / 2 - textWidth("loading") / 2, SCREEN_Y / 2);

        backButtonImage = loadImage("backButton.png");
        forwardButtonImage = loadImage("forwardButton.png");
        homeButtonImage = loadImage("home.png");
        shoppingImage = loadImage("72x72_shopping.png");
        nightLifeImage = loadImage("72x72_nightlife.png");
        sportsImage = loadImage("72x72_active_life.png");
        beautyImage = loadImage("72x72_beauty.png");
        automotiveImage = loadImage("72x72_automotive.png");
        restaurantImage = loadImage("72x72_restaurants.png");
        testLogo = loadImage("testLogo_white_2.png");
        fullStar = loadImage("fullStar.png");
        halfStar = loadImage("halfStar.png");
        emptyStar = loadImage("emptyStar.png");
        placeHolderImage = loadImage("businessPlaceholder.png");
        noParking = loadImage("noParking.png");

        searchResultButtons = new Button[10];
        businessesSearch = new ImageCrawler[10];
        //starsList = null;

        //Graph screen setup
        graphScreen = new GraphScreen(this, SCREEN_X - 300, 50, 250, 250);

        //Control P5 setup
        setupHomeScreen();
        setupSearchHeader();
        setupBusinessScreen();
        homeScreenController.setAutoDraw(false);
        searchResultController.setAutoDraw(false);
        searchResultHeaders.setAutoDraw(false);
        businessScreenController.setAutoDraw(false);
        //End Control P5 setup
    }

    public void draw() {
        if (connected) {
            if (qControl == null) {
                qControl = new queries(this);
            }
            background(255);
            switch (currentController) {
                case HOME_SCREEN:
                    fill(0, 169, 154);
                    noStroke();
                    rect(0, 0, SCREEN_X, 340);
                    image(testLogo, SCREEN_X / 2 - 400, -90);
                    homeScreenController.draw();
                    break;
                case SEARCH_RESULT_SCREEN:
                    drawBusinesses();
                    break;
                case BUSINESS_SCREEN:
                    drawBusinessScreen();
                    break;
            }
        } else {
            background(0, 169, 154);
            println("Connecting...");
            textFont(searchFont);
            text("loading...", SCREEN_X / 2 - textWidth("loading") / 2, SCREEN_Y / 2);
            connected = true;
        }
    }

    public void mousePressed() {                                        // when these three mouse methods are used in the main program, make sure to have this code within them to control scrollbar
        if (searchScroll != null && searchScroll.getEvent(mouseX, mouseY) == SCROLLBAR_EVENT) {
            searchScrollPressed = true;
            searchMouseDifference = mouseY - searchScroll.getY();
        }
        if (reviewScroll != null && reviewScroll.getEvent(mouseX, mouseY) == SCROLLBAR_EVENT) {
            reviewScrollPressed = true;
            reviewMouseDifference = mouseY - reviewScroll.getY();
        }
    }

    public void mouseReleased() {
        searchScrollPressed = false;
        reviewScrollPressed = false;
    }

    public void mouseDragged() {
        if (searchScrollPressed) {
            searchScroll.setY(mouseY - searchMouseDifference);
            if (searchScroll.getY() < 0)
                searchScroll.setY(0);
            else if (searchScroll.getY() + searchScroll.getHeight() > SCREEN_Y)
                searchScroll.setY(SCREEN_Y - searchScroll.getHeight());
        }
        else if (reviewScrollPressed) {
            reviewScroll.setY(mouseY - reviewMouseDifference);
            if (reviewScroll.getY() < 0)
                reviewScroll.setY(0);
            else if (reviewScroll.getY() + reviewScroll.getHeight() > SCREEN_Y)
                reviewScroll.setY(SCREEN_Y - reviewScroll.getHeight());
        }
    }

    public void searchBar(String text) {
        currentController = SEARCH_RESULT_SCREEN;
        searchString = text;
        if (selected == 0) {
            ArrayList<Business> businessesC = qControl.categorySearch(text, 0, 10);
            buttonBusinessList(businessesC);
            if (businessesC.size() != 0) {
                for (Business b : businessesC) {
                    System.out.println(b.toString());
                }
            } else {
                System.out.println("No results");
            }
        } else if (selected == 1) {
            ArrayList<Business> businessesN = qControl.businessSearch(text, 0, 10);
            buttonBusinessList(businessesN);
            if (businessesN.size() != 0) {
                for (Business b : businessesN) {
                    System.out.println(b);
                }
            } else {
                System.out.println("No results");
            }
        } else if (selected == 2) {
            ArrayList<Business> businessesL = qControl.citySearch(text, 0, 10);
            buttonBusinessList(businessesL);
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
    public void Filter(int n) {
        selectedFiter = n;
        reviewScroll = null;

        reviewCrawler = new ReviewCrawler(selectedBusiness, qControl);
    }

    public void keyPressed() {
        if(key == '+')
        {
            offsetFromTop = offsetFromTop + 20;
        }
        else if(key == '-')
        {
            offsetFromTop = offsetFromTop - 20;
        }
    }

    public void selectedFilter(int n){
        selectedFilter = n;
    }


    public void backButton() {
        ArrayList<Business> businessList = UI.backButton();
        if (businessList != null) {
            buttonBusinessList(businessList);
        }
    }

    public void forwardButton() {
        ArrayList<Business> businessList = UI.forwardButton();
        if (businessList != null) buttonBusinessList(businessList);
    }

    public void homeButton() {
        currentController = UI.homeButton();
    }

    public void beautyButton() {
        ArrayList<Business> businessList = UI.beautyButton(qControl);
        buttonBusinessList(businessList);
        searchString = "Beauty";
    }

    public void autoButton() {
        ArrayList<Business> businessList = UI.autoButton(qControl);
        buttonBusinessList(businessList);
        searchString = "Automotive";
    }

    public void shoppingButton() {
        ArrayList<Business> businessList = UI.shoppingButton(qControl);
        buttonBusinessList(businessList);
        searchString = "Shopping";
    }

    public void nightlifeButton() {
        ArrayList<Business> businessList = UI.nightlifeButton(qControl);
        buttonBusinessList(businessList);
        searchString = "Nightlife";
    }

    public void restaurantsButton() {
        ArrayList<Business> businessList = UI.restaurantsButton(qControl);
        buttonBusinessList(businessList);
        searchString = "Restaurants";
    }

    public void sportsButton() {
        ArrayList<Business> businessList = UI.sportsButton(qControl);
        buttonBusinessList(businessList);
        searchString = "Sports";
    }

    public void buttonBusinessList(ArrayList<Business> businessList) {
        UI.buttonBusinessList(businessList);
    }

    public void controlEvent(ControlEvent event) {
        if (event.getValue() == 10) {
            String business = event.getName();//event.getLabel().split(" ");//event.getLabel().split("\n")[0].replaceAll(spaceFromEdge, "").split(" {2}");
            println(business);
            selectedBusiness = qControl.getBusinessInfo(business);
            selectedBusiness.setName(qControl.getBusinessName(selectedBusiness.getBusiness_id()));
            reviewCrawler = new ReviewCrawler(selectedBusiness, qControl);

            currentController = BUSINESS_SCREEN;
        }
        else if(event.getValue() == 15){
            if(event.getName().equals("graphForward")){
                int index = graphScreen.getActiveIndex()+1;
                if(index == graphScreen.getGraphs().size()) index = 0;
                graphScreen.setActive(index);
            }
            else{
                int index = graphScreen.getActiveIndex()-1;
                if(index == -1) index = graphScreen.getGraphs().size()-1;
                graphScreen.setActive(index);
            }
        }
    }

    void drawBusinesses() {
        draws.drawBusinesses();
    }

    public void drawBusinessScreen(){
        draws.drawBusinessScreen();
    }

    void drawReviews(int xStart, int yStart) {
        draws.drawReviews(xStart, yStart);
    }

    void setupHomeScreen() {
        draws.setupHomeScreen();
    }

    public void setupSearchHeader() {
        UI.searchHeader();
    }

    void setupBusinessScreen() {
        UI.setupBusinessScreen();
    }

    public static Drawable getDraw() {
        return draws;
    }
}
