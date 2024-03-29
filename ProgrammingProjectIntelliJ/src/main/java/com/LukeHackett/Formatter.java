package com.LukeHackett;

import processing.core.PApplet;

import java.util.ArrayList;

public class Formatter {
    void formatReview(PApplet canvas, Review r) {
        // Splits the review in order to format with a specific line length and then sets the review to the formatted review

        String[] splitReview = r.getReview().split("");
        StringBuilder formattedReview = new StringBuilder();
        int existingLines = 0;
        for (String aSplitReview : splitReview) {
            if (aSplitReview.equals("\n")) {
                existingLines++;
            }
        }
        formattedReview.append(r.getUser_name()).append(":").append("  ");

        formattedReview.append("\n");
        boolean toNextLine = false;
        int lines = 3;
        for (int i = 0; i < splitReview.length; i++) {
            // Checks to see if line length has exceeded
            if ((i % Main.LINE_LENGTH == 0) && (i != 0)) {
                toNextLine = true;
            }

            // If line length has been exceeded it will put a new line at the next whitespace
            if (toNextLine && splitReview[i].equals(" ")) {
                splitReview[i] = "\n";
                toNextLine = false;
            }
            if (splitReview[i].equals("\n")) {
                lines++;
            }
            formattedReview.append(splitReview[i]);
        }

        r.setNumberOfLines(lines+1);
        r.setFormattedReview(formattedReview.toString());
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
