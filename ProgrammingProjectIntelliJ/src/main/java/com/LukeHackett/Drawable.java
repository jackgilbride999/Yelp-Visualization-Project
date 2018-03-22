package com.LukeHackett;

import controlP5.ControllerInterface;
import controlP5.Label;
import processing.core.PApplet;
import java.util.ArrayList;

import java.util.List;

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
        Main.searchScroll.draw(0);
        Main.previousSearchMouseY = canvas.mouseY;
        Main.offsetFromTopSearch = Main.searchScroll.getY();

        List<ControllerInterface<?>> list = Main.searchResultController.getAll();
        int controllerCount = 0;
        for (ControllerInterface i : list) {
            if (i.getName() != "backButton" && i.getName() != "forwardButton" && i.getName() != "homeButton" && i.getName() != "searchBar" && i.getName() != "Options")
               // i.setPosition(i.getPosition()[0], i.getPosition()[1] - (Main.searchRatio * Main.offsetFromTopSearch));
            i.setPosition(i.getPosition()[0], floatValue(initialBusinessYs.get(controllerCount)) - (Main.searchRatio * Main.offsetFromTopSearch));
            controllerCount++;
        }
        Main.searchResultController.draw();

        int x = Main.SCREEN_X / 2 - 490;
        int y = 0;
        for (ImageCrawler image : Main.businessesSearch) {
            if (image != null) {
                canvas.image(image.getBusiness().getImage(), x, y + 90 - (Main.searchRatio * Main.offsetFromTopSearch), 180, 180);
                y = y + 200 + Main.BORDER_OFFSET_Y;
            }
        }
    }

    public void drawReviews(int xStart, int yStart) {
        for (Review r : Main.reviewCrawler.getReviews()) {
            if (r.getUser_name().equals("")) {
                r.setUser_name(Main.qControl.getUserName(r.getUserId()));
                r.setBusinessName(Main.selectedBusiness.getName());
            }
        }
        formatter.formatReviews(Main.reviewCrawler.getReviews());
        /*
        for (Review r : Main.reviewCrawler.getReviews()) {
            Main.println(r.getFormattedReview());
        }
        */

        int reviewOffset = yStart;
        int borderOffsetY = 20;
        int borderOffsetX = xStart;
        float lineHeight = canvas.textAscent() + canvas.textDescent();
        for (Review r : Main.reviewCrawler.getReviews()) {
            canvas.fill(175, 255, 248);
            canvas.rect(borderOffsetX / 2, reviewOffset - Main.offsetFromTop, Main.SCREEN_X - 10, (r.getNumberOfLines() * (int) lineHeight) + borderOffsetY - 5);
            canvas.fill(0);
            canvas.text(r.getDate(), Main.SCREEN_X - canvas.textWidth(r.getDate()) - 20, reviewOffset + borderOffsetY - Main.offsetFromTop);
            canvas.text(r.getFormattedReview(), borderOffsetX, reviewOffset + borderOffsetY - Main.offsetFromTop);

            reviewOffset = reviewOffset + (r.getNumberOfLines() * (int) lineHeight) + borderOffsetY;
        }
    }

    public CheckinsBarChart setupCheckinGraph(String name) {
        CheckinsBarChart chart;
        DbAccess db = new DbAccess();


        String id = db.getBusinessIdByName(name);

        ArrayList<Float> visitorsList = db.getBusinessCheckins(id);
        chart = new CheckinsBarChart(canvas,visitorsList, name);
        return chart;
    }



    public void drawCheckIns(CheckinsBarChart chart) {
        chart.draw();

    }

    public StarBarChart setupStarsChart(String name) {
        StarBarChart chart2;
        DbAccess db2 = new DbAccess();

        String idStars = db2.getBusinessIdByName(name);

        ArrayList<Float> starsList = db2.getStarsList(idStars);

        chart2 = new StarBarChart(canvas, starsList, name);
        return chart2;
    }

    public void drawStarChart(StarBarChart chart2) {
        chart2.draw();

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
