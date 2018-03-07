import java.util.Scanner;
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

        while ((nextLine = reader.readNext()) != null && count<= 1000) {
            reviews.add(new Review(nextLine[0], nextLine[1], nextLine[2], nextLine[3],  Integer.parseInt(nextLine[4]), nextLine[5], nextLine[6], Integer.parseInt(nextLine[7]), Integer.parseInt(nextLine[8]), Integer.parseInt(nextLine[9])));
            count++;
        }

        println("Time taken: " + millis());

        for(Review r : reviews){
            if(r.user_id.equals("3wB-JjsUjkvdNUDRnChsVg")){
                println(r);
            }
        }
    } catch (Exception e){
        println(count);
    }
}