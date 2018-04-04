package com.LukeHackett;

import controlP5.*;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static com.LukeHackett.Main.*;
import static com.LukeHackett.Main.SEARCH_RESULT_SCREEN;

public class UI {

    private static final int BUSINESS_SEARCH = 0;
    private static final int CATEGORY_SEARCH = 1;
    private static final int CITY_SEARCH = 0;
    private final PApplet canvas;

    UI(PApplet canvas) {
        this.canvas = canvas;
    }

    public void searchBar(String text) {
        Main.currentController = SEARCH_RESULT_SCREEN;
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

    public ArrayList<Business> backButton() {
        //Main.searchScroll = null;
        println(Main.searchType);
        if(Main.graphScreen != null) Main.graphScreen.setGraphs(new LinkedHashMap<Graph, Boolean>());

        Main.starLoaded = false;
        Main.checkinLoaded = false;
        Main.hoursLoaded = false;

        if (Main.currentSearch != 0) {
            Main.currentSearch -= 10;
            if(Main.searchType == BUSINESS_SEARCH)return Main.qControl.businessSearch(Main.searchString, Main.currentSearch, 10);
            else if(Main.searchType == CATEGORY_SEARCH)return Main.qControl.categorySearch(Main.searchString, Main.currentSearch, 10);
            else if(Main.searchType == CITY_SEARCH)return Main.qControl.citySearch(Main.searchString, Main.currentSearch, 10);
        }
        return null;
    }

    public ArrayList<Business> forwardButton() {
        if (Main.searchResultController.getAll().size() == 10) {
            Main.currentSearch += 10;
            if(Main.searchType == BUSINESS_SEARCH)return Main.qControl.businessSearch(Main.searchString, Main.currentSearch, 10);
            else if(Main.searchType == CATEGORY_SEARCH)return Main.qControl.categorySearch(Main.searchString, Main.currentSearch, 10);
            else if(Main.searchType == CITY_SEARCH)return Main.qControl.citySearch(Main.searchString, Main.currentSearch, 10);
        }
        return null;
    }

    public void backButtonReview() {
        if (Main.currentReview != 0) {
            Main.currentReview -= 10;
            reviewScroll = null;
            for(Textarea t : Main.reviewText){
                businessScreenController.remove(t.getName());
            }
            Main.reviewText = new ArrayList<Textarea>();
        }
    }

    public void forwardButtonReview(){
        if((Main.currentReview+10) < Main.reviews.size()){
            Main.currentReview += 10;
            reviewScroll = null;
            for(Textarea t : Main.reviewText){
                businessScreenController.remove(t.getName());
            }
            Main.reviewText = new ArrayList<Textarea>();
        }
    }

    public int homeButton() {
        Main.starsList = null;
        Main.visitorsList = null;
        Main.reviewScroll = null;
        Drawable.reviewRatio = 0;
        Drawable.previousReviewMouseY = 0;
        Main.reviews = new ArrayList<Review>();
        Main.reviewsToShow = new ArrayList<Review>();
        Main.graphScreen.setGraphs(new LinkedHashMap<Graph, Boolean>());
        Main.zoom = 10;
        switch (Main.currentController) {
            case SEARCH_RESULT_SCREEN:
                //if (Main.currentSearch != 0) {
                //    Main.currentSearch -= 10;
                    //return Main.qControl.businessSearch(Main.searchString, Main.currentSearch, 10);
                //}
                return HOME_SCREEN;
            case BUSINESS_SCREEN:
                //Main.currentController = SEARCH_RESULT_SCREEN;
                Main.reviewFilterOptions.setValue(0);
                for(Textarea t : Main.reviewText){
                    businessScreenController.remove(t.getName());
                }
                Main.reviewText = new ArrayList<Textarea>();
                return SEARCH_RESULT_SCREEN;
        }
        return 0;
    }

    public ArrayList<Business> beautyButton(queries qControl) {
        Main.currentController = SEARCH_RESULT_SCREEN;
        Main.searchType = CATEGORY_SEARCH;
        return qControl.categorySearch("Beauty", 0, 10);
    }

    public ArrayList<Business> autoButton(queries qControl) {
        Main.currentController = SEARCH_RESULT_SCREEN;
        Main.searchType = CATEGORY_SEARCH;

        return qControl.categorySearch("Automotive", 0, 10);
    }

    public ArrayList<Business> shoppingButton(queries qControl) {
        Main.currentController = SEARCH_RESULT_SCREEN;
        Main.searchType = CATEGORY_SEARCH;
        return qControl.categorySearch("Shopping", 0, 10);
    }

    public ArrayList<Business> nightlifeButton(queries qControl) {
        Main.currentController = SEARCH_RESULT_SCREEN;
        Main.searchType = CATEGORY_SEARCH;
        return qControl.categorySearch("nightlife", 0, 10);
    }

    public ArrayList<Business> restaurantsButton(queries qControl) {
        Main.currentController = SEARCH_RESULT_SCREEN;
        Main.searchType = CATEGORY_SEARCH;
        return qControl.categorySearch("Restaurant", 0, 10);
    }

    public ArrayList<Business> sportsButton(queries qControl) {
        Main.currentController = SEARCH_RESULT_SCREEN;
        Main.searchType = CATEGORY_SEARCH;
        return qControl.categorySearch("sports", 0, 10);
    }

    public void buttonBusinessList(ArrayList<Business> businessList) {
        List<ControllerInterface<?>> elements = Main.searchResultController.getAll();
        for (ControllerInterface e : elements) {
            Main.searchResultController.remove(e.getName());
        }

        Button businessButton;
        if (businessList != null) {
            Main.businessesSearch = new ImageCrawler[10];
            for (int i = 0; i < businessList.size(); i++) {
                Business b = businessList.get(i);
                Main.businessesSearch[i] = new ImageCrawler(canvas, b);

                String[] catSplit = b.getCategories().split(";");
                StringBuilder categories = new StringBuilder();
                for(int j = 0; j < 3 && j < catSplit.length; j++) {
                    categories.append(catSplit[j]);
                    if(j < catSplit.length-1 && j != 2) categories.append(", ");
                }

                canvas.textSize(22);
                String spacesToOuter = " ";
                String spacesToOuterLower = " ";
                String address = (!b.getAddress().equals("")) ? b.getAddress() : "N/a";
                float nameWidth = canvas.textWidth(b.getName());
                while (canvas.textWidth(spacesToOuter) + canvas.textWidth(Main.spaceFromEdge) + nameWidth + canvas.textWidth(address) < 955) {
                    spacesToOuter += " ";
                }
                while (canvas.textWidth(spacesToOuterLower) + canvas.textWidth(Main.spaceFromEdge) + canvas.textWidth(b.getCity())+125 < 1075) {
                    spacesToOuterLower += " ";
                }

                b.setImage(Main.placeHolderImage);
                /*
                LinkedHashMap<String, String> attributes = qControl.getBusinessAttributes(b.getBusiness_id());

                if(attributes.size() > 0){
                    if(!attributes.get("BusinessParking_garage").equals("Na")
                            || !attributes.get("BusinessParking_street").equals("Na")
                            || !attributes.get("BusinessParking_lot").equals("Na")) {
                        if (!attributes.get("BusinessParking_garage").equals("False")
                                || !attributes.get("BusinessParking_street").equals("False")
                                || !attributes.get("BusinessParking_lot").equals("False")) {
                            //b.setParking(true);
                        }
                    }
                }
                */
                businessButton = Main.searchResultController.addButton(b.getBusiness_id())
                        .setValueSelf(10)
                        .setLabel('\n' + Main.spaceFromEdge + b.getName()
                                + spacesToOuter + address
                                + '\n' + spaceFromEdge + spacesToOuterLower + b.getCity()
                                + '\n' + '\n'  + '\n' + '\n' + '\n'
                                + Main.spaceFromEdge + categories)
                        .setPosition((float)260, (float) yOffset + 80)
                        .setSize(SCREEN_X-270, 200)
                        .setFont(Main.searchFont)
                        .setColorBackground(canvas.color(230,230,230))
                        .setColorForeground(canvas.color(200,200,200))
                        .setColorActive(canvas.color(170,170,170));



                Label label = businessButton.getValueLabel();
                label.align(ControlP5.LEFT, ControlP5.TOP);
                label = businessButton.getCaptionLabel();
                label.align(ControlP5.LEFT, ControlP5.TOP);
                label.toUpperCase(false);
                label.setColor(100);

                Main.getDraw().initialBusinessYs.add(businessButton.getPosition()[1]);
                Main.searchResultButtons[i] = businessButton;

                yOffset = yOffset + 200 + BORDER_OFFSET_Y;
            }

            float totalHeight = (searchResultController.getAll().size())*200+180;
            Main.searchScroll = new Scrollbar(canvas, 10, totalHeight, canvas.color(150), SCROLLBAR_EVENT);
            Drawable.searchRatio = searchScroll.getRatio();
            Main.yOffset = 0;
        }
    }

    public void searchHeader(){
        Main.backButton = Main.searchResultHeaders.addButton("backButton")
                .setSize(50, 50)
                .setPosition(SCREEN_X - 180, 20);
        backButtonImage.resize(backButton.getWidth(), backButton.getHeight());
        backButton.setImage(backButtonImage);

        Main.forwardButton = Main.searchResultHeaders.addButton("forwardButton")
                .setSize(50, 50)
                .setPosition(SCREEN_X - 60, 20);
        forwardButtonImage.resize(forwardButton.getWidth(), forwardButton.getHeight());
        forwardButton.setImage(forwardButtonImage);

        homeButton = Main.searchResultHeaders.addButton("homeButton")
                .setSize(60, 60)
                .setPosition(10, 10);
        homeButtonImage.resize(homeButton.getWidth(), homeButton.getHeight());
        homeButton.setImage(homeButtonImage);

        Main.searchBarSearch = Main.searchResultHeaders.addTextfield("searchBarSearch")
                .setCaptionLabel("")
                .setColorBackground(canvas.color(255, 255, 255))
                .setPosition(45, 90)
                .setSize(195, 30)
                .setFont(Main.searchFont)
                .setFocus(false)
                .setColor(canvas.color(0, 0, 0))
                .setColorCursor(canvas.color(0, 0, 0))
                .setColor(canvas.color(0, 0, 0))
                .setColorActive(canvas.color(0, 0, 0));

        Main.searchHeaderButtons = new ArrayList<Button>();
        int y = 130;
        int ySize = 28;
        for(int i = 0; i < Main.categories.size(); i++){
            Button catButton = Main.searchResultHeaders.addButton(categories.get(i))
                    .setValueSelf(20)
                    .setCaptionLabel(categories.get(i))
                    .setColorBackground(canvas.color(255, 255, 255))
                    .setPosition(15, y)
                    .setSize(225, ySize)
                    .setFont(Main.reviewFont)
                    .setColorBackground(canvas.color(245))
                    .setColorForeground(canvas.color(230))
                    .setColorActive(canvas.color(215));

            Label label = catButton.getValueLabel();
            label.align(ControlP5.LEFT, ControlP5.TOP);
            label = catButton.getCaptionLabel();
            label.align(ControlP5.LEFT, ControlP5.TOP);
            label.toUpperCase(false);
            label.setColor(100);

            y += ySize+5;
            Main.searchHeaderButtons.add(catButton);
        }
    }

    public void setupBusinessScreen() {
        Main.selectedFilter = 0;
        Main.backButtonBusiness = Main.businessScreenController.addButton("backButton")
                .setValue(0)
                .setSize(50, 50)
                .setPosition(10, 10);
        Main.backButtonImage.resize(Main.backButtonBusiness.getWidth(), Main.backButtonBusiness.getHeight());
        Main.backButtonBusiness.setImage(Main.backButtonImage);

        Main.starsChartButton = Main.businessScreenController.addButton("starsChartButton")
                .setValueSelf(15)
                .setCaptionLabel("")
                .setImage(starPressed)
                .setPosition(420, 290);

        Main.openingTimesButton = Main.businessScreenController.addButton("openingTimesButton")
                .setValueSelf(15)
                .setCaptionLabel("")
                .setImage(timesPressed)
                .setPosition(420, 330);

        Main.checkInGraphButton = Main.businessScreenController.addButton("checkInGraphButton")
                .setValueSelf(15)
                .setCaptionLabel("")
                .setImage(checkInPressed)
                .setPosition(420, 370);

        Main.reviewFilterOptions = Main.businessScreenController.addScrollableList("Filter")
                .addItem("All", 0)
                .addItem("5 Star Reviews", 1)
                .addItem("4 Star Reviews", 2)
                .addItem("3 Star Reviews", 3)
                .addItem("2 Star Reviews", 4)
                .addItem("1 Star Reviews", 5)
                .setFont(Main.reviewFont)
                .setColorBackground(canvas.color(0, 145, 135))
                .setColorForeground(canvas.color(0, 135, 122))
                .setColorActive(canvas.color(0, 100, 100))
                .setMouseOver(false)
                .setOpen(false)
                .setHeight(300)
                .setWidth(200)
                .setBarHeight(40)
                .setItemHeight(40)
                .setPosition(20, 440 - Main.offsetFromTop);

        Label label = Main.reviewFilterOptions.getCaptionLabel();
        label.toUpperCase(false);
        label.getStyle()
                .setPaddingLeft(5)
                .setPaddingTop(10);
        label = Main.reviewFilterOptions.getValueLabel();
        label.toUpperCase(false);
        label.getStyle()
                .setPaddingLeft(5)
                .setPaddingTop(10);

        Main.zoomInButton = Main.businessScreenController.addButton("zoomIn")
                .setSize(31, 30)
                .setCaptionLabel("")
                .setImage(zoomIn)
                .setPosition(Main.SCREEN_X - 50, 331);

        Main.zoomOutButton = Main.businessScreenController.addButton("zoomOut")
                .setSize(31, 30)
                .setCaptionLabel("")
                .setImage(zoomOut)
                .setPosition(Main.SCREEN_X - 50, 361);

        Drawable.initialReviewYs.add(Main.backButtonBusiness.getPosition()[1]);
        Drawable.initialReviewYs.add(Main.starsChartButton.getPosition()[1]);
        Drawable.initialReviewYs.add(Main.checkInGraphButton.getPosition()[1]);
        Drawable.initialReviewYs.add(Main.graphScreen.getyPos());
        Drawable.initialReviewYs.add(Main.reviewFilterOptions.getPosition()[1]);
        Drawable.initialReviewYs.add(Main.zoomInButton.getPosition()[1]);
        Drawable.initialReviewYs.add(Main.zoomOutButton.getPosition()[1]);
        Drawable.initialReviewYs.add(Main.openingTimesButton.getPosition()[1]);
    }

    public void setupReviewHeader(){
        reviewBackButton = reviewHeaders.addButton("backButtonReview")
                .setSize(50, 50)
                .setPosition(SCREEN_X - 180, 7);
        backButtonImage.resize(backButton.getWidth() - 10, backButton.getHeight() - 10);
        reviewBackButton.setImage(backButtonImage);

        reviewForwardButton = reviewHeaders.addButton("forwardButtonReview")
                .setSize(50, 50)
                .setPosition(SCREEN_X - 60, 7);
        forwardButtonImage.resize(forwardButton.getWidth()- 10, forwardButton.getHeight() - 10);
        reviewForwardButton.setImage(forwardButtonImage);

        reviewReturnButton = reviewHeaders.addButton("homeButton")
                .setSize(40, 40)
                .setPosition(10, 7);
        returnButton.resize(reviewReturnButton.getWidth(), reviewReturnButton.getHeight());
        reviewReturnButton.setImage(returnButton);
    }
}
