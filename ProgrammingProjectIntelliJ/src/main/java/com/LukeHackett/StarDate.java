package com.LukeHackett;

public class StarDate {
    /*
    Written by LH - object to simplify calculating average stars per month in graph
     */

    private final float star;
    private final int date;

    StarDate(float star, int date){
        this.star = star;
        this.date = date;
    }

    public float getStar() {
        return star;
    }

    public int getDate() {
        return date;
    }
}
