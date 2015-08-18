package com.sourabh.appnews.core;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sourabh.businessService.PropertyService;
import com.sourabh.entity.PropertyDetails;
import com.sourabh.entity.User;
import com.sourabh.utility.ConnectionDetector;
import com.sourabh.utility.Utilities;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import it.sauronsoftware.ftp4j.FTPClient;

import static org.joda.time.format.ISODateTimeFormat.time;

/**
 * Created by saurabh goyal on 5/29/2015.
 */
public class Online_information_Fragment_sell extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    Spinner spinner_type;
    Spinner property_type;
    ImageButton propety_image;
    AutoCompleteTextView State;
    AutoCompleteTextView address;
    static Uri imageUri;
    Spinner city;
    EditText date;
    ArrayAdapter<String> adapter;
    String value;
    String value1;
    String value2;
  static  String filePath=null;
   static String consta=null;
    int pos;
    ArrayAdapter<String> adapter1;
    ArrayAdapter<String> adapter2;
    Button update;
    Button camera_button;
    Button gallery_button;
    Button done;
    String image;
    public static String mode;
        public static PropertyDetails pd;

    EditText length;
    EditText breadth;
    EditText sale_price_in_inr;

    ImageView google_map_view;
    CheckBox chkbox;
    boolean dhd;
    String img_str;


    private String[] cities;
    private String[] property;


    private static final int CAMERA_PIC_REQUEST = 1111;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.online_information_sell, container, false);

        connectionDetector=new ConnectionDetector(getActivity());

        TextView property_image_text = (TextView) rootview.findViewById(R.id.property_imaage_text);
        TextView tell_us_about_property = (TextView) rootview.findViewById(R.id.online_propertymoredetils);

        TextView procession_pricing = (TextView) rootview.findViewById(R.id.possesion_pricing);
        TextView modeOfProperty = (TextView) rootview.findViewById(R.id.headerForProperty);
        if(mode==null)
            mode="";
        if(mode.equals("BUY"))
        modeOfProperty.setText("Post Your Requirements");
        else if(mode.equals("SELL"))
        modeOfProperty.setText("Post Your Property Details");
        else if(mode.equals("UPDATE"))
            modeOfProperty.setText("Your Property Details");
        State = (AutoCompleteTextView) rootview.findViewById(R.id.locality_locality);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.states));
        State.setAdapter(adapter);
        address = (AutoCompleteTextView) rootview.findViewById(R.id.Address);

        length = (EditText) rootview.findViewById(R.id.length);
        breadth = (EditText) rootview.findViewById(R.id.breadth);
        date = (EditText) rootview.findViewById(R.id.date);
        jodadatetime();
        sale_price_in_inr = (EditText) rootview.findViewById(R.id.pricing);

        done = (Button) rootview.findViewById(R.id.done);

        camera_button = (Button) rootview.findViewById(R.id.camer_button);
        gallery_button = (Button) rootview.findViewById(R.id.gallery_button);
        gallery_button.setOnClickListener(this);
        camera_button.setOnClickListener(this);


        cities = getResources().getStringArray(R.array.cities);

        property = getResources().getStringArray(R.array.property_type);

        spinner_type = (Spinner) rootview.findViewById(R.id.spinner1);
        property_type = (Spinner) rootview.findViewById(R.id.spinner_propertytype);
        city = (Spinner) rootview.findViewById(R.id.spinner_city);

        adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, cities);

        adapter2 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, property);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        city.setAdapter(adapter);
        city.setOnItemSelectedListener(this);
       //adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        property_type.setAdapter(adapter2);
        property_type.setOnItemSelectedListener(this);
        chkbox = (CheckBox) rootview.findViewById(R.id.chkbox);
        propety_image = (ImageButton) rootview.findViewById(R.id.property_image);
        propety_image.setOnClickListener(this);
//        if(pd==null) {
//
//
//            pd = new PropertyDetails();
//        }
        if (pd != null) {
            populateField();

        }
        done.setOnClickListener(this);


        google_map_view = (ImageView) rootview.findViewById(R.id.googlemap_imageview);


        return rootview;

    }

    public void populateField() {

        date.setText(pd.getDate());
        address.setText(pd.getAddress());
        breadth.setText(pd.getBreadth());
        length.setText(pd.getLength());
        breadth.setText(pd.getBreadth());
        sale_price_in_inr.setText(pd.getPrice());
        State.setText(pd.getState());
        adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, cities);

//getting a spinner back and re setting
        value = pd.getCity();
        pos = adapter.getPosition(value);
        city.setSelection(pos);
        value2 = pd.getPropertyType();
        int pos = adapter2.getPosition(value2);
        property_type.setSelection(pos);


//image back to image bitmap

