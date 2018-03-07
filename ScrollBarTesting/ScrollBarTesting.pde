final int SCREEN_X=400;
final int SCREEN_Y=400;
final int SCROLLBAR_HEIGHT=50;
final int SCROLLBAR_WIDTH=10;
final int NULL_EVENT=0;
final int SCROLLBAR_EVENT=6;
final color BLACK = color(0);
ScrollBar scrollBar;
int previousMouseY;
int mouseDifference;
boolean scrollBarPressed;
int offsetFromTop;
void settings() {
  size(SCREEN_X, SCREEN_Y);
}

void setup() {
  scrollBar = new ScrollBar();
  offsetFromTop=0;
}
void draw() {
  background(255);
  noStroke();
  scrollBar.draw();
  previousMouseY=mouseY;
  mouseDifference=mouseY-scrollBar.getY();
  offsetFromTop=scrollBar.getY();
  rect(50, 50-offsetFromTop, 50, 50);
  rect(100, 200-offsetFromTop, 60, 40);
  ellipse(250, 500-offsetFromTop, 30, 30);
}

void mousePressed() {
  if (scrollBar.getEvent(mouseX, mouseY)==SCROLLBAR_EVENT) {
    scrollBarPressed=true;
  }
}

void mouseReleased() {
  scrollBarPressed=false;
}

void mouseDragged() {
  if (scrollBarPressed==true) {

    //if (mouseY>previousMouseY && scrollBar.getY()+scrollBar.getHeight() < SCREEN_X ||
    // mouseY<previousMouseY && scrollBar.getY()>0)
    scrollBar.setY(mouseY-mouseDifference);
    if (scrollBar.getY()<0)
      scrollBar.setY(0);
    else if (scrollBar.getY()+scrollBar.getHeight()>SCREEN_Y)
      scrollBar.setY(SCREEN_X-scrollBar.getHeight());
  }
}