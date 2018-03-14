import controlP5.*;

class Screen {
  ArrayList screenWidgets;
  color screenColor;
  int previousMouseY;            // added for scrollbar
  int mouseDifference;           // scrollbar
  boolean scrollbarPressed;      //    ""
  int scrollOffsetFromTop;       //    ""
  int totalHeight;               //    ""
  int ratio;                     //    ""
  
  Screen(color in_color) {
    screenWidgets=new ArrayList();
    screenColor=in_color;
  }
  void add(Widget w) {
    screenWidgets.add(w);
  }

  void draw() {
    background(screenColor);
    
    for (int i = 0; i<screenWidgets.size(); i++) {
      Widget aWidget = (Widget)screenWidgets.get(i);
      aWidget.draw(ratio*scrollOffsetFromTop);
    }
    previousMouseY=mouseY;
    Scrollbar scroll = (Scrollbar)screenWidgets.get(0);
    ratio = scroll.getRatio();
    scrollOffsetFromTop= scroll.getY();  
  } 

  int getEvent(int mx, int my) {          
    for (int i = 0; i<screenWidgets.size(); i++) {
      Widget aWidget = (Widget) screenWidgets.get(i);
      int event = aWidget.getEvent(mouseX, mouseY);
      if (event != EVENT_NULL) {
        return event;
      }
    }
    return EVENT_NULL;
  }

  void highlightButtons() {    // added this in, better checking just the widgets on a screen than every button in the program
    int event;
    for (int i = 0; i<screenWidgets.size(); i++) {
      Widget aWidget = (Widget) screenWidgets.get(i);
      event = aWidget.getEvent(mouseX, mouseY);
      if (event != EVENT_NULL) {
        aWidget.mouseOver();
      } else
        aWidget.mouseNotOver();
    }
  }

  ArrayList getWidgets() {    // added this method as it was needed or the code in the slides
    return screenWidgets;
  }

  void pressScrollbar() {      // added this method for the scrollbar
    Scrollbar scrollbar = (Scrollbar)screenWidgets.get(0);
    if (scrollbar.getEvent(mouseX, mouseY)==SCROLLBAR_EVENT) {
      scrollbarPressed=true;
      mouseDifference=mouseY-scrollbar.getY();
    }
  }

  void releaseScrollbar() {    // added this method for the scrollbar
    scrollbarPressed=false;
  }

  void dragScrollbar() {      // added this method for the scrollbar
    Scrollbar scrollbar = (Scrollbar)screenWidgets.get(0);
    if (scrollbarPressed==true) {
      scrollbar.setY(mouseY-mouseDifference);
      if (scrollbar.getY()<0)
        scrollbar.setY(0);
      else if (scrollbar.getY()+scrollbar.getHeight()>SCREEN_Y)
        scrollbar.setY(SCREEN_Y-scrollbar.getHeight());
    }
  }
}