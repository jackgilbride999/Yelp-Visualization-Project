class Widget {
  private int x, y, width, height;
  private String label; 
  private int event;
  private color widgetColor, labelColor, borderColor;
  private PFont widgetFont;
  boolean hasText;

  Widget() {
  }

  Widget(int x, int y, int width, int height, String label, 
    color widgetColor, PFont widgetFont, int event) {
    this(x, y, width, height, widgetColor, event);
    this.label=label; 
    this.widgetFont=widgetFont;
    labelColor= color(0);
    hasText=true;
  }

  Widget(int x, int y, int width, int height, 
    color widgetColor, int event) {
    this.x=x; 
    this.y=y; 
    this.width = width; 
    this.height= height;
    this.event=event; 
    this.widgetColor=widgetColor; 
    borderColor=color(0);
    hasText=false;
  }
  void draw() {
    stroke(borderColor);
    fill(widgetColor);
    rect(x, y, width, height);
    if (hasText) {
      fill(labelColor);
      text(label, x+10, y+height-10);
    }
  }
  int getEvent(int mX, int mY) {
    if (mX>x && mX < x+width && mY >y && mY <y+height) {
      return event;
    }
    return 0;
  }

  int getX() {
    return x;
  }

  int getY() {
    return y;
  }

  int getHeight() {
    return height;
  }

  int getWidth() {
    return width;
  }

  void setX(int x) {
    this.x=x;
  }

  void setY(int y) {
    this.y=y;
  }

  void setHeight(int height) {
    this.height=height;
  }

  void setWidth(int width) {
    this.width=width;
  }

  void setBorderColor(color colorToSet) {
    borderColor = colorToSet;
  }
}

class Scrollbar extends Widget {

  Scrollbar(int x, int y, int width, int totalHeightOfPage, 
    color widgetColor, int event) {
    super(x,y,width,0,widgetColor,event); 
    int ratio = totalHeightOfPage/SCREEN_X;
    setHeight(SCREEN_X/ratio);
  }
}