package com.sourabh.appnews.core;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sourabh.entity.News;
import com.sourabh.entity.RowIconLoad;
import com.sourabh.entity.Search;
import com.sourabh.utility.LoadImagesAndSerialize;
import com.sourabh.utility.Utilities;

import java.util.HashMap;
import java.util.List;

public class NewsListAdapterSearch extends ArrayAdapter<Search> {

    int resource;
    String response;
    Context context;
    List<Search> itemsList;

	LoadImage loadImage;

	HashMap<String, LinearLayout> layoutMap;
    @Override
	public int getCount() {
		return itemsList==null?0:itemsList.size();
	}


	//Initialize adapter
    public NewsListAdapterSearch(Context context, int resource, List<Search> items) {
        super(context, resource, items);
        this.context=context;
        this.resource=resource;
        layoutMap=new HashMap<String, LinearLayout>();
        if(items!=null){
        this.itemsList=items;
        }else{
        	return;
        }
    }
     
     
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        
        //Get the current alert object
		Search searchProperty = getItem(position);
        
        LinearLayout newsView;
        //Inflate the view
        if(convertView==null)
        {
        	newsView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi;
            vi = (LayoutInflater)getContext().getSystemService(inflater);
            vi.inflate(resource, newsView, true);
        }
        else
        {
        	newsView = (LinearLayout) convertView;
        }
      //  ImageView img=(ImageView)offerView.findViewById(R.id.offerIconImage);
        TextView txt1 = (TextView) newsView.findViewById(R.id.newsName);
		
		//TextView txt4 = (TextView) offerView.findViewById(R.id.cost);
		//img.setBackground(R.drawable.ic_launcher);
		
		txt1.setText(searchProperty.getProperty_type().replaceAll("\\\\n", "\n"));
		
		if(searchProperty.getImage()!=null && !searchProperty.getImage().equals("")){
			layoutMap.put(searchProperty.getImage(), newsView);
			loadImage=new LoadImage();
        	loadImage.execute(searchProperty.getImage());
        }
         
        return newsView;
    }
    private class LoadImage extends AsyncTask<String, String,RowIconLoad> {
	    @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
//	            pDialog = new ProgressDialog(MainActivity.this);
//	            pDialog.setMessage("Loading Image ....");
//	            pDialog.show();
	    }
	       protected RowIconLoad doInBackground(String... args) {
	         Bitmap bitmap = null;
	         RowIconLoad rowIconLoad=new RowIconLoad();
			try {
				bitmap=new LoadImagesAndSerialize().fetchImages(args[0],layoutMap.get(args[0]).getContext());
				rowIconLoad.setUrl(args[0]);
				rowIconLoad.setBitmap(bitmap);
			    	   
			}catch(Exception ex){
				Utilities.write("ErrorLog", "Encountered error in Load Image doInBackground of Offer List Fragment."+ex.getMessage());
			}
	      return rowIconLoad;
	       }
	       protected void onPostExecute(RowIconLoad rowIconLoad) {
	       try{
	    	   if(rowIconLoad != null){
		        	 ImageView img= (ImageView)layoutMap.get(rowIconLoad.getUrl()).findViewById(R.id.newsIconImage);
		           img.setImageBitmap(rowIconLoad.getBitmap());
//		           pDialog.dismiss();
		         }else{
//		           pDialog.dismiss();
		           //Toast.makeText(getActivity(), getResources().getString(R.string.Image_Error), Toast.LENGTH_SHORT).show();
		         }
	       }catch(Exception ex){
				Utilities.write("ErrorLog", "Encountered error in Load Image postExecute of Offer List Fragment."+ex.getMessage());
			}
	       }
	   }
 
}