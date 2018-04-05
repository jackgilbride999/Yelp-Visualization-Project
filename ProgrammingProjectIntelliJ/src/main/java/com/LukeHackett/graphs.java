package com.LukeHackett;

import org.gicentre.utils.stat.*;
import processing.core.PApplet;

import java.util.*;

import static javafx.scene.paint.Color.color;

interface Graph {
    String getName();
    void setName(String name);
    void draw(float xPos, float yPos, float xSize, float ySize);
    int type();
}

class StarLineChart implements Graph {
    /*
     |
  n  |              *
  u  |
  m  |   *
     |
  r  |         *         *
     |
     ------------------------------
                month
     */
    private XYChart starChart;
    private float[] starArray;
    private String name;
    private float max;
    private PApplet canvas;
    private int type;

    public StarLineChart(PApplet canvas, HashMap<Integer, Float> inputList, String name) {
        this.canvas = canvas;
        starChart = new XYChart(canvas);
        this.type = Main.STARS_CHART;

        starArray = new float[12];
        for(int i = 0; i < 12; i++){
            if(inputList.containsKey(i+1)){
                starArray[i] = inputList.get(i+1);
            }
            else{
                starArray[i] = 0;
            }
        }

        float[] coords = {1,2,3,4,5,6,7,8,9,10,11,12};
        starChart.setData(coords,starArray);
        this.name = name;
        starChart.showXAxis(true);
        starChart.showYAxis(true);
        starChart.setLineColour(0);
        starChart.calcDataSpacing();

        starChart.setPointColour(0);
        starChart.setMinX(1);
        starChart.setPointSize(5);
        starChart.setLineWidth(2);

        starChart.setLineColour(255);
        starChart.setAxisLabelColour(255);
        starChart.setAxisColour(255);
        starChart.setAxisValuesColour(255);
        starChart.showYAxis(true);
        starChart.setPointColour(0);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void draw(float xPos, float yPos, float xSize, float ySize) {
        canvas.fill(255);
        starChart.draw(xPos, yPos + 35, xSize, ySize);
        canvas.textFont(Main.graphFont);
        canvas.text(name, xPos+xSize/2-canvas.textWidth(name)/2, yPos + 15);
        canvas.textFont(Main.graphFont);
        canvas.text("Change in Rating Over Time:", xPos+xSize/2-canvas.textWidth("Change in Rating Over Time:")/2, yPos + 30);
    }

    public int type(){
        return type;
    }
}

class CheckinsBarChart implements Graph {
    private BarChart barChart;
    private float[] visitorsArray;
    private String name;
    private float max;
    private PApplet canvas;
    private int type;

    public CheckinsBarChart(PApplet canvas, ArrayList<Float> inputList, String name) {
        this.canvas = canvas;
        this.type = Main.CHECKIN_CHART;
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
        barChart.setBarLabels(new String[]{"Mon", "Tues", "Wed", "Thurs", "Fri", "Sat", "Sun"});
        barChart.showCategoryAxis(true);
        barChart.setAxisValuesColour(canvas.color(255,255,255));

        // Bar colours and appearance
        barChart.setBarColour(canvas.color(255));//175, 255, 248));
        barChart.setBarGap(2);

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
        canvas.textFont(Main.graphFont);
        canvas.text(name, xPos+xSize/2-canvas.textWidth(name)/2, yPos + 15);
        canvas.textFont(Main.graphFont);
        canvas.text("Check-in Statistics:", xPos+xSize/2-canvas.textWidth("Check-in Statistics:")/2, yPos + 30);
        //canvas.textSize(12);
    }

    public int type(){
        return type;
    }
}

class BusinessHoursChart implements Graph{
    private PApplet canvas;
    private boolean[][] openingHours;
    private static final int DAYS_PER_WEEK = 7;
    private static final int HALF_HOURS_PER_DAY = 48;
    private int boxWidth;
    private int boxHeight;
    private String name;
    private int type;

