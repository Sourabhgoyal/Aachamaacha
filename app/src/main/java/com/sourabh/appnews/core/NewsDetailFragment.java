package com.sourabh.appnews.core;


import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sourabh.database.Thedb;
import com.sourabh.entity.News;
import com.sourabh.utility.LoadImagesAndSerialize;
import com.sourabh.utility.Utilities;

public class NewsDetailFragment extends Activity{
	
	
	public static News news;
	ImageView img ;
	LoadImage loadImage;
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(loadImage!=null)
		loadImage.cancel(true);
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		try {
			ArrayList<String> arrayFiles=Utilities.read(getResources().getString(R.string.LBL_SAVED_PAGES), true);
			if(arrayFiles!=null && arrayFiles.contains(news.getTitle())){
				getMenuInflater().inflate(R.menu.menu_offer_detail_saved_offer, menu);
			}else{
				getMenuInflater().inflate(R.menu.detail_menu, menu);	
			}
		} catch (NotFoundException e) {
			Utilities.write("ErrorLog", "Encountered error in offer detail fragment on create options menu."+e.getMessage());
	    		}		
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case R.id.share:
			Intent i=new Intent(android.content.Intent.ACTION_SEND);
			i.setType("text/plain");
			i.putExtra(android.content.Intent.EXTRA_SUBJECT,news.getTitle());
			String toShare=news.toShare();
			if(toShare!=null){
			i.putExtra(android.content.Intent.EXTRA_TEXT, toShare);
			startActivity(Intent.createChooser(i,"Share via"));
			}else{
				Toast.makeText(getApplicationContext(), getResources().getString(R.string.MSG_NEWS_NOT_AVAILABLE), Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.save:
			Utilities.write(news.getTitle(), news.toShare());
			Utilities.write(getResources().getString(R.string.LBL_SAVED_PAGES), news.getTitle());
			Toast.makeText(getApplicationContext(), getResources().getString(R.string.MSG_NEWS_SAVED), Toast.LENGTH_LONG).show();
			Intent initializer2Intent=new Intent(getApplicationContext(),Intitializer.class);
			startActivity(initializer2Intent);
			finish();
			break;
		case R.id.delete:
			Utilities.deleteFile(news.getTitle());
			Toast.makeText(getApplicationContext(), getResources().getString(R.string.MSG_FILE_DELETED), Toast.LENGTH_SHORT).show();
			Intent initializerIntent=new Intent(getApplicationContext(),Intitializer.class);
			startActivity(initializerIntent);
			finish();

			break;
		default:
			super.onBackPressed();
		}
		
		return super.onOptionsItemSelected(item);

	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news_detail_layout);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.rgb(51, 181, 229)));
		getActionBar().setTitle("Property Details");
		View v = null;
		try{
		if(news.getImages()!=null && !news.getImages().equals("")){
		loadImage=new LoadImage();
		loadImage.execute(news.getImages());
			}
		TextView newsTitle = (TextView)findViewById(R.id.newsDetailNewsName);
		TextView newsReporter = (TextView) findViewById(R.id.newsDetailNewsReporter);
		TextView detail = (TextView) findViewById(R.id.newsDetailDetail);
		
		LinearLayout videoArea=(LinearLayout)findViewById(R.id.hideAbleVideoArea);
		if(news.getAdvertiseImage()==null || news.getAdvertiseImage().equals("")){
			videoArea.setVisibility(View.GONE);
		}
		// Setting currently selected river name in the TextView
		newsTitle.setText(news.getTitle());
		Thedb databaseHandler=Thedb.getInstance(getApplicationContext());
		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyyMMddHHmmss");
		
		DateTime dateTime = formatter.parseDateTime(news.getPostingDate());
		
		
		detail.setText(Html.fromHtml(news.getDetail().replaceAll("\\\\n", "\n")));//+"\n\n"+getResources().getString(R.string.LBL_EXPIRES_ON) +" : "+dateTime.toString(DateTimeFormat.forPattern("dd-MMM-yyyy HH:mm:ss")));
		newsReporter.setText(news.getReporter()+"\t"+dateTime.toString(DateTimeFormat.forPattern("dd-MMM-yyyy HH:mm:ss")));
		

		// Updating the action bar title
		//getActivity().getActionBar().setTitle(rivers[position]);
		}catch(Exception ex){
			Utilities.write("ErrorLog", "Encountered error in onCreateView of News Detail Fragment."+ex.getMessage());
		}		

	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
				return null;
	}

	public void setNews(News news) {
		this.news=news;
		
	}

	private class LoadImage extends AsyncTask<String, String, Bitmap> {
	    @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
//	            pDialog = new ProgressDialog(MainActivity.this);
//	            pDialog.setMessage("Loading Image ....");
//	            pDialog.show();
	    }
	       protected Bitmap doInBackground(String... args) {
	         Bitmap bitmap = null;
			try {
				bitmap=new LoadImagesAndSerialize().fetchImages(args[0],getApplicationContext());
			}catch(Exception ex){
				Utilities.write("ErrorLog", "Encountered error in Load Image doInBackground of News Detail Fragment."+ex.getMessage());
			}
	      return bitmap;
	       }
	       protected void onPostExecute(final Bitmap image) {
	        try{
	    	   if(image != null){
	        	 img= (ImageView)findViewById(R.id.newsDetailImage);
	           img.setImageBitmap(image);
	           img.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
				Intent fullScreenImageIntent=new Intent(getApplicationContext(),FullScreenImage.class);
				FullScreenImage.bitmap=image;
				startActivity(fullScreenImageIntent);
				}
			});
//	           pDialog.dismiss();
	         }else{
//	           pDialog.dismiss();
	           //Toast.makeText(getActivity(), getResources().getString(R.string.Image_Error), Toast.LENGTH_SHORT).show();
	         }
	        }catch(Exception ex){
				Utilities.write("ErrorLog", "Encountered error in Load Image onPostExecute onPostExecute of News Detail Fragment."+ex.getMessage());
			}
	       }
	   }
	public void playVideo(View v){
	
		
		if(news.getAdvertiseImage().contains("youtube")){
			Intent i = new Intent(Intent.ACTION_VIEW);
			i.setData(Uri.parse(news.getAdvertiseImage()));
			this.startActivity(i);	
		}else{
			VideoPlayerActivity.url=news.getAdvertiseImage();
		Intent mediaIntent=new Intent(getApplicationContext(), VideoPlayerActivity.class);
		startActivity(mediaIntent);
		}
	}
}
