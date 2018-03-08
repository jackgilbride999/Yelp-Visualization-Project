import java.util.Scanner; //<>//
import java.util.Arrays;
import java.util.ArrayList;
import java.io.FileReader;
import java.util.Arrays;

final int SCREEN_X=400;
final int SCREEN_Y=1000;
final int LINE_LENGTH = 60;
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
PFont myFont;

ArrayList<Review> reviews;

void settings() {
  size(SCREEN_X, SCREEN_Y);
}

void setup() {
  myFont = loadFont("ArialMT-30.vlw");
  reviews = new ArrayList<Review>();
  int count = 0;

  try {
    CSVReader reader = new CSVReader(new FileReader(dataPath("reviews.csv")), ',', '"', 1);
    //CSVReader reader = new CSVReader(new FileReader(dataPath("yelp_review.csv")), ',', '"', 1);
    String[] nextLine;

    while ((nextLine = reader.readNext()) != null) {
      if (nextLine != null) {
        reviews.add(new Review(nextLine));
      }
    }

    println("Time taken: " + millis());
// Splits the review in order to format with a specific line length and then sets the review to the formatted review
    for (Review r : reviews) {
      String[] splitReview = r.getReview().split("");
      String formattedReview = "";
      boolean toNextLine = false;
      int lines = 0;
      for (int i = 0; i < splitReview.length; i++) //<>//
      {
        // Checks to see if line length has exceeded
        if ((i % LINE_LENGTH == 0) && (i != 0))
        {
          toNextLine = true;
        }
        // If line lenght has been exceeded it will put a new line at the next whitespace
        if(toNextLine && splitReview[i].equals(" ")) //<>//
        {
           splitReview[i] = "\n";
           toNextLine = false;
        }
        if(splitReview[i].equals("\n"))
        {
           lines++; 
        }
        formattedReview = formattedReview + splitReview[i];
      }
     r.setNumberOfLines(lines);
     r.setReview(formattedReview);
    }
  } 
  catch (Exception e) {
    println(e);
  }
  scrollBar = new ScrollBar();
  offsetFromTop=0;
}

void draw() {
  background(255);
  noStroke();
  scrollBar.draw();
  previousMouseY=mouseY;
  offsetFromTop=scrollBar.getY() * 20;
  int reviewOffset = 80;
  for (Review r : reviews)
  {

    rect(5, reviewOffset - 80 - offsetFromTop, SCREEN_X-10, 1);
    text(r.getBusiness_name(), 10, reviewOffset-60-offsetFromTop);
    int starX = 20;
    for (int i = 0; i<r.getStars(); i++)
    {
      ellipse(starX, reviewOffset-45-offsetFromTop, 10, 10);
      starX = starX + 20;
    }
    text(r.getName(), 10, reviewOffset-20-offsetFromTop); 
    text(r.getDate(), SCREEN_X-textWidth(r.getDate()), reviewOffset-20-offsetFromTop);
    text(r.getReview(), 10, reviewOffset-offsetFromTop);
    reviewOffset = reviewOffset + (r.getNumberOfLines());
    text(r.getReview().length(), 400, reviewOffset-20-offsetFromTop);

    reviewOffset = reviewOffset + (r.getNumberOfLines() * 30);

  }
}

void mousePressed() {
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