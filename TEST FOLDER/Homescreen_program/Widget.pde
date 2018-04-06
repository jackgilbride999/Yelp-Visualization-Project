class Widget {
  int x, y, width, height;
  String label; 
  int event;
  color widgetColor, labelColor, borderColor;
  PFont widgetFont;
  Widget(int x, int y, int width, int height, String label, 
    color widgetColor, PFont widgetFont, int event) {
    this.x=x; 
    this.y=y; 
    this.width = width; 
    this.height= height;
    this.label=label; 
    this.event=event;
    this.widgetColor=widgetColor; 
    this.widgetFont=widgetFont;
    labelColor= color(0);
    borderColor=color(0);
  }
  void draw(int subFromY) {
    stroke(borderColor);
    fill(widgetColor);
    rect(x, y-subFromY, width, height);
    fill(labelColor);
    text(label, x+10, y+height-10-subFromY);
  }
  int getEvent(int mX, int mY) {
    if (mX>x && mX < x+width && mY >y && mY <y+height) {
      return event;
    }
    return EVENT_NULL;
  }
  
  void mouseOver(){      // added these two methods for the sample code from slides to work
    borderColor = color(255);
  }
  
  void mouseNotOver(){
    borderColor = color(0);
  }
  
}

class Scrollbar extends Widget {
  int ratio;
  Scrollbar(int width, int totalHeightOfPage, 
    color widgetColor, int event) {
    super(SCREEN_X-width, 0, width, 0, "", widgetColor, null, event); // again the super constructor for the class is called to stop code duplication
    this.ratio = totalHeightOfPage/SCREEN_Y;
    setHeight(SCREEN_Y/ratio);
  }
  
  public int getRatio(){
    return this.ratio;
  }
  
  public void setHeight(int height){
    this.height = height;
  }
  
  public int getY(){
    return y;
  }
  
  public void setY(int y){
    this.y = y;
  }
  
  public int getHeight(){
    return height;
  }
  
  public void draw(int subFromY){ // method overriding draw so the scrollbar doesn't "drag itself"
   super.draw(0);
  }
}