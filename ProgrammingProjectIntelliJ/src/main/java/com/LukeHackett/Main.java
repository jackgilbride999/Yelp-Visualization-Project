package com.LukeHackett;

import controlP5.*;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

import de.fhpotsdam.unfolding.providers.OpenStreetMap;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
    public static final int CHECKIN_CHART = 1;
    public static final int STARS_CHART = 2;

    public static int currentController;
    public static int currentSearch = 0;
    public static int currentReview = 0;
    public static int selected = 0;
    public static int selectedFilter = 0;
    public static int yOffset;
    public static int offsetFromTop = 0;
    public static int searchType;

    public static String searchString;
    public static String spaceFromEdge;
    public static ArrayList<String> categories;

    public static boolean connected = false;

    public static ControlP5 homeScreenController;
    public static ControlP5 searchResultController;
    public static ControlP5 searchResultHeaders;
    public static ControlP5 reviewHeaders;
    public static ControlP5 businessScreenController;

    public static Textfield searchBar;
    public static Textfield searchBarSearch;
    public static ScrollableList searchOptions;
    public static ScrollableList searchOptionsSearch;
    public static ScrollableList reviewFilterOptions;

    public static Button backButton;
    public static Button forwardButton;
    public static Button backButtonReview;
    public static Button forwardButtonReview;
    public static Button homeButton;
    public static Button reviewBackButton;
    public static Button reviewForwardButton;
    public static Button reviewHomeButton;
    public static Button backButtonBusiness;
    public static Button nightLifeButton;
    public static Button beautyButton;
    public static Button sportsButton;
    public static Button automotiveButton;
    public static Button restaurantsButton;
    public static Button shoppingButton;
    public static Button[] searchResultButtons;
    public static ArrayList<Button> searchHeaderButtons;
    public static Button graphForward;
    public static Button graphBackward;

    public static PImage sidebarShadow;
    public static PImage background;
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
    public static PImage searchIcon;
    public static PImage parking;
    public static PImage noParking;
    public static PImage wifi;
    public static PImage noWifi;
    public static PImage wheelchair;
    public static PImage noWheelchair;

    public static ImageCrawler[] businessesSearch;
    public static Scrollbar searchScroll;
    public static Scrollbar reviewScroll;
    public static Business selectedBusiness;
    public static ArrayList<Review> reviews;
    public static ArrayList<Review> reviewsToShow;

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
    public static PFont reviewFont;
    public static PFont bigFont;
    public static float searchMouseDifference;
    public static boolean searchScrollPressed;
    public static float reviewMouseDifference;
    public static boolean reviewScrollPressed;
    public static boolean emptyReview;
    public static boolean moveBackground;
    public static boolean returnBackgroundToStart;

    public static Animation loadingAnimation;

    public static int backgroundX;
    public static int backgroundXTimer;
    public static UnfoldingMap map;
    public static int mapX;
    public static int mapY;


    public static void main(String[] args) {
        PApplet.main("com.LukeHackett.Main");
    }

    public void settings() {
        size(SCREEN_X, SCREEN_Y, P2D);
    }

    @Override
    public void setup() {
        mapX =SCREEN_X/2 + 260;
        mapY = 75;
        loadingAnimation = new Animation("frames", 8, this);
        backgroundX = 0;
        moveBackground = false;
        returnBackgroundToStart = false;
        qControl = null;
        UI = new UI(this);
        draws = new Drawable(this);
        searchFont = createFont("OpenSans-Regular", 22);
        reviewFont = createFont("OpenSans-Regular", 18);
        bigFont = createFont("OpenSans-Regular", 30);
        spaceFromEdge = " ";

        textSize(18);
        while (textWidth(spaceFromEdge) < 193) {
            spaceFromEdge += " ";
        }

        //Load categories
        try {
            FileReader catReader = new FileReader("top20Categories.txt");
            BufferedReader reader = new BufferedReader(catReader);
            categories = new ArrayList<String>();
            String nextLine;
            while((nextLine = reader.readLine()) != null){
                categories.add(nextLine);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        println("Categories: " + categories.size());
        //End Load

        homeScreenController = new ControlP5(this);
        searchResultController = new ControlP5(this);
        searchResultHeaders = new ControlP5(this);
        businessScreenController = new ControlP5(this);
        reviewHeaders = new ControlP5(this);

        background(0, 169, 154);
        textFont(searchFont);
        text("loading...", SCREEN_X / 2 - textWidth("loading") / 2, SCREEN_Y / 2);

        sidebarShadow = loadImage("sidebar_shadow.png");
        background = loadImage("background_scroll_2.jpg");
        backButtonImage = loadImage("backButton.png");
        forwardButtonImage = loadImage("forwardButton.png");
        homeButtonImage = loadImage("home.png");
        shoppingImage = loadImage("72x72_shopping.png");
        nightLifeImage = loadImage("72x72_nightlife.png");
        sportsImage = loadImage("72x72_active_life.png");
        beautyImage = loadImage("72x72_beauty.png");
        automotiveImage = loadImage("72x72_automotive.png");
        restaurantImage = loadImage("72x72_restaurants.png");
        testLogo = loadImage("testLogo_green_small.png");
        fullStar = loadImage("fullStar.png");
        halfStar = loadImage("halfStar.png");
        emptyStar = loadImage("emptyStar.png");
        placeHolderImage = loadImage("businessPlaceholder.png");
        noParking = loadImage("parking_unavailable.png");
        parking = loadImage("parking_unavailable.png");
        wifi = loadImage("wifi_available.png");
        noWifi = loadImage("wifi_unavailable.png");
        wheelchair = loadImage("wheelchair_available.png");
        noWheelchair = loadImage("wheelchair_unavailable.png");
        searchIcon = loadImage("searchbar_icon.png");


        searchResultButtons = new Button[10];
        businessesSearch = new ImageCrawler[10];
        reviews = new ArrayList<Review>();
        reviewsToShow = new ArrayList<Review>();
        //starsList = null;

        //Graph screen setup
        graphScreen = new GraphScreen(this, 20, 290, 400, 100);

        //Control P5 setup
        setupHomeScreen();
        setupSearchHeader();
        setupBusinessScreen();
        setupReviewScreen();
        homeScreenController.setAutoDraw(false);
        searchResultController.setAutoDraw(false);
        searchResultHeaders.setAutoDraw(false);
        businessScreenController.setAutoDraw(false);
        reviewHeaders.setAutoDraw(false);
        //End Control P5 setup

        map = new UnfoldingMap(this, mapX, mapY, SCREEN_X/4, SCREEN_X/4, new OpenStreetMap.OpenStreetMapProvider());
        MapUtils.createDefaultEventDispatcher(this, Main.map);

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
                    //rect(0, 0, SCREEN_X, 600);
                    image(background, backgroundX, 0);
                    changePicture();
                    image(testLogo, SCREEN_X / 2 - 240, 100);
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

    public void changePicture() {
        int pic1 = 0;
        int pic2 = -SCREEN_X;
        int pic3 = -SCREEN_X * 2;
        int pic4 = -SCREEN_X * 3;
        backgroundXTimer++;
        // System.out.println(backgroundXTimer + "     " + backgroundX);
        if (backgroundXTimer % 300 == 0) {
            moveBackground = true;
        }
        if (moveBackground) {
            backgroundX = backgroundX - 4;
            if (backgroundX == pic2 || backgroundX == pic3 || backgroundX == pic4 || backgroundX == pic1) {
                backgroundXTimer = 1;
                if (backgroundX == pic4) {
                    returnBackgroundToStart = true;
                }
                if (backgroundX == pic1 && returnBackgroundToStart) {
                    returnBackgroundToStart = false;
                }
                moveBackground = false;

            }
        }
        if (returnBackgroundToStart && moveBackground) {
            backgroundX = backgroundX + 16;
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
            if (searchScroll.getY() < 0) {
                searchScroll.setY(0);
            }
            else if (searchScroll.getY() + searchScroll.getHeight() > SCREEN_Y) {
                searchScroll.setY(SCREEN_Y - searchScroll.getHeight());
            }
        } else if (reviewScrollPressed) {
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
        searchType = selected;
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

    public void searchBarSearch(String text) {
        currentController = SEARCH_RESULT_SCREEN;
        searchString = text;
        ArrayList<Business> businessesN = qControl.businessSearch(text, 0, 10);
        buttonBusinessList(businessesN);
        if (businessesN.size() != 0) {
            searchType = 0;
            for (Business b : businessesN) {
                System.out.println(b);
            }
        } else {
            ArrayList<Business> businessesC = qControl.categorySearch(text, 0, 10);
            buttonBusinessList(businessesC);
            if (businessesC.size() != 0) {
                searchType = 1;
                for (Business b : businessesC) {
                    System.out.println(b.toString());
                }
            } else {
                ArrayList<Business> businessesL = qControl.citySearch(text, 0, 10);
                buttonBusinessList(businessesL);
                if (businessesL.size() != 0) {
                    searchType = 2;
                    for (Business b : businessesL) {
                        System.out.println(b);
                    }
                } else {
                    System.out.println("No results");
                }
            }
        }
    }

    public void Options(int n) {
        selected = n;
    }

    public void Filter(int n) {
        selectedFilter = n;
        reviewScroll = null;

        switch (selectedFilter) {
            case 0:
                reviewsToShow = reviews;
                break;
            case 1:
                reviewsToShow = new ArrayList<Review>();
                for (Review r : reviews) {
                    if (r.getStars() == 5) {
                        reviewsToShow.add(r);
                    }
                }
                break;
            case 2:
                reviewsToShow = new ArrayList<Review>();
                println("hereeeee");
                for (Review r : reviews) {
                    if (r.getStars() == 4) {
                        reviewsToShow.add(r);
                        println(r);
                    }
                }
                break;
            case 3:
                reviewsToShow = new ArrayList<Review>();
                for (Review r : reviews) {
                    if (r.getStars() == 3) {
                        reviewsToShow.add(r);
                    }
                }
                break;
            case 4:
                reviewsToShow = new ArrayList<Review>();
                for (Review r : reviews) {
                    if (r.getStars() == 2) {
                        reviewsToShow.add(r);
                    }
                }
                break;
            case 5:
                reviewsToShow = new ArrayList<Review>();
                for (Review r : reviews) {
                    if (r.getStars() == 1) {
                        reviewsToShow.add(r);
                    }
                }
                break;
        }
        //reviewCrawler = new ReviewCrawler(selectedBusiness, qControl);
    }

    public void keyPressed() {
        if (key == '+') {
            offsetFromTop = offsetFromTop + 20;
        } else if (key == '-') {
            offsetFromTop = offsetFromTop - 20;
        }
    }

    //public void selectedFilter(int n){

    // }

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

    public void backButtonReview() {
        UI.backButtonReview();
    }

    public void forwardButtonReview() {
        UI.forwardButtonReview();
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

            new GraphCrawler(this, selectedBusiness.getName(), selectedBusiness.getBusiness_id(), CHECKIN_CHART, graphScreen);
            new GraphCrawler(this, selectedBusiness.getName(), selectedBusiness.getBusiness_id(), STARS_CHART, graphScreen);

            currentController = BUSINESS_SCREEN;
        } else if (event.getValue() == 15) {
            if (event.getName().equals("graphForward")) {
                int index = graphScreen.getActiveIndex() + 1;
                if (index == graphScreen.getGraphs().size()) index = 0;
                graphScreen.setActive(index);
            } else {
                int index = graphScreen.getActiveIndex() - 1;
                if (index == -1) index = graphScreen.getGraphs().size() - 1;
                graphScreen.setActive(index);
            }
        } else if(event.getValue() == 20){
            currentController = SEARCH_RESULT_SCREEN;
            searchString = event.getName();
            searchType = 1;
            ArrayList<Business> businesses = qControl.categorySearch(searchString, 0, 10);
            buttonBusinessList(businesses);
        }
    }

    void drawBusinesses() {
        draws.drawBusinesses();
    }

    public void drawBusinessScreen() {
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

    public void setupBusinessScreen() {
        UI.setupBusinessScreen();
    }

    public void setupReviewScreen() {
        UI.setupReviewHeader();
    }

    public static Drawable getDraw() {
        return draws;
    }

}
