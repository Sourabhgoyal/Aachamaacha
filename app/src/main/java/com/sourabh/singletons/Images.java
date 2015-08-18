package com.sourabh.singletons;

import java.io.Serializable;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;

import com.sourabh.utility.Utilities;

public class Images implements Serializable{
	private static Images images=new Images();
	public HashMap<String, byte[]> imageMap;

	private Images(){
		imageMap=new HashMap<String, byte[]>();
	}
	public static synchronized Images  getInstance(Context context){
		synchronized (images) {
//			images=(Images)Utilities.readFromFile(context, "Images");
			if(images==null){
				
				images=new Images();
			}
		}
		
		return images;
	}
	public static synchronized Images  getFileInstance(Context context){
		synchronized (images) {
			
			if(images==null){
				images=(Images)Utilities.readFromFile(context, "Images");	
				if(images==null)
				images=new Images();
			}
		}
		
		return images;
	}
}
