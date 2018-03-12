import java.util.Scanner; //<>// //<>// //<>// //<>//
import java.util.Arrays;
import java.util.ArrayList;
import java.io.FileReader;
import java.util.Arrays;

final int SCREEN_X=400;
final int SCREEN_Y=1000;
final int LINE_LENGTH =60;
final int SCROLLBAR_HEIGHT=50;
final int SCROLLBAR_WIDTH=10;
final int NULL_EVENT=0;
final int SCROLLBAR_EVENT=6;
final color BLACK = color(0);
final int BORDER_OFFSET_Y = 20;

ScrollBar scrollBar;
int previousMouseY;
int mouseDifference;
boolean scrollBarPressed;
int offsetFromTop;
PFont myFont;
ArrayList<String> formattedReviewsList; 
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
    formattedReviewsList = formatReviews(reviews);
    // Splits the review in order to format with a specific line length and then sets the review to the formatted review
    
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
  drawReviews(formattedReviewsList);
  
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


void drawReviews(ArrayList<String> formattedReviewList) {
  int reviewOffset = 0;
  int borderOffsetY = 20;
  int borderOffsetX = 10;
  float lineHeight = textAscent() + textDescent();
  for (Review r : reviews)
  {
    rect(borderOffsetX / 2, reviewOffset - offsetFromTop, SCREEN_X-10, 1);
    text(r.getDate(), SCREEN_X-textWidth(r.getDate()), reviewOffset+ borderOffsetY -offsetFromTop);
    text(r.getReview(), borderOffsetX, reviewOffset + borderOffsetY -offsetFromTop);
//  text(r.getNumberOfLines(), 250, reviewOffset + borderOffsetY -offsetFromTop);
    
    reviewOffset = reviewOffset + (r.getNumberOfLines() * (int)lineHeight) + borderOffsetY;
  }
}


ArrayList<String> formatReviews(ArrayList<Review> reviews) {
  // Splits the review in order to format with a specific line length and then sets the review to the formatted review
  ArrayList<String> formattedReviewList = new ArrayList<String>();
 for (Review r : reviews) {
      String[] splitReview = r.getReview().split("");
      String formattedReview = r.getBusiness_name() + "\n";
      for (int i = 0; i<r.getStars(); i++)
     {
        formattedReview = formattedReview + " * ";
      }
      formattedReview = formattedReview + "\n" + r.getName() + ":" + "\n";
      boolean toNextLine = false;
      int lines = 4;
      for (int i = 0; i < splitReview.length; i++)
      {
        // Checks to see if line length has exceeded
        if ((i % LINE_LENGTH == 0) && (i != 0))
        {
          toNextLine = true;
        }
        // If line lenght has been exceeded it will put a new line at the next whitespace
        if (toNextLine && splitReview[i].equals(" "))
        {
          splitReview[i] = "\n";
          toNextLine = false;
        }
        if (splitReview[i].equals("\n"))
        {
          lines++;
        }
        formattedReview = formattedReview + splitReview[i];
      }
      r.setNumberOfLines(lines);
      r.setReview(formattedReview);
      formattedReviewList.add(formattedReview);
      
    }
    return formattedReviewList;
}


int getTotalHeight(ArrayList<String> formattedReviews) {
  int totalHeight = 0;
  int lineHeight = 15;

  for (String str : formattedReviews)
  {
    String[] split = str.split("");
    int lines = 4;
    for (int i = 0; i < split.length; i++)
    {
      if (split[i].equals("\n"))
      {
        lines++;
      }
    }
    totalHeight = lines * lineHeight + BORDER_OFFSET_Y;
    
  }
  return totalHeight;
}