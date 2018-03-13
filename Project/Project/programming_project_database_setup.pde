import java.sql.*;

//A Field is a single piece of data. 

//A Record is a collection of fields.  (column)

//A Table is a collection of records. 

//A File is the database in its entirety, i.e. all tables, records and fields. 


// SELECT * FROM books WHERE author = "Joe Bloggs" 

//[SELECT] As it suggests it is selecting certain data. 
//[*] This is what is called a wildcard and, in this example, means select all fields. 
//[FROM] Specifies the table to retrieve the data from. 
//[WHERE] This defines the criteria for our query. 

// you can also use [AND] & [OR] operators when making queries, in my class the queries are setup as strings that are inputted into the statement


class Example  {  
      ArrayList<String> userIdList = new ArrayList<String>();
      ArrayList<String> reviewTextList = new ArrayList<String>();
       {  
       try  
       { 
            // Load the SQLServerDriver class, build the 
            // connection string, and get a connection 
            Class.forName("com.mysql.jdbc.Driver"); 
            String connectionUrl = "jdbc:mysql://localhost/feedback?"
                            + "user=sqluser&password=sqluserpw"; 
            Connection connection = DriverManager.getConnection(connectionUrl); 
            System.out.println("Connected.");

            // Create and execute an SQL statement that returns some data.  
            //String SQL = "SELECT CustomerID, ContactName FROM Customers";
            String SQL = "SELECT user_Id, review FROM DataSet";
            
            String nameQuery = "SELECT * FROM DataSet WHERE author = 'Quiktrip No 453' AND stars > 3";
            
            Statement statement = connection.createStatement();  
            // the statement takes in the SQL query which is a direction of what to take from the file eg SELECT reviews FROM database
            ResultSet resultSet = statement.executeQuery(SQL);

            // Iterate through the data in the result set and display it.  
            while (resultSet.next())  
            {  
              String userId = resultSet.getString(0);
              String reviewText = resultSet.getString(1);
              // this will display the desired query in text form in this case, It could be added to an arrayList or other type of storage at will
              
              println("userId: "+userId +" reviewText:" + reviewText);
              if(userId != null)  {
                userIdList.add(userId);
              }
              if(reviewText != null) {
              reviewTextList.add(userId);
              }
              
               
               
            }

       }  
       catch(Exception e)  
       { 
            System.out.println(e.getMessage()); 
            System.exit(0);  
       } 
    } 
}