import org.gicentre.utils.*;

import org.gicentre.utils.stat.*;
 BarChart barChart;
ArrayList<Float> visitorsList = new ArrayList<Float>();
float max =0;

// pass in arrayList from query function a graph is drawn
void setup()

{

  size(400,300);
  for(int i =0; i < 7; i++) {
  visitorsList.add(random(20));
  }
  
  
  barChart = new BarChart(this);


float[] visitorsArray = new float[visitorsList.size()];
int i = 0;

for (Float f : visitorsList) {
    visitorsArray[i++] = (f != null ? f : Float.NaN); // Or whatever default you want.
}
  float max =0;
  for (int counter = 1; counter < visitorsArray.length; counter++)
{
     if (visitorsArray[counter] > max)
     {
      max = visitorsArray[counter];
     }
}
  barChart.setData(visitorsArray);
  
  
     
  // Scaling
  barChart.setMinValue(0);
  barChart.setMaxValue(max+4);
   
  // Axis appearance
  textFont(createFont("Serif",15),15);
   
  barChart.showValueAxis(true);
  barChart.setValueFormat("");
  barChart.setBarLabels(new String[] {"Mon","Tues","Wed",
                                       "Thurs","Fri","Sat","Sun"});
  barChart.showCategoryAxis(true);
 
  // Bar colours and appearance
  barChart.setBarColour(color(200,80,80,150));
  barChart.setBarGap(10);
   
  // Bar layout
  barChart.transposeAxes(false);
}

// Draws the chart in the sketch
void draw()
{
  background(255);
  barChart.draw(15,15,width-30,height-30); 
  
  
  //
  
   fill(120);
  textSize(20);
  text("Costa Coffee.", 70,30);
  textSize(15);
}