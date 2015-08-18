package com.sourabh.businessService;

import java.util.ArrayList;
import java.util.List;

import com.sourabh.entity.News;

public interface NewsInterface {
	
	ArrayList<News> fetchNews(String lastOfferId, String location);
	String fetchClosedNews(String location);

}
