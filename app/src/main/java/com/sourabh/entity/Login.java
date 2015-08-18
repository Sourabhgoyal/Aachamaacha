package com.sourabh.entity;

/**
 * Created by saurabh goyal on 6/4/2015.
 */
public class Login {

    String id;

    String password;
    String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    String toJsonString(){
        String string="[{\"id\":\""+getId()+"\",\"name\":\""+getName()+"\",\"password\":\""+getPassword()+"\"}]";
        return string;


    }
}
