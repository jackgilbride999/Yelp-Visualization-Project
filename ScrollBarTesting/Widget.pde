class Widget {
  private int x, y, width, height;
  private String label; 
  private int event;
  private color widgetColor, labelColor, borderColor;
  private PFont widgetFont;
  
  Widget(){
  }

  Widget(int x, int y, int width, int height, String label, 
    color widgetColor, PFont widgetFont, int event) {
    this(x, y, width, height, widgetColor, event);
    this.label=label; 
    this.widgetFont=widgetFont;
    labelColor= BLACK;
  }
  
    Widget(int x, int y, int width, int height, 
    color widgetColor, int event) {
    this.x=x; 
    this.y=y; 
    this.width = width; 
    this.height= height;
    this.event=event; 
    this.widgetColor=widgetColor; 
    borderColor=BLACK;
    this.label="";
  }
  void draw() {
    stroke(borderColor);
    fill(widgetColor);
    rect(x, y, width, height);
    fill(labelColor);
    text(label, x+10, y+height-10);
  }
  int getEvent(int mX, int mY) {
    if (mX>x && mX < x+width && mY >y && mY <y+height) {
      return event;
    }
    return NULL_EVENT;
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
  
  void setX(int x){
    this.x=x;
  }
  
  void setY(int y){
    this.y=y;
  }
  
  void setHeight(int height){
    this.height=height;
  }
  
  void setWidth(int width){
    this.width=width;
  }
  
  void setBorderColor(color colorToSet){
   borderColor = colorToSet; 
  }
}

class ScrollBar extends Widget{
 ScrollBar(){
   super(SCREEN_X-SCROLLBAR_WIDTH-1, 0, SCROLLBAR_WIDTH, SCROLLBAR_HEIGHT, color(123), SCROLLBAR_EVENT);
 }

}