package com.sourabh.assemblers;

import com.sourabh.entity.Search;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by saurabh goyal on 8/14/2015.
 */
public class SearchAssembler {



    public ArrayList<Search> responseToSearch(String json){
        ArrayList<Search> Search=new ArrayList<Search>();
        JSONArray jarray= null;
        try {
            jarray = new JSONArray(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (jarray != null) {
            for(int i=0;i<jarray.length();i++){
                JSONObject jsonObject= null;
                try {
                    jsonObject = jarray.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Search Search1=new Search();
                //Set company

                try {
                    Search1.setId(jsonObject.getString("id"));
                    Search1.setName(jsonObject.getString("name"));
                    Search1.setEmail(jsonObject.getString("latitude"));
                    Search1.setContact(jsonObject.getString("contact"));
                    Search1.setClient_type(jsonObject.getString("client_type"));
                    Search1.setPrice(jsonObject.getString("price"));
                    Search1.setProperty_type(jsonObject.getString("property_type"));
                    Search1.setCity(jsonObject.getString("city"));
                    Search1.setImage(jsonObject.getString("image"));
                    Search1.setBhk(jsonObject.getString("bhk"));
                    Search1.setBathroom(jsonObject.getString("bathrom"));
                    Search1.setBalconie(jsonObject.getString("balconie"));
                    Search1.setDate(jsonObject.getString("date"));
                    Search1.setNegotiable(jsonObject.getString("negotiable"));
                    Search1.setProperty_area(jsonObject.getString("property_area"));
                    Search1.setBuiltup(jsonObject.getString("builtup"));
                    Search1.setCarpet(jsonObject.getString("carpet"));
                    Search1.setPrice(jsonObject.getString("price"));
                    Search1.setMonthly_rent(jsonObject.getString("monthly_rent"));
                    Search1.setMaintence_rent(jsonObject.getString("maintence_rent"));
                    Search1.setSecurity_deposit(jsonObject.getString("security_deposit"));
                    Search1.setBachellor_allowed(jsonObject.getString("Bachellor_allowed"));
                    Search1.setDescription(jsonObject.getString("description"));
                    Search1.setPost_type(jsonObject.getString("post_type"));






                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Search.add(Search1);
            }
        }


        return Search;



    }
    
}
