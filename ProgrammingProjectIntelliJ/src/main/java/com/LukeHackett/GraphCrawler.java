package com.LukeHackett;

/*
Basically the same as the review and image crawler. Purpose of this is
to speed up the program by running graph queries on separate threads
to stop queueing and improve overall feel of the program - LH
 */

import processing.core.PApplet;
import java.util.ArrayList;

public class GraphCrawler extends Thread{
    PApplet canvas;
    String name;
    String id;
    int chartType;
    GraphScreen graphScreen;

    ArrayList<Float> inputList;
    Graph graph;

    GraphCrawler(PApplet canvas, String name, String id, int chartType, GraphScreen graphScreen){
        this.canvas = canvas;
        this.name = name;
        this.id = id;
        this.chartType = chartType;
        this.graphScreen = graphScreen;
        start();
    }

    public void run(){
        if(chartType == Main.CHECKIN_CHART){
            inputList = Main.qControl.getBusinessCheckins(id);
            if (inputList == null) {
                System.out.println("Checkins not available for " + name);
            } else {
                graph = new CheckinsBarChart(canvas, inputList, name);
                if (graph != null) {
                    graphScreen.addGraph(graph, false);
                }
            }
        }
        else if(chartType == Main.STARS_CHART){
            inputList = Main.qControl.getStarsList(id);
            if (inputList != null) {
                // i added the line chart graph, however it looks pretty bad right now, just change the 'StarBarChart' to 'StarLineChart'
                graph = new StarLineChart(canvas, inputList, name);
                if (graph != null) {
                    graphScreen.addGraph(graph, true);
                }
            } else {
                System.out.println("Ratings not available for " + name);
            }
        }
    }

    public Graph getGraph() {
        return graph;
    }
}
