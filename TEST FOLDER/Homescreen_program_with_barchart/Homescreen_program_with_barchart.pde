
import org.gicentre.utils.*;

import org.gicentre.utils.stat.*;
 BarChart barChart;
ArrayList<Float> visitorsList = new ArrayList<Float>();
float max =0;

// pass in arrayList from query function a graph is drawn

final int SCREEN_X= 1244;
final int SCREEN_Y=700;
final int EVENT_NULL=0;
final int SCROLLBAR_EVENT=1;
final int SEARCHBAR_EVENT=2; 
final int TEST_EVENT1 = 3;
final int TEST_EVENT2 = 4;
final int TEST_EVENT3 = 5;
final int TEST_EVENT4 = 6;
Screen homeScreen;
Screen currentScreen;
Widget test1, test2, test3, test4;
PFont searchFont;
void settings() {
  size(SCREEN_X, SCREEN_Y);
}

void setup() {
  
  
  
  
  
  
 
  for(int i =0; i < 7; i++) {
  visitorsList.add(random(10,20));
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
  
  
  homeScreen = new Screen(color(255)); // setup new white Homescreen
  int searchbarHeight=40;
  int searchbarWidth=3*SCREEN_Y/4;
  searchFont = loadFont("ArialMT-30.vlw");
  Scrollbar scrollbar = new Scrollbar(10, SCREEN_Y, color(150), SCROLLBAR_EVENT); // just adding a scrollbar incase we need it, by default it will not scroll and will just take up side of screen
  // the best way I can get the scrollbar to work is to know its index in a screen's widgetList, so for now just always add a scrollbar to any screen as the first element
  TextWidget searchbar = new TextWidget(SCREEN_X/2-searchbarWidth/2, 20, searchbarWidth, searchbarHeight, "Enter the name of a buisiness...", color(200), searchFont, SEARCHBAR_EVENT, 28);
  test1 = new Widget(SCREEN_X/4-25, SCREEN_Y/2-25, 50, 50, "test 1", color(255, 0, 0), searchFont, TEST_EVENT1);
  test2 = new Widget(3*SCREEN_X/4-25, SCREEN_Y/2-25, 50, 50, "test 2", color(0, 255, 0), searchFont, TEST_EVENT2);
  test3 = new Widget(SCREEN_X/4-25, 3*SCREEN_Y/4-25, 50, 50, "test 3", color(0, 0, 255), searchFont, TEST_EVENT3);
  test4 = new Widget(3*SCREEN_X/4-25, 3*SCREEN_Y/4-25, 50, 50, "test 4", color(255, 255, 0), searchFont, TEST_EVENT4);
  homeScreen.add(scrollbar);
  homeScreen.add(searchbar);
  homeScreen.add(test1);
  homeScreen.add(test2);
  homeScreen.add(test3);
  homeScreen.add(test4);
  currentScreen = homeScreen; // when the program starts, the homeScreen will be the current one
}

void draw() {
  currentScreen.draw();
  
  // bar chart  
  barChart.draw(15,5,width-1000,height-500); 
  
  
  //
  
   fill(120);
  textSize(20);
  text("Costa Coffee.", 70,20);
  textSize(15);
  text("visitors per day",70,40);
}

void mousePressed() {                            
  int event = currentScreen.getEvent(mouseX, mouseY);
  if (event==SCROLLBAR_EVENT)
    currentScreen.pressScrollbar();
  else
    currentScreen.releaseScrollbar();

  if (event==SEARCHBAR_EVENT)
    currentScreen.focus((TextWidget)currentScreen.getWidgets().get(1));
  else
    currentScreen.focus(null);
  switch(event) {
  case TEST_EVENT1: 
    println("test button 1 pressed!");
    break;
  case TEST_EVENT2: 
    println("test button 2 pressed!");
    break;
  case TEST_EVENT3: 
    println("test button 3 pressed!");
    break;
  case TEST_EVENT4: 
    println("test button 4 pressed!");
    break;
  default:
  }
}

void mouseMoved() {
  currentScreen.highlightButtons();
}  


void mouseReleased() {
  currentScreen.releaseScrollbar();
}

void mouseDragged() {
  currentScreen.dragScrollbar();
}

void keyPressed() {
  if (currentScreen.getFocus() != null) {
    currentScreen.getFocus().append(key);
  }
}