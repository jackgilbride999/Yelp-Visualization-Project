package com.LukeHackett;

import controlP5.Button;
import controlP5.ControlElement;
import controlP5.Label;
import processing.core.PApplet;

import java.util.*;

import static com.LukeHackett.Main.SCROLLBAR_EVENT;
import static com.LukeHackett.Main.reviewScroll;
import static com.LukeHackett.Main.yOffset;
import static oracle.jrockit.jfr.events.Bits.floatValue;

public class Drawable {
    private final PApplet canvas;
    private final Formatter formatter;

    public static float searchRatio;
    public static float previousSearchMouseY;
    public static float offsetFromTopSearch;

    public static float reviewRatio;
    public static float previousReviewMouseY;
    public static float offsetFromTopReview;

    public static ArrayList<Float> initialBusinessYs;
    public static ArrayList<Float> initialReviewYs;

    Drawable(PApplet canvas) {
        this.canvas = canvas;
        formatter = new Formatter();
        initialBusinessYs = new ArrayList<Float>();
        initialReviewYs = new ArrayList<Float>();
    }

    public void drawBusinesses() {
        previousSearchMouseY = canvas.mouseY;
        offsetFromTopSearch = Main.searchScroll.getY();

        int controllerCount = 0;
        for(Button b : Main.searchResultButtons){
            if(b != null) {
                b.setPosition(b.getPosition()[0], floatValue(initialBusinessYs.get(controllerCount)) - (searchRatio * offsetFromTopSearch));
                controllerCount++;

                if (b.getPosition()[1] < 75 && canvas.mouseY < 75) {
                    b.setValueSelf(15);
                    b.setColorBackground(canvas.color(0, 169, 154));
                    b.setColorForeground(canvas.color(0, 169, 154));
                    b.setColorActive(canvas.color(0, 169, 154));
                } else {
                    b.setValueSelf(10);
                    b.setColorBackground(canvas.color(0, 169, 154));
                    b.setColorForeground(canvas.color(0, 135, 122));
                    b.setColorActive(canvas.color(0, 100, 100));
                }
            }
        }

        Main.searchResultController.draw();
        int x = 20;
        int y = 0;

        for (ImageCrawler image : Main.businessesSearch) {
            if (image != null) {
                //Draw images
                canvas.image(image.getBusiness().getImage(), x, y + 90 - (searchRatio * offsetFromTopSearch), 180, 180);

                //Draw stars piggybacking here
                double stars = image.getBusiness().getStars();
                float starX = 210;
                for(int i = 0; i < 5; i++){
                    if(stars <= 0){
                        canvas.image(Main.emptyStar, starX, y + 115 - (searchRatio * offsetFromTopSearch), 20, 20);
                    }
                    else{
                        if(stars == 0.5){
                            canvas.image(Main.halfStar, starX, y + 115 - (searchRatio * offsetFromTopSearch), 20, 20);
                        }
                        else{
                            canvas.image(Main.fullStar, starX, y + 115 - (searchRatio * offsetFromTopSearch), 20, 20);
                        }
                        stars--;
                    }
                    starX += 25;
                }

                boolean parking = image.getBusiness().getParking();
                /*
                ATTRIBUTE BUTTONS IN PROGRESS - LH

                if(parking){
                    //canvas.image(Main.Parking, 100, y, 20, 20);
                }
                else{
                    //canvas.image(Main.noParking, 100, y, 20, 20);
                }
                */
                //Increment y for business
                y = y + 200 + Main.BORDER_OFFSET_Y;
            }
        }
        canvas.fill(0, 169, 154);
        canvas.noStroke();
        canvas.rect(0, 0, Main.SCREEN_X, 75);
        canvas.fill(255);
        canvas.textSize(32);
        canvas.text("Search results for " + Main.searchString, Main.SCREEN_X/2-canvas.textWidth("Search results for " + Main.searchString)/2, 40);
        canvas.textSize(12);

        Main.searchResultHeaders.draw();
        Main.searchScroll.draw(0);
    }

