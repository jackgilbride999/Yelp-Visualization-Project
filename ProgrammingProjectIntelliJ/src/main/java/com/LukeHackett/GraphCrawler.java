package com.LukeHackett;

/*
Written by LH -

Basically the same as the review and image crawler. Purpose of this is
to speed up the program by running graph queries on separate threads
to stop queueing and improve overall feel of the program
 */

import processing.core.PApplet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class GraphCrawler extends Thread {
    PApplet canvas;
    String name;
    String id;
    int chartType;
    GraphScreen graphScreen;

    ArrayList<Float> inputList;
    Graph graph;

    GraphCrawler(PApplet canvas, String name, String id, int chartType, GraphScreen graphScreen) {
        this.canvas = canvas;
        this.name = name;
        this.id = id;
        this.chartType = chartType;
        this.graphScreen = graphScreen;
        start();
    }

    public void run() {
        if (chartType == Main.CHECKIN_CHART) {
            inputList = Main.qControl.getBusinessCheckins(id);
            if (inputList == null) {
                System.out.println("Checkins not available for " + name);
            } else {
                graph = new CheckinsBarChart(canvas, inputList, name);
                if (graph != null) {
                    graphScreen.addGraph(graph, false);
                    Main.checkInGraphButton.setImage(Main.checkInPressed);
                    Main.checkinLoaded = true;
                }
                else{
                    Main.checkInGraphButton.setImage(Main.checkIn);
                }
            }
        } else if (chartType == Main.STARS_CHART) {
            HashSet<StarDate> inputSet = Main.qControl.getStarsList(id);
            HashMap<Integer, Float> monthMap = new HashMap<Integer, Float>();
            for (int i = 1; i <= 12; i++) {
                int starAdd = 0;
                int numRatings = 0;
                for (StarDate s : inputSet) {
                    if (s.getDate() == i) {
                        starAdd += s.getStar();
                        numRatings++;
                    }
                }
                if (numRatings != 0) {
                    monthMap.put(i, (float) starAdd / numRatings);
                }
            }

            if (monthMap == null) {
                System.out.println("I can't star!!!");
            } else {
                graph = new StarLineChart(canvas, monthMap, name);
                if (graph != null) {
                    graphScreen.addGraph(graph, false);
                    Main.starsChartButton.setImage(Main.starPressed);
                    Main.starLoaded = true;
                }
                else{
                    Main.starsChartButton.setImage(Main.star);
                }
            }
        } else if (chartType == Main.HOURS_CHART) {
            String[] inputArray = Main.qControl.getBusinessHours(id);
            try {
                if (inputArray == null)
                    throw new NullPointerException();
                graph = new BusinessHoursChart(canvas, inputArray, name);
                if (graph == null)
                    throw new NullPointerException();
                else {
                    graph.setName("" + Main.qControl.getBusinessName(id) + " Opening Hours (A.M., P.M.):");
                    graphScreen.addGraph(graph, false);
                    Main.openingTimesButton.setImage(Main.timesPressed);
                    Main.hoursLoaded = true;
                }
            } catch (Exception nullPointerException) {
                System.out.println("Hours not available for " + name);
                Main.openingTimesButton.setImage(Main.times);
            }
        }
    }

    public Graph getGraph() {
        return graph;
    }
}
