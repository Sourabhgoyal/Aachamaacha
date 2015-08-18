package com.sourabh.appnews.core;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.analytics.tracking.android.EasyTracker;
import com.sourabh.backgroundwork.LoadTestActivity;
import com.sourabh.businessService.CategoriesService;
import com.sourabh.businessService.NewsInterface;
import com.sourabh.businessService.NewsService;
import com.sourabh.constants.AllScreens;
import com.sourabh.database.Thedb;
import com.sourabh.entity.Category;
import com.sourabh.entity.Filter;
import com.sourabh.entity.News;
import com.sourabh.entity.User;
import com.sourabh.singletons.Location;
import com.sourabh.utility.Utilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MainActivity extends Activity implements
		TextToSpeech.OnInitListener, SearchView.OnQueryTextListener, OnItemClickListener, AdapterView.OnItemSelectedListener, View.OnClickListener {

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1) {
			refreshRegion();
		}
	}
	private static final String JAIPUR = "ALL";
	private static final String LOCATION = "Location";
	@Override
	protected void onResume() {
		loadLocation();
		Thedb.getInstance(getApplicationContext()).getUser().getUserId();
		super.onResume();
	}
	private void loadLocation() {
		String city = Utilities.readFromSharedPref(getApplicationContext(),
				LOCATION);
		if (city.equals("")) {
			city = JAIPUR;
			Utilities.writeToSharedPref(getApplicationContext(), LOCATION,
					JAIPUR);
		}
		Location.getInstance().setCity(city);
	}
	private void refreshRegion() {
		AllScreens.ACTIONBARTITLE = getResources()
				.getString(R.string.News_Home);
		FragmentManager fragmentManager = getFragmentManager();
		NewsList rFragment = new NewsList();
		FragmentTransaction ft = fragmentManager.beginTransaction();
		ft.replace(R.id.content_frame, rFragment);
		ft.commit();
	}

	@Override
	protected void onPostResume() {
		super.onPostResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}
	 static Filter filter;
	ArrayList<News> news;
	ArrayList<String> categoryList;
	NewsInterface newsService;
	DrawerLayout mDrawerLayout;
	String forCategory = null;
	ListView mDrawerList;
	static ArrayList search;

	static AutoCompleteTextView actv;
	Spinner mpricefrom;
	Spinner mpriceto;
	Spinner mtype ;
	Spinner mposted;
	Spinner msale;
 String[] pricefrom;
 String[] priceto;
	 String[] type;
	 String[] posted;
	 String[] sale;
	ArrayAdapter<String> adapter1;
	ArrayAdapter<String> adapter2;
	ArrayAdapter<String> adapter3;
	ArrayAdapter<String> adapter4;
	ArrayAdapter<String> adapter5;





	RelativeLayout mCrteriaDrawer;
	TextToSpeech tts = null;
	ActionBarDrawerToggle mDrawerToggle;
	String mTitle = "";
	private ListView mDrawerCategoriesList;

	@Override
	protected void onStart() {
		super.onStart();
		EasyTracker.getInstance(this).activityStart(this);
		// Toast.makeText(getApplicationContext(), "Analytics Started",
		// Toast.LENGTH_LONG).show();
	}

	@Override
	protected void onStop() {
		super.onStop();
		EasyTracker.getInstance(this).activityStop(this);
		// Toast.makeText(getApplicationContext(), "Analytics Stopped",
		// Toast.LENGTH_LONG).show();
	}

	ArrayList<String> arrayFiles;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// fetchFromBundle();
		// tts = new TextToSpeech(getApplicationContext(), this);
		// categoryList=new ArrayList<String>();
	cancelNotification();
		final FragmentManager fragmentManager = getFragmentManager();
		newsService = new NewsService();
		// fetchNewss();
		//setAutocompleteBox();
		AllScreens.ACTIONBARTITLE = getResources().getString(R.string.News_Home);
		getActionBar().setTitle(AllScreens.ACTIONBARTITLE);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		mDrawerList = (ListView) findViewById(R.id.drawer_list);
		mposted=(Spinner)findViewById(R.id.postedby);
		mpricefrom=(Spinner)findViewById(R.id.pricelistfrom);
		mpriceto=(Spinner)findViewById(R.id.pricelistto);
		msale=(Spinner)findViewById(R.id.saletype);
		mtype=(Spinner)findViewById(R.id.typelist);
		pricefrom = getResources().getStringArray(R.array.pricefrom);
		priceto = getResources().getStringArray(R.array.priceto);
		type=getResources().getStringArray(R.array.property_type);
		posted=getResources().getStringArray(R.array.type);
		sale=getResources().getStringArray(R.array.sale);
		adapter1 = new ArrayAdapter<String>(getApplicationContext(),
				android.R.layout.simple_list_item_1 ,pricefrom);
		adapter2 = new ArrayAdapter<String>(getApplicationContext(),
				android.R.layout.simple_list_item_1, priceto);
		adapter3 = new ArrayAdapter<String>(getApplicationContext(),
				android.R.layout.simple_spinner_item, type);
		adapter4 = new ArrayAdapter<String>(getApplicationContext(),
				android.R.layout.simple_spinner_item, posted);
		adapter5 = new ArrayAdapter<String>(getApplicationContext(),
				android.R.layout.simple_spinner_item, sale);
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mpricefrom.setAdapter(adapter1);
		mpriceto.setAdapter(adapter2);
		msale.setAdapter(adapter5);
		mtype.setAdapter(adapter3);
		mposted.setAdapter(adapter4);
		 actv = (AutoCompleteTextView)findViewById(R.id.autoCompleteTextView1);
		ArrayAdapter<String> adapter6 = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.City));
		actv.setAdapter(adapter6);
		Button apply=(Button)findViewById(R.id.apply);
		apply.setOnClickListener(this);



		//	mCrteriaDrawer = (RelativeLayout) findViewById(R.id.criteria_drawer);
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {
			/** Called when drawer is closed */
			public void onDrawerClosed(View view) {
				// if(AllScreens.ACTIONBARTITLE.equals(getResources().getString(R.string.Options)))
				getActionBar().setTitle(AllScreens.ACTIONBARTITLE);
				// AllScreens.ACTIONBARTITLE=getResources().getString(R.string.Newss_Home);
				invalidateOptionsMenu();
			}

			/** Called when a drawer is opened */
			public void onDrawerOpened(View drawerView) {
				// AllScreens.ACTIONBARTITLE=getResources().getString(R.string.Options);
				getActionBar().setTitle(
						getResources().getString(R.string.Options));
				invalidateOptionsMenu();
			}
		};

		// Setting DrawerToggle on DrawerLayout
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		LeftNavAdapter adapter = null;
		// if(User.getInstance().getUserId()!=null){
