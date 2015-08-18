package com.sourabh.businessService;

import com.sourabh.DTO.RegisterDTO;
import com.sourabh.DTO.UserDetailsDTO;
import com.sourabh.assemblers.LoginAssembler;
import com.sourabh.assemblers.NewsAssembler;
import com.sourabh.assemblers.PropertyAssembler;
import com.sourabh.constants.ServiceConstants;
import com.sourabh.entity.News;
import com.sourabh.entity.PropertyDetails;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by saurabh goyal on 6/5/2015.
 */
public class PropertyService {
    ArrayList<PropertyDetails> propertylist ;//propertylist of type object
    Object response;
    static String returnValue;



    public String postProperty(PropertyDetails propertyDetails){



        SoapObject request = new SoapObject(ServiceConstants.WSDL_TARGET_NAMESPACE,ServiceConstants.POST_PROPERTY_OPERATION);
        PropertyInfo pi=new PropertyInfo();
        pi.setName("json");
        pi.setValue(propertyDetails.toJsonString());
        pi.setType(String.class);
        request.addProperty(pi);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransport = new HttpTransportSE(ServiceConstants.PROPERTY_SOAP_ADDRESS);
        Object response=null;
      returnValue=null;
        try {
            httpTransport.call(ServiceConstants.WSDL_TARGET_NAMESPACE+ServiceConstants.POST_PROPERTY_OPERATION, envelope);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            response =envelope.getResponse();
            returnValue=response.toString();
            return returnValue;
        } catch (SoapFault e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return returnValue;
        }





    }








    public ArrayList<PropertyDetails> fetchProperty(String userid) {
        PropertyAssembler assembler=new PropertyAssembler();
        SoapObject request = new SoapObject(ServiceConstants.WSDL_TARGET_NAMESPACE,ServiceConstants.FETCH_ALL_PROPERTY_OPERATION);
        PropertyInfo pi=new PropertyInfo();
        pi.setName("userId");
        pi.setValue(userid);
        pi.setType(String.class);
        request.addProperty(pi);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);

        envelope.dotNet = true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransport = new HttpTransportSE(ServiceConstants.PROPERTY_SOAP_ADDRESS);
        Object response=null;
        try
        {
            httpTransport.call(ServiceConstants.WSDL_TARGET_NAMESPACE+ServiceConstants.FETCH_ALL_PROPERTY_OPERATION, envelope);
            response =envelope.getResponse();
            propertylist=assembler.responseToPropertyDetails(response.toString());
            return propertylist ;
        }
        catch (Exception exception)
        {
            return propertylist;
        }

    }
    public String updateProperty(PropertyDetails pd){
        SoapObject request = new SoapObject(ServiceConstants.WSDL_TARGET_NAMESPACE,ServiceConstants.UPDATE_PROPERTY_OPERATION);
        PropertyInfo pi=new PropertyInfo();
        pi.setName("json");
        pi.setValue(pd.toJsonString());
        pi.setType(String.class);
        request.addProperty(pi);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransport = new HttpTransportSE(ServiceConstants.PROPERTY_SOAP_ADDRESS);
        Object response=null;
        returnValue=null;
        try {
            httpTransport.call(ServiceConstants.WSDL_TARGET_NAMESPACE+ServiceConstants.UPDATE_PROPERTY_OPERATION, envelope);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            response =envelope.getResponse();
            returnValue=response.toString();
            return returnValue;
        } catch (SoapFault e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return returnValue;
        }







    }
    public String deleteProperty(String pd){
        SoapObject request = new SoapObject(ServiceConstants.WSDL_TARGET_NAMESPACE,ServiceConstants.DELETE_PROPERTY_OPERATION);
        PropertyInfo pi=new PropertyInfo();
        pi.setName("propertyId");
        pi.setValue(pd);
        pi.setType(String.class);
        request.addProperty(pi);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransport = new HttpTransportSE(ServiceConstants.PROPERTY_SOAP_ADDRESS);
        Object response=null;
        returnValue=null;
        try {
            httpTransport.call(ServiceConstants.WSDL_TARGET_NAMESPACE+ServiceConstants.DELETE_PROPERTY_OPERATION, envelope);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            response =envelope.getResponse();
            returnValue=response.toString();
            return returnValue;
        } catch (SoapFault e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return returnValue;
        }





    }











//        request.addProperty(pi);
//        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
//                SoapEnvelope.VER11);
//
//        envelope.dotNet = true;
//
//        envelope.setOutputSoapObject(request);
//
//        HttpTransportSE httpTransport = new HttpTransportSE(ServiceConstants.NEWS_SERVICESOAP_ADDRESS);
//        Object response=null;
//        try
//        {
//            httpTransport.call(ServiceConstants.WSDL_TARGET_NAMESPACE+ServiceConstants.FETCH_ALL_NEWS_OPERATION, envelope);
//            response =envelope.getResponse();
//            newsList=newsAssembler.responseToNews(response.toString());
//            return newsList ;
//        }
//        catch (Exception exception)
//        {
//            return newsList;
//        }
//
//
//    }




    }



