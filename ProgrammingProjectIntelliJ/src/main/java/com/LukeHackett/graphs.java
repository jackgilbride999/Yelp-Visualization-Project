package com.LukeHackett;

import org.gicentre.utils.stat.*;
import processing.core.PApplet;

import java.util.ArrayList;

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

class StarBarChart {
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
        canvas.textFont(canvas.createFont("Serif", 15), 15);

        barChart2.showValueAxis(true);
        barChart2.setValueFormat("");
        barChart2.setBarLabels(new String[]{"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sept", "Oct", "Nov", "Dec",});
        barChart2.showCategoryAxis(true);

        // Bar colours and appearance
        barChart2.setBarColour(canvas.color(65, 244, 169));
        barChart2.setBarGap(5);

        // Bar layout
        barChart2.transposeAxes(true);
    }

    void draw() {
        // bar chart can be called, by barChart.draw(xpos,ypos,width,height);

        barChart2.draw(1010, 300, canvas.width - 1000, canvas.height - 600);
        canvas.fill(80);
        canvas.textSize(15);
        canvas.text(name, 1010, 280);
        canvas.textSize(15);
        canvas.text("Change in rating over time.", 1010, 300);



    }
}



class CheckinsBarChart {
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
        canvas.textFont(canvas.createFont("Serif", 15), 15);

        barChart.showValueAxis(true);
        barChart.setValueFormat("");
        barChart.setBarLabels(new String[]{"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"});
        barChart.showCategoryAxis(true);

        // Bar colours and appearance
        barChart.setBarColour(canvas.color(65, 244, 169));
        barChart.setBarGap(10);

        // Bar layout
        barChart.transposeAxes(false);
    }

    void draw() {
        // bar chart can be called, by barChart.draw(xpos,ypos,width,height);

            barChart.draw(1010, 60, canvas.width - 1000, canvas.height - 600);
            canvas.fill(80);
            canvas.textSize(15);
            canvas.text(name, 1010, 40);
            canvas.textSize(15);
            canvas.text("check-in statistics.", 1010, 60);



    }
}
/*
    void draw() {
        chart2.draw();
        chart.draw();
    }

*/