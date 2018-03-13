import java.sql.*;

void setup(){

    try {
        Class.forName("com.mysql.jdbc.Driver"); 
        String connectionUrlAWS = "jdbc:mysql://yelpdatabase.cioogriagt5l.eu-west-1.rds.amazonaws.com/yelp?"
                            + "user=root&password=programming4&"
                            + "autoReconnect=true&useSSL=false"; 
        String connectionUrlCUSTOM = "jdbc:mysql://37.228.204.207/yelp?"
                            + "user=root&password=programming4&"
                            + "autoReconnect=true&useSSL=false"; 
        java.sql.Connection connection = DriverManager.getConnection(connectionUrlCUSTOM); 
        System.out.println("Connected.");
      
        String nameSearch = "SELECT * FROM yelp_user WHERE name LIKE 'Luke' LIMIT 10";
        java.sql.Statement statement = connection.createStatement();  
        ResultSet results = statement.executeQuery(nameSearch);
        
        while(results.next()){
          println(results.getString("user_id") + " " + results.getString("yelping_since"));
        }
        
        println("Done");
    } catch (Exception e){
        println(e);
    }
}