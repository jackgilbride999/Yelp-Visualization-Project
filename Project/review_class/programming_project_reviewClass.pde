import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;


class Review {
  
  public final int USERID_INDEX = 0;
  public final int NAME_INDEX = 1;
  public final int BUSINESS_ID_INDEX = 2;
  public final int BUSINESS_NAME_INDEX = 3;
  public final int STARS_INDEX = 4;
  public final int DATE_INDEX = 5;
  public final int REVIEW_INDEX = 6;
  public final int REVIEW_USEFUL_INDEX = 7;
  public final int REVIEW_FUNNY_INDEX = 8;
  public final int REVIEW_COOL_INDEX = 9;
  
  

  private String userId;
  private String name;
  private String business_id;
  private String business_name;
  private int stars;
  private String date;
  private String reviewBody;
  private int usefulRatings;
  private int funnyRatings;
  private int coolRatings;
  
  

  
  Date toDate(String dateStr) {
    DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
    Date dt = new Date();
    try {
      dt = df.parse(dateStr);
    }
    catch(Exception e){
      println(e);
    }
    return dt;
  }
  
  String[] parseReview(String sample) {
      int index= 0;
      boolean inQuotes = false;
      boolean doubleQuotes = false;
      String field = "";
      
      String [] fields = new String[10];
      for ( int i = 0; i < sample.length(); i++){
        char ch =  sample.charAt(i);
        char nextCh = ' ';
        if (i != sample.length()-1){
           nextCh =  sample.charAt(i+1);
        }       
        doubleQuotes = false;
        if (ch == '\"' && nextCh != '\"')
        {
          inQuotes = !inQuotes; 
        }
        else if (ch == '\"' && nextCh == '\"')
        {
          doubleQuotes = true;
        }
        if (ch == ',' && !inQuotes )
        {
          fields[index] = field;
          index++;
          field = "";
        }
        else {
           if (ch != '\"' || (doubleQuotes && index != BUSINESS_NAME_INDEX)){
              field +=  ch;
           }
        }
      }
      if (!field.equals("")){
          fields[index] = field; 
      }
      
      return fields;
  }
  
  
  Review(String nextLine) {
    
  String[] fields = parseReview(nextLine);  
  userId = fields[USERID_INDEX]; //<>//
  name = fields[NAME_INDEX];
  business_id = fields[BUSINESS_ID_INDEX];
  business_name = fields[BUSINESS_NAME_INDEX];
  date = fields[DATE_INDEX];
  reviewBody = fields[REVIEW_INDEX]; 
  
  stars = Integer.parseInt(fields[STARS_INDEX]);
  usefulRatings = Integer.parseInt(fields[REVIEW_USEFUL_INDEX]);
  funnyRatings = Integer.parseInt(fields[REVIEW_FUNNY_INDEX]);
  coolRatings = Integer.parseInt(fields[REVIEW_COOL_INDEX]);
    
  }
  
@Override  
public String toString(){
  
  return "User id: "+userId + "; Name: "+name + "; Business_id " + business_id + "; Business name: "+ business_name + "; Date: " + toDate(date).toString() + "; Review: " + reviewBody + "; Stars: " + stars+ "; Useful ratings: "+ funnyRatings + "; Funny ratings: "+ funnyRatings+ "; Cool ratings: "+ coolRatings;                    
  
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
  return reviewBody;
}
public void setReview(String review) {
  this.reviewBody = review;
}
public int getUsefulRatings() {
  return usefulRatings;
}
public void setUsefulRatings(int reviewUseful) {
  this.usefulRatings = reviewUseful;
}
public int getFunnyRatings() {
  return funnyRatings;
}
public void setFunnyRatings(int reviewFunny) {
  this.funnyRatings = reviewFunny;
}
public int getCoolRatings() {
  return coolRatings;
}
public void setCoolRatings(int reviewCool) {
  this.coolRatings = reviewCool;
}

}
String line =  "bv2nCi5Qv5vroFiqKGopiw,Tim,AEx2SYEUJmTxVVB18LlCwA,\"\"\"Quiktrip No 453\"\"\",5,28-05-2016,\"Super simple place but amazing nonetheless. It's been around since the 30's and they still serve the same thing they started with: a bologna and salami sandwich with mustard.Staff was very helpful and friendly.\",0,0,0";
String line2 = "u0LXt3Uea_GidxRW1xcsfg,Felecia,N93EYZy9R0sdlEvubu94ig,\"\"\"Cool Springs Golf Center\"\"\",3,23-09-2012,\"Not sure what the hype is, but decided to give this a try based on all the reviews.\n"+

"Maybe it was too hyped up, but it wasn't as amazing as I thought it would be. Maybe it was what we had ordered, but it was just ok. We had ordered the kimchi fries, which was kind of like a NY Fries \"\"the works\"\". The grilled pork banh mi was ok.\n"+ 

"Will be back to try other stuff, like the bao version and the pork belly.\",0,0,0";

Review review = new Review(line);
Review review2 = new Review(line2);
println(review.toString());
println(review2.toString());