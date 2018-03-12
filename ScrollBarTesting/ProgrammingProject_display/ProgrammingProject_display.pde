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

Scrollbar scrollbar;
int previousMouseY;
int mouseDifference;
boolean scrollBarPressed;
int offsetFromTop;
PFont myFont;
ArrayList<String> formattedReviewsList; 
ArrayList<Review> reviews;
int totalHeight;

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
  totalHeight = getTotalHeight(formatReviews(reviews));
  scrollbar = new Scrollbar(10, totalHeight, color(120), SCROLLBAR_EVENT); // in theory the scrollbar works but there are so many reviews, that it is so small (invisible)
                                                                           // feed in a test value in place of totalHeight if you want to test, i.e. 10000
  offsetFromTop=0;
  println("total height: " + totalHeight);

}

void draw() {
  background(255);
  noStroke();
  previousMouseY=mouseY;
  offsetFromTop=scrollbar.getY() * 20;
  drawReviews();
  scrollbar.draw();
}

void mousePressed() {
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
    if (scrollbar.getY()<0)
      scrollbar.setY(0);
    else if (scrollbar.getY()+scrollbar.getHeight()>SCREEN_Y)
      scrollbar.setY(SCREEN_X-scrollbar.getHeight());
  }
}


void drawReviews() {
  fill(color(0));
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
  int lines = 4;

  for (String str : formattedReviews)
  {
    String[] split = str.split("");
    
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