import java.sql.*;
import java.util.ArrayList;

DbAccess db = null;

class BusinessNameId{
  public String id;
  public String name;
  
  public BusinessNameId(String name, String id){
    this.name = name;
    this.id = id;
  }
}


class DbAccess {
  
  private java.sql.Connection connection = null;
  
  

  public java.sql.Connection connect() {
    try {
         Class.forName("com.mysql.jdbc.Driver"); 
          String connectionUrl = "jdbc:mysql://37.228.204.207/yelp?"
                              + "user=root&password=programming4&"
                              + "autoReconnect=true&useSSL=false"; 
          connection = DriverManager.getConnection(connectionUrl); 
          System.out.println("Connected.");
          return connection;
      } catch (Exception e){
          println(e);
          return null;
      }    
   }

 public ResultSet getQueryResult(String query) {
  
  try {
        if (connection == null)
           connect();
        java.sql.Statement statement = connection.createStatement();  
        ResultSet results = statement.executeQuery(query);
        return results;
   } catch (Exception e){
        println(e);
        return null;
    }
 }

  public ArrayList<String> getTop20UserIdList()
  {
         ArrayList<String> userIdList = new ArrayList<String>();       
         String query = "SELECT user_id FROM yelp_user ORDER BY review_count DESC LIMIT 20";
        // set the query string as your needed query
        ResultSet results = getQueryResult(query);
     try {        
        while(results.next())
        {
          String userId = results.getString("user_id") ;
          userIdList.add(userId);
          // extract the info you want here
        }
        return userIdList;
       } catch (Exception e){
        println(e);
        return null;
    }
  }

  public ArrayList<BusinessNameId> getTop10BusinessIdListByCity(String city)
  {
         ArrayList<BusinessNameId> businessIdList = new ArrayList<BusinessNameId>();       
         String query = "SELECT business_id, name FROM yelp_business WHERE city = '"+city+"' ORDER BY stars DESC LIMIT 10";
        // set the query string as your needed query
        ResultSet results = getQueryResult(query);
     try {        
        while(results.next())
        {
          String id = results.getString("business_id") ;
          String name = results.getString("name") ;
          businessIdList.add(new BusinessNameId(name,id));
          // extract the info you want here
        }
        return businessIdList;
       } catch (Exception e){
        println(e);
        return null;
    }
  }


 public ArrayList<Float> getStarsList(String businessId)
  {
         ArrayList<Float> starsList= new ArrayList<Float>();
         
         String query = "SELECT stars FROM yelp_review WHERE business_id = '"+businessId+"' LIMIT 12";
        // set the query string as your needed query
        ResultSet results = getQueryResult(query);
     try {
        while(results.next())
        {
          
          float stars = Float.parseFloat(results.getString("stars"));
          
          starsList.add(stars);
        }
        return starsList;
       } catch (Exception e){
        println(e);
        return null;
    }
  }



  public String getBusinessIdByName(String name)
  {
         String id= "";      
         String query = "SELECT business_id FROM yelp_business WHERE name = '\""+name+"\"' LIMIT 1";
        // set the query string as your needed query
        ResultSet results = getQueryResult(query);
     try {
           results.next();
           id = results.getString("business_id");
           return id;
       } catch (Exception e){
        println(e);
        return "";
       }
  }


  public ArrayList<Float> getBusinessCheckins(String businessId)
  {
         ArrayList<Float> businessCheckins = new ArrayList<Float>();
         for (int i=0; i<7;i++)
           businessCheckins.add(0.0);
         String query = "SELECT weekday,SUM(checkins) AS checkins FROM yelp_checkin WHERE business_id = '"+businessId+"' GROUP BY weekday ORDER BY weekday";
        // set the query string as your needed query
        ResultSet results = getQueryResult(query);
     try {
        while(results.next())
        {
          String weekday = results.getString("weekday") ;
          float checkins = Float.parseFloat(results.getString("checkins"));
          
          if( weekday.equals("Mon"))
                businessCheckins.set(0,checkins);
          if( weekday.equals("Tue"))
                businessCheckins.set(1,checkins);
          if( weekday.equals("Wed"))
                businessCheckins.set(2,checkins);
          if( weekday.equals("Thu"))
                businessCheckins.set(3,checkins);
          if( weekday.equals("Fri"))
                businessCheckins.set(4,checkins); //<>//
          if( weekday.equals("Sat"))
                businessCheckins.set(5,checkins);
          if( weekday.equals("Sun"))
                businessCheckins.set(6,checkins);
        }
        return businessCheckins; //<>//
       } catch (Exception e){
        println(e);
        return null;
    }
  }
}



//void setup() {
 
  //db = new DbAccess();
  //4srfPk1s8nlm1YusyDUbjg
  //db.getBusinessCheckins("4srfPk1s8nlm1YusyDUbjg");
  //for(String userId : db.getTop20UserIdList()){
  //    println(userId);
  //}
  
//}