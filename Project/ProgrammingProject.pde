import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;
import java.io.FileReader;

ArrayList<Review> reviews;

void setup(){
    reviews = new ArrayList<Review>();
    int count = 0;

    try {
        CSVReader reader = new CSVReader(new FileReader(dataPath("reviews.csv")), ',', '"', 1);
        //CSVReader reader = new CSVReader(new FileReader(dataPath("yelp_review.csv")), ',', '"', 1);
        String[] nextLine;

        while ((nextLine = reader.readNext()) != null) {
            if(nextLine != null){
              reviews.add(new Review(nextLine)); //<>//
            }
        }

        println("Time taken: " + millis());

        for(Review r : reviews){
            if(r.userId.equals("3wB-JjsUjkvdNUDRnChsVg")){
                println(r);
            }
        }
    } catch (Exception e){
        println(e);
    }
}