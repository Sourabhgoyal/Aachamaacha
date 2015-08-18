package com.sourabh.entity;

/**
 * Created by saurabh goyal on 8/15/2015.
 */
public class Filter {
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPricefrom() {
        return pricefrom;
    }

    public void setPricefrom(String pricefrom) {
        this.pricefrom = pricefrom;
    }

    public String getPriceto() {
        return priceto;
    }

    public void setPriceto(String priceto) {
        this.priceto = priceto;
    }

    public String getPosted_by() {
        return posted_by;
    }

    public void setPosted_by(String posted_by) {
        this.posted_by = posted_by;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSaletype() {
        return saletype;
    }

    public void setSaletype(String saletype) {
        this.saletype = saletype;
    }

    public String location;
    public String pricefrom;
    public String priceto;
    public String type;
    public String posted_by;

    public String saletype;
    public  String toJsonString(){
        String string="{\"location\":\""+getLocation()+"\",\"pricefrom\":\""+getPricefrom()+"\",\"priceto\":\""+getPriceto()+"\",\"type\":\""+getType()+"\",\"posted_by\":\""+getPosted_by()+"\",\"saletype\":\""+getSaletype()+"\"}";
        return string;
    }


}