//        byte[] imageAsBytes = Base64.decode(pd.getPropertyImage().getBytes(), Base64.DEFAULT);
//        Bitmap decodedByte = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
//        propety_image.setImageBitmap(decodedByte);
 //       propety_image.setImage(https://aachamaacha.com/MyPictures/);

        AsyncImageProperty asyncImageProperty = new AsyncImageProperty();
        asyncImageProperty.execute(pd.getPropertyImage());
//String back to chkbox
        Boolean q = Boolean.valueOf(pd.getNegotiable());
        chkbox.setChecked(q);


    }

    public String imagettobit_tostring(ImageButton propety_image) {
        try {

            Bitmap bitmap = ((BitmapDrawable) propety_image.getDrawable()).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
            byte[] image = stream.toByteArray();


            img_str = Base64.encodeToString(image, 0);
            return img_str;

        } catch (Exception ex) {
            Utilities.write("ErrorLog", "Encountered error in image." + ex.getMessage());
        }


        return img_str;


    }//image_view to string


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

//    public void addListenerOnChkbox() {
//
//
//
//        chkbox.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                //is chkIos checked?
//                if (((CheckBox) v).isChecked()) {
//                    Toast.makeText(getActivity(),
//                            "Includes Registration Charges", Toast.LENGTH_LONG).show();
//                }
//
//            }
//        });

    //    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        // Inflate the menu; this adds items to the action bar if it is present.

        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.online_information_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_PIC_REQUEST) {
            Bitmap thumbnail = null;
//            try {
//                thumbnail = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),
//                        Uri.parse(MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString()));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
try {
            thumbnail = MediaStore.Images.Media.getBitmap(
                    getActivity().getContentResolver(), imageUri);
    propety_image.setImageBitmap(thumbnail);
    String imageurl = getRealPathFromURI(imageUri);
        } catch (Exception e) {
            e.printStackTrace();
        }

//            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
//            propety_image.setImageBitmap(thumbnail);
            image = imagettobit_tostring(propety_image);
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            thumbnail.compress(Bitmap.CompressFormat.JPEG, 20, bytes);
            String tm=String.valueOf(System.currentTimeMillis());

            try {
                consta = tm.substring(tm.length() - 4, tm.length());
            }
            catch(Exception ex){
                Toast.makeText(getActivity(),ex.getMessage(),Toast.LENGTH_LONG).show();
            }
            File file = new File(Environment.getExternalStorageDirectory() + File.separator + consta+".jpg");
            try {
                file.createNewFile();
                FileOutputStream fo = new FileOutputStream(file);
                fo.write(bytes.toByteArray());
                fo.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            filePath =file.getAbsolutePath();
            upLoadPicture();
//            Asyncuploadimage asyncloadProperty=new Asyncuploadimage();
//            asyncloadProperty.onPostExecute();


        }


        if (requestCode == 1 && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();


            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
           String  filePathinitially = cursor.getString(columnIndex);
            cursor.close();
            // String picturePath contains the path of selected Image

            // Show the Selected Image on ImageView
            Bitmap imageBitmap = BitmapFactory.decodeFile(filePathinitially);

            propety_image.setImageBitmap(imageBitmap);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 20, stream);

            //          image   =  imagettobit_tostring(propety_image);
            String tm=String.valueOf(System.currentTimeMillis());

            try {
                consta = tm.substring(tm.length() - 4, tm.length());
            } catch(Exception ex){
                Toast.makeText(getActivity(),ex.getMessage(),Toast.LENGTH_LONG).show();
            }
            File file = new File(Environment.getExternalStorageDirectory() + File.separator + consta+".jpg");
            try {
                file.createNewFile();
                FileOutputStream fo = new FileOutputStream(file);
                fo.write(stream.toByteArray());
                fo.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            filePath =file.getAbsolutePath();
//            Asyncuploadimage asyncloadProperty=new Asyncuploadimage();
//            asyncloadProperty.onPostExecute();
            upLoadPicture();

        }

    }
    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    public void onClick(View view) {
        ContentValues values;
        Intent intent;
        switch (view.getId()) {
            case R.id.property_image:
                // Code for button 1 click
             values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "New Picture");
                values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
               imageUri = getActivity().getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
               intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, CAMERA_PIC_REQUEST);







                break;

            case R.id.camer_button:
                // Code for button 2 click


                values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "New Picture");
                values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                imageUri = getActivity().getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, CAMERA_PIC_REQUEST);
                break;

            case R.id.gallery_button:
                // Code for button 3 click
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 1);


                break;
            case R.id.done:

                if(connectionDetector.isConnectingToInternet()) {
                    AsyncPostProperty asyncPostProperty = new AsyncPostProperty();
                    asyncPostProperty.execute();
                }else{
                    Toast.makeText(getActivity(),"Please connect to internet",Toast.LENGTH_LONG).show();
                }


        }
    }
