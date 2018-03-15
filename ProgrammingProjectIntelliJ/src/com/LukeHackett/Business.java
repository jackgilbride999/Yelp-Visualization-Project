package com.LukeHackett;

public class Business {
    String business_id;
    String name;
    String neighborhood;
    String address;
    String city;
    String state;
    String postal_code;
    double latitude;
    double longitude;
    int stars;
    int review_count;
    int is_open;
    String categories;

    public Business(String business_id, String name, String neighborhood, String address, String city, String state, String postal_code, double latitude, double longitude, int stars, int review_count, int is_open, String categories) {
        this.business_id = business_id;
        this.name = name.substring(1, name.length() - 1);
        this.neighborhood = neighborhood;
        this.address = address.substring(1, address.length() - 1);
        this.city = city;
        this.state = state;
        this.postal_code = postal_code;
        this.latitude = latitude;
        this.longitude = longitude;
        this.stars = stars;
        this.review_count = review_count;
        this.is_open = is_open;
        this.categories = categories;
    }

    public String getBusiness_id() {
        return business_id;
    }

    public void setBusiness_id(String business_id) {
        this.business_id = business_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public int getReview_count() {
        return review_count;
    }

    public void setReview_count(int review_count) {
        this.review_count = review_count;
    }

    public int isIs_open() {
        return is_open;
    }

    public void setIs_open(int is_open) {
        this.is_open = is_open;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "Business: " +
                "business_id=" + business_id +
                ", name='" + name +
                ", neighborhood='" + neighborhood +
                ", address='" + address +
                ", city='" + city +
                ", state='" + state +
                ", postal_code='" + postal_code +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", stars=" + stars +
                ", review_count=" + review_count +
                ", is_open=" + is_open +
                ", categories='" + categories
                ;
    }
}