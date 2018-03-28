package com.LukeHackett;

import org.gicentre.utils.stat.*;
import processing.core.PApplet;

import java.util.*;
import java.util.Scanner;

import static javafx.scene.paint.Color.color;

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


class StarLineChart implements Graph {
    private XYChart starChart;
    private float[] starArray2;
    private String name2;
    private float max2;
    private PApplet canvas;

    public StarLineChart(PApplet canvas, ArrayList<Float> inputList, String name) {

        this.canvas = canvas;
        starChart = new XYChart(canvas);
        starArray2 = new float[inputList.size()];
        max2 = 0;
        for (int i = 0; i < inputList.size(); i++) {

            starArray2[i] = inputList.get(i);
            if (max2 < starArray2[i])
                max2 = starArray2[i];
        }
        float[] coords = new float[inputList.size()];
        int counter =0;
        for(int i =0; i < inputList.size();i++) {
            coords[i] = counter;
            counter++;

        }

        starChart.setData(coords,starArray2);
        this.name2 = name;
        starChart.showXAxis(true);
        starChart.showYAxis(true);
        starChart.setMinY(0);
        starChart.setLineColour(0);
        starChart.calcDataSpacing();

        starChart.setPointColour(0);
        starChart.setPointSize(5);
        starChart.setLineWidth(2);

    }

    public String getName() {
        return name2;
    }

    public void setName(String name) {
        this.name2 = name;
    }

    public void draw(float xPos, float yPos, float xSize, float ySize) {
        // bar chart can be called, by barChart.draw(xpos,ypos,width,height);
        canvas.fill(0);
        starChart.draw(xPos, yPos + 35, xSize, ySize);
        canvas.textSize(15);
        canvas.text(name2, xPos, yPos + 15);
        canvas.textSize(15);
        canvas.text("Change in rating over time.", xPos, yPos + 30);
        //canvas.textSize(12);
    }

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