    public void drawBusinessScreen(){
        previousReviewMouseY = canvas.mouseY;
        if(Main.reviewScroll != null){
            offsetFromTopReview = Main.reviewScroll.getY();
        }

        canvas.fill(0, 169, 154);
        canvas.noStroke();
        canvas.rect(0, 0, Main.SCREEN_X, 500 - Main.offsetFromTop - (reviewRatio * offsetFromTopReview));
        canvas.fill(255);
        canvas.textSize(25);
        canvas.text(Main.selectedBusiness.getName().substring(1, Main.selectedBusiness.getName().length()-1) + '\n'
                + Main.selectedBusiness.getAddress() + '\n'
                + Main.selectedBusiness.getCity() + ", "
                + Main.selectedBusiness.getPostal_code()
                , 100, 100 - Main.offsetFromTop  - (reviewRatio * offsetFromTopReview));
        drawReviews(10, 510);

        // Start graph drawings
        String name = Main.selectedBusiness.getName();
        name = name.replace("\"", "");

        String id = Main.selectedBusiness.getBusiness_id();

        if (Main.visitorsList == null) {
            Main.visitorsList = Main.qControl.getBusinessCheckins(id);
            if (Main.visitorsList == null) {
                System.out.println("Checkins not available for " + name);
            } else {
                Main.chart = new CheckinsBarChart(canvas, Main.visitorsList, name);
                if(Main.chart != null) {
                    Main.graphScreen.addGraph(Main.chart, false);
                }
            }
        }

        //Setup star chart if it is first time drawing
        String id2 = Main.selectedBusiness.getBusiness_id();
        if (Main.starsList == null) {
            Main.starsList = Main.qControl.getStarsList(id2);
            if (Main.starsList != null) {
                Main.starChart = new StarBarChart(canvas, Main.starsList, name);
                if(Main.starChart != null) {
                    Main.graphScreen.addGraph(Main.starChart, true);
                }
            } else {
                Main.starsList = new ArrayList<Float>();
                System.out.println("Ratings not available for " + name);
            }
        }

        //Draw star chart or failure

        Main.graphScreen.setyPos(initialReviewYs.get(3) - (reviewRatio * offsetFromTopReview));
        Main.graphScreen.draw();

        Main.businessScreenController.get("backButton").setPosition(Main.businessScreenController.get("backButton").getPosition()[0], initialReviewYs.get(0) - (reviewRatio * offsetFromTopReview));
        Main.businessScreenController.get("graphForward").setPosition(Main.businessScreenController.get("graphForward").getPosition()[0], initialReviewYs.get(1) - (reviewRatio * offsetFromTopReview));
        Main.businessScreenController.get("graphBackward").setPosition(Main.businessScreenController.get("graphBackward").getPosition()[0], initialReviewYs.get(2) - (reviewRatio * offsetFromTopReview));

        Main.businessScreenController.draw();
        if(Main.reviewScroll != null)Main.reviewScroll.draw(0);

        //map test
                    /*
                    map = new UnfoldingMap(this, SCREEN_X/4, 0, SCREEN_X/4, SCREEN_X/4, new OpenStreetMap.OpenStreetMapProvider());
                    Location businessLocation = new Location(selectedBusiness.getLatitude(), selectedBusiness.getLongitude());
                    if(map!=null) {
                        settings();
                        map.zoomAndPanTo(businessLocation, 15); // about 15 for street level, 13 for city level etc
                        float maxPanningDistance = 10;
                        map.setPanningRestriction(businessLocation, maxPanningDistance);
                        ImageMarker businessMarker = new ImageMarker(businessLocation, loadImage("pink.png"));
                        map.addMarker(businessMarker);
                        map.draw();
                    }
                    */
        // Haven't gotten map working yet ^^

    }

    public void drawReviews(int xStart, int yStart){
        Main.reviews = Main.reviewCrawler.getReviews();
        if(Main.reviews.size() > 0) {
            try {
                for (Review r : Main.reviews) {
                    if (r.getUser_name().equals("")) {
                        r.setUser_name(Main.qControl.getUserName(r.getUserId()));
                        r.setBusinessName(Main.selectedBusiness.getName());
                    }
                }
            } catch (ConcurrentModificationException e) {
                System.out.println("Couldn't get info this time");
            }
            int reviewOffset = yStart;
            int borderOffsetY = 20;
            int borderOffsetX = xStart;
            float lineHeight = canvas.textAscent() + canvas.textDescent();
            int reviewBoxHeight;
            String[] dateFormat;

            ListIterator<Review> reviewIterator = Main.reviews.listIterator();
            try {
                while (reviewIterator.hasNext()) {
                    Review r = reviewIterator.next();
                    if (r.getFormattedReview() == null) {
                        formatter.formatReview(r);
                    }

                    if(initialReviewYs.size() < Main.reviews.size()){
                        initialReviewYs.add((float)reviewOffset);
                        System.out.println(reviewOffset);
                    }

                    canvas.textSize(15);
                    dateFormat = r.getDate().split(" ");
                    reviewBoxHeight = (r.getNumberOfLines() * (int) lineHeight) + borderOffsetY - 5;
                    if(reviewRatio != 0) {
                        canvas.fill(175, 255, 248);
                        canvas.rect(borderOffsetX / 2, reviewOffset - Main.offsetFromTop - (reviewRatio * offsetFromTopReview), Main.SCREEN_X - 10, reviewBoxHeight);
                        canvas.fill(0);
                        canvas.text(dateFormat[0], Main.SCREEN_X - canvas.textWidth(dateFormat[0]) - 20 , reviewOffset + borderOffsetY - Main.offsetFromTop- Main.offsetFromTop - (reviewRatio * offsetFromTopReview));
                        canvas.text(r.getFormattedReview(), borderOffsetX, reviewOffset + borderOffsetY - Main.offsetFromTop - (reviewRatio * offsetFromTopReview));
                    }
                    else{
                        canvas.fill(175, 255, 248);
                        canvas.rect(borderOffsetX / 2, reviewOffset - Main.offsetFromTop, Main.SCREEN_X - 10, reviewBoxHeight);
                        canvas.fill(0);
                        canvas.text(dateFormat[0], Main.SCREEN_X - canvas.textWidth(dateFormat[0]) - 20, reviewOffset + borderOffsetY - Main.offsetFromTop);
                        canvas.text(r.getFormattedReview(), borderOffsetX, reviewOffset + borderOffsetY - Main.offsetFromTop);
                    }

                    if(r != Main.reviews.get(Main.reviews.size()-1)) reviewOffset = reviewOffset + (r.getNumberOfLines() * (int) lineHeight) + borderOffsetY;
                }

                if(Main.reviewScroll == null){
                    Main.reviewScroll = new Scrollbar(canvas, 20, reviewOffset, canvas.color(150), SCROLLBAR_EVENT);
                    reviewRatio = Main.reviewScroll.getRatio();
                }

            } catch (ConcurrentModificationException e) {
                System.out.println("Couldn't draw this time");
            }
        }
        else {
            canvas.fill(0);
            canvas.text("loading reviews...", Main.SCREEN_X/2 - canvas.textWidth("loading reviews...")/2, Main.SCREEN_Y-200);
        }
    }

    public void drawFailedCheckIns() {
        canvas.text("No Check-Ins available",1010,60);

    }

    public void drawFailedStars() {
        canvas.text("No ratings available",1030,300);
    }

    public boolean starChartInvalid(StarBarChart chart) {
	if(chart == null) {
		return true;
	}
	return false;
    }

	public boolean checkinChartInvalid(CheckinsBarChart chart) {
	if(chart == null) {
		return true;
	}
	return false;
    }

    public void setupHomeScreen() {
        int searchbarHeight = 40;
        int searchbarWidth = 3 * (Main.SCREEN_X / 4);

        Main.beautyButton = Main.homeScreenController.addButton("beautyButton")
                .setSize(110, 110)
                .setPosition( Main.SCREEN_X / 2 - (72 / 2) - 250 - 72,  Main.SCREEN_Y / 2)
                .setPosition( Main.SCREEN_X / 2 - (72 / 2) - 250 - 72,  Main.SCREEN_Y / 2)
                .setImage(Main.beautyImage);

        Main.sportsButton = Main.homeScreenController.addButton("sportsButton")
                .setSize(110, 110)
                .setPosition(Main.SCREEN_X / 2 - (72 / 2), Main.SCREEN_Y / 2)
                .setImage(Main.sportsImage);

        Main.restaurantsButton = Main.homeScreenController.addButton("restaurantsButton")
                .setSize(110, 110)
                .setPosition(Main.SCREEN_X / 2 + (72 / 2) + 250, Main.SCREEN_Y / 2)
                .setImage(Main.restaurantImage);


        Main.shoppingButton = Main.homeScreenController.addButton("shoppingButton")
                .setSize(110, 110)
                .setPosition(Main.SCREEN_X / 2 - (72 / 2) - 250 - 72, Main.SCREEN_Y / 2 + 190)
                .setImage(Main.shoppingImage);

        Main.automotiveButton = Main.homeScreenController.addButton("autoButton")
                .setSize(110, 110)
                .setPosition(Main.SCREEN_X / 2 - (72 / 2), Main.SCREEN_Y / 2 + 190)
                .setImage(Main.automotiveImage);

        Main.nightLifeButton = Main.homeScreenController.addButton("nightlifeButton")
                .setSize(110, 110)
                .setPosition(Main.SCREEN_X / 2 + (72 / 2) + 250, Main.SCREEN_Y / 2 + 190)
                .setImage(Main.nightLifeImage);

        Main.searchBar = Main.homeScreenController.addTextfield("searchBar")
                .setCaptionLabel("")
                .setColorBackground(canvas.color(255, 255, 255))
                .setPosition(Main.SCREEN_X / 2 - searchbarWidth / 2, 250)
                .setSize(searchbarWidth - 200, searchbarHeight)
                .setFont(Main.searchFont)
                .setFocus(false)
                .setColor(canvas.color(0, 0, 0))
                .setColorCursor(canvas.color(0, 0, 0))
                .setColor(canvas.color(0, 0, 0))
                .setColorActive(canvas.color(0, 0, 0))
        ;

        Main.searchOptions = Main.homeScreenController.addScrollableList("Options")
                .addItem("By Category", 0)
                .addItem("By Name", 1)
                .addItem("By City", 2)
                .setFont(Main.searchFont)
                .setColorBackground(canvas.color(0, 145, 135))
                .setColorForeground(canvas.color(0, 135, 122))
                .setColorActive(canvas.color(0, 100, 100))
                .setMouseOver(false)
                .setOpen(false)
                .setHeight(300)
                .setWidth(200)
                .setBarHeight(40)
                .setItemHeight(40)
                .setPosition(Main.SCREEN_X / 2 + 3 * (Main.SCREEN_X / 4) / 2 - 200, 250);

        Label label = Main.searchOptions.getCaptionLabel();
        label.toUpperCase(false);
        label.getStyle()
                .setPaddingLeft(5)
                .setPaddingTop(10);
        label = Main.searchOptions.getValueLabel();
        label.toUpperCase(false);
        label.getStyle()
                .setPaddingLeft(5)
                .setPaddingTop(10);
    }


}
