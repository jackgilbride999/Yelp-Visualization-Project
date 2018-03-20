package com.LukeHackett;

import controlP5.*;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import java.util.ArrayList;
import java.util.List;

public class Main extends PApplet {

    public static final int SCREEN_X = 1244;
    public static final int SCREEN_Y = 800;
    public static final int EVENT_NULL = 0;
    public static final int SCROLLBAR_EVENT = 1;
    public static final int HOME_SCREEN = 0;
    public static final int SEARCH_RESULT_SCREEN = 1;
    public static final int BUSINESS_SCREEN = 2;
    public static final int BORDER_OFFSET_Y = 10;
    public static final int LINE_LENGTH = 150;

    private int currentController;
    private int currentSearch = 0;
    private int selected = 0;
    private int yOffset;
    private int offsetFromTop = 0;

    private String searchString;
    private String spaces;

    private boolean connected = false;

    private ControlP5 homeScreenController;
    private ControlP5 searchResultController;
    private ControlP5 businessScreenController;

    private Textfield searchBar;
    private Textfield searchBarSearch;
    private ScrollableList searchOptions;
    private ScrollableList searchOptionsSearch;

    private Button backButton;
    private Button forwardButton;
    private Button homeButton;
    private Button backButtonBusiness;
    private Button nightLifeButton;
    private Button beautyButton;
    private Button sportsButton;
    private Button automotiveButton;
    private Button restaurantsButton;
    private Button shoppingButton;

    private PImage restaurantImage;
    private PImage beautyImage;
    private PImage sportsImage;
    private PImage nightLifeImage;
    private PImage automotiveImage;
    private PImage shoppingImage;
    private PImage testLogo;
    private PImage backButtonImage;
    private PImage forwardButtonImage;
    private PImage homeButtonImage;

    private ImageCrawler[] businessesSearch;
    private Scrollbar searchScroll;
    private Business selectedBusiness;
    private ArrayList<Review> reviews;

    ImageCrawler businessImages;
    queries qControl;

    private PFont searchFont;
    private int searchRatio;
    private int previousSearchMouseY;
    private int offsetFromTopSearch;
    private int searchMouseDifference;
    private boolean searchScrollPressed;

    public static void main(String[] args) {
        PApplet.main("com.LukeHackett.Main");
    }

    public void settings() {
        size(SCREEN_X, SCREEN_Y);
    }

    @Override
    public void setup() {
        qControl = null;
        spaces = " ";
        while (textWidth(spaces) < 100) {
            spaces += " ";
        }

        homeScreenController = new ControlP5(this);
        searchResultController = new ControlP5(this);
        businessScreenController = new ControlP5(this);
        searchFont = createFont("OpenSans-Regular", 28);

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

        businessesSearch = new ImageCrawler[10];

        //Control P5 setup
        setupHomeScreen();
        setupBusinessScreen();
        homeScreenController.setAutoDraw(false);
        searchResultController.setAutoDraw(false);
        businessScreenController.setAutoDraw(false);
        //End Control P5 setup
    }

