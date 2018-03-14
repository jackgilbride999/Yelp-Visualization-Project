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
  homeScreen = new Screen(color(255)); // setup new white Homescreen
  int searchbarHeight=40;
  int searchbarWidth=3*SCREEN_Y/4;
  searchFont = loadFont("ArialMT-30.vlw");
  Scrollbar scrollbar = new Scrollbar(10, SCREEN_Y, color(150), SCROLLBAR_EVENT); // just adding a scrollbar incase we need it, by default it will not scroll and will just take up side of screen //<>//
  // the best way I can get the scrollbar to work is to know its index in a screen's widgetList, so for now just always add a scrollbar to any screen as the first element
  TextWidget searchbar = new TextWidget(SCREEN_X/2-searchbarWidth/2, 20, searchbarWidth, searchbarHeight, "Enter the name of a buisiness...", color(200), searchFont, SEARCHBAR_EVENT, 2);
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