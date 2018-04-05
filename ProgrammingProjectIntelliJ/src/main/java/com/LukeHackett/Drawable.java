package com.LukeHackett;

import controlP5.Button;
import controlP5.ControlElement;
import controlP5.Label;
import controlP5.Textarea;
import processing.core.PApplet;

import java.io.IOException;
import java.util.*;

import de.fhpotsdam.unfolding.*;
import de.fhpotsdam.unfolding.geo.*;
import de.fhpotsdam.unfolding.ui.*;
import de.fhpotsdam.unfolding.providers.OpenStreetMap;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PImage;


import javax.xml.soap.Text;

import static com.LukeHackett.Main.*;
import static com.LukeHackett.Main.sidebarShadow;
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
        for (Button b : Main.searchResultButtons) {
            if (b != null) {
                b.setPosition(b.getPosition()[0], floatValue(initialBusinessYs.get(controllerCount)) - (searchRatio * offsetFromTopSearch));
                controllerCount++;

                if (b.getPosition()[1] < 75 && canvas.mouseY < 75) {
                    b.setValueSelf(15);
                    b.setColorBackground(canvas.color(240));
                    b.setColorForeground(canvas.color(240));
                    b.setColorActive(canvas.color(240));
                } else {
                    b.setValueSelf(10);
                    b.setColorBackground(canvas.color(240));
                    b.setColorForeground(canvas.color(230));
                    b.setColorActive(canvas.color(215));
                }
            }

        }

        Main.searchResultController.draw();
        int x = 20;
        int y = 0;

        for (ImageCrawler image : Main.businessesSearch) {
            if (image != null) {
                //Draw images
                try {
                    canvas.image(image.getBusiness().getImage(), x + 15 + 250, y + 90 + 15 - (searchRatio * offsetFromTopSearch), 150, 150);
                } catch (NullPointerException e){
                    canvas.image(Main.placeHolderImage, x + 15 + 250, y + 90 + 15 - (searchRatio * offsetFromTopSearch), 150, 150);
                }
                //Draw stars piggybacking here
                double stars = image.getBusiness().getStars();
                float starX = 210;
                for (int i = 0; i < 5; i++) {
                    if (stars <= 0) {
                        canvas.image(Main.emptyStar, starX + 250, y + 115 + 18 - (searchRatio * offsetFromTopSearch), 20, 20);
                    } else {
                        if (stars == 0.5) {
                            canvas.image(Main.halfStar, starX + 250, y + 115 + 18 - (searchRatio * offsetFromTopSearch), 20, 20);
                        } else {
                            canvas.image(Main.fullStar, starX + 250, y + 115 + 18 - (searchRatio * offsetFromTopSearch), 20, 20);
                        }
                        stars--;
                    }
                    starX += 25;
                }

                boolean parking = image.getBusiness().getParking();
                boolean wifi = image.getBusiness().getWifi();
                boolean wheelchair = image.getBusiness().getWheelchair();
                canvas.image((parking ? Main.parking : Main.noParking), x + 15 + 1045, y + 90 + 130 - (searchRatio * offsetFromTopSearch), 40, 40);
                canvas.image((wifi ? Main.wifi : Main.noWifi), x + 15 + 1095, y + 90 + 130 - (searchRatio * offsetFromTopSearch), 40, 40);
                canvas.image((wheelchair ? Main.wheelchair : Main.noWheelchair), x + 15 + 1145, y + 90 + 130 - (searchRatio * offsetFromTopSearch), 40, 40);
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
        canvas.fill(0, 150, 137);
        canvas.noStroke();
        canvas.rect(0, 0, Main.SCREEN_X, 75);
        canvas.fill(255);
        canvas.textFont(searchFont);
        canvas.text("Search results for " + Main.searchString, Main.SCREEN_X / 2 - canvas.textWidth("Search results for " + Main.searchString) / 2, 45);
        canvas.fill(255);
        canvas.textFont(reviewFont);
        canvas.text("Pages", SCREEN_X - 125, 45);
        //canvas.textSize(12);

        Main.searchResultHeaders.draw();
        Main.searchScroll.draw();

        canvas.image(Main.sidebarShadow, 260, 75);
        canvas.image(Main.searchIcon, 15, 90);

    }

    public void drawBusinessScreen() {
        float businessInfoX = 240;
        float businessInfoY = 100;
        previousReviewMouseY = canvas.mouseY;
        if (Main.reviewScroll != null) {
            offsetFromTopReview = Main.reviewScroll.getY();
        }

        canvas.fill(0, 169, 154);
        canvas.noStroke();
        canvas.rect(0, 0, Main.SCREEN_X, 500 - Main.offsetFromTop - (reviewRatio * offsetFromTopReview));
        canvas.fill(255f);
        canvas.textFont(businessInfoFont);
        canvas.text(Main.selectedBusiness.getName().substring(1, Main.selectedBusiness.getName().length() - 1) + '\n' + '\n'
                        + Main.selectedBusiness.getAddress() + '\n'
                        + Main.selectedBusiness.getCity() + ", "
                        + Main.selectedBusiness.getPostal_code()
                , businessInfoX, businessInfoY - Main.offsetFromTop - (reviewRatio * offsetFromTopReview));
        canvas.textFont(Main.reviewFont);
        canvas.text(Main.selectedBusiness.getReview_count() + " reviews", 240, 470 - (reviewRatio * offsetFromTopReview));
        drawReviews(10, 510);

        float stars = (float) Main.selectedBusiness.getStars();
        int starX = (int) businessInfoX;
        int y = (int) businessInfoY + 18;
        drawStars(starX, stars, y);

        Main.graphScreen.setyPos(initialReviewYs.get(3) - (reviewRatio * offsetFromTopReview));
        Main.graphScreen.draw();

        Main.businessScreenController.get("backButton").setPosition(Main.businessScreenController.get("backButton").getPosition()[0], initialReviewYs.get(0) - (reviewRatio * offsetFromTopReview));
        Main.businessScreenController.get("starsChartButton").setPosition(Main.businessScreenController.get("starsChartButton").getPosition()[0], initialReviewYs.get(1) - (reviewRatio * offsetFromTopReview));
        Main.businessScreenController.get("checkInGraphButton").setPosition(Main.businessScreenController.get("checkInGraphButton").getPosition()[0], initialReviewYs.get(2) - (reviewRatio * offsetFromTopReview));
        Main.businessScreenController.get("Filter").setPosition(Main.businessScreenController.get("Filter").getPosition()[0], initialReviewYs.get(4) - (reviewRatio * offsetFromTopReview));
        Main.businessScreenController.get("zoomIn").setPosition(Main.businessScreenController.get("zoomIn").getPosition()[0], initialReviewYs.get(5) - (reviewRatio * offsetFromTopReview));
        Main.businessScreenController.get("zoomOut").setPosition(Main.businessScreenController.get("zoomOut").getPosition()[0], initialReviewYs.get(6) - (reviewRatio * offsetFromTopReview));
        Main.businessScreenController.get("openingTimesButton").setPosition(Main.businessScreenController.get("openingTimesButton").getPosition()[0], initialReviewYs.get(7) - (reviewRatio * offsetFromTopReview));

        canvas.fill(255);
        canvas.noStroke();
        canvas.rect(20, 70 - (reviewRatio * offsetFromTopReview), 200, 200);
        canvas.image((Main.selectedBusiness.getImage() != null) ? Main.selectedBusiness.getImage() : placeHolderImage, 20 + 5, 70 + 5 - (reviewRatio * offsetFromTopReview), 190, 190);
        canvas.fill(255);
        canvas.rect(SCREEN_X/2 + 260 - 5, 75  - (reviewRatio * offsetFromTopReview) - 5, SCREEN_X/4 + 10, SCREEN_X/4 + 10);
        Main.businessScreenController.draw();
        if (Main.selectedBusiness.getMapImage() != null)
            canvas.image(Main.selectedBusiness.getMapImage(), SCREEN_X / 2 + 260, 75 - (reviewRatio * offsetFromTopReview), SCREEN_X / 4, SCREEN_X / 4);

        canvas.fill(0, 150, 137);
        canvas.noStroke();
        canvas.rect(0, 0, Main.SCREEN_X, 55);
        reviewHeaders.draw();
        canvas.fill(255);
        canvas.textFont(reviewFont);
        canvas.text("Reviews", SCREEN_X - 134, 34);
        if (Main.reviewScroll != null && reviewRatio > 1) Main.reviewScroll.draw();

    }

    public void drawReviews(int xStart, int yStart) {
        if (Main.selectedFilter == 0) {
            Main.reviewsToShow = Main.reviews;
        }
        if (Main.reviewsToShow.size() != 0) {
            for (Review r : Main.reviewsToShow) {
                if (r.getUser_name().equals("")) {
                    r.setUser_name(Main.qControl.getUserName(r.getUserId()));
                    r.setBusinessName(Main.selectedBusiness.getName());
                }
            }

            int reviewOffset = yStart;
            int borderOffsetY = 20;
            int borderOffsetX = xStart;
            canvas.textSize(15);
            canvas.textLeading(18);
            float lineHeight = canvas.textAscent() + canvas.textDescent() + 9;
            int reviewBoxHeight;
            String[] dateFormat;
            List<Review> iterableList = Main.reviewsToShow.subList(Main.currentReview, (Main.reviewsToShow.size() < Main.currentReview + 10) ? Main.reviewsToShow.size() : Main.currentReview + 10);
            ListIterator<Review> reviewIterator = iterableList.listIterator();
            while (reviewIterator.hasNext()) {
                Review r = reviewIterator.next();
                if (r.getFormattedReview() == null) {
                    formatter.formatReview(canvas, r);
                }

                if (initialReviewYs.size() < Main.reviews.size()) {
                    initialReviewYs.add((float) reviewOffset);
                }

                canvas.textFont(Main.graphFont);

                dateFormat = r.getDate().split(" ");
                reviewBoxHeight = (r.getNumberOfLines() * (int) lineHeight) + borderOffsetY-5;
                if (reviewRatio != 0) {
                    canvas.fill(245);
                    canvas.rect(borderOffsetX / 2, reviewOffset - Main.offsetFromTop - (reviewRatio * offsetFromTopReview), Main.SCREEN_X - 10, reviewBoxHeight);
                    canvas.fill(0);
                    canvas.text(dateFormat[0], Main.SCREEN_X - canvas.textWidth(dateFormat[0]) - 20, reviewOffset + borderOffsetY - Main.offsetFromTop - Main.offsetFromTop - (reviewRatio * offsetFromTopReview));
                    canvas.text(r.getFormattedReview(), borderOffsetX, reviewOffset + borderOffsetY - Main.offsetFromTop - (reviewRatio * offsetFromTopReview));
                } else {
                    canvas.fill(0, 169, 154, 80);
                    canvas.rect(borderOffsetX / 2, reviewOffset - Main.offsetFromTop, Main.SCREEN_X - 10, reviewBoxHeight);
                    canvas.fill(0);
                    canvas.text(dateFormat[0], Main.SCREEN_X - canvas.textWidth(dateFormat[0]) - 20, reviewOffset + borderOffsetY - Main.offsetFromTop);
                    canvas.text(r.getFormattedReview(), borderOffsetX, reviewOffset + borderOffsetY - Main.offsetFromTop);
                }
                drawStars((int) canvas.textWidth(r.getUser_name()) + 20, (float) r.getStars(), reviewOffset + 5);
                //if(r != Main.reviews.get(Main.reviews.size()-1))
                reviewOffset = reviewOffset + (r.getNumberOfLines() * (int) lineHeight) + borderOffsetY;
            }

            if (Main.reviewScroll == null) {
                Main.reviewScroll = new Scrollbar(canvas, 20, reviewOffset, canvas.color(150), SCROLLBAR_EVENT);
                reviewRatio = Main.reviewScroll.getRatio();
            }
        } else {
            canvas.fill(0);
            if (emptyReview) {
                canvas.text("No reviews to show!", Main.SCREEN_X / 2 - canvas.textWidth("No reviews to show!") / 2, Main.SCREEN_Y - 200);

            } else {
                if (reviewsToShow.size() == 0 && selectedFilter != 0) {
                    canvas.text("No reviews to show!", Main.SCREEN_X / 2 - canvas.textWidth("No reviews to show!") / 2, Main.SCREEN_Y - 200);
                } else {
                    //canvas.text("loading reviews...", Main.SCREEN_X / 2 - canvas.textWidth("loading reviews...") / 2, Main.SCREEN_Y - 200);
                    loadingAnimation.displayAnimation(SCREEN_X / 2 - 32, SCREEN_Y - 200);
                }
            }
        }

    }

    public void drawFailedCheckIns() {
        canvas.text("No Check-Ins available", 1010, 60);

    }

    public void drawFailedStars() {
        canvas.text("No ratings available", 1030, 300);
    }

    public boolean checkinChartInvalid(CheckinsBarChart chart) {
        if (chart == null) {
            return true;
        }
        return false;
    }

    public void setupHomeScreen() {
        int searchbarHeight = 40;
        int searchbarWidth = 3 * (Main.SCREEN_X / 4);
        int y = 640;
        int startX = Main.SCREEN_X / 6 - 72 - 85;

        Main.beautyButton = Main.homeScreenController.addButton("beautyButton")
                .setSize(110, 110)
                .setPosition(startX, y)
                .setImage(Main.beautyImage);

        Main.sportsButton = Main.homeScreenController.addButton("sportsButton")
                .setSize(110, 110)
                .setPosition(startX + 207, y)
                .setImage(Main.sportsImage);

        Main.restaurantsButton = Main.homeScreenController.addButton("restaurantsButton")
                .setSize(110, 110)
                .setPosition(startX + 414, y)
                .setImage(Main.restaurantImage);


        Main.shoppingButton = Main.homeScreenController.addButton("shoppingButton")
                .setSize(110, 110)
                .setPosition(startX + 621, y)
                .setImage(Main.shoppingImage);

        Main.automotiveButton = Main.homeScreenController.addButton("autoButton")
                .setSize(110, 110)
                .setPosition(startX + 828, y)
                .setImage(Main.automotiveImage);

        Main.nightLifeButton = Main.homeScreenController.addButton("nightlifeButton")
                .setSize(110, 110)
                .setPosition(startX + 1035, y)
                .setImage(Main.nightLifeImage);

        Main.searchBar = Main.homeScreenController.addTextfield("searchBar")
                .setCaptionLabel("")
                .setColorBackground(canvas.color(255, 255, 255))
                .setPosition(Main.SCREEN_X / 2 - searchbarWidth / 2, 350)
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
                .setPosition(Main.SCREEN_X / 2 + 3 * (Main.SCREEN_X / 4) / 2 - 200, 350);

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

    public void drawStars(int starX, float stars, int y) {
        for (int i = 0; i < 5; i++) {
            if (stars <= 0) {
                // canvas.image(Main.emptyStar, starX, y + 115, 20, 20);
            } else {
                if (stars == 0.5) {
                    canvas.image(Main.halfStar, starX, y - (reviewRatio * offsetFromTopReview), 20, 20);
                } else {
                    canvas.image(Main.fullStar, starX, y - (reviewRatio * offsetFromTopReview), 20, 20);
                }
                stars--;
            }
            starX += 25;
        }
    }


}
