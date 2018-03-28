package com.LukeHackett;

import java.util.ArrayList;

public class Formatter {
    void formatReview(Review r) {
        // Splits the review in order to format with a specific line length and then sets the review to the formatted review
        //ArrayList<String> formattedReviewList = new ArrayList<String>();
        //for (Review r : reviews) {
        String[] splitReview = r.getReview().split("");
        String formattedReview = "";
        int existingLines = 0;
        for(int i = 0; i<splitReview.length; i++)
        {
            if(splitReview[i].equals("\n"))
            {
                existingLines++;
            }
        }
        formattedReview = formattedReview + r.getUser_name() + ":" + "  ";
//            for (int i = 0; i<r.getStars(); i++)
//            {
//                formattedReview = formattedReview + " * ";
//            }
        formattedReview = formattedReview + "\n";
        boolean toNextLine = false;
        int lines = 3;
        for (int i = 0; i < splitReview.length; i++) {
            // Checks to see if line length has exceeded
            if ((i % Main.LINE_LENGTH == 0) && (i != 0)) {
                toNextLine = true;
            }
            else
            {
                //toNextLine = false;
            }
            // If line length has been exceeded it will put a new line at the next whitespace
            if (toNextLine && splitReview[i].equals(" ")) {
                splitReview[i] = "\n";
                toNextLine = false;
            }
            if (splitReview[i].equals("\n")) {
                lines++;
            }
            formattedReview = formattedReview + splitReview[i];
        }
       // System.out.println("lines = " + lines + "    existingLines = " + existingLines);
        //lines = lines + existingLines;

        r.setNumberOfLines(lines);
        r.setFormattedReview(formattedReview);
        //     formattedReviewList.add(formattedReview);

        // }
    }

    int getTotalHeight(ArrayList<Review> reviews) {
        int totalHeight = 0;
        int lineHeight = 15;
        int lines = 4;

        for (Review r : reviews) {
            String[] split = r.getFormattedReview().split("");

            for (int i = 0; i < split.length; i++) {
                if (split[i].equals("\n")) {
                    lines++;
                }
            }
            totalHeight = lines * lineHeight + Main.BORDER_OFFSET_Y;
        }
        return totalHeight;
    }
}
