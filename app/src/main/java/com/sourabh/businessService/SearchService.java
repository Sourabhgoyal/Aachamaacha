package com.sourabh.businessService;

import com.sourabh.assemblers.SearchAssembler;
import com.sourabh.constants.ServiceConstants;
import com.sourabh.entity.Search;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

/**
 * Created by saurabh goyal on 8/14/2015.
 */
public class SearchService {
    ArrayList<Search> Searchlist ;//propertylist of type object
    public ArrayList<Search> fetchSearch(String userid) {
      SearchAssembler assembler=new SearchAssembler();
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
            Searchlist=assembler.responseToSearch(response.toString());
            return Searchlist ;
        }
        catch (Exception exception)
        {
            return Searchlist;
        }

    }
    
}
