package com.LukeHackett;

import controlP5.ControllerInterface;
import controlP5.Label;
import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;

import java.util.List;

import static oracle.jrockit.jfr.events.Bits.floatValue;

public class Drawable {
    private final PApplet canvas;
    private final Formatter formatter;

    ArrayList<Float> initialBusinessYs;

    Drawable(PApplet canvas) {
        this.canvas = canvas;
        formatter = new Formatter();
        initialBusinessYs = new ArrayList<Float>();
    }

    public void drawBusinesses() {
        Main.previousSearchMouseY = canvas.mouseY;
        Main.offsetFromTopSearch = Main.searchScroll.getY();

        List<ControllerInterface<?>> list = Main.searchResultController.getAll();
        int controllerCount = 0;
        for (ControllerInterface i : list) {
            if (i.getName() != "backButton" && i.getName() != "forwardButton" && i.getName() != "homeButton" && i.getName() != "searchBar" && i.getName() != "Options") {
                // i.setPosition(i.getPosition()[0], i.getPosition()[1] - (Main.searchRatio * Main.offsetFromTopSearch));
                i.setPosition(i.getPosition()[0], floatValue(initialBusinessYs.get(controllerCount)) - (Main.searchRatio * Main.offsetFromTopSearch));
                controllerCount++;
            }
        }
        Main.searchResultController.draw();
        int x = 20;
        int y = 0;

        for (ImageCrawler image : Main.businessesSearch) {
            if (image != null) {
                //Draw images
                canvas.image(image.getBusiness().getImage(), x, y + 90 - (Main.searchRatio * Main.offsetFromTopSearch), 180, 180);

                //Draw stars piggybacking here
                double stars = image.getBusiness().getStars();
                float starX = 210;
                for(int i = 0; i < 5; i++){
                    if(stars <= 0){
                        canvas.image(Main.emptyStar, starX, y + 115 - (Main.searchRatio * Main.offsetFromTopSearch), 20, 20);
                    }
                    else{
                        if(stars == 0.5){
                            canvas.image(Main.halfStar, starX, y + 115 - (Main.searchRatio * Main.offsetFromTopSearch), 20, 20);
                        }
                        else{
                            canvas.image(Main.fullStar, starX, y + 115 - (Main.searchRatio * Main.offsetFromTopSearch), 20, 20);
                        }
                        stars--;
                    }
                    starX += 25;
                }

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

    public void drawReviews(int xStart, int yStart) {
        for (Review r : Main.reviewCrawler.getReviews()) {
            if (r.getUser_name().equals("")) {
                r.setUser_name(Main.qControl.getUserName(r.getUserId()));
                r.setBusinessName(Main.selectedBusiness.getName());
            }
        }
        formatter.formatReviews(Main.reviewCrawler.getReviews());
        int reviewOffset = yStart;
        int borderOffsetY = 20;
        int borderOffsetX = xStart;
        float lineHeight = canvas.textAscent() + canvas.textDescent();
        int reviewBoxHeight;
        String[] dateFormat;
        for (Review r : Main.reviewCrawler.getReviews()) {
            dateFormat = r.getDate().split(" ");
            reviewBoxHeight = (r.getNumberOfLines() * (int) lineHeight) + borderOffsetY - 5;
            canvas.fill(175, 255, 248);
            canvas.rect(borderOffsetX / 2, reviewOffset - Main.offsetFromTop, Main.SCREEN_X - 10, reviewBoxHeight);
            canvas.fill(0);
            canvas.text(dateFormat[0], Main.SCREEN_X - canvas.textWidth(dateFormat[0]) - 20, reviewOffset + borderOffsetY - Main.offsetFromTop);
            canvas.text(r.getFormattedReview(), borderOffsetX, reviewOffset + borderOffsetY - Main.offsetFromTop);

            reviewOffset = reviewOffset + (r.getNumberOfLines() * (int) lineHeight) + borderOffsetY;
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

    public void setupBusinessScreen() {
        Main.backButtonBusiness = Main.businessScreenController.addButton("backButton")
                .setValue(0)
                .setSize(50, 50)
                .setPosition(10, 10);
        Main.backButtonImage.resize(Main.backButtonBusiness.getWidth(), Main.backButtonBusiness.getHeight());
        Main.backButtonBusiness.setImage(Main.backButtonImage);
    }
}
