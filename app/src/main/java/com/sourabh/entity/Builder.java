package com.sourabh.entity;

/**
 * Created by saurabh goyal on 6/4/2015.
 */
public class Builder {

    String id;
    String name;
    String password;
    String state;
    String city;
    String address;
    String mobile;
    String email;



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


    String toJsonString(){
        String string="[{\"id\":\""+getId()+"\",\"name\":\""+getName()+"\",\"password\":\""+getPassword()+"\",\"state\":\""+getState()+"\",\"city\":\""+getCity()+"\",\"address\":\""+getAddress()+"\",\"mobile\":\""+getMobile()+"\",\"email\":\""+getEmail()+"\"}]";
                return string;


    }



}
