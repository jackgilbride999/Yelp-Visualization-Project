package com.LukeHackett;

import controlP5.Button;
import controlP5.ControlP5;
import controlP5.ControllerInterface;
import controlP5.Label;
import processing.core.PApplet;

import java.util.ArrayList;
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
                String spacesToOuter = " ";
                String spacesToOuterLower = " ";
                String stars = new String(new char[b.getStars()]).replace("\0", "*");
                String[] catSplit = b.getCategories().split(";");
                StringBuilder categories = new StringBuilder();
                for(int j = 0; j < 3 && j < catSplit.length; j++) {
                    categories.append(catSplit[j]);
                    if(j < catSplit.length-1 && j != 2) categories.append(", ");
                }

                float nameWidth = canvas.textWidth(b.getName());
                while (canvas.textWidth(spacesToOuter) + canvas.textWidth(Main.spaceFromEdge) + nameWidth + canvas.textWidth(b.getAddress()) < 990) {
                    spacesToOuter += " ";
                }
                while (canvas.textWidth(spacesToOuterLower) + canvas.textWidth(Main.spaceFromEdge) + canvas.textWidth(stars) + canvas.textWidth(b.getCity()) < 990) {
                    spacesToOuterLower += " ";
                }

                b.setImage(canvas.loadImage("businessPlaceholder.png"));
                businessButton = Main.searchResultController.addButton(b.getBusiness_id())
                        .setValueSelf(10)
                        .setLabel(Main.spaceFromEdge + b.getName()
                                + spacesToOuter + b.getAddress()
                                + '\n' + Main.spaceFromEdge + stars
                                + spacesToOuterLower + b.getCity()
                                + '\n' + '\n'  + '\n' + '\n' + '\n' + '\n'
                                + Main.spaceFromEdge + categories)
                        .setPosition((float) Main.SCREEN_X / 2 - 500, (float) yOffset + 80)
                        .setSize(1000, 200)
                        .setFont(Main.searchFont)
                        .setColorBackground(canvas.color(0, 169, 154))
                        .setColorForeground(canvas.color(0, 135, 122))
                        .setColorActive(canvas.color(0, 100, 100));

                Label label = businessButton.getValueLabel();
                label.align(ControlP5.LEFT, ControlP5.TOP);
                label = businessButton.getCaptionLabel();
                label.align(ControlP5.LEFT, ControlP5.TOP);
                label.toUpperCase(false);

                Main.getDraw().initialBusinessYs.add(new Float(businessButton.getPosition()[1]));

                yOffset = yOffset + 200 + BORDER_OFFSET_Y;
            }
            Main.searchScroll = new Scrollbar(canvas, 10, yOffset - 200 - BORDER_OFFSET_Y, canvas.color(123), SCROLLBAR_EVENT);
            searchRatio = searchScroll.getRatio();
            Main.yOffset = 0;
        }
    }

    public void searchHeader(){
        backButton = Main.searchResultHeaders.addButton("backButton")
                .setSize(50, 50)
                .setPosition(SCREEN_X - 120, SCREEN_Y - 60);
        backButtonImage.resize(backButton.getWidth(), backButton.getHeight());
        backButton.setImage(backButtonImage);

        forwardButton = Main.searchResultHeaders.addButton("forwardButton")
                .setSize(50, 50)
                .setPosition(SCREEN_X - 60, SCREEN_Y - 60);
        forwardButtonImage.resize(forwardButton.getWidth(), forwardButton.getHeight());
        forwardButton.setImage(forwardButtonImage);

        homeButton = Main.searchResultHeaders.addButton("homeButton")
                .setSize(60, 60)
                .setPosition(10, 10);
        homeButtonImage.resize(homeButton.getWidth(), homeButton.getHeight());
        homeButton.setImage(homeButtonImage);

        searchOptionsSearch = Main.searchResultHeaders.addScrollableList("Options")
                .addItem("By Category", 0)
                .addItem("By Name", 1)
                .addItem("By City", 2)
                .setFont(searchFont)
                .setColorBackground(canvas.color(0, 145, 135))
                .setColorForeground(canvas.color(0, 135, 122))
                .setColorActive(canvas.color(0, 100, 100))
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

        int searchbarHeight = 40;
        int searchbarWidth = 3 * (SCREEN_X / 4);
        searchBarSearch = Main.searchResultHeaders.addTextfield("searchBar")
                .setCaptionLabel("")
                .setColorBackground(canvas.color(255, 255, 255))
                .setPosition(SCREEN_X / 2 - searchbarWidth / 2, 20)
                .setSize(searchbarWidth - 200, searchbarHeight)
                .setFont(searchFont)
                .setFocus(false)
                .setColor(canvas.color(0, 0, 0))
                .setColorCursor(canvas.color(0, 0, 0))
                .setColor(canvas.color(0, 0, 0))
                .setColorActive(canvas.color(0, 0, 0))
        ;
    }
}
