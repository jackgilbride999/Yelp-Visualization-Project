import java.util.ArrayList;

class Review {

    private String review_id;
    private String user_id;
    private String business_id;
    private int stars;
    private String date;
    private String text;
    private int reviewUseful;
    private int reviewFunny;
    private int reviewCool;
    private int numberOfLines;
    private String formattedReview;
    
    private String business_name;
    
    public Review(String review_id, String user_id, String business_id, int stars, String date, String text, int reviewUseful, int reviewFunny, int reviewCool) {
        this.review_id = review_id;
        this.user_id = user_id;
        this.business_id = business_id;
        this.stars = stars;
        this.date = date;
        this.text = text;
        this.reviewUseful = reviewUseful;
        this.reviewFunny = reviewFunny;
        this.reviewCool = reviewCool;
    }
    
    public String getUserId() {
        return user_id;
    }
    public void setUserId(String user_id) {
        this.user_id = user_id;
    }
    public String getBusiness_id() {
        return business_id;
    }
    public void setBusiness_id(String business_id) {
        this.business_id = business_id;
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
        return text;
    }
    public void setReview(String text) {
        this.text = text;
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
    public void setBusinessName(String business_name){
      this.business_name = business_name;
    }
    public String getBusinessName(){
      return business_name;
    }
    public void setFormattedReview(String review) {
      this.formattedReview = review;
    }
    public String getFormattedReview() {
      return this.formattedReview;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Review{" +
                "review_id='" + review_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", business_name='" + business_name + '\'' +
                ", business_id='" + business_id + '\'' +
                ", stars=" + stars +
                ", date='" + date + '\'' +
                ", text='" + text + '\'' +
                ", reviewUseful=" + reviewUseful +
                ", reviewFunny=" + reviewFunny +
                ", reviewCool=" + reviewCool +
                '}';
    }
}