ConnectionDetector connectionDetector;
    private class AsyncPostProperty extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... arg0) {

            PropertyService propertyService = new PropertyService();
            if (pd == null) {
                save();
                propertyService.postProperty(pd);
            } else {
                update();
                propertyService.updateProperty(pd);
            }
            return null;
        }

        protected void onPostExecute(Void result) {

            Toast.makeText(getActivity(), getResources().getString(R.string.PROPERTY_POSTED_SUCCESSFULLY), Toast.LENGTH_LONG).show();
//            if(authentic){
//                navigationIntent=new Intent(getApplicationContext(),MainActivity.class);
//                startActivity(navigationIntent);
//            }
        }

    }

    public void save() {


        pd = new PropertyDetails();
        pd.setAddress(address.getText().toString());
        pd.setBreadth(breadth.getText().toString());
        pd.setCity((String) city.getSelectedItem().toString());
        pd.setLength(length.getText().toString());
        dhd = chkbox.isChecked();
        pd.setNegotiable(String.valueOf(dhd));//to set boolean to string
        pd.setPrice(sale_price_in_inr.getText().toString());
        pd.setPropertyType((String) property_type.getSelectedItem().toString());
        pd.setState(State.getText().toString());
        pd.setDate(date.getText().toString());
        pd.setPropertyImage("http://www.aachamaacha.com/MyPictures/" + consta+".jpg");


//        pd.setPropertyDetail((String) spinner_type.getSelectedItem().toString());
        pd.setUid(User.getInstance().getUserId());


    }

    public void update() {


        pd.setAddress(address.getText().toString());
        pd.setBreadth(breadth.getText().toString());
        pd.setCity((String) city.getSelectedItem().toString());
        pd.setLength(length.getText().toString());
        dhd = chkbox.isChecked();
        pd.setNegotiable(String.valueOf(dhd));//to set boolean to string
        pd.setPrice(sale_price_in_inr.getText().toString());
        pd.setPropertyType((String) property_type.getSelectedItem().toString());
        pd.setState(State.getText().toString());
        pd.setDate(date.getText().toString());
        pd.setPropertyImage("http://aachamaacha.com/MyPictures/" + consta+".jpg");


//        pd.setPropertyDetail((String) spinner_type.getSelectedItem().toString());
        pd.setUid(User.getInstance().getUserId());


    }

    public void jodadatetime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String strDate = sdf.format(c.getTime());
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyyMMddHHmmss");

        DateTime dateTime = formatter.parseDateTime(strDate);
        date.setText(dateTime.toString(DateTimeFormat.forPattern("dd-MMM-yyyy HH:mm:ss")));

    }

    //Method for uploading picture on FtpServer
    public void upLoadPicture() {



            new Thread(){

                @Override
                public void run() {

                    File file = new File(filePath);

                    try {

                        FTPClient client = new FTPClient();
                        client.connect("ftp.aachamaacha.com");
                        client.login("ftp007@aachamaacha.com", "ftp007"); //this is the login credentials of your ftpserver. Ensure to use valid username and password otherwise it throws exception

                        try {

                            client.changeDirectory("MyPictures"); //I want to upload picture in MyPictures directory/folder. you can use your own.
                        } catch (Exception e) {

                            client.createDirectory("MyPictures");
                            client.changeDirectory("MyPictures");

                        }
                        client.upload(file); //this is actual file uploading on FtpServer in specified directory/folder
                        client.disconnect(true);   //after file upload, don't forget to disconnect from FtpServer.
                        file.delete();
                    }
                    catch (Exception e) {
                        Log.d("Failure", "Exception: "+e.getMessage());
//                        Toast.makeText(getActivity(), "Exception: "+e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }.start();

        }
//    class Asyncuploadimage extends AsyncTask<String, Void, Void> {
//        @Override
//        protected Void doInBackground(String... params) {
//            File file = new File(filePath);
//
//            try {
//
//                FTPClient client = new FTPClient();
//                client.connect("ftp.aachamaacha.com");
//                client.login("ftp007@aachamaacha.com", "ftp007"); //this is the login credentials of your ftpserver. Ensure to use valid username and password otherwise it throws exception
//
//                try {
//
//                    client.changeDirectory("MyImages"); //I want to upload picture in MyPictures directory/folder. you can use your own.
//                } catch (Exception e) {
//
//                    client.createDirectory("MyImages");
//                    client.changeDirectory("MyImages");
//
//                }
//                client.upload(file); //this is actual file uploading on FtpServer in specified directory/folder
//                client.disconnect(true);   //after file upload, don't forget to disconnect from FtpServer.
//                file.delete();
//            }
//            catch (Exception e) {
//
//                Toast.makeText(getActivity(), "Exception: "+e.getMessage(), Toast.LENGTH_LONG).show();
//            }
//
//            return null;
//
//        }

//        protected void onPostExecute() {
//
//            Toast.makeText(getActivity(), "Successfully Uploaded", Toast.LENGTH_LONG).show();
//
//        }
//    }



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
            propety_image.setImageBitmap(bitmap);

        }
    }


}




