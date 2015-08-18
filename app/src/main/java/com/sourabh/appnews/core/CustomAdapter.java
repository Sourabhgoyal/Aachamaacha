package com.sourabh.appnews.core;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sourabh.businessService.PropertyService;
import com.sourabh.entity.PropertyDetails;
import com.sourabh.entity.RowIconLoad;
import com.sourabh.utility.LoadImagesAndSerialize;
import com.sourabh.utility.Utilities;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by saurabh goyal on 5/21/2015.
 */
public class CustomAdapter extends ArrayAdapter<PropertyDetails> {
    Context context;
    //    int images[];
//    String[] titlearray;
//    String[] mdesc;
    ArrayList<PropertyDetails> propertylist;
    ImageView mimage;
    LoadImage loadImage;

    HashMap<String, View> layoutMap;
    int resource;
//method of passing a getting things here and then passing

    //    public CustomAdapter(Context c, int imgs[], String[] titles, String[] desc) {
//        super(c, R.layout.single_row,R.id.textView, titles);
//        this.context=c;
//        this.images=imgs;
//        this.titlearray=titles;
//        this.mdesc=desc;
//    }
    public CustomAdapter(Context context, int resource, ArrayList<PropertyDetails> propertylist) {
        super(context, resource,propertylist);
        this.context = context;
        this.propertylist = propertylist;
        this.resource=resource;
        layoutMap=new HashMap<String, View>();
//        if(propertylist!=null){
//            this.propertylist=propertylist;
//        }else{
//            return;
//        }

    }




    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LinearLayout row;
        if(convertView==null)
        {
            row = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi;
            vi = (LayoutInflater)getContext().getSystemService(inflater);

            if(propertylist.size()==0)
                vi.inflate(R.layout.emptycart, row, true);
            else
            vi.inflate(resource, row, true);

        }
        else
        {
            row = (LinearLayout) convertView;
        }


         //only for the first time row is created when we need to load the things
//        mimage = (ImageView) row.findViewById(R.id.imageView);//here find view by id is called many time
        TextView mtitle = (TextView) row.findViewById(R.id.textView);
        TextView mdescription = (TextView) row.findViewById(R.id.textView2);

        ImageView delete = (ImageView) row.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncPostProperty asyncPostProperty = new AsyncPostProperty(propertylist.get(position).getId());
                asyncPostProperty.execute();


            }
        });
        //method of passing a getting things here and then passing

//        mimage.setImageResource(images[position]);
//        mtitle.setText(titlearray[position]);
//        mdescription.setText(mdesc[position]);


        mtitle.setText(propertylist.get(position).getPropertyType());
//        AsyncImageProperty asyncImageProperty = new AsyncImageProperty();
//        asyncImageProperty.execute(propertylist.get(position).getPropertyImage());
//        byte[] imageAsBytes = Base64.decode(propertylist.get(position).getPropertyImage().getBytes(), Base64.DEFAULT);
//        Bitmap decodedByte = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
//       mimage.setImageBitmap(decodedByte);

        mdescription.setText(propertylist.get(position).getCity());
//        if(propertylist.get(position).getPropertyImage()!=null && !propertylist.get(position).getPropertyImage().equals("")){
//            layoutMap.put(propertylist.get(position).getPropertyImage(), row);
//            loadImage=new LoadImage();
//            loadImage.execute(propertylist.get(position).getPropertyImage());
//        }
        return row;

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
                Utilities.write("ErrorLog", "Encountered error in Load Image doInBackground of Offer List Fragment." + ex.getMessage());
            }
            return rowIconLoad;
        }
        protected void onPostExecute(RowIconLoad rowIconLoad) {
            try{
                if(rowIconLoad != null){
                    ImageView img= (ImageView)layoutMap.get(rowIconLoad.getUrl()).findViewById(R.id.imageView);
                    img.setImageBitmap(rowIconLoad.getBitmap());
//		           pDialog.dismiss();
                }else{
//		           pDialog.dismiss();
                    //Toast.makeText(getActivity(), getResources().getString(R.string.Image_Error), Toast.LENGTH_SHORT).show();
                }
            }catch(Exception ex){
                Utilities.write("ErrorLog", "Encountered error in Load Image postExecute of Offer List Fragment." + ex.getMessage());
            }
        }
    }
    public void navigateHome() {

        NewsList.clearAll();
        FragmentManager fragmentManager =((Activity)
                context).getFragmentManager();
        PagerFragment rFragment = new PagerFragment();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.content_frame, rFragment);
        ft.commit();
    }

    @Override
    public int getCount() {
      return propertylist==null?0:propertylist.size();

    }


    class AsyncPostProperty extends AsyncTask<String, Void, Void> {
        String propertyId;
        FragmentTransaction fragmentTransaction;


        public AsyncPostProperty(String propertyId) {
            this.propertyId=propertyId;

        }

        @Override
        protected Void doInBackground(String... arg0) {


            PropertyService propertyService = new PropertyService();


            propertyService.deleteProperty(propertyId);

            return null;
        }

        protected void onPostExecute(Void result) {

            Toast.makeText(getContext(), "Deleted Property", Toast.LENGTH_SHORT).show();
            navigateHome();



//            if(authentic){
//                navigationIntent=new Intent(getApplicationContext(),MainActivity.class);
//                startActivity(navigationIntent);
//            }
        }


    }
    class AsyncImageProperty extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... uri) {
            URL url = null;
            try {
                url = new URL(uri[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Bitmap bmp = null;
            try {
                if (url != null) {
                    bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bmp;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            mimage.setImageBitmap(bitmap);

        }
    }

}