        barChart2.showValueAxis(true);
        barChart2.setValueFormat("");
        barChart2.setBarLabels(new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sept", "Oct", "Nov", "Dec",});
        barChart2.showCategoryAxis(false);
        barChart2.setAxisValuesColour(canvas.color(255,255,255));

        // Bar colours and appearance
        barChart2.setBarColour(canvas.color(200));//175, 255, 248));
        barChart2.setBarGap(5);

        // Bar layout
        barChart2.transposeAxes(false);
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

        barChart.showValueAxis(false);
        barChart.setValueFormat("");
        barChart.setBarLabels(new String[]{});
        barChart.showCategoryAxis(false);
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

/*class BusinessHoursBarChart{
    private BarChart barChart;
    private String[] hoursArray;
    private String name;
    private float max;
    private float min;
    private PApplet canvas;

    public BusinessHoursBarChart(PApplet canvas, String[] hoursArray, String name){
        this.canvas = canvas;
        barChart = new BarChart(canvas);
        this.hoursArray=hoursArray;
        max = 0;
        min = 0;
    }
}*/

class BusinessHoursChart {
    private PApplet canvas;
    private boolean[][] openingHours;
    private static final int DAYS_PER_WEEK = 7;
    private static final int HALF_HOURS_PER_DAY = 48;
    private static final int BOX_WIDTH = 20;
    private static final int BOX_HEIGHT = 20;
    private int graphX, graphY;

    public BusinessHoursChart(PApplet canvas, String[] hoursArray, int x, int y) {
        this.canvas=canvas;
        this.graphX=x;
        this.graphY=y;
        openingHours = new boolean[DAYS_PER_WEEK][HALF_HOURS_PER_DAY];
        if (hoursArray!=null) {
            for (int i=0; i<hoursArray.length; i++) {
                try {
                    String hoursOpen = hoursArray[i];
                    if (!hoursOpen.equals("None")) {
                        String[] hours = hoursOpen.split("-"); // opening time will be at index  0,  closing time at index  1
                        String openingTime = hours[0];
                        int indexToOpenAt = 2*Integer.parseInt(openingTime.split(":")[0]); //eg if the business opens at 7:00, will start indexing at index 14 as we are working with half hours
                        if (Integer.parseInt(openingTime.split(":")[1])>=30) { // but if it opens at 7:30 we will start indexing at index 15
                            indexToOpenAt++;
                        }
                        String closingTime = hours[1];
                        int indexToCloseAt = 2*Integer.parseInt(closingTime.split(":")[0]);
                        if (Integer.parseInt(closingTime.split(":")[1])<30) {
                            indexToCloseAt--;
                        }
                        else if(Integer.parseInt(closingTime.split(":")[1])>45){
                            indexToCloseAt++;
                        }
                        for (int count = indexToOpenAt; count<=indexToCloseAt; count++) {
                            openingHours[i][count] = true;
                        }
                    }
                }
                catch(Exception e) {
                }
            }
        }
    }
    void draw() {
        canvas.fill(0);
        drawAxes();
        drawXLabels();
        drawYLabels();
        for (int dayCount = 0; dayCount<openingHours.length; dayCount++) {
            for (int hourCount = 0; hourCount<openingHours[dayCount].length; hourCount++) {
                boolean isOpen = openingHours[dayCount][hourCount];
                if (isOpen) { // draw full boxes if the business is open during the specific half-hour
                    canvas.fill(65, 244, 169);
                } else {
                    canvas.fill(255, 0, 0);
                }
                canvas.stroke(0);
                canvas.rect(graphX+hourCount*BOX_WIDTH, graphY+dayCount*BOX_HEIGHT+BOX_HEIGHT, BOX_WIDTH, BOX_HEIGHT);
            }
        }
    }

    void drawAxes() {
        canvas.line(graphX, graphY+BOX_HEIGHT, graphX+HALF_HOURS_PER_DAY*BOX_WIDTH, graphY+BOX_HEIGHT);
        canvas.line(graphX, graphY+BOX_HEIGHT, graphX, graphY + DAYS_PER_WEEK*BOX_HEIGHT+BOX_HEIGHT);
        String title = "Opening Hours:";
        canvas.text(title, graphX+(HALF_HOURS_PER_DAY*BOX_WIDTH)/2-canvas.textWidth(title)/2, graphY);
    }

    void drawYLabels() {
        canvas.text("Mon", graphX-canvas.textWidth("Mon")-2, graphY+2*BOX_HEIGHT);
        canvas.text("Tue", graphX-canvas.textWidth("Tue")-2, graphY+3*BOX_HEIGHT);
        canvas.text("Wed", graphX-canvas.textWidth("Wed")-2, graphY+4*BOX_HEIGHT);
        canvas.text("Thu", graphX-canvas.textWidth("Thu")-2, graphY+5*BOX_HEIGHT);
        canvas.text("Fri", graphX-canvas.textWidth("Fri")-2, graphY+6*BOX_HEIGHT);
        canvas.text("Sat", graphX-canvas.textWidth("Sat")-2, graphY+7*BOX_HEIGHT);
        canvas.text("Sun", graphX-canvas.textWidth("Sun")-2, graphY+8*BOX_HEIGHT);
    }

    void drawXLabels() {
        for (int hour = 0; hour<24; hour++) {
            String time = "" + hour + ":00";
            canvas.text(time, graphX+BOX_WIDTH+2*BOX_WIDTH*hour-canvas.textWidth(time)/2, graphY+BOX_HEIGHT-2);
        }
    }

    boolean hasBusinessHours() {
        for (int day=0; day<openingHours.length; day++)
            for (int halfHour=0; halfHour<openingHours[day].length; halfHour++)
                if (openingHours[day][halfHour]==true)  // This means that in at least one day there are business hours for this business
                    return true;
        return false;
    }
}
