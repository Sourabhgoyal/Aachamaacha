package com.sourabh.entity;

/**
 * Created by saurabh goyal on 6/4/2015.
 */
public class Indivisual {

    String id;
    String name;
    String password;
    String state;
    String city;
    String address;
    String mobile;
    String email;
    String type;

    public Indivisual() {
    }

    public Indivisual(String id, String name, String password, String state, String city, String address, String mobile, String email, String type) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.state = state;
        this.city = city;

        this.address = address;
        this.mobile = mobile;
        this.email = email;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    String toJsonString(){
        String string="[{\"id\":\""+getId()+"\",\"name\":\""+getName()+"\",\"password\":\""+getPassword()+"\",\"state\":\""+getState()+"\",\"city\":\""+getCity()+"\",\"address\":\""+getAddress()+"\",\"mobile\":\""+getMobile()+"\",\"email\":\""+getEmail()+"\",\"type\":\""+getType() +"\"}]";
        return string;


    }
}
