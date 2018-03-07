import java.util.ArrayList;

class Review {
  
  public final int USERID_INDEX = 0;
  public final int NAME_INDEX = 1;
  public final int BUSINESS_ID_INDEX = 2;
  public final int BUSINESS_NAME_INDEX = 3;
  public final int DATE_INDEX = 4;
  public final int REVIEW_INDEX = 5;
  public final int STARS_INDEX = 0;
  public final int REVIEW_USEFUL_INDEX = 1;
  public final int REVIEW_FUNNY_INDEX = 2;
  public final int REVIEW_COOL_INDEX = 3;
  
  

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
  
 // for each object we need an array of parameters eg userId at array[0], name at array[1]
  ArrayList<String> stringParameters = new ArrayList<String>();
  ArrayList<Integer> integerParameters = new ArrayList<Integer>();
  // we would need to store the result of the file read in some way like this
  // both arrays need to be passed in from the file reader
  
  
  Review() {
    
    
  userId = stringParameters.get(USERID_INDEX);
  name = stringParameters.get(NAME_INDEX); 
  business_id = stringParameters.get(BUSINESS_ID_INDEX); 
  business_name = stringParameters.get(BUSINESS_NAME_INDEX); 
  date = stringParameters.get(DATE_INDEX); 
  review = stringParameters.get(REVIEW_INDEX); 
  
  stars = integerParameters.get(STARS_INDEX);
  reviewUseful = integerParameters.get(REVIEW_USEFUL_INDEX);
  reviewFunny = integerParameters.get(REVIEW_FUNNY_INDEX);
  reviewCool = integerParameters.get(REVIEW_COOL_INDEX);
    
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
  
  


}