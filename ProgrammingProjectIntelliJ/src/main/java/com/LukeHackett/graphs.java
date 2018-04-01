package com.LukeHackett;

import org.gicentre.utils.stat.*;
import processing.core.PApplet;

import java.util.*;

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

    public StarLineChart(PApplet canvas, HashMap<Integer, Float> inputList, String name) {
        this.canvas = canvas;
        starChart = new XYChart(canvas);

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
        canvas.textSize(15);
        canvas.text(name, xPos, yPos + 15);
        canvas.textSize(15);
        canvas.text("Change in rating over time.", xPos, yPos + 30);
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
        barChart2.setBarColour(canvas.color(255));//175, 255, 248));
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
        canvas.textSize(15);
        canvas.text(name, xPos, yPos + 15);
        canvas.textSize(15);
        canvas.text("check-in statistics.", xPos, yPos + 30);
        //canvas.textSize(12);
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

    public BusinessHoursChart(PApplet canvas, String[] hoursArray, String name) {
        this.name=name;
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
        drawAxes(graphX, graphY);
        drawXLabels(graphX, graphY);
        drawYLabels(graphX, graphY);
        for (int dayCount = 0; dayCount<openingHours.length; dayCount++) {
            for (int hourCount = 0; hourCount<openingHours[dayCount].length; hourCount++) {
                boolean isOpen = openingHours[dayCount][hourCount];
                if (isOpen) { // draw full boxes if the business is open during the specific half-hour
                    canvas.fill(65, 244, 169);
                } else {
                    canvas.fill(255, 0, 0);
                }
                canvas.stroke(0);
                canvas.rect(graphX+hourCount* boxWidth, graphY+dayCount* boxHeight + 2*boxHeight, boxWidth, boxHeight);
            }
        }
    }

    void drawAxes(float graphX, float graphY) {
        canvas.line(graphX, graphY+ boxHeight, graphX+HALF_HOURS_PER_DAY* boxWidth, graphY+ boxHeight);
        canvas.line(graphX, graphY+ boxHeight, graphX, graphY + DAYS_PER_WEEK* boxHeight + boxHeight);
        String title = "Opening Hours:";
        canvas.text(title, graphX+(HALF_HOURS_PER_DAY* boxWidth)/2-canvas.textWidth(title)/2, graphY+boxHeight);
    }

    void drawYLabels(float graphX, float graphY) {
        canvas.text("Mo", graphX-canvas.textWidth("Mo"), graphY+3* boxHeight);
        canvas.text("Tu", graphX-canvas.textWidth("Tu"), graphY+4* boxHeight);
        canvas.text("We", graphX-canvas.textWidth("We"), graphY+5* boxHeight);
        canvas.text("Th", graphX-canvas.textWidth("Th"), graphY+6* boxHeight);
        canvas.text("Fr", graphX-canvas.textWidth("Fr"), graphY+7* boxHeight);
        canvas.text("Sa", graphX-canvas.textWidth("Sa"), graphY+8* boxHeight);
        canvas.text("Su", graphX-canvas.textWidth("Su"), graphY+9* boxHeight);
    }

    void drawXLabels(float graphX, float graphY) {
        for (int hour = 0; hour<24; hour+=2) {
            String time = "" + hour + ":00";
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
}
