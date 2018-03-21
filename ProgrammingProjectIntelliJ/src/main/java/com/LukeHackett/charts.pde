import org.gicentre.utils.*;

import org.gicentre.utils.stat.*;

CheckinsBarChart chart;
StarBarChart chart2;

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



class StarBarChart{
  private BarChart barChart2;
  private float[] starsArray;
  private String name;
  float max;
  float avg;
  float sum;
  public StarBarChart(ArrayList<Float> inputList,String name, dbAccess_Class sketch){
        barChart2 = new BarChart(sketch);
        starsArray = new float[inputList.size()];
        max = 0;
        for (int i=0; i< inputList.size(); i++){
 
           starsArray[i] = inputList.get(i);
           if ( max < starsArray[i])
               max = starsArray[i];
        }
        this.name = name;
        
        for(int i=0; i < starsArray.length;i++) {
          sum += starsArray[i];
        }
        avg = sum/12;
        // Draws the chart in the sketch        
        barChart2.setData(starsArray);
        // Scaling
          
  // Scaling
  barChart2.setMinValue(0);
  barChart2.setMaxValue(max+2);
   
  // Axis appearance
  textFont(createFont("Serif",15),15);
  
  barChart2.showValueAxis(true);
  barChart2.setValueFormat("");
  barChart2.setBarLabels(new String[] {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sept","Oct","Nov","Dec"});
  barChart2.showCategoryAxis(true);
 
  // Bar colours and appearance
  barChart2.setBarColour(color(128, 179, 255));
  barChart2.setBarGap(10);
   
  // Bar layout
  barChart2.transposeAxes(false);
}

// Draws the chart in the sketch
void draw()
{
  background(255);
  barChart2.draw(600,30,width-1200,height-700); 
  
  
  //
  
   fill(120);
  textSize(20);
  text("Average Star Rating for "+name, 600,20);
  textSize(15);
  text("Change over time",600,40);
  text("Average rating over year: "+String.format("%.2f", avg) ,730,40);
}
  
}



class CheckinsBarChart{
  private BarChart barChart;
  private float[] visitorsArray;
  private String name;
  private float max;
  public CheckinsBarChart(ArrayList<Float> inputList,String name, dbAccess_Class sketch){
        barChart = new BarChart(sketch);
        visitorsArray = new float[inputList.size()];
        max = 0;
        for (int i=0; i< inputList.size(); i++){
 
           visitorsArray[i] = inputList.get(i);
           if ( max < visitorsArray[i])
               max = visitorsArray[i];
        }
        this.name = name;
        // Draws the chart in the sketch        
        barChart.setData(visitorsArray);
        // Scaling
        barChart.setMinValue(0);
        barChart.setMaxValue(max+2);
         
        // Axis appearance
        textFont(createFont("Serif",15),15);
         
        barChart.showValueAxis(true);
        barChart.setValueFormat("");
        barChart.setBarLabels(new String[] {"Mon","Tue","Wed", "Thu","Fri","Sat","Sun"});
        barChart.showCategoryAxis(true);
       
        // Bar colours and appearance
        barChart.setBarColour(color(200,80,80,150));
        barChart.setBarGap(10);
         
        // Bar layout
        barChart.transposeAxes(false);              
  }
  void draw(){
    // bar chart can be called, by barChart.draw(xpos,ypos,width,height);  
    barChart.draw(15,30,width-1300,height-700);
    fill(120);
    textSize(20);
    text(name, 30,20);
    textSize(15);
    
  }
  
}

void draw()
{
  chart2.draw();
  chart.draw();
}