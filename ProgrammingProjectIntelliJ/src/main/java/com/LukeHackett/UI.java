package com.LukeHackett;

import controlP5.Button;
import controlP5.ControlP5;
import controlP5.ControllerInterface;
import controlP5.Label;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static com.LukeHackett.Main.*;
import static com.LukeHackett.Main.SEARCH_RESULT_SCREEN;

public class UI {

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
        Main.starsList = null;
        Main.visitorsList = null;
        if(Main.graphScreen != null) Main.graphScreen.setGraphs(new LinkedHashMap<Graph, Boolean>());
        switch (Main.currentController) {
            case SEARCH_RESULT_SCREEN:
                if (Main.currentSearch != 0) {
                    Main.currentSearch -= 10;
                    return Main.qControl.businessSearch(Main.searchString, Main.currentSearch, 10);
                }
                break;
            case BUSINESS_SCREEN:
                Main.currentController = SEARCH_RESULT_SCREEN;
                break;
        }
        return null;
    }

    public ArrayList<Business> forwardButton() {
        if (Main.searchResultController.getAll().size() == 10) {
            Main.currentSearch += 10;
            return Main.qControl.businessSearch(Main.searchString, Main.currentSearch, 10);
        }
        return null;
    }

    public int homeButton() {
        return HOME_SCREEN;
    }

    public ArrayList<Business> beautyButton(queries qControl) {
        Main.currentController = SEARCH_RESULT_SCREEN;
        return qControl.businessSearch("Beauty", 0, 10);
    }

    public ArrayList<Business> autoButton(queries qControl) {
        Main.currentController = SEARCH_RESULT_SCREEN;
        return qControl.businessSearch("Automotive", 0, 10);
    }

    public ArrayList<Business> shoppingButton(queries qControl) {
        Main.currentController = SEARCH_RESULT_SCREEN;
        return qControl.businessSearch("Shopping", 0, 10);
    }

    public ArrayList<Business> nightlifeButton(queries qControl) {
        Main.currentController = SEARCH_RESULT_SCREEN;
        return qControl.businessSearch("nightlife", 0, 10);
    }

    public ArrayList<Business> restaurantsButton(queries qControl) {
        Main.currentController = SEARCH_RESULT_SCREEN;
        return qControl.businessSearch("Restaurant", 0, 10);
    }

    public ArrayList<Business> sportsButton(queries qControl) {
        Main.currentController = SEARCH_RESULT_SCREEN;
        return qControl.businessSearch("sports", 0, 10);
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

                canvas.textSize(12);
                String spacesToOuter = " ";
                String spacesToOuterLower = " ";
                String address = (!b.getAddress().equals("")) ? b.getAddress() : "N/a";
                float nameWidth = canvas.textWidth(b.getName());
                while (canvas.textWidth(spacesToOuter) + canvas.textWidth(Main.spaceFromEdge) + nameWidth + canvas.textWidth(address) < 660) {
                    spacesToOuter += " ";
                }
                while (canvas.textWidth(spacesToOuterLower) + canvas.textWidth(Main.spaceFromEdge) + canvas.textWidth(b.getCity()) + 125 < 785) {
                    spacesToOuterLower += " ";
                }

                b.setImage(Main.placeHolderImage);
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

                businessButton = Main.searchResultController.addButton(b.getBusiness_id())
                        .setValueSelf(10)
                        .setLabel(Main.spaceFromEdge + b.getName()
                                + spacesToOuter + address
                                + '\n' + spaceFromEdge + spacesToOuterLower + b.getCity()
                                + '\n' + '\n'  + '\n' + '\n' + '\n' + '\n'
                                + Main.spaceFromEdge + categories)
                        .setPosition((float)10, (float) yOffset + 80)
                        .setSize(SCREEN_X-20, 200)
                        .setFont(Main.searchFont)
                        .setColorBackground(canvas.color(0, 169, 154))
                        .setColorForeground(canvas.color(0, 135, 122))
                        .setColorActive(canvas.color(0, 100, 100));

                Label label = businessButton.getValueLabel();
                label.align(ControlP5.LEFT, ControlP5.TOP);
                label = businessButton.getCaptionLabel();
                label.align(ControlP5.LEFT, ControlP5.TOP);
                label.toUpperCase(false);

                Main.getDraw().initialBusinessYs.add(businessButton.getPosition()[1]);
                Main.searchResultButtons[i] = businessButton;

                yOffset = yOffset + 200 + BORDER_OFFSET_Y;
            }

            float totalHeight = (searchResultController.getAll().size())*200+180;
            Main.searchScroll = new Scrollbar(canvas, 10, totalHeight, canvas.color(150), SCROLLBAR_EVENT);
            Main.searchRatio = searchScroll.getRatio();
            Main.yOffset = 0;
        }
    }

    public void searchHeader(){
        Main.backButton = Main.searchResultHeaders.addButton("backButton")
                .setSize(50, 50)
                .setPosition(SCREEN_X - 120, 10);
        backButtonImage.resize(backButton.getWidth(), backButton.getHeight());
        backButton.setImage(backButtonImage);

        Main.forwardButton = Main.searchResultHeaders.addButton("forwardButton")
                .setSize(50, 50)
                .setPosition(SCREEN_X - 60, 10);
        forwardButtonImage.resize(forwardButton.getWidth(), forwardButton.getHeight());
        forwardButton.setImage(forwardButtonImage);

        homeButton = Main.searchResultHeaders.addButton("homeButton")
                .setSize(60, 60)
                .setPosition(10, 10);
        homeButtonImage.resize(homeButton.getWidth(), homeButton.getHeight());
        homeButton.setImage(homeButtonImage);
    }

    public void setupBusinessScreen() {
        Main.backButtonBusiness = Main.businessScreenController.addButton("backButton")
                .setValue(0)
                .setSize(50, 50)
                .setPosition(10, 10);
        Main.backButtonImage.resize(Main.backButtonBusiness.getWidth(), Main.backButtonBusiness.getHeight());
        Main.backButtonBusiness.setImage(Main.backButtonImage);

        Main.graphForward = Main.businessScreenController.addButton("graphForward")
                .setValueSelf(15)
                .setSize(50, 50)
                .setCaptionLabel("")
                .setImage(forwardButtonImage)
                .setPosition(Main.SCREEN_X - 100, 350);

        Main.graphBackward = Main.businessScreenController.addButton("graphBackward")
                .setValueSelf(15)
                .setSize(50, 50)
                .setCaptionLabel("")
                .setImage(backButtonImage)
                .setPosition(Main.SCREEN_X - 300, 350);

    }
}
