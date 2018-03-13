
import controlP5.*;
import org.gicentre.utils.stat.*;
 BarChart barChart;
ArrayList<Float> starRatings = new ArrayList<Float>();
float max =0;
float sum=0;
ControlP5 cp5;

// pass in arrayList from query function a graph is drawn
void setup()

{  
    
  size(400,300);
  /*
    cp5 = new ControlP5(this);
    cp5.addButton("")
     .setValue(0)
     .setPosition(20,270)
     .setSize(20,20)
     ;
     */

  
 
  for(int i =0; i < 12; i++) {
  float rating = random(3,5);
  starRatings.add(rating);
  sum  += rating;
  }
  sum = sum/starRatings.size();
  
  barChart = new BarChart(this);


float[] visitorsArray = new float[starRatings.size()];
int i = 0;

for (Float f : starRatings) {
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
  barChart.setMaxValue(max+2);
   
  // Axis appearance
  textFont(createFont("Serif",15),15);
   
  barChart.showValueAxis(true);
  barChart.setValueFormat("");
  barChart.setBarLabels(new String[] {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sept","Oct","Nov","Dec"});
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
  text("Average Star Rating for Costa Coffee", 70,30);
  textSize(15);
  text("Change over 2017",70,50);
  text("Average rating over year: "+String.format("%.2f", sum) ,200,50);
}