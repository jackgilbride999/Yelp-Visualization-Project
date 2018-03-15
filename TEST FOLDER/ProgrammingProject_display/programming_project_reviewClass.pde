import java.util.ArrayList;

class Review {
  
  public final int USERID_INDEX = 0;
  public final int NAME_INDEX = 1;
  public final int BUSINESS_ID_INDEX = 2;
  public final int BUSINESS_NAME_INDEX = 3;
  public final int DATE_INDEX = 5;
  public final int REVIEW_INDEX = 6;
  public final int STARS_INDEX = 4;
  public final int REVIEW_USEFUL_INDEX = 7;
  public final int REVIEW_FUNNY_INDEX = 8;
  public final int REVIEW_COOL_INDEX = 9;
  
  

  private String userId;
  private String name;
  private String business_id;
  private String business_name;
  private int stars;
  private String date;
  private String review;
  private int reviewUseful;
  private int reviewFunny;
  private int reviewCool;
  private int numberOfLines;
  private String formattedReview;
  
 // for each object we need an array of parameters eg userId at array[0], name at array[1]
  String[] stringParameters = new String[10];
  // we would need to store the result of the file read in some way like this
  // both arrays need to be passed in from the file reader
  
  
  Review(String[] stringParameters) {
    this.stringParameters = stringParameters;
    
  userId = stringParameters[USERID_INDEX];
  name = stringParameters[NAME_INDEX]; 
  business_id = stringParameters[BUSINESS_ID_INDEX]; 
  business_name = stringParameters[BUSINESS_NAME_INDEX]; 
  date = stringParameters[DATE_INDEX]; 
  review = stringParameters[REVIEW_INDEX]; 
  
  stars = Integer.parseInt((stringParameters[STARS_INDEX]));
  reviewUseful = Integer.parseInt(stringParameters[REVIEW_USEFUL_INDEX]);
  reviewFunny = Integer.parseInt(stringParameters[REVIEW_FUNNY_INDEX]);
  reviewCool = Integer.parseInt(stringParameters[REVIEW_COOL_INDEX]);

    
  }
  
public String getUserId() {
  return userId;
}
public void setUserId(String userId) {
  this.userId = userId;
}
public String getName() {
  return name;
}
public void setName(String name) {
  this.name = name;
}
public String getBusiness_id() {
  return business_id;
}
public void setBusiness_id(String business_id) {
  this.business_id = business_id;
}
public String getBusiness_name() {
  return business_name;
}
public void setBusiness_name(String business_name) {
  this.business_name = business_name;
}
public int getStars() {
  return stars;
}
public void setStars(int stars) {
  this.stars = stars;
}
public String getDate() {
  return date;
}
public void setDate(String date) {
  this.date = date;
}
public String getReview() {
  return review;
}
public void setReview(String review) {
  this.review = review;
}
public int getReviewUseful() {
  return reviewUseful;
}
public void setReviewUseful(int reviewUseful) {
  this.reviewUseful = reviewUseful;
}
public int getReviewFunny() {
  return reviewFunny;
}
public void setReviewFunny(int reviewFunny) {
  this.reviewFunny = reviewFunny;
}
public int getReviewCool() {
  return reviewCool;
}
public void setReviewCool(int reviewCool) {
  this.reviewCool = reviewCool;
}
public int getNumberOfLines() {
  return numberOfLines; 
}
public void setNumberOfLines(int numberOfLines) {
  this.numberOfLines = numberOfLines; 
}
public void setFormattedReview(String review) {
  this.formattedReview = review;
}
public String getFormattedReview() {
  return this.formattedReview;
}


}