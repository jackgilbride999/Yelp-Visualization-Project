package com.LukeHackett;

import processing.core.PApplet;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.ArrayList;

import static processing.core.PApplet.println;

class queries {
    private final PApplet canvas;
    java.sql.Connection connection;

    queries(PApplet canvas) {
        this.canvas = canvas;

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

            connection = DriverManager.getConnection(connectionUrlCUSTOM);
            System.out.println("Connected.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public ResultSet getQueryResultAnton(String query) {

        try {

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




        try {
            String query ="SELECT stars FROM yelp_review WHERE MATCH(business_id) AGAINST " + "(" + '\'' + businessId + '\'' + ") LIMIT 12";
            java.sql.Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                starsList.add(Float.parseFloat(results.getString("stars")));
            }
            if(starsList == null) {
                return null;
            }
            else return starsList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return starsList;

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
            businessCheckins.add(0.0f);
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
            int count =0;
            for(int i =0; i < 7;i++) {
               if(businessCheckins.get(i) == 0.0f)  {
                   count++;

               }
            }
            if(count == 7) {
                return null;
            }
            else {
                return businessCheckins;
            }

        } catch (Exception e){
            println(e);
            return null;
        }
    }


    public ResultSet getQueryResult(String query) {

        try {

            java.sql.Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(query);
            return results;
        } catch (Exception e){
            println(e);
            return null;
        }
    }

    public ArrayList<Business> businesses(int start, int limit) {
        ArrayList<Business> businesses = new ArrayList<Business>();
        try {
            String businessQuery = "SELECT * " +
                    "FROM yelp_business " +
                    "LIMIT " + start + "," + limit;
            java.sql.Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(businessQuery);

            while (results.next()) {
                businesses.add(new Business(results.getString("business_id"), results.getString("name"), results.getString("neighbourhood"), results.getString("address"), results.getString("city"), results.getString("state"), results.getString("postal_code"), results.getDouble("latitude"), results.getDouble("longitude"), results.getDouble("stars"), results.getInt("review_count"), results.getInt("is_open"), results.getString("categories")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return businesses;
    }

    public ArrayList<Review> reviews(String business_id, int start, int lim) {
        ArrayList<Review> reviews = new ArrayList<Review>();
        try {
            String businessQuery = "SELECT * " +
                    "FROM yelp_review " +
                    "WHERE MATCH(business_id) AGAINST(" + '\'' + business_id + '\'' + ")" +
                    "LIMIT " + start + "," + lim;
            java.sql.Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(businessQuery);

            while (results.next()) {
                Review r = new Review(results.getString("id"), results.getString("user_id"), results.getString("business_id"), results.getDouble("stars"), results.getString("date"), results.getString("text"), results.getInt("useful"), results.getInt("funny"), results.getInt("cool"));
                r.setUser_name(getUserName(r.getUserId()));
                reviews.add(r);
                System.out.println(results.getString("id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reviews;
    }

    public String getBusinessName(String business_id) {
        try {
            String businessNameQuery = "SELECT name " +
                    "FROM yelp_business " +
                    "WHERE MATCH(business_id) " +
                    "AGAINST (" + '"' + business_id + '"' + ")" +
                    " LIMIT " + 1;
            java.sql.Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(businessNameQuery);
            while (results.next()) {
                return results.getString("name");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getBusinessID(String business_name) {
        try {
            String businessNameQuery = "SELECT business_id " +
                    "FROM yelp_business " +
                    "WHERE name " +
                    "LIKE " + '"' + '%' + business_name + '%' + '"' +
                    " LIMIT " + 1;
            java.sql.Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(businessNameQuery);
            while (results.next()) {
                return results.getString("business_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Business getBusinessInfo(String business_id) {
        try {
            String businessQuery = "SELECT * " +
                    "FROM yelp_business " +
                    "WHERE MATCH(business_id)" +
                    "AGAINST (" + '"' + business_id + '"' + ")" +
                    " LIMIT " + 1;
            java.sql.Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(businessQuery);

            while (results.next()) {
                return new Business(results.getString("business_id"), results.getString("name"), results.getString("neighbourhood"), results.getString("address"), results.getString("city"), results.getString("state"), results.getString("postal_code"), results.getDouble("latitude"), results.getDouble("longitude"), results.getDouble("stars"), results.getInt("review_count"), results.getInt("is_open"), results.getString("categories"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Business getBusinessInfoName(String business_name) {
        try {
            String businessQuery = "SELECT * " +
                    "FROM yelp_business " +
                    "WHERE MATCH(name) " +
                    "AGAINST (" + '"' + business_name + '"' + ")" +
                    "LIMIT " + 1;
            java.sql.Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(businessQuery);

            while (results.next()) {
                return new Business(results.getString("business_id"), results.getString("name"), results.getString("neighbourhood"), results.getString("address"), results.getString("city"), results.getString("state"), results.getString("postal_code"), results.getDouble("latitude"), results.getDouble("longitude"), results.getDouble("stars"), results.getInt("review_count"), results.getInt("is_open"), results.getString("categories"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getUserName(String user_id) {
        try {
            String userNameQuery = "SELECT name " +
                    "FROM yelp_user " +
                    "WHERE MATCH (user_id) AGAINST('" + user_id + "')";
            java.sql.Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(userNameQuery);
            while (results.next()) {
                return results.getString("name");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public LinkedHashMap<String, String> getBusinessAttributes(String business_id) {
        LinkedHashMap<String, String> attributes = new LinkedHashMap<String, String>();
        String[] attributeHeadings = {"business_id", "AcceptsInsurance", "ByAppointmentOnly", "BusinessAcceptsCreditCards", "BusinessParking_garage", "BusinessParking_street", "BusinessParking_validated", "BusinessParking_lot", "BusinessParking_valet", "HairSpecializesIn_coloring", "HairSpecializesIn_africanamerican", "HairSpecializesIn_curly", "HairSpecializesIn_perms", "HairSpecializesIn_kids", "HairSpecializesIn_extensions", "HairSpecializesIn_asian", "HairSpecializesIn_straightperms", "RestaurantsPriceRange2", "GoodForKids", "WheelchairAccessible", "BikeParking", "Alcohol", "HasTV", "NoiseLevel", "RestaurantsAttire", "Music_dj", "Music_background_music", "Music_no_music", "Music_karaoke", "Music_live", "Music_video", "Music_jukebox", "Ambience_romantic", "Ambience_intimate", "Ambience_classy", "Ambience_hipster", "Ambience_divey", "Ambience_touristy", "Ambience_trendy", "Ambience_upscale", "Ambience_casual", "RestaurantsGoodForGroups", "Caters", "WiFi", "RestaurantsReservations", "RestaurantsTakeOut", "HappyHour", "GoodForDancing", "RestaurantsTableService", "OutdoorSeating", "RestaurantsDelivery", "BestNights_monday", "BestNights_tuesday", "BestNights_friday", "BestNights_wednesday", "BestNights_thursday", "BestNights_sunday", "BestNights_saturday", "GoodForMeal_dessert", "GoodForMeal_latenight", "GoodForMeal_lunch", "GoodForMeal_dinner", "GoodForMeal_breakfast", "GoodForMeal_brunch", "CoatCheck", "Smoking", "DriveThru", "DogsAllowed", "BusinessAcceptsBitcoin", "Open24Hours", "BYOBCorkage", "BYOB", "Corkage", "DietaryRestrictions_dairy-free", "DietaryRestrictions_gluten-free", "DietaryRestrictions_vegan", "DietaryRestrictions_kosher", "DietaryRestrictions_halal", "DietaryRestrictions_soy-free", "DietaryRestrictions_vegetarian", "AgesAllowed", "RestaurantsCounterService"};
        try {
            String attributesQuery = "SELECT * " +
                    "FROM yelp_business_attributes " +
                    "WHERE MATCH (business_id) " +
                    "AGAINST(" + '"' + business_id + '"' + ")" +
                    " LIMIT 1";
            java.sql.Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(attributesQuery);
            while (results.next()) {
                for (int i = 2; i <= 82; i++) {
                    attributes.put(attributeHeadings[i - 2], results.getString(i));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return attributes;
    }




    public String[] getBusinessHours(String business_id) {
        String[] businessHours = new String[7];
        try {
            String attributesQuery = "SELECT * " +
                    "FROM yelp_business_hours " +
                    "WHERE MATCH (business_id)" +
                    "AGAINST (" + '"' + business_id + '"' + ")" +
                    " LIMIT 1";
            java.sql.Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(attributesQuery);
            while (results.next()) {
                for (int i = 2; i <= 8; i++) {
                    businessHours[i - 2] = results.getString(i);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return businessHours;
    }

    public ArrayList<Business> categorySearch(String category, int start, int limit) {
        ArrayList<Business> businesses = new ArrayList<Business>();
        try {
            String businessQuery = "SELECT * " +
                    "FROM yelp_business " +
                    "WHERE MATCH(categories) " +
                    "AGAINST " + "(" + '\'' + category + '\'' + ")" +
                    "LIMIT " + start + "," + limit;
            java.sql.Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(businessQuery);

            while (results.next()) {
                businesses.add(new Business(results.getString("business_id"), results.getString("name"), results.getString("neighbourhood"), results.getString("address"), results.getString("city"), results.getString("state"), results.getString("postal_code"), results.getDouble("latitude"), results.getDouble("longitude"), results.getDouble("stars"), results.getInt("review_count"), results.getInt("is_open"), results.getString("categories")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return businesses;
    }

    public ArrayList<Business> businessSearch(String name, int start, int limit) {
        println(canvas.millis());
        ArrayList<Business> businesses = new ArrayList<Business>();
        try {
            String businessQuery = "SELECT * " +
                    "FROM yelp_business " +
                    "WHERE MATCH(name) " +
                    "AGAINST " + "(" + '\'' + name + '\'' + ")" +
                    "LIMIT " + start + "," + limit;
            java.sql.Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(businessQuery);

            while (results.next()) {
                businesses.add(new Business(results.getString("business_id"), results.getString("name"), results.getString("neighbourhood"), results.getString("address"), results.getString("city"), results.getString("state"), results.getString("postal_code"), results.getDouble("latitude"), results.getDouble("longitude"), results.getDouble("stars"), results.getInt("review_count"), results.getInt("is_open"), results.getString("categories")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        println(canvas.millis());
        return businesses;
    }

    public ArrayList<Business> citySearch(String city, int start, int limit) {
        ArrayList<Business> businesses = new ArrayList<Business>();
        try {
            String businessQuery = "SELECT * " +
                    "FROM yelp_business " +
                    "WHERE MATCH(city) " +
                    "AGAINST " + "(" + '\'' + city + '\'' + ")" +
                    "LIMIT " + start + "," + limit;
            java.sql.Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(businessQuery);

            while (results.next()) {
                businesses.add(new Business(results.getString("business_id"), results.getString("name"), results.getString("neighbourhood"), results.getString("address"), results.getString("city"), results.getString("state"), results.getString("postal_code"), results.getDouble("latitude"), results.getDouble("longitude"), results.getDouble("stars"), results.getInt("review_count"), results.getInt("is_open"), results.getString("categories")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return businesses;
    }

    ArrayList<Integer> visitorsList = new ArrayList<Integer>();


    public String getBusinessVisitors(String business_id) {
        try {
            String businessVisitorsQuery = "SELECT * " +
                    "FROM check_ins " +
                    "WHERE business_id " +
                    "LIKE " + '"' + business_id + '"' +
                    " LIMIT " + 168; // 7*24 so the vistors for the last week
            java.sql.Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(businessVisitorsQuery);
            while (results.next()) {
                int visitorNum = Integer.parseInt(results.getString(0));
                visitorsList.add(visitorNum);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getRow(String review_id) {

        return 0;
    }

    public String getTip(String business_id) {
        try {
            String tipQuery = "SELECT text " +
                    "FROM yelp_tip " +
                    "WHERE MATCH (business_id) AGAINST(" + '\"' + business_id + '\"' + ")" +
                    " LIMIT 1";
            java.sql.Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(tipQuery);
            while (results.next()) {
                return results.getString("text");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        println("null : " + business_id);
        return null;
    }
}