    public void draw() {
        if (connected) {
            if (qControl == null) qControl = new queries();
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
                    fill(0, 169, 154);
                    noStroke();
                    rect(0, 0, SCREEN_X, 75);
                    drawBusinesses();
                    break;
                case BUSINESS_SCREEN:
                    fill(0, 169, 154);
                    noStroke();
                    rect(0, 0, SCREEN_X, 500 - offsetFromTop);
                    businessScreenController.draw();
                    fill(255);
                    text(selectedBusiness.getName(), 100, 100 - offsetFromTop);
                    drawReviews(reviews, 10, 510);
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

    //dont mind all of this, this is just a temporary scollable feature until the scrollbar is integrated
    /*
    public void keyPressed() {
        int totalHeight = 0;
        //when "w" is pressed
        if (key == 'w' && offsetFromTop >= 0 && offsetFromTop <= (getTotalHeight(reviews) - SCREEN_Y)) {
            offsetFromTop = offsetFromTop + 20;
            println(offsetFromTop);
            println((getTotalHeight(reviews) - SCREEN_Y));
        }
        //when "s" is pressed
        else if (key == 's' && offsetFromTop >= 0 && offsetFromTop <= (getTotalHeight(reviews) - SCREEN_Y)) {
            offsetFromTop = offsetFromTop - 20;
            println(offsetFromTop);
            println((getTotalHeight(reviews) - SCREEN_Y));
        } else if (offsetFromTop <= 0) {
            offsetFromTop = 0;
        }
    }
    */
    public void mousePressed() {                                        // when these three mouse methods are used in the main program, make sure to have this code within them to control scrollbar
        if (searchScroll != null && searchScroll.getEvent(mouseX, mouseY)==SCROLLBAR_EVENT) {
            searchScrollPressed=true;
            searchMouseDifference=mouseY-searchScroll.getY();
        }
    }

    public void mouseReleased() {
        searchScrollPressed=false;
    }

    public void mouseDragged() {
        if (searchScrollPressed==true) {
            searchScroll.setY(mouseY-searchMouseDifference);
            if (searchScroll.getY()<0)
                searchScroll.setY(0);
            else if (searchScroll.getY()+searchScroll.getHeight()>SCREEN_Y)
                searchScroll.setY(SCREEN_Y-searchScroll.getHeight());
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

    public void backButton() {
        switch (currentController) {
            case SEARCH_RESULT_SCREEN:
                if (currentSearch != 0) {
                    currentSearch -= 10;
                    ArrayList<Business> businessList = qControl.businessSearch(searchString, currentSearch, 10);
                    buttonBusinessList(businessList);
                }
                break;
            case BUSINESS_SCREEN:
                currentController = SEARCH_RESULT_SCREEN;
                break;
        }
    }

    public void forwardButton() {
        if (searchResultController.getAll().size() == 15) {
            currentSearch += 10;
            ArrayList<Business> businessList = qControl.businessSearch(searchString, currentSearch, 10);
            buttonBusinessList(businessList);
        }
    }

    public void homeButton() {
        currentController = HOME_SCREEN;
    }

    public void beautyButton() {
        currentController = SEARCH_RESULT_SCREEN;
        searchString = "Beauty";
        ArrayList<Business> businessList = qControl.businessSearch("Beauty", 0, 10);
        buttonBusinessList(businessList);
    }

    public void autoButton() {
        currentController = SEARCH_RESULT_SCREEN;
        searchString = "Automotive";
        ArrayList<Business> businessList = qControl.businessSearch("Automotive", 0, 10);
        buttonBusinessList(businessList);
    }

    public void shoppingButton() {
        currentController = SEARCH_RESULT_SCREEN;
        ArrayList<Business> businessList = qControl.businessSearch("Shopping", 0, 10);
        buttonBusinessList(businessList);
    }

    public void nightlifeButton() {
        currentController = SEARCH_RESULT_SCREEN;
        ArrayList<Business> businessList = qControl.businessSearch("nightlife", 0, 10);
        buttonBusinessList(businessList);
    }

    public void restaurantsButton() {
        currentController = SEARCH_RESULT_SCREEN;
        searchString = "Restaurant";
        ArrayList<Business> businessList = qControl.businessSearch("Restaurant", 0, 10);
        buttonBusinessList(businessList);
    }

    public void sportsButton() {
        currentController = SEARCH_RESULT_SCREEN;
        searchString = "sports";
        ArrayList<Business> businessList = qControl.businessSearch("sports", 0, 10);
        buttonBusinessList(businessList);
    }

    public void buttonBusinessList(ArrayList<Business> businessList) {

        List<ControllerInterface<?>> elements = searchResultController.getAll();
        for (ControllerInterface e : elements) {
            searchResultController.remove(e.getName());
        }

        Button businessButton;
        if (businessList != null) {
            businessesSearch = new ImageCrawler[10];
            for (int i = 0; i < businessList.size(); i++) {
                Business b = businessList.get(i);
                businessesSearch[i] = new ImageCrawler(this, b);

                String stars = new String(new char[b.getStars()]).replace("\0", "*");
                b.setImage(loadImage("businessPlaceholder.png"));
                businessButton = searchResultController.addButton(b.getBusiness_id())
                        .setValueSelf(10)
                        .setLabel(spaces + b.getName() + '\n' + spaces + stars)
                        .setPosition((float) SCREEN_X / 2 - 500, (float) yOffset + 80)
                        .setSize(1000, 200)
                        .setFont(searchFont)
                        .setColorBackground(color(0, 169, 154))
                        .setColorForeground(color(0, 135, 122))
                        .setColorActive(color(0, 100, 100));

                Label label = businessButton.getValueLabel();
                label.align(ControlP5.LEFT, ControlP5.TOP);
                label = businessButton.getCaptionLabel();
                label.align(ControlP5.LEFT, ControlP5.TOP);
                label.toUpperCase(false);

                yOffset = yOffset + 200 + BORDER_OFFSET_Y;
            }
            searchScroll = new Scrollbar(this, 10,yOffset - 200 - BORDER_OFFSET_Y, color(123), SCROLLBAR_EVENT);
            searchRatio = searchScroll.getRatio();
            yOffset = 0;
        }

        backButton = searchResultController.addButton("backButton")
                .setSize(50, 50)
                .setPosition(SCREEN_X - 120, SCREEN_Y - 60);
        backButtonImage.resize(backButton.getWidth(), backButton.getHeight());
        backButton.setImage(backButtonImage);

        forwardButton = searchResultController.addButton("forwardButton")
                .setSize(50, 50)
                .setPosition(SCREEN_X - 60, SCREEN_Y - 60);
        forwardButtonImage.resize(forwardButton.getWidth(), forwardButton.getHeight());
        forwardButton.setImage(forwardButtonImage);

        homeButton = searchResultController.addButton("homeButton")
                .setSize(60, 60)
                .setPosition(10, 10);
        homeButtonImage.resize(homeButton.getWidth(), homeButton.getHeight());
        homeButton.setImage(homeButtonImage);

        int searchbarHeight = 40;
        int searchbarWidth = 3 * (SCREEN_X / 4);
        searchBarSearch = searchResultController.addTextfield("searchBar")
                .setCaptionLabel("")
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

        searchOptionsSearch = searchResultController.addScrollableList("Options")
                .addItem("By Category", 0)
                .addItem("By Name", 1)
                .addItem("By City", 2)
                .setFont(searchFont)
                .setColorBackground(color(0, 145, 135))
                .setColorForeground(color(0, 135, 122))
                .setColorActive(color(0, 100, 100))
                .setMouseOver(false)
                .setOpen(false)
                .setHeight(300)
                .setWidth(200)
                .setBarHeight(40)
                .setItemHeight(40)
                .setPosition(SCREEN_X / 2 + 3 * (SCREEN_X / 4) / 2 - 200, 20);

        Label label = searchOptionsSearch.getCaptionLabel();
        label.toUpperCase(false);
        label.getStyle()
                .setPaddingLeft(5)
                .setPaddingTop(10);
        label = searchOptionsSearch.getValueLabel();
        label.toUpperCase(false);
        label.getStyle()
                .setPaddingLeft(5)
                .setPaddingTop(10);
    }

    public void controlEvent(ControlEvent event) {
        if (event.getValue() == 10) {
            String business = event.getLabel().split("\n")[0].replaceAll(spaces, "");
            selectedBusiness = qControl.getBusinessInfoName(business);
            selectedBusiness.setName(qControl.getBusinessName(selectedBusiness.getBusiness_id()));
            System.out.println(selectedBusiness);
            reviews = qControl.reviews(selectedBusiness.getBusiness_id());

            for (Review r : reviews) {
                //r.setUser_name(qControl.getUserName(r.getUserId()));
                r.setBusinessName(selectedBusiness.getName());
            }
            formatReviews(reviews);
            for (Review r : reviews) {
                println(r.getFormattedReview());
            }
            currentController = BUSINESS_SCREEN;
        }
    }

    void drawBusinesses() {
        searchScroll.draw(0);
        previousSearchMouseY=mouseY;
        offsetFromTopSearch=searchScroll.getY();

        List<ControllerInterface<?>> list = searchResultController.getAll();
        println(list.get(0).getPosition());
        for (ControllerInterface i : list) {
            if(i.getName() != "backButton" && i.getName() != "forwardButton" && i.getName() != "homeButton" && i.getName() != "searchBar" && i.getName() != "Options")
                i.setPosition(i.getPosition()[0], i.getPosition()[1]-(searchRatio*offsetFromTopSearch));
        }
        searchResultController.draw();

        int x = SCREEN_X / 2 - 490;
        int y = 0;
        for (ImageCrawler image : businessesSearch) {
            if(image.getBusiness()!=null) {
                image(image.getBusiness().getImage(), x, y + 90-(searchRatio*offsetFromTopSearch), 180, 180);
                y = y + 200 + BORDER_OFFSET_Y;
            }
        }
    }

    void drawReviews(ArrayList<Review> reviews, int xStart, int yStart) {
        int reviewOffset = yStart;
        int borderOffsetY = 20;
        int borderOffsetX = xStart;
        float lineHeight = textAscent() + textDescent();
        for (Review r : reviews) {
            fill(175, 255, 248);
            rect(borderOffsetX / 2, reviewOffset - offsetFromTop, SCREEN_X - 10, (r.getNumberOfLines() * (int) lineHeight) + borderOffsetY - 5);
            fill(0);
            text(r.getDate(), SCREEN_X - textWidth(r.getDate()) - 20, reviewOffset + borderOffsetY - offsetFromTop);
            text(r.getFormattedReview(), borderOffsetX, reviewOffset + borderOffsetY - offsetFromTop);

            reviewOffset = reviewOffset + (r.getNumberOfLines() * (int) lineHeight) + borderOffsetY;
        }
    }

    void formatReviews(ArrayList<Review> reviews) {
        // Splits the review in order to format with a specific line length and then sets the review to the formatted review
        ArrayList<String> formattedReviewList = new ArrayList<String>();
        for (Review r : reviews) {
            String[] splitReview = r.getReview().split("");
            String formattedReview = "";

            formattedReview = formattedReview + "\n" + r.getBusiness_id() + ":" + "\n";
            boolean toNextLine = false;
            int lines = 4;
            for (int i = 0; i < splitReview.length; i++) {
                // Checks to see if line length has exceeded
                if ((i % LINE_LENGTH == 0) && (i != 0)) {
                    toNextLine = true;
                }
                // If line length has been exceeded it will put a new line at the next whitespace
                if (toNextLine && splitReview[i].equals(" ")) {
                    splitReview[i] = "\n";
                    toNextLine = false;
                }
                if (splitReview[i].equals("\n")) {
                    lines++;
                }
                formattedReview = formattedReview + splitReview[i];
            }
            r.setNumberOfLines(lines);
            r.setFormattedReview(formattedReview);
            //     formattedReviewList.add(formattedReview);

        }
    }

    int getTotalHeight(ArrayList<Review> reviews) {
        int totalHeight = 0;
        int lineHeight = 15;
        int lines = 4;

        for (Review r : reviews) {
            String[] split = r.getFormattedReview().split("");

            for (int i = 0; i < split.length; i++) {
                if (split[i].equals("\n")) {
                    lines++;
                }
            }
            totalHeight = lines * lineHeight + BORDER_OFFSET_Y;

        }
        return totalHeight;
    }

    void setupHomeScreen() {
        int searchbarHeight = 40;
        int searchbarWidth = 3 * (SCREEN_X / 4);

        beautyButton = homeScreenController.addButton("beautyButton")
                .setSize(110, 110)
                .setPosition(SCREEN_X / 2 - (72 / 2) - 250 - 72, SCREEN_Y / 2)
                .setPosition(SCREEN_X / 2 - (72 / 2) - 250 - 72, SCREEN_Y / 2)
                .setImage(beautyImage);

        sportsButton = homeScreenController.addButton("sportsButton")
                .setSize(110, 110)
                .setPosition(SCREEN_X / 2 - (72 / 2), SCREEN_Y / 2)
                .setImage(sportsImage);

        restaurantsButton = homeScreenController.addButton("restaurantsButton")
                .setSize(110, 110)
                .setPosition(SCREEN_X / 2 + (72 / 2) + 250, SCREEN_Y / 2)
                .setImage(restaurantImage);


        shoppingButton = homeScreenController.addButton("shoppingButton")
                .setSize(110, 110)
                .setPosition(SCREEN_X / 2 - (72 / 2) - 250 - 72, SCREEN_Y / 2 + 190)
                .setImage(shoppingImage);


        automotiveButton = homeScreenController.addButton("autoButton")
                .setSize(110, 110)
                .setPosition(SCREEN_X / 2 - (72 / 2), SCREEN_Y / 2 + 190)
                .setImage(automotiveImage);

        nightLifeButton = homeScreenController.addButton("nightlifeButton")
                .setSize(110, 110)
                .setPosition(SCREEN_X / 2 + (72 / 2) + 250, SCREEN_Y / 2 + 190)
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
                .setColorForeground(color(0, 135, 122))
                .setColorActive(color(0, 100, 100))
                .setMouseOver(false)
                .setOpen(false)
                .setHeight(300)
                .setWidth(200)
                .setBarHeight(40)
                .setItemHeight(40)
                .setPosition(SCREEN_X / 2 + 3 * (SCREEN_X / 4) / 2 - 200, 250);

        Label label = searchOptions.getCaptionLabel();
        label.toUpperCase(false);
        label.getStyle()
                .setPaddingLeft(5)
                .setPaddingTop(10);
        label = searchOptions.getValueLabel();
        label.toUpperCase(false);
        label.getStyle()
                .setPaddingLeft(5)
                .setPaddingTop(10);
    }

    void setupBusinessScreen() {
        backButtonBusiness = businessScreenController.addButton("backButton")
                .setValue(0)
                .setSize(50, 50)
                .setPosition(10, 10);
        backButtonImage.resize(backButtonBusiness.getWidth(), backButtonBusiness.getHeight());
        backButtonBusiness.setImage(backButtonImage);
    }
}
