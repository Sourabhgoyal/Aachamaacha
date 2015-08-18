package com.sourabh.entity;

import android.util.Property;

/**
 * Created by saurabh goyal on 6/3/2015.
 */
  public class PropertyDetails {


    public String id;
    public String propertytype;
    public String propertyimage;
    public String propertydetail;
    public String uid;
    public String transverto;
    public String transverdate;
    public String length;
    public String breadth;
    public String price;
    public String negotiable;
    public String latitude;
    public String longitude;
    public String city;
    public String state;
    public String address;
    public String type;
    public String date;

    public String getId() {return id;}
    public void setId(String id) {
        this.id = id;
    }


    public String getPropertyDetail() {return propertydetail;}
    public void setPropertyDetail(String propertydetail) {
        this.propertydetail= propertydetail;
    }
    public String getPropertyType() {return propertytype;}
    public void setPropertyType(String propertytype) {
        this.propertytype= propertytype;
    }
    public String getPropertyImage() {return propertyimage;}
    public void setPropertyImage(String propertyimage) {
        this.propertyimage= propertyimage;
    }
    public String getUid() {return uid;}
    public void setUid(String uid) {
        this.uid= uid;
    }
    public String getTransverto() {return transverto;}
    public void setTransverto(String transverto) {
        this.transverto= transverto
        ;
    }
    public String getTransferDate() {return transverdate;}
    public void setTransferDate(String transverdate) {
        this.transverdate= transverdate;
    }
    public String getLength() {return length;}
    public void setLength(String length) {
        this.length= length;
    }
    public String getBreadth() {return breadth;}
    public void setBreadth(String breadth) {
        this.breadth= breadth;
    }
    public String getPrice() {return price;}
    public void setPrice(String price) {
        this.price= price;
    }
    public String getNegotiable() {return negotiable;}
    public void setNegotiable(String negotiable) {
        this.negotiable= negotiable;
    }
      public String getLatitude() {return latitude;}
    public void setLatitude(String latitude) {
        this.latitude= latitude;
    }
    public String getLongitude() {return longitude;}
    public void setLongitude(String longitude) {
        this.longitude= longitude;
    }
    public String getCity() {return city;}
    public void setCity(String city) {
        this.city= city;
    }
    public String getState() {return state;}
    public void setState(String state) {
        this.state= state;
    }
    public String getAddress() {return address;}
    public void setAddress(String address) {
        this.address= address;
    }
    public String getType() {return type;}
    public void setType(String type) {
        this.type= type;
    }
    public String getDate() {return date;}
    public void setDate(String date) {
        this.date= date;
    }
   public  String toJsonString(){
        String string="{\"id\":\""+getId()+"\",\"propertytype\":\""+getPropertyType()+"\",\"propertyimage\":\""+getPropertyImage()+"\",\"propertydetail\":\""+getPropertyDetail()+"\",\"datee\":\""+getDate()+"\",\"uid\":\""+getUid()+"\",\"transverto\":\""+getTransverto()+"\",\"transverdate\":\""+getTransferDate()+"\",\"length\":\""+getLength()+"\",\"breadth\":\""+getBreadth()+"\",\"price\":\""+getPrice()+"\",\"negotiable\":\""+getNegotiable()+"\",\"latitude\":\""+getLatitude()+"\",\"longitude\":\""+getLongitude()+"\",\"city\":\""+getCity()+"\",\"state\":\""+getState()+"\",\"address\":\""+getAddress()+"\",\"type\":\""+getType()+"\"}";
        return string;
    }






}
