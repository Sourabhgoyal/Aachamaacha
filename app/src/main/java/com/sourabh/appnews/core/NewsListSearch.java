package com.sourabh.appnews.core;

import android.app.ListFragment;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.sourabh.assemblers.SearchAssembler;
import com.sourabh.entity.Search;
import com.sourabh.utility.Utilities;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class NewsListSearch extends ListFragment{

	ArrayList<Search> searchPropertyList;
	public static com.sourabh.entity.Filter filter;
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		
		super.onListItemClick(l, v, position, id);
		
		/*try{
			if(news.size()>=position){
				Intent newsDetail=new Intent(getActivity(),NewsDetailFragment.class);
				NewsDetailFragment.news=news.get(position);
				getActivity().startActivity(newsDetail);

			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
*/
		}

	public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
	    getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
		RequestTask requestTask=new RequestTask();
		requestTask.execute("http://www.aachamaacha.com/propertySearch.php?filterCriteria=" + Uri.encode(filter.toJsonString()));

		
	}

	class RequestTask extends AsyncTask<String, String, String>{

		@Override
		protected String doInBackground(String... uri) {
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response;
			String responseString = null;
			try {
				response = httpclient.execute(new HttpGet(uri[0]));
				StatusLine statusLine = response.getStatusLine();
				if(statusLine.getStatusCode() == HttpStatus.SC_OK){
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					response.getEntity().writeTo(out);
					responseString = out.toString();
					out.close();
				} else{
					//Closes the connection.
					response.getEntity().getContent().close();
					throw new IOException(statusLine.getReasonPhrase());
				}
			}catch (Exception ex){
//				Toast.makeText(getActivity(),ex.getMessage(),Toast.LENGTH_LONG).show();
			}
			return responseString;
		}

		@Override
		protected void onPostExecute(String result) {
			//Do anything with response..
			try {
				SearchAssembler searchAssembler = new SearchAssembler();
				searchPropertyList = searchAssembler.responseToSearch(result);
				populateFilteredOffers();

			}
			catch (Exception e){

			}



		}
	}
	private synchronized void populateFilteredOffers() {
		try{

	    if(searchPropertyList.size()>0 && getActivity()!=null){
	    	try{
	    	NewsListAdapterSearch arrayAdanewspter = new NewsListAdapterSearch(getActivity(), R.layout.single_row,searchPropertyList);
	    	setListAdapter(arrayAdanewspter);
	    	
	    	}catch(Exception ex){
	    		Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_LONG).show();
	    	}
	    }else{
	    	NewsListAdapter arrayAdanewspter = new NewsListAdapter(getActivity(), R.layout.single_row,null);
	    	setListAdapter(arrayAdanewspter);
	    }

		}catch(Exception ex){
			Utilities.write("ErrorLog", "Encountered error in PopulateOffers in Offer List."+ex.getMessage());
		}		
		
        //Set the above adapter as the adapter of choice for our list
	
	}


	


}
