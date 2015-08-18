package com.sourabh.assemblers;

import com.sourabh.entity.Filter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by saurabh goyal on 8/15/2015.
 */
public class FilterAssembler {

    public ArrayList<Filter> responseToFilter(String json){
        ArrayList<Filter> Filter=new ArrayList<Filter>();
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
                Filter Filter1=new Filter();
                //Set company

                try {
                    Filter1.setLocation(jsonObject.getString("location"));
                    Filter1.setPricefrom(jsonObject.getString("pricefrom"));
                    Filter1.setPriceto(jsonObject.getString("priceto"));
                    Filter1.setType(jsonObject.getString("type"));
                    Filter1.setPosted_by(jsonObject.getString("posted_by"));
                    Filter1.setSaletype(jsonObject.getString("saletype"));







                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Filter.add(Filter1);
            }
        }


        return Filter;



    }
    
    
}
