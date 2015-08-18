package com.sourabh.appnews.core;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sourabh.businessService.PropertyService;
import com.sourabh.entity.PropertyDetails;
import com.sourabh.entity.User;
import com.sourabh.utility.ConnectionDetector;
import com.sourabh.utility.Utilities;

import java.util.ArrayList;


public class List_Activity_myproperty extends Fragment {
    static ArrayList<PropertyDetails> propertylist = new ArrayList<PropertyDetails>();
    ListView mlist;
    FragmentTransaction fragmentTransaction;
    ConnectionDetector connectionDetector;
    static View rootView;
    //method of passing a getting things here and then passing
    CustomAdapter mcustomadapter;

//    String mtitles[];
//    String mdescription[];
//    int mimages[];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        connectionDetector = new ConnectionDetector(getActivity());
        //method of passing a getting things here and then passing
        // for(int i = 0 ; i < propertylist.size() ; i++) {
        //  mtitles[i] = String.valueOf(propertylist.get(13).getPropertyType());//here propertylist is array of object so get(13)will give object of 13 position so at that position we want string
        // mdescription[i] = String.valueOf(propertylist.get(10).getPrice());//in this it will written an object sowe want a String so get price
        // mimages[i] = Integer.parseInt(String.valueOf(propertylist.get(12).getPropertyImage()));
        // mlist = (ListView) rootView.findViewById(R.id.listView);
        //  CustomAdapter mcustomadapter = new CustomAdapter(getActivity(), mimages, mtitles, mdescription);
        if (connectionDetector.isConnectingToInternet()) {
            AsyncFetchProperty asyncFetchProperty = new AsyncFetchProperty();

            asyncFetchProperty.execute();
        } else {
            Toast.makeText(getActivity(), "Please connect to internet.", Toast.LENGTH_LONG).show();
        }


        try {

            rootView = inflater.inflate(R.layout.activity_list_, container, false);

            mlist = (ListView) rootView.findViewById(R.id.listView);


            mcustomadapter = new CustomAdapter(getActivity(), R.layout.single_row, propertylist);
            mlist.setAdapter(mcustomadapter);
            TextView text=(TextView)rootView.findViewById(R.id.headertext);
            text.setText("Property List");

            mlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView parent, View view,
                                        int position, long id) {
                    if (propertylist.size() > 0) {

                        Online_information_Fragment_sell.mode = "UPDATE";
                        Online_information_Fragment_sell.pd = propertylist.get(position);
                        Online_information_Fragment_sell online_information_fragment_sell = new Online_information_Fragment_sell();
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.content_frame, online_information_fragment_sell);
                        fragmentTransaction.addToBackStack(null);


                        fragmentTransaction.commit();


                    }
                }
            });
        }
        catch (Exception e) {
            Utilities.write("ErrorLog", "Encountered error in list activity my property " + e.getMessage());

        }

        // }

        return rootView;


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.list_activity_myproperty, menu);

        //  menu.findItem(R.id.filter_list).setVisible(false);


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


    private class AsyncFetchProperty extends AsyncTask<String, Void, Boolean> {
        ProgressDialog Asycdialog = new ProgressDialog(getActivity());


        @Override
        protected Boolean doInBackground(String... arg0) {

            PropertyService propertyService = new PropertyService();

            //    Thedb databaseHandler=Thedb.getInstance(getActivity());
try{
            propertylist = propertyService.fetchProperty(User.getInstance().getUserId());//in this we are fetching information from services
            return true;

        }

        catch (Exception e) {
            Utilities.write("ErrorLog", "Encountered error in fetch property " + e.getMessage());
            return false;
        }

//         databaseHandler.addProperty(propertylist);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Asycdialog.setMessage(getResources().getString(
                    R.string.LOADING_YOUR_PROPERTY));
            Asycdialog.setCanceledOnTouchOutside(false);
            // show dialog
            Asycdialog.show();
        }

        protected void onPostExecute(Boolean result) {

            Asycdialog.hide();
            Toast.makeText(getActivity(), "See All Property", Toast.LENGTH_SHORT).show();
            mcustomadapter = new CustomAdapter(getActivity(), R.layout.single_row, propertylist);
            mlist.setAdapter(mcustomadapter);


        }

    }
}