    public BusinessHoursChart(PApplet canvas, String[] hoursArray, String name) {
        this.name=name;
        this.type = Main.HOURS_CHART;
        this.canvas=canvas;
        openingHours = new boolean[DAYS_PER_WEEK][HALF_HOURS_PER_DAY];
        if (hoursArray!=null) {
            for (int i=0; i<hoursArray.length; i++) {
                try {
                    String hoursOpen = hoursArray[i];
                    if (!hoursOpen.equals("None")) {
                        String[] hours = hoursOpen.split("-");                             // opening time will be at index  0,  closing time at index  1
                        String openingTime = hours[0];                                     // if the business opens at 7:XX, the first square filled will be 7:00-7:30
                        int indexToOpenAt = 2*Integer.parseInt(openingTime.split(":")[0]); //eg if the business opens at 7:00, will start indexing at index 14 as we are working with half hours
                        if (Integer.parseInt(openingTime.split(":")[1])>15) {              // but if it opens at 7:30 we will start indexing at index 15 (will round up from 7:16 onwards)
                            indexToOpenAt++;
                        }
                        if (Integer.parseInt(openingTime.split(":")[1])>45){
                            indexToOpenAt++;                                                 // if the business opens at 7:46, graph will round to 8:00
                        }
                        String closingTime = hours[1];                                     // if the business closes at 7:XX, the last square filled will be 7:00-7:30
                        int indexToCloseAt = 2*Integer.parseInt(closingTime.split(":")[0]);
                        if (Integer.parseInt(closingTime.split(":")[1])<=15) {             // if the business closes at 7:00 or 7:10, the last rect filled will be the 6:30-7:00 square
                            indexToCloseAt--;
                        }
                        else if(Integer.parseInt(closingTime.split(":")[1])>45){           // if the business closes at 7:46, the last filled rect will be the 7:30-8:00 square
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
        if(!this.hasBusinessHours())
            throw new NullPointerException();
    }
    public void draw(float graphX, float graphY, float width, float height) {
        graphX+=15;
        this.boxWidth=(int)(width-2)/HALF_HOURS_PER_DAY;
        this.boxHeight=(int)(height-2)/DAYS_PER_WEEK;
        canvas.fill(255);
        drawXLabels(graphX, graphY);
        drawYLabels(graphX, graphY);
        for (int dayCount = 0; dayCount<openingHours.length; dayCount++) {
            for (int hourCount = 0; hourCount<openingHours[dayCount].length; hourCount++) {
                boolean isOpen = openingHours[dayCount][hourCount];
                canvas.noStroke();
                if (isOpen) { // draw full boxes if the business is open during the specific half-hour
                    canvas.fill(255);
                } else {
                    canvas.fill(0, 140, 130);
                }
                canvas.rect(graphX+hourCount* boxWidth, graphY+dayCount* boxHeight + 2*boxHeight, boxWidth, boxHeight);
                if(hourCount%2==1){
                    canvas.stroke(0, 80);
                    canvas.noFill();
                    canvas.rect(graphX+hourCount* boxWidth-boxWidth, graphY+dayCount* boxHeight + 2*boxHeight, 2*boxWidth, boxHeight);
                }
            }
        }
    }


    void drawYLabels(float graphX, float graphY) {
        canvas.textFont(Main.graphFontSmall);
        canvas.text("Mo", graphX-canvas.textWidth("Mo"), graphY+3* boxHeight);
        canvas.text("Tu", graphX-canvas.textWidth("Tu"), graphY+4* boxHeight);
        canvas.text("We", graphX-canvas.textWidth("We"), graphY+5* boxHeight);
        canvas.text("Th", graphX-canvas.textWidth("Th"), graphY+6* boxHeight);
        canvas.text("Fr", graphX-canvas.textWidth("Fr"), graphY+7* boxHeight);
        canvas.text("Sa", graphX-canvas.textWidth("Sa"), graphY+8* boxHeight);
        canvas.text("Su", graphX-canvas.textWidth("Su"), graphY+9* boxHeight);
    }

    void drawXLabels(float graphX, float graphY) {
        canvas.textFont(Main.graphFontSmall);
        canvas.text(name, graphX+(HALF_HOURS_PER_DAY* boxWidth)/2-canvas.textWidth(name)/2, graphY+boxHeight);
        for (int hour = 0; hour<24; hour++) {
            String time;
            if(hour==0 || hour==12)
               time = "12";
            else
                time = "" + hour;
            canvas.text(time, graphX+ boxWidth +2* boxWidth *hour-canvas.textWidth(time)/2, graphY+ 2*boxHeight -2);
        }
    }

    boolean hasBusinessHours() {
        for (int day=0; day<openingHours.length; day++)
            for (int halfHour=0; halfHour<openingHours[day].length; halfHour++)
                if (openingHours[day][halfHour]==true)  // This means that in at least one day there are business hours for this business
                    return true;
        return false;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int type(){
        return type;
    }
}
