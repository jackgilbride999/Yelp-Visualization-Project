final int SCREEN_X= 1244;
final int SCREEN_Y=700;
final int EVENT_NULL=0;
final int SCROLLBAR_EVENT=1;
final int SEARCHBAR_EVENT=2; 
final int TEST_EVENT1 = 3;
final int TEST_EVENT2 = 4;
final int TEST_EVENT3 = 5;
final int TEST_EVENT4 = 6;
final int BORDER_OFFSET_Y = 10;

Screen homeScreen;
Screen currentScreen;
Screen businessScreen;
Widget test1, test2, test3, test4;

ControlP5 cp5;
Textfield searchBar;
ArrayList<Business> businessList;
Button businessButton;
ScrollableList searchOptions;
int selected = 0;
int yOffset;

queries q;

PFont searchFont;
void settings() {
  size(SCREEN_X, SCREEN_Y);
}

void setup() {
  q = new queries();
  cp5 = new ControlP5(this);
  searchFont = loadFont("CenturyGothic-24.vlw");



  //Control P5 setup
  int searchbarHeight=40;
  int searchbarWidth =3*(SCREEN_X/4);
  println(searchbarWidth);

  searchBar = cp5.addTextfield("searchBar")
    .setColorBackground(color(255, 255, 255))
    .setPosition(SCREEN_X/2-searchbarWidth/2, 20)
    .setSize(searchbarWidth-200, searchbarHeight)
    .setFont(searchFont)
    .setFocus(false)
    .setColor(color(0, 0, 0))
    .setColorCursor(color(0, 0, 0))
    .setColor(color(0, 0, 0))
    .setColorActive(color(0, 0, 0))
    ;

  searchOptions = cp5.addScrollableList("Options")
    .addItem("By Category", 0)
    .addItem("By Name", 1)
    .addItem("By City", 2)
    .setFont(searchFont)
    .setColorBackground(color(230, 0, 0))
    .setColorForeground(color(200, 0, 0))
    .setColorActive(color(170, 0, 0))
    .setMouseOver(false)
    .setOpen(false)
    .setHeight(300)
    .setWidth(200)
    .setBarHeight(40)
    .setItemHeight(40)
    .setPosition(SCREEN_X/2+3*(SCREEN_X/4)/2-200, 20);

  Label label = searchOptions.getCaptionLabel();
  label.toUpperCase(false);
  label.getStyle()
    .setPaddingLeft(20)
    .setPaddingTop(10);
  label = searchOptions.getValueLabel();
  label.toUpperCase(false);
  label.getStyle()
    .setPaddingLeft(20)
    .setPaddingTop(10);

  //End Control P5 setup


  homeScreen = new Screen(color(255)); // setup new white Homescreen

  Scrollbar scrollbar = new Scrollbar(10, SCREEN_Y, color(150), SCROLLBAR_EVENT); // just adding a scrollbar incase we need it, by default it will not scroll and will just take up side of screen
  // the best way I can get the scrollbar to work is to know its index in a screen's widgetList, so for now just always add a scrollbar to any screen as the first element
  //TextWidget searchbar = new TextWidget(SCREEN_X/2-searchbarWidth/2, 20, searchbarWidth, searchbarHeight, "Enter the name of a buisiness...", color(200), searchFont, SEARCHBAR_EVENT, 2);
  test1 = new Widget(SCREEN_X/4-25, SCREEN_Y/2-25, 200, 50, "Restaurants", color(255, 0, 0), searchFont, TEST_EVENT1);
  test2 = new Widget(3*SCREEN_X/4-25, SCREEN_Y/2-25, 200, 50, "Nightlife", color(0, 255, 0), searchFont, TEST_EVENT2);
  test3 = new Widget(SCREEN_X/4-25, 3*SCREEN_Y/4-25, 200, 50, "Shopping", color(0, 0, 255), searchFont, TEST_EVENT3);
  test4 = new Widget(3*SCREEN_X/4-25, 3*SCREEN_Y/4-25, 200, 50, "Active Life", color(255, 255, 0), searchFont, TEST_EVENT4);
  homeScreen.add(scrollbar);
  //homeScreen.add(searchbar);
  homeScreen.add(test1);
  homeScreen.add(test2);
  homeScreen.add(test3);
  homeScreen.add(test4);
  currentScreen = homeScreen; // when the program starts, the homeScreen will be the current one

  businessScreen = new Screen(color(255));
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

  switch(event) {
  case TEST_EVENT1: 
    println("Restaurants Searched");
    businessList = q.businessSearch("Restaurant", 0, 10);
    buttonBusinessList(businessList);
    currentScreen.showBusinesses();


    println(businessList);
    break;
  case TEST_EVENT2: 
    println("test button 2 pressed!");
    businessList = q.businessSearch("Nightlife", 0, 10);
    buttonBusinessList(businessList);
    println(businessList);
    break;
  case TEST_EVENT3: 
    println("test button 3 pressed!");
    businessList = q.businessSearch("Shopping", 0, 10);
    buttonBusinessList(businessList);
    println(businessList);
    break;
  case TEST_EVENT4: 
    println("test button 4 pressed!");
    businessList = q.businessSearch("Active Life", 0, 10);
    buttonBusinessList(businessList);
    println(businessList);
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

public void searchBar(String text) {
  if (selected == 0) {
    ArrayList<Business> businessesC = q.categorySearch(text, 0, 10);
    businessList = businessesC;
    buttonBusinessList(businessList);
    if (businessesC.size() != 0) {
      for (Business b : businessesC) {
        println(b);
      }
    } else {
      println("No results");
    }
  } else if (selected == 1) {
    ArrayList<Business> businessesN = q.businessSearch(text, 0, 10);
    businessList = businessesN;
    buttonBusinessList(businessList);
    if (businessesN.size() != 0) {
      for (Business b : businessesN) {
        println(b);
      }
    } else {
      println("No results");
    }
  } else if (selected == 2) {
    ArrayList<Business> businessesL = q.citySearch(text, 0, 10);
    businessList = businessesL;
    buttonBusinessList(businessList);
    if (businessesL.size() != 0) {
      for (Business b : businessesL) {
        println(b);
      }
    } else {
      println("No results");
    }
  }
  
}

public void Options(int n) {
  selected = n;
  println(selected);
}

public void buttonBusinessList(ArrayList<Business> businesses) {
  if (businessList != null)
  {
    for (Business b : businessList)
    {
      cp5.addButton(b.getName())
        .setValue(0)
        .setPosition((float)SCREEN_X/2-500, (float)yOffset + 80)
        .setSize(1000, 50);

      yOffset = yOffset + 50 + BORDER_OFFSET_Y;
    }
    yOffset = 0;
  }
}