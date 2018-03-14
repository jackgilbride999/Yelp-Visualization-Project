import org.gicentre.utils.*;

import org.gicentre.utils.stat.*;

CheckinsBarChart chart;

// pass in arrayList from query function a graph is drawn
void settings() {
size(400,300);
}
void setup()
{
  db = new DbAccess();
  //println(this.getClass());
  
  ArrayList<BusinessNameId> list = db.getTop10BusinessIdListByCity("Las Vegas");
  for (int i=0; i<list.size(); i++)
    println(list.get(i).name);
  
  String name = "Subway";
  String id = db.getBusinessIdByName(name);
  println(id);
  ArrayList<Float> visitorsList = db.getBusinessCheckins(id);
  

  
  chart = new CheckinsBarChart(visitorsList, name, this);
  // sets up the bar chart
  // just not to make a mess of the setup
  // we can have these types of calls anywhere we need to have barcharts and graphs

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
    barChart.draw(15,15,width-30,height-30);
    fill(120);
    textSize(20);
    text(name, 70,30);
    textSize(15);
    
  }
  
}

void draw()
{
  background(255);
  chart.draw();
}