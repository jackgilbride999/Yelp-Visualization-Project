// demo program of how to use widget as scrollbar
final int SCREEN_X=400;
final int SCREEN_Y=400;
final int SCROLLBAR_WIDTH=10;
final int EVENT_NULL=0;
final int SCROLLBAR_EVENT=6;
Scrollbar scrollbar;
int previousMouseY;
int mouseDifference;
boolean scrollBarPressed;
int offsetFromTop;       // have a variable like this when using scrollbar in a program
int totalHeight;
int ratio;
void settings() {
  size(SCREEN_X, SCREEN_Y);
}

void setup() {
  totalHeight = 1200; // test value for height of page to be scrolled, pass this into Scrollbar as seen on next line
  scrollbar = new Scrollbar(SCROLLBAR_WIDTH, totalHeight,  color(123), SCROLLBAR_EVENT); // how to call Scrollbar object
  offsetFromTop=0;
  ratio = scrollbar.getRatio();
}
void draw() {
  background(255);
  noStroke();
  scrollbar.draw(0); // have to pass a number to  widget(how far the scrollbar has to use it), but the scrollbar will override this 
                     //so it can move other widgets but never 'move itself'
  previousMouseY=mouseY;
  offsetFromTop=scrollbar.getY();              // test shapes to show scrollbar working 
  rect(50, 50-(ratio*offsetFromTop), 50, 50);          // scrollbar works by subtracting ("offset from top" * ratio) from the original y position of whatever is on the screen
  rect(100, 200-(ratio*offsetFromTop), 60, 40);
  ellipse(250, 500-(ratio*offsetFromTop), 30, 30);
  rect(50, totalHeight-50-(ratio*offsetFromTop), 50, 50);  // square to show bottom of page
}

void mousePressed() {                                        // when these three mouse methods are used in the main program, make sure to have this code within them to control scrollbar
  if (scrollbar.getEvent(mouseX, mouseY)==SCROLLBAR_EVENT) {
    scrollBarPressed=true;
     mouseDifference=mouseY-scrollbar.getY();
  }
}

void mouseReleased() {
  scrollBarPressed=false;
}

void mouseDragged() {
  if (scrollBarPressed==true) {
    scrollbar.setY(mouseY-mouseDifference);
     println("" +  scrollbar.getY());
    if (scrollbar.getY()<0)
      scrollbar.setY(0);
    else if (scrollbar.getY()+scrollbar.getHeight()>SCREEN_Y)
      scrollbar.setY(SCREEN_Y-scrollbar.getHeight());
  }
}