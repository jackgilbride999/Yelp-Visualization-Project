import org.gicentre.utils.spatial.*;
import org.gicentre.utils.network.*;
import org.gicentre.utils.network.traer.physics.*;
import org.gicentre.utils.geom.*;
import org.gicentre.utils.move.*;
import org.gicentre.utils.stat.*;
import org.gicentre.utils.gui.*;
import org.gicentre.utils.colour.*;
import org.gicentre.utils.text.*;
import org.gicentre.utils.*;
import org.gicentre.utils.network.traer.animation.*;
import org.gicentre.utils.io.*;

import org.gicentre.utils.stat.*;
 BarChart barChart;

void setup()

{

  size(400,300);
  
  barChart = new BarChart(this);
  float[] visitors = new float[] {20, 14,22,26,30,22,19};
  float max =0;
  for (int counter = 1; counter < visitors.length; counter++)
{
     if (visitors[counter] > max)
     {
      max = visitors[counter];
     }
}
  barChart.setData(visitors);
  
  
     
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
  text("Visitors to costa coffee", 70,30);
  textSize(15);
  text("throughout the week", 
        70,45);
}