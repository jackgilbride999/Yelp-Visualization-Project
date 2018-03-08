// demo program of how to use widget as scrollbar
final int SCREEN_X=400;
final int SCREEN_Y=400;
final int SCROLLBAR_WIDTH=10;
final int NULL_EVENT=0;
final int SCROLLBAR_EVENT=6;
Widget scrollBar;
int previousMouseY;
int mouseDifference;
boolean scrollBarPressed;
int offsetFromTop;       // have a variable like this when using scrollbar in a program
int totalHeight;
void settings() {
  size(SCREEN_X, SCREEN_Y);
}

void setup() {
  totalHeight = 1200; // test value for height of page to be scrolled, padd this into Scrollbar as seen on next line
  scrollBar = new Scrollbar(SCREEN_X-SCROLLBAR_WIDTH-1, 0, SCROLLBAR_WIDTH, totalHeight,  color(123), SCROLLBAR_EVENT); // how to call Scrollbar object
  offsetFromTop=0;
}
void draw() {
  background(255);
  noStroke();
  scrollBar.draw();
  previousMouseY=mouseY;
  offsetFromTop=scrollBar.getY();              // test shapes to show scrollbar working 
  rect(50, 50-offsetFromTop, 50, 50);          // scrollbar works by subtracting "offset from top" from the original y position of whatever is on the screen
  rect(100, 200-offsetFromTop, 60, 40);
  ellipse(250, 500-offsetFromTop, 30, 30);
}

void mousePressed() {                                        // when these three mouse methods are used in the main program, make sure to have this code within them to control scrollbar
  if (scrollBar.getEvent(mouseX, mouseY)==SCROLLBAR_EVENT) {
    scrollBarPressed=true;
     mouseDifference=mouseY-scrollBar.getY();
  }
}

void mouseReleased() {
  scrollBarPressed=false;
}

void mouseDragged() {
  if (scrollBarPressed==true) {
    scrollBar.setY(mouseY-mouseDifference);
    if (scrollBar.getY()<0)
      scrollBar.setY(0);
    else if (scrollBar.getY()+scrollBar.getHeight()>SCREEN_Y)
      scrollBar.setY(SCREEN_X-scrollBar.getHeight());
  }
}