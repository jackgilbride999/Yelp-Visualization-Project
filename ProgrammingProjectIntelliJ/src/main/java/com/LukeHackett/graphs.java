package com.LukeHackett;

import org.gicentre.utils.stat.*;
import processing.core.PApplet;

import java.util.*;

/*

CheckinsBarChart chart;
StarBarChart chart2;import java.util.ArrayList;
// pass in arrayList from query function a graph is drawn
void settings() {
size(1800,900);
}
void setup()
{
  db = new DbAccess();
  //println(this.getClass());
  
  //ArrayList<BusinessNameId> list = db.getTop10BusinessIdListByCity("Las Vegas");
 // for (int i=0; i<list.size(); i++)
   // println(list.get(i).name);
  
  String name ="Starbucks";
  String id = db.getBusinessIdByName(name);
  println(id);
  ArrayList<Float> visitorsList = db.getBusinessCheckins(id);
  ArrayList<Float> starsList = db.getStarsList(id);
  

  
  chart = new CheckinsBarChart(visitorsList, name, this);
  chart2 = new StarBarChart(starsList,name,this);
  // sets up the bar chart
  // just not to make a mess of the setup
  // we can have these types of calls anywhere we need to have barcharts and graphs

}

*/

interface Graph {
    String getName();
    void setName(String name);
    void draw(float xPos, float yPos, float xSize, float ySize);
}

class StarBarChart implements Graph {
    private BarChart barChart2;
    private float[] starArray;
    private String name;
    private float max;
    private PApplet canvas;

    public StarBarChart(PApplet canvas, ArrayList<Float> inputList, String name) {
        this.canvas = canvas;
        barChart2 = new BarChart(canvas);
        starArray = new float[inputList.size()];
        max = 0;
        for (int i = 0; i < inputList.size(); i++) {

            starArray[i] = inputList.get(i);
            if (max < starArray[i])
                max = starArray[i];
        }
        this.name = name;
        // Draws the chart in the sketch
        barChart2.setData(starArray);
        // Scaling
        barChart2.setMinValue(0);
        barChart2.setMaxValue(max + 2);

        // Axis appearance
        canvas.textFont(Main.searchFont, 15);

        barChart2.showValueAxis(true);
        barChart2.setValueFormat("");
        barChart2.setBarLabels(new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sept", "Oct", "Nov", "Dec",});
        barChart2.showCategoryAxis(true);
        barChart2.setAxisValuesColour(canvas.color(255,255,255));

        // Bar colours and appearance
        barChart2.setBarColour(canvas.color(200));//175, 255, 248));
        barChart2.setBarGap(5);

        // Bar layout
        barChart2.transposeAxes(true);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void draw(float xPos, float yPos, float xSize, float ySize) {
        // bar chart can be called, by barChart.draw(xpos,ypos,width,height);
        canvas.fill(255);
        barChart2.draw(xPos, yPos + 35, xSize, ySize);
        canvas.textSize(15);
        canvas.text(name, xPos, yPos + 15);
        canvas.textSize(15);
        canvas.text("Change in rating over time.", xPos, yPos + 30);
        //canvas.textSize(12);
    }
}

class CheckinsBarChart implements Graph {
    private BarChart barChart;
    private float[] visitorsArray;
    private String name;
    private float max;
    private PApplet canvas;

    public CheckinsBarChart(PApplet canvas, ArrayList<Float> inputList, String name) {
        this.canvas = canvas;
        barChart = new BarChart(canvas);
        visitorsArray = new float[inputList.size()];
        max = 0;
        for (int i = 0; i < inputList.size(); i++) {

            visitorsArray[i] = inputList.get(i);
            if (max < visitorsArray[i])
                max = visitorsArray[i];
        }
        this.name = name;
        // Draws the chart in the sketch        
        barChart.setData(visitorsArray);
        // Scaling
        barChart.setMinValue(0);
        barChart.setMaxValue(max + 2);

        // Axis appearance
        canvas.textFont(Main.searchFont, 15);

        barChart.showValueAxis(true);
        barChart.setValueFormat("");
        barChart.setBarLabels(new String[]{"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"});
        barChart.showCategoryAxis(true);
        barChart.setAxisValuesColour(canvas.color(255,255,255));

        // Bar colours and appearance
        barChart.setBarColour(canvas.color(200));//175, 255, 248));
        barChart.setBarGap(10);

        // Bar layout
        barChart.transposeAxes(false);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void draw(float xPos, float yPos, float xSize, float ySize) {
        // bar chart can be called, by barChart.draw(xpos,ypos,width,height);
        canvas.fill(255);
        barChart.draw(xPos, yPos + 35, xSize, ySize);
        canvas.textSize(15);
        canvas.text(name, xPos, yPos + 15);
        canvas.textSize(15);
        canvas.text("check-in statistics.", xPos, yPos + 30);
        //canvas.textSize(12);
    }
}
/*
    void draw() {
        chart2.draw();
        chart.draw();
    }

*/