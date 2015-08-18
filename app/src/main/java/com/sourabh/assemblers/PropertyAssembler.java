package com.sourabh.assemblers;

import com.sourabh.entity.News;
import com.sourabh.entity.PropertyDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by saurabh goyal on 6/7/2015.
 */
public class PropertyAssembler {


    public ArrayList<PropertyDetails> responseToPropertyDetails(String json){
        ArrayList<PropertyDetails> propertyDetails=new ArrayList<PropertyDetails>();
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
                PropertyDetails propertyDetails1=new PropertyDetails();
                //Set company

                try {
                    propertyDetails1.setNegotiable(jsonObject.getString("negotiable"));
                    propertyDetails1.setAddress(jsonObject.getString("address"));
                    propertyDetails1.setBreadth(jsonObject.getString("breadth"));
                    propertyDetails1.setCity(jsonObject.getString("city"));
                    propertyDetails1.setDate(jsonObject.getString("datee"));
                    propertyDetails1.setId(jsonObject.getString("id"));
                    propertyDetails1.setLatitude(jsonObject.getString("latitude"));
                    propertyDetails1.setLength(jsonObject.getString("length"));
                    propertyDetails1.setLongitude(jsonObject.getString("longitude"));
                    propertyDetails1.setPrice(jsonObject.getString("price"));
                    propertyDetails1.setPropertyDetail(jsonObject.getString("propertydetail"));
                    propertyDetails1.setPropertyImage(jsonObject.getString("propertyimage"));
                    propertyDetails1.setPropertyType(jsonObject.getString("propertytype"));
                    propertyDetails1.setState(jsonObject.getString("state"));
                    propertyDetails1.setTransferDate(jsonObject.getString("transverdate"));
                    propertyDetails1.setTransverto(jsonObject.getString("transverto"));
                    propertyDetails1.setType(jsonObject.getString("type"));
                    propertyDetails1.setUid(jsonObject.getString("uid"));


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                propertyDetails.add(propertyDetails1);
            }
        }


        return propertyDetails;



    }




}