//		if (false) {
			// Creating an ArrayAdapter to add items to the listview mDrawerList
			// adapter= new LeftNavAdapter(getApplicationContext(),
			// R.layout.drawer_list_item,
			// getResources().getStringArray(R.array.SignedInUser));
//			sir ka aacha code
//		} else {
//
//			List<String> arrayIcons = Arrays.asList(getResources()
//					.getStringArray(R.array.GuestUser));

//			arrayFiles = Utilities.read(
//					getResources().getString(R.string.LBL_SAVED_PAGES), true);
//			if (arrayFiles != null && arrayFiles.size() > 0) {
//				arrayFiles.add(0,
//						getResources().getString(R.string.LBL_SAVED_PAGES));
//
//				arrayFiles.addAll(0, arrayIcons);//it added to the saved page to our list
//
//			} else {
//				arrayFiles = new ArrayList<String>();
//
//				arrayFiles.addAll(arrayIcons);//use to add to save list   in flipcart

//			}
			// System.arraycopy(getResources().getStringArray(R.array.GuestUser),0,
			// fileNames, 0,
			// getResources().getStringArray(R.array.GuestUser).length);
//			adapter = new LeftNavAdapter(getApplicationContext(),
//					R.layout.drawer_list_item, arrayFiles);

//		}
		// Setting the adapter on mDrawerList
		// Enabling Home button
		getActionBar().setHomeButtonEnabled(true);
		// Enabling Up navigation
		getActionBar().setDisplayHomeAsUpEnabled(true);
		if (User.getInstance().getUserId() != null && User.getInstance().getType().equals(getResources().getString(R.string.AGENT))) {
			adapter= new LeftNavAdapter(getApplicationContext(),
					 R.layout.drawer_list_item,
					 getResources().getStringArray(R.array.Agent));
			mDrawerList.setAdapter(adapter);


			mDrawerList.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {

					if (position == 0) {
						NewsListSearch.filter=new Filter();
						navigateHome();
					}else if(position==1){
						navigateNewsHome();
					} else if (position == 2) {

						FragmentManager fragmentManager = getFragmentManager();
						AccountDetailsFragment rFragment = new AccountDetailsFragment();
						FragmentTransaction ft = fragmentManager.beginTransaction();
						ft.replace(R.id.content_frame, rFragment);
						ft.commit();


					} else if (position == 3) {
						alertBox();

						//Intent navigationIntent = new Intent(
								//getApplicationContext(), GpsMainActivity.class);
						// navigationIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
						//startActivityForResult(navigationIntent, 1);
					} else if (position == 4) {
						Intent i = new Intent(
								android.content.Intent.ACTION_SEND);
						i.setType("text/plain");
						i.putExtra(android.content.Intent.EXTRA_SUBJECT,
								"www.aachamaacha.com");
						i.putExtra(android.content.Intent.EXTRA_TEXT,
								"www.aachamaacha.com");
						startActivity(Intent.createChooser(i, "Share via"));
//						Intent navigationIntent = new Intent(
//								getApplicationContext(),
//								EventMainActivity.class);
						// navigationIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//						startActivity(navigationIntent);
					}
// else if (position == 4) {
//						Intent navigationIntent = new Intent(
//								getApplicationContext(), Settings.class);
//						startActivity(navigationIntent);
//					}
		else if (position == 5) {
						Thedb databaseHandler = Thedb.getInstance(getApplicationContext());
						if (databaseHandler.logOut(User.getInstance().getUserId())) {
							User.getInstance().reset();
							finish();
							Intent navigationIntent = new Intent(getApplicationContext(), LoginActivity.class);
							//navigationIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
							startActivity(navigationIntent);
						}


					}



					else {
						Thedb databaseHandler = Thedb
								.getInstance(getApplicationContext());
						News news = databaseHandler.fetchNewsByName(arrayFiles
								.get(position));
						try {
							if (news != null) {
								Intent newsDetail = new Intent(
										getApplicationContext(),
										NewsDetailFragment.class);
								NewsDetailFragment.news = news;
								startActivity(newsDetail);
							}
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}

					mDrawerLayout.closeDrawer(mDrawerList);
					// mDrawerLayout.openDrawer(mDrawerCategoriesList);
					// mDrawerLayout.openDrawer(mCrteriaDrawer);
				}
			});
			 }
		else if (User.getInstance().getUserId() != null &&User.getInstance().getType().toLowerCase().equals(getResources().getString(R.string.User))) {

			adapter= new LeftNavAdapter(getApplicationContext(),
					R.layout.drawer_list_item,
					getResources().getStringArray(R.array.User));
			mDrawerList.setAdapter(adapter);
			mDrawerList.setOnItemClickListener(new OnItemClickListener() {


				@Override
				public void onItemClick(AdapterView<?> parent, View view,
										int position, long id) {

					if (position == 0) {

						NewsListSearch.filter=new Filter();
					navigateHome();

					}

					if (position == 1) {
						navigateNewsHome();
					} else if (position == 2) {
						FragmentManager fragmentManager = getFragmentManager();
						AccountDetailsFragment rFragment = new AccountDetailsFragment();
						FragmentTransaction ft = fragmentManager.beginTransaction();
						ft.replace(R.id.content_frame, rFragment);
						ft.commit();


//						// AboutUs rFragment=new AboutUs();
//						// FragmentTransaction ft =
//						// fragmentManager.beginTransaction();
//						// ft.add(R.id.content_frame, rFragment);
//						// ft.addToBackStack(null);
//						// ft.commit();
//						Intent aboutusIntent = new Intent(
//								getApplicationContext(), AboutUs.class);
//						startActivity(aboutusIntent);
					} else if (position == 3) {
						Online_information_Fragment_sell.mode = "BUY";
						navigateOnline_information_fragment_sell();
						//Intent navigationIntent = new Intent(
						//getApplicationContext(), GpsMainActivity.class);
						// navigationIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
						//startActivityForResult(navigationIntent, 1);
					} else if (position == 4) {
						Online_information_Fragment_sell.mode = "SELL";
						navigateOnline_information_fragment_sell();
						//						Intent navigationIntent = new Intent(
//								getApplicationContext(),
//								EventMainActivity.class);
						// navigationIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//						startActivity(navigationIntent);
					} else if (position == 5) {
						navigateList_Activity_myproperty();

						// Settings rFragment=new Settings();
						// FragmentTransaction ft =
						// fragmentManager.beginTransaction();
						// ft.add(R.id.content_frame, rFragment);
						// ft.addToBackStack(null);
						// ft.commit();

//						Intent settingsIntent = new Intent(
//								getApplicationContext(), Settings.class);
//						startActivity(settingsIntent);
					} else if (position == 6) {


						Intent aboutUsIntent = new Intent(
								getApplicationContext(), AboutUs.class);
						startActivity(aboutUsIntent);

					} else if (position == 7) {
						Intent i = new Intent(android.content.Intent.ACTION_SEND);
						i.setType("text/plain");
						i.putExtra(android.content.Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
						i.putExtra(android.content.Intent.EXTRA_TEXT, getResources().getString(R.string.APPLINK));
						startActivity(Intent.createChooser(i, getResources().getString(R.string.LBL_SHARE_VIA)));

					} else if (position == 8) {
						Thedb databaseHandler = Thedb.getInstance(getApplicationContext());
						if (databaseHandler.logOut(User.getInstance().getUserId())) {
							User.getInstance().reset();
							finish();
							Intent navigationIntent = new Intent(getApplicationContext(), LoginActivity.class);
							//navigationIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
							startActivity(navigationIntent);

						}


					} else if (position > 8) {
						Thedb databaseHandler = Thedb.getInstance(getApplicationContext());
						News news = databaseHandler.fetchNewsByName(arrayFiles.get(position));
						try {
							if (news != null && news.toShare() != null) {
								Intent newsDetail = new Intent(getApplicationContext(), NewsDetailFragment.class);
								NewsDetailFragment.news = news;
								startActivity(newsDetail);
							} else {
								Toast.makeText(getApplicationContext(), getResources().getString(R.string.MSG_NEWS_NOT_AVAILABLE), Toast.LENGTH_LONG).show();

								Utilities.deleteFile(arrayFiles.get(position));
								Intent initializerIntent = new Intent(getApplicationContext(), Intitializer.class);
								startActivity(initializerIntent);
								finish();

							}
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
					// if(mDrawerLayout.isDrawerOpen(Gravity.LEFT)){
					mDrawerLayout.closeDrawer(mDrawerList);
					// }
					// mDrawerLayout.openDrawer(mDrawerCategoriesList);
					//
				}
			});
		}
		initializeCategories();
		// navigateIfCategorySelected();
		NewsListSearch.filter=new Filter();
		navigateHome();
		// speakOut();
	}
	protected void alertBox() {  
		  AlertDialog.Builder builder = new AlertDialog.Builder(this);  
		  builder.setMessage("We build powerful,feature intensive,client oriented,user friendly smartphone applications"+"\n\nReach us at:\n"+getResources().getString(R.string.companyAddress)+"\n"+getResources().getString(R.string.companyContactPerson)+"\n"+getResources().getString(R.string.companyEmailAddress)+"\n"+getResources().getString(R.string.companyMobNo)+"\n")  
		  .setCancelable(true)  
		  .setTitle("Aachamaacha");
		  AlertDialog alert = builder.create();  
		  alert.show();  
		 }  
	private void cancelNotification() {
		NotificationManager mNotificationManager = (NotificationManager) getApplicationContext()
				.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.cancel(112211);
		mNotificationManager.cancel(112212);
		mNotificationManager.cancel(112213);
		AlarmReciever.previousNewsCount = 0;
		AlarmReciever.previousEventCount = 0;
		AlarmReciever.previousMessageCount = 0;
		updateSeenStatus();
	}

	private void updateSeenStatus() {
		Thedb databaseHandler = Thedb.getInstance(getApplicationContext());
		databaseHandler.updateSeenStatus();
	}

	private void navigateIfCategorySelected() {
		FragmentManager fragmentManager = getFragmentManager();
		NewsList rFragment = new NewsList();
		if (forCategory != null)
			rFragment.setCategory(forCategory);
		else
			rFragment.setCategory("ALL");
		FragmentTransaction ft = fragmentManager.beginTransaction();
		ft.replace(R.id.content_frame, rFragment);
		ft.commit();

	}

	private void navigateHome() {
		AllScreens.ACTIONBARTITLE = getResources()
				.getString(R.string.aachamaacha);


		FragmentManager fragmentManager = getFragmentManager();
		NewsListSearch rFragment = new NewsListSearch();
		FragmentTransaction ft = fragmentManager.beginTransaction();
		ft.replace(R.id.content_frame, rFragment);
		ft.commit();
	}
	private void navigateNewsHome() {
		AllScreens.ACTIONBARTITLE = getResources()
				.getString(R.string.aachamaacha);

		NewsList.clearAll();
		FragmentManager fragmentManager = getFragmentManager();
		PagerFragment rFragment = new PagerFragment();
		FragmentTransaction ft = fragmentManager.beginTransaction();
		ft.replace(R.id.content_frame, rFragment);
		ft.commit();
	}

	private void navigateOnline_information_fragment_sell(){

		Online_information_Fragment_sell.pd=null;
		FragmentManager fragmentManager = getFragmentManager();
		Online_information_Fragment_sell frag=new Online_information_Fragment_sell();
		FragmentTransaction ft = fragmentManager.beginTransaction();
		ft.addToBackStack(null);
		ft.replace(R.id.content_frame, frag);
		ft.commit();
	}
	private void navigateList_Activity_myproperty(){

		FragmentManager fragmentManager = getFragmentManager();
		List_Activity_myproperty frag=new List_Activity_myproperty();
		FragmentTransaction ft = fragmentManager.beginTransaction();
		ft.addToBackStack(null);

		ft.replace(R.id.content_frame, frag);
		ft.commit();
	}


	private void fetchFromBundle() {
		try {
			Bundle recievedCategory = getIntent().getExtras();
			forCategory = recievedCategory.getString("Category").toString();
		} catch (Exception ex) {
			forCategory = "ALL";
			Utilities.write(
					"ErrorLog",
					"Encountered error in fetchFromBundle MainActivity."
							+ ex.getMessage());
		}
	}

	private void initializeCategories() {
		// ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
		// // Pass results to ViewPagerAdapter Class
		// ViewPagerAdapter adapter = new ViewPagerAdapter(MainActivity.this);
		// // Binds the Adapter to the ViewPager
		// viewPager.setAdapter(adapter);
//		getActionBar().setBackgroundDrawable(
//				new ColorDrawable(Color.rgb(51, 181, 229)));
//		Drawable d=getResources().getDrawable(R.drawable.orange);
//		getActionBar().setBackgroundDrawable(d);
		Thedb databaseHandler = Thedb.getInstance(getApplicationContext());
		categoryList = databaseHandler.getCategoryList();

		populateCategoriesInExpandableList();
		AsyncPopulateCategories asyncPopulateCategories = new AsyncPopulateCategories();
		asyncPopulateCategories.execute();
	}

	private void setAutocompleteBox() {
		AutoCompleteTextView actv;
		actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
		Thedb databaseHandler = Thedb.getInstance(getApplicationContext());
		ArrayList<String> localityList = databaseHandler.getLocalityList();
		String[] countries = getResources().getStringArray(
				R.array.list_of_countries);
		ArrayAdapter adapter = new ArrayAdapter(this,
				android.R.layout.simple_list_item_1, localityList);
		actv.setAdapter(adapter);
		actv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
									long arg3) {
				searchInLocalityHint();
			}
		});
	}

	public void searchInLocality(View v) {
		final FragmentManager fragmentManager = getFragmentManager();
		AutoCompleteTextView actv;
		actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.cities));
		actv.setAdapter(adapter);
