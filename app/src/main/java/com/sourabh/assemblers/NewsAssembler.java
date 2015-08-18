package com.sourabh.assemblers;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sourabh.entity.News;

public class NewsAssembler {
	public ArrayList<News> responseToNews(String json){
		ArrayList<News> newsList=new ArrayList<News>();
		try {
			
			JSONArray jarray=new JSONArray(json);
			for(int i=0;i<jarray.length();i++){
				JSONObject jsonObject=jarray.getJSONObject(i);
				News news=new News();
				//Set company
				news.setCity(jsonObject.getString("city"));
				news.setCountry(jsonObject.getString("country"));
				news.setLocality(jsonObject.getString("locality"));
				news.setState(jsonObject.getString("state"));
				news.setStatus(jsonObject.getString("status"));
				news.setCategoryId(jsonObject.getString("categoryId"));
				news.setDetail(jsonObject.getString("detail"));
				news.setEndDate(jsonObject.getString("endDate"));
				news.setNewsId(jsonObject.getString("newsId"));
				news.setTitle(jsonObject.getString("title"));
				news.setPostingDate(jsonObject.getString("postingDate"));
				news.setImages(jsonObject.getString("images"));
				news.setCommentsId(jsonObject.getString("commentsId"));
				news.setType(jsonObject.getString("type"));
				news.setReporter(jsonObject.getString("reporter"));
				news.setAdvertiseImage(jsonObject.getString("advertiseImage"));
				newsList.add(news);
			}
			return newsList;
		} catch (JSONException e) {
			String bb=e.toString();
			e.printStackTrace();
			return newsList;
		}catch(Exception ex){
			String bb=ex.toString();
			return newsList;
		}
		
	}

}
