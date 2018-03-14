import java.sql.*;
import java.util.LinkedHashMap;
import java.util.ArrayList;

class queries {
  java.sql.Connection connection;

  queries() {
    try {
      Class.forName("com.mysql.jdbc.Driver");
      String connectionUrlAWS = "jdbc:mysql://yelpdatabase.cioogriagt5l.eu-west-1.rds.amazonaws.com/yelp?"
        + "user=root&password=programming4&"
        + "autoReconnect=true&useSSL=false";
      String connectionUrlCUSTOM = "jdbc:mysql://37.228.204.207/yelp?"
        + "user=root&password=programming4&"
        + "autoReconnect=true&useSSL=false";
      String connectionUrlLOCAL = "jdbc:mysql://localhost/yelp?"
        + "user=root&password=Carrot!4!5&"
        + "autoReconnect=true&useSSL=false";

      connection = DriverManager.getConnection(connectionUrlAWS);
      System.out.println("Connected.");

    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  public ArrayList<Business> businesses(int start, int limit){
    ArrayList<Business> businesses = new ArrayList<Business>();
    try{
      String businessQuery = "SELECT * "+
                            "FROM yelp_business " +
                            "LIMIT " + start + "," + limit;
      java.sql.Statement statement = connection.createStatement();
      ResultSet results = statement.executeQuery(businessQuery);

      while(results.next()){
        businesses.add(new Business(results.getString("business_id"), results.getString("name"), results.getString("neighbourhood"), results.getString("address"), results.getString("city"), results.getString("state"), results.getString("postal_code"), results.getDouble("latitude"), results.getDouble("longitude"), results.getInt("stars"), results.getInt("review_count"), results.getInt("is_open"), results.getString("categories")));
      }
    } catch(Exception e){
       e.printStackTrace();
    }
    return businesses;
  }
  
  public ArrayList<Review> reviews(int start, int limit){
    ArrayList<Review> reviews = new ArrayList<Review>();
    try{
      String businessQuery = "SELECT * "+
                            "FROM yelp_review " +
                            "LIMIT " + start + "," + limit;
      java.sql.Statement statement = connection.createStatement();
      ResultSet results = statement.executeQuery(businessQuery);

      while(results.next()){
        reviews.add(new Review(results.getString("id"), results.getString("user_id"), results.getString("business_id"), results.getInt("stars"), results.getString("date"), results.getString("text"), results.getInt("useful"), results.getInt("funny"), results.getInt("cool")));
      }
    } catch(Exception e){
       e.printStackTrace();
    }
    return reviews;
  }
  
  public String getBusinessName(String business_id){
    try{
      String businessNameQuery = "SELECT name "+
                            "FROM yelp_business " +
                            "WHERE business_id " +
                            "LIKE " + '"' + business_id + '"' +
                            " LIMIT " + 1;
      java.sql.Statement statement = connection.createStatement();
      ResultSet results = statement.executeQuery(businessNameQuery);
      while(results.next()){
        return results.getString("name");
      }
    } catch(Exception e){
       e.printStackTrace();
    }
    return null;
  }
  
  public String getBusinessID(String business_name){
    try{
      String businessNameQuery = "SELECT business_id "+
                            "FROM yelp_business " +
                            "WHERE name " +
                            "LIKE " + '\'' + '"' + business_name + '"' + '\'' +
                            " LIMIT " + 1;
      java.sql.Statement statement = connection.createStatement();
      ResultSet results = statement.executeQuery(businessNameQuery);
      while(results.next()){
        return results.getString("business_id");
      }
    } catch(Exception e){
       e.printStackTrace();
    }
    return null;
  }
  
  public Business getBusinessInfo(String business_id){
    try{
      String businessQuery = "SELECT * "+
                            "FROM yelp_business " +
                            "WHERE business_id " +
                            "LIKE " + '"' + business_id + '"' +
                            "LIMIT " + 1;
      java.sql.Statement statement = connection.createStatement();
      ResultSet results = statement.executeQuery(businessQuery);

      while(results.next()){
        return new Business(results.getString("business_id"), results.getString("name"), results.getString("neighbourhood"), results.getString("address"), results.getString("city"), results.getString("state"), results.getString("postal_code"), results.getDouble("latitude"), results.getDouble("longitude"), results.getInt("stars"), results.getInt("review_count"), results.getInt("is_open"), results.getString("categories"));
      }
    } catch(Exception e){
       e.printStackTrace();
    }
    return null;
  }
  
  public String getUserName(String user_id){
    try{
      String userNameQuery = "SELECT name "+
                            "FROM yelp_user " +
                            "WHERE user_id " +
                            "LIKE " + '"' + user_id + '"' +
                            " LIMIT " + 1;
      java.sql.Statement statement = connection.createStatement();
      ResultSet results = statement.executeQuery(userNameQuery);
      while(results.next()){
        return results.getString("name");
      }
    } catch(Exception e){
       e.printStackTrace();
    }
    return null;
  }
  
  public LinkedHashMap<String, String> getBusinessAttributes(String business_id){
    LinkedHashMap<String, String> attributes = new LinkedHashMap<String, String>();
    String[] attributeHeadings = {"business_id","AcceptsInsurance","ByAppointmentOnly","BusinessAcceptsCreditCards","BusinessParking_garage","BusinessParking_street","BusinessParking_validated","BusinessParking_lot","BusinessParking_valet","HairSpecializesIn_coloring","HairSpecializesIn_africanamerican","HairSpecializesIn_curly","HairSpecializesIn_perms","HairSpecializesIn_kids","HairSpecializesIn_extensions","HairSpecializesIn_asian","HairSpecializesIn_straightperms","RestaurantsPriceRange2","GoodForKids","WheelchairAccessible","BikeParking","Alcohol","HasTV","NoiseLevel","RestaurantsAttire","Music_dj","Music_background_music","Music_no_music","Music_karaoke","Music_live","Music_video","Music_jukebox","Ambience_romantic","Ambience_intimate","Ambience_classy","Ambience_hipster","Ambience_divey","Ambience_touristy","Ambience_trendy","Ambience_upscale","Ambience_casual","RestaurantsGoodForGroups","Caters","WiFi","RestaurantsReservations","RestaurantsTakeOut","HappyHour","GoodForDancing","RestaurantsTableService","OutdoorSeating","RestaurantsDelivery","BestNights_monday","BestNights_tuesday","BestNights_friday","BestNights_wednesday","BestNights_thursday","BestNights_sunday","BestNights_saturday","GoodForMeal_dessert","GoodForMeal_latenight","GoodForMeal_lunch","GoodForMeal_dinner","GoodForMeal_breakfast","GoodForMeal_brunch","CoatCheck","Smoking","DriveThru","DogsAllowed","BusinessAcceptsBitcoin","Open24Hours","BYOBCorkage","BYOB","Corkage","DietaryRestrictions_dairy-free","DietaryRestrictions_gluten-free","DietaryRestrictions_vegan","DietaryRestrictions_kosher","DietaryRestrictions_halal","DietaryRestrictions_soy-free","DietaryRestrictions_vegetarian","AgesAllowed","RestaurantsCounterService"};
    try{
      String attributesQuery = "SELECT * "+
                            "FROM yelp_business_attributes " +
                            "WHERE business_id " +
                            "LIKE " + '"' + business_id + '"' + 
                            " LIMIT 1";
      java.sql.Statement statement = connection.createStatement();
      ResultSet results = statement.executeQuery(attributesQuery);
      while(results.next()){
        for(int i = 2; i <= 82; i++){
          attributes.put(attributeHeadings[i-2], results.getString(i));
        }
      }
    } catch(Exception e){
       e.printStackTrace();
    }
    return attributes;
  }
  
  public String[] getBusinessHours(String business_id){
    String[] businessHours = new String[7];
    try{
      String attributesQuery = "SELECT * "+
                            "FROM yelp_business_hours " +
                            "WHERE business_id " +
                            "LIKE " + '"' + business_id + '"' + 
                            " LIMIT 1";
      java.sql.Statement statement = connection.createStatement();
      ResultSet results = statement.executeQuery(attributesQuery);
      while(results.next()){
        for(int i = 2; i <= 8; i++){
          businessHours[i-2]= results.getString(i);
        }
      }
    } catch(Exception e){
       e.printStackTrace();
    }
    return businessHours;
  }
  
   ArrayList<Integer> visitorsList = new ArrayList<Integer>();
   public String getBusinessVisitors(String business_id){
    try{
      String businessVisitorsQuery = "SELECT * "+
                            "FROM check_ins " +
                            "WHERE business_id " +
                            "LIKE " + '"' + business_id + '"' +
                            " LIMIT " + 168; // 7*24 so the vistors for the last week
      java.sql.Statement statement = connection.createStatement();
      ResultSet results = statement.executeQuery(businessVisitorsQuery);
      while(results.next()){
        int visitorNum = Integer.parseInt(results.getString(0));
        visitorsList.add(visitorNum);
      }
    } catch(Exception e){
       e.printStackTrace();
    }
    return null;
  }
}