//		NewsList rFragment = new NewsList();
//		// rFragment.setLocality(actv.getText().toString());
//		NewsList.searchCriteria.put("locality", actv.getText().toString());
//		// rFragment.setCategory(forCategory!=null?forCategory:"");
//		// NewsList.searchCriteria.put("category",
//		// forCategory!=null?forCategory:"");
//		FragmentTransaction ft = fragmentManager.beginTransaction();
//		ft.replace(R.id.content_frame, rFragment);
//		ft.commit();
//		mDrawerLayout.closeDrawer(Gravity.RIGHT);
	}

	public void searchInLocalityHint() {
		final FragmentManager fragmentManager = getFragmentManager();
		AutoCompleteTextView actv;
		actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
		NewsList rFragment = new NewsList();
		// rFragment.setLocality(actv.getText().toString());
		NewsList.searchCriteria.put("locality", actv.getText().toString());
		// rFragment.setCategory(forCategory!=null?forCategory:"");
		FragmentTransaction ft = fragmentManager.beginTransaction();
		ft.replace(R.id.content_frame, rFragment);
		ft.commit();
		mDrawerLayout.closeDrawer(Gravity.RIGHT);
		try {
			InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
		} catch (Exception e) {
		}
	}

	private synchronized void populateNewss() {
		try {
			NewsListAdapter arrayAdanewspter = new NewsListAdapter(this,
					R.layout.news_row_list_layout, news);
			// ListView listViewNewss=(ListView)findViewById(R.id.ViewNewss);
			// //Set the above adapter as the adapter of choice for our list
			// listViewNewss.setAdapter(arrayAdanewspter);
			// CustomNewsListAdapter newsAdapter = new
			// CustomNewsListAdapter(getApplicationContext(),news);
			// ListView
			// listViewNewss=(ListView)findViewById(R.id.listViewNewss);
			// listViewNewss.setAdapter(newsAdapter);
		} catch (Exception ex) {
			Log.e("Exception in populate newss", ex.getMessage());
			File sdcard = Environment.getExternalStorageDirectory();
			File file = new File(sdcard, "ErrorLog.txt");
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(file));
				bw.append(ex.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	/** Handling the touch event of app icon */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {

		}
		switch (item.getItemId()) {
		case R.id.action_settings:
			if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
				mDrawerLayout.closeDrawer(Gravity.LEFT);
			} else if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
				mDrawerLayout.closeDrawer(Gravity.RIGHT);
			} else {
				// mDrawerLayout.openDrawer(mDrawerCategoriesList);
				mDrawerLayout.openDrawer(mCrteriaDrawer);
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/** Called whenever we call invalidateOptionsMenu() */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the drawer is open, hide action items related to the content view
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		//menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		SearchView searchView = (SearchView) menu.findItem(R.id.action_search)
				.getActionView();
		searchView.setOnQueryTextListener(this);
		return true;
	}

	public void loadTest(View v) {
		Intent intent = new Intent(getApplicationContext(),
				LoadTestActivity.class);
		startActivity(intent);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
			case R.id.apply:
				save();
				NewsListSearch.filter=filter;
				navigateHome();
				break;

		}



	}


	public void save() {
		filter= new Filter();
		filter.setLocation(actv.getText().toString());
		filter.setPricefrom((String)mpricefrom.getSelectedItem().toString());
		filter.setPriceto((String) mpriceto.getSelectedItem().toString());
		filter.setType((String) mtype.getSelectedItem().toString());
		filter.setPosted_by((String) mposted.getSelectedItem().toString());
		filter.setSaletype((String) msale.getSelectedItem().toString());





	}

	private class AsyncPopulateCategories extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... arg0) {
			try {
				Thedb databaseHandler = Thedb
						.getInstance(getApplicationContext());
				CategoriesService categoryService = new CategoriesService();
				// news=
				// newsService.fetchNewss(databaseHandler.getLastNewsId());
				ArrayList<Category> categoryList = categoryService
						.getCategories("0");
				// news=databaseHandler.fetchAllNewss();
				databaseHandler.addCategory(categoryList);
			} catch (Exception ex) {
				Utilities.write("ErrorLog",
						"Encountered error in Category populate in doInBackground."
								+ ex.getMessage());
				return null;
			}
			return null;
		}

		protected void onPostExecute(Void result) {
			try {
				Thedb databaseHandler = Thedb
						.getInstance(getApplicationContext());
				;
				categoryList = databaseHandler.getCategoryList();
				// populateCategories();
			} catch (Exception ex) {
				Utilities.write("ErrorLog",
						"Encountered error in Category populate in postExecute."
								+ ex.getMessage());
			}
		}
	}

	void populateCategoriesInExpandableList() {
		/*prepareListData();

		ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.lvExp);
		ExpandableListAdapter listAdapter = new ExpandableListAdapter(this,
				listDataHeader, listDataChild);

		// setting list adapter
		expandableListView.setAdapter(listAdapter);
		expandableListView.expandGroup(0);
		expandableListView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView arg0, View arg1,
					int groupPosition, int childPosition, long id) {
				mDrawerLayout.closeDrawer(Gravity.RIGHT);
				NewsList.searchCriteria.put(listDataHeader.get(groupPosition),
						listDataChild.get(listDataHeader.get(groupPosition))
								.get(childPosition));
				navigateToList();
				*//*
				 * listDataHeader.get(groupPosition) + " : " +
				 * listDataChild.get( listDataHeader.get(groupPosition)).get(
				 * childPosition)
				 *//*
				return false;
			}
		});*/
		/*
		 * expandableListView.setOnItemClickListener(new OnItemClickListener() {
		 * 
		 * @Override public void onItemClick(AdapterView<?> arg0, View arg1, int
		 * position, long arg3) { mDrawerLayout.closeDrawer(Gravity.END); Thedb
		 * databaseHandler=Thedb.getInstance(getApplicationContext());
		 * databaseHandler.fetchNewssByCategory(categoryList.get(position));
		 * FragmentManager fragmentManager=getFragmentManager(); NewsList
		 * rFragment=new NewsList(); FragmentTransaction ft =
		 * fragmentManager.beginTransaction(); ft.replace(R.id.content_frame,
		 * rFragment); fragmentManager.popBackStack();
		 * rFragment.setCategory(categoryList.get(position)); //ft
		 * .addToBackStack(null); ft.commit(); } });
		 */
	}

	List<String> listDataHeader;
	HashMap<String, List<String>> listDataChild;

	private void prepareListData() {
		listDataHeader = Arrays.asList(getResources().getStringArray(
				R.array.FilterCriterias));
		listDataChild = new HashMap<String, List<String>>();

		Thedb databaseHandler = Thedb.getInstance(getApplicationContext());
		listDataChild.put(listDataHeader.get(0), categoryList);
		// listDataChild.put(listDataHeader.get(1),
		// databaseHandler.getCompanyList());
		// listDataChild.put(listDataHeader.get(1), null);
	}

	private void speakOut() {
		try {
			String text = "Showing results for " + forCategory;
			tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
		} catch (Exception ex) {
			Toast.makeText(getApplicationContext(), ex.getMessage(),
					Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void onInit(int status) {
		try {
			if (status == TextToSpeech.SUCCESS) {
				int result = tts.setLanguage(Locale.US);
				if (result == TextToSpeech.LANG_MISSING_DATA
						|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
					Log.e("TTS", "This Language is not supported");
				} else {
					// btnSpeak.setEnabled(true);
					speakOut();
				}
			} else {
				Log.e("TTS", "Initilization Failed!");
			}
		} catch (Exception ex) {
			Toast.makeText(getApplicationContext(), ex.getMessage(),
					Toast.LENGTH_LONG).show();
		}
	}

	private class AsyncSpeech extends AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... arg0) {
			Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG)
					.show();
			return null;
		}

		protected void onPostExecute(Void result) {
		}
	}

	@Override
	public boolean onQueryTextChange(String arg0) {
		String newText = null;
		newText = newText == null ? "" : "Query so far: " + newText;
		// Toast.makeText(getApplicationContext(), newText,
		// Toast.LENGTH_LONG).show();
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String searchString) {
		Thedb databaseHandler = Thedb.getInstance(getApplicationContext());
		NewsList.searchCriteria.put("search", searchString);
		super.onBackPressed();
		navigateToList();
		// ArrayList<News> newslist=databaseHandler.fetchAllNewss(null,
		// searchString);
		return false;
	}

	public void navigateToList() {

		FragmentManager fragmentManager = getFragmentManager();
		NewsList rFragment = new NewsList();
		FragmentTransaction ft = fragmentManager.beginTransaction();
		ft.replace(R.id.content_frame, rFragment);
		fragmentManager.popBackStack();
		// rFragment.setCategory(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition));

		// ft .addToBackStack(null);
		ft.commit();

	}
}
