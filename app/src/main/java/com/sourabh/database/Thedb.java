package com.sourabh.database;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import com.sourabh.appnews.core.FilterLayer;
import com.sourabh.entity.Builder;
import com.sourabh.entity.Category;
import com.sourabh.entity.Indivisual;
import com.sourabh.entity.Login;
import com.sourabh.entity.News;
import com.sourabh.entity.PropertyDetails;
import com.sourabh.entity.Subcategory;
import com.sourabh.entity.Subcategory2;
import com.sourabh.entity.Subcategory3;
import com.sourabh.entity.User;
import com.sourabh.events.entity.Event;
import com.sourabh.singletons.Location;

public class Thedb extends SQLiteOpenHelper {
	public static Thedb theDbInstance;
	private static final String KEY_ID = "id";
	private static final String KEY_USERID = "userid";

	private static final String KEY_ADDRESS = "address";
	private static final String KEY_AGE = "age";
	private static final String KEY_CITY = "city";
	private static final String KEY_DATE = "dateOfRegistration";
	private static final String KEY_DETAILS = "details";
	private static final String KEY_EMAIL = "emailAddress";
	private static final String KEY_LOCALITY = "locality";
	private static final String KEY_MOBILE_NO = "mobileNo";
	private static final String KEY_PASSWORD = "password";
	private static final String KEY_PINCODE = "pincode";
	private static final String KEY_REFFERAL_ID = "refferalId";
	private static final String KEY_STATE = "state";
	private static final String KEY_USERNAME = "username";
	private static final String KEY_STATUS = "status";
	private static final String KEY_CREDIT = "credit";

	// Offers table fields

	private static final String KEY_OFFER_ID = "offerId";
	private static final String KEY_OFFER_NAME = "offerName";
	private static final String KEY_COMPANY_ID = "companyId";
	private static final String KEY_START_DATE = "startDate";
	private static final String KEY_END_DATE = "enddate";
	private static final String KEY_VALUE = "value";
	private static final String KEY_COST = "cost";
	private static final String KEY_CATEGORY = "categoryid";
	private static final String KEY_SUBCATEGORY = "subcategoryid";
	private static final String KEY_SUBCATEGORY2 = "subcategory2id";
	private static final String KEY_SUBCATEGORY3 = "subcategory3id";
	private static final String KEY_CATEGORY_NAME = "category";
	private static final String KEY_SUB_CATEGORY_NAME = "subcategory";
	private static final String KEY_SUB_CATEGORY2_NAME = "subcategory2";
	private static final String KEY_SUB_CATEGORY3_NAME = "subcategory3";
	private static final String KEY_COMPANY_NAME = "name";
	private static final String KEY_LOGO = "logo";
	private static final String KEY_COUNTRY = "country";
	private static final String KEY_MOBILE2 = "mobile2";
	private static final String KEY_CONTACT_PERSON = "contactPerson";
	private static final String KEY_IMAGES = "images";
//News table fields

	private static final String KEY_NEWS_ID = "newsId";
	private static final String KEY_NEWS_TITLE = "newsTitle";
	private static final String KEY_POSTING_DATE = "postingDate";
	private static final String KEY_COMMENTS_ID= "commentsId";
	private static final String KEY_TYPE= "type";
	//private static final String KEY_END_DATE = "enddate";
	//private static final String KEY_CATEGORY = "categoryid";
	//private static final String KEY_LOCALITY = "locality";
	//private static final String KEY_CITY = "city";
	//private static final String KEY_STATE = "state";
//	private static final String KEY_COUNTRY = "country";
	private static final String KEY_REPORTER= "contactPerson";
	//private static final String KEY_IMAGES = "images";
	//private static final String KEY_DETAILS = "details";
	//private static final String KEY_STATUS = "status";
	private static final String KEY_ADVERTISE_IMAGE= "advertiseImage";




	//property table
	private static final String KEY_Id="id";
	private static final String KEY_PROPERTYTYPE="propertytype";
	private static final String KEY_PROPERTYIMAGE="propertyimage";
	private static final String KEY_PROPERTYDETAIL="propertydetail";
	private static final String KEY_date="datee";

	private static final String KEY_UId="uid";
	private static final String KEY_TRANSVERO="transverto";
	private static final String KEY_TRANSVERDATE="transverdate";
	private static final String KEY_LENGTH="length";
	private static final String KEY_BREADTH="breadth";
	private static final String KEY_PRICE="price";
	private static final String KEY_NEGOTIABLE="negotiable";
	private static final String KEY_LATITUDE="latitude";
	private static final String KEY_LONGITUDE="longitude";
	private static final String KEY_cITY="city";
	private static final String KEY_sTATE="state";
	private static final String KEY_aDDRESS="address";
	private static final String KEY_tYPE="type";
	private static final String KEY_UQID="uqid";
	//login table

	private static final String KEY_NAME="name";


	//indivisual table
	private static final String KEY_MOBILE="mobile";



	
	
	// Events Table
	private static final String KEY_EVENT_ID = "eventId";
	private static final String KEY_ATTRACTION_POINT = "attractionPoint";
	private static final String KEY_EVENT_CATEGORY = "category";
	// private static final String KEY = "detail";
	private static final String KEY_EVENT_ADDRESS = "eventAddress";
	private static final String KEY_EVENT_END_DATE_TIME = "eventEndDateTime";
	private static final String KEY_EVENT_LOCATION = "eventLocation";
	private static final String KEY_EVENT_NAME = "eventName";
	private static final String KEY_EVENT_START_DATE_TIME = "eventStartDateTime";
	private static final String KEY_ORGANIZER_DETAIL = "organizerDetail";
	private static final String KEY_PEOPLE_JOINING = "peopleJoining";
	private static final String KEY_GOING = "going";
	// KEY_STATUS
	// +KEY_IMAGES+" TEXT,"
	// private static final String KEY_CITY = "city";
	// private static final String KEY_STATE = "state";
	// private static final String KEY_COUNTRY = "country";
	private static Context context = null;

	public static Thedb getInstance(Context context) {
		if (theDbInstance == null) {
			synchronized (Thedb.class) {
				if (theDbInstance == null) {
					theDbInstance = new Thedb(context);
				}
			}

		}
		return theDbInstance;
	}

	private Thedb(Context context) {
		super(context, "aachamaacha", null, 1);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String CREATE_USERS_TABLE = "CREATE TABLE USERS(" + KEY_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_USERID + " TEXT,"
				+ KEY_ADDRESS + " TEXT," + KEY_AGE + " TEXT," + KEY_CITY
				+ " TEXT," + KEY_DATE + " TEXT," + KEY_DETAILS + " TEXT,"
				+ KEY_EMAIL + " TEXT," + KEY_LOCALITY + " TEXT,"
				+ KEY_MOBILE_NO + " TEXT," + KEY_PASSWORD + " TEXT,"
				+ KEY_PINCODE + " TEXT," + KEY_REFFERAL_ID + " TEXT,"
				+ KEY_STATE + " TEXT," + KEY_USERNAME + " TEXT," + KEY_CREDIT
				+ " TEXT," + KEY_STATUS + " TEXT,"+KEY_TYPE+" TEXT)";

		db.execSQL(CREATE_USERS_TABLE);
		String CREATE_LOGIN_TABLE="CREATE TABLE LOGIN(" +KEY_UQID+ " INTEGER PRIMARY KEY AUTOINCREMENT,"+KEY_Id+" TEXT ," +KEY_NAME+" TEXT, "+KEY_PASSWORD+" TEXT )";
		db.execSQL(CREATE_LOGIN_TABLE);
		String CREATE_INDIVISUAL_TABLE="CREATE TABLE INDIVISUAL("+KEY_UQID +" INTEGER PRIMARY KEY AUTOINCREMENT,"
				+KEY_Id+" TEXT ," +KEY_NAME+" TEXT, "+KEY_PASSWORD+" TEXT ,"
		+KEY_MOBILE+" TEXT ,"+KEY_EMAIL+" TEXT, "+KEY_CITY+" TEXT, "
				+KEY_STATE+" TEXT ,"+KEY_ADDRESS+"TEXT,"
				+KEY_TYPE+"TEXT )";
		db.execSQL(CREATE_INDIVISUAL_TABLE);
		String CREATE_BUILDER_TABLE="CREATE TABLE BUILDER("+KEY_UQID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+KEY_Id+" TEXT ,"
				+KEY_NAME+" TEXT, "+KEY_PASSWORD+" TEXT ,"
				+KEY_MOBILE+" TEXT ,"+KEY_EMAIL+" TEXT, "
				+KEY_CITY+" TEXT, "+KEY_STATE+" TEXT ,"
				+KEY_ADDRESS+"TEXT)";
		db.execSQL(CREATE_BUILDER_TABLE);

		String CREATE_PROPERTY_TABLE="CREATE TABLE PROPERTYDETAILS("+KEY_UQID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
				+KEY_Id+" TEXT, "+KEY_PROPERTYTYPE +" TEXT ,"+KEY_PROPERTYIMAGE+" TEXT ,"
				+KEY_PROPERTYDETAIL+ " TEXT ,"+ KEY_UId+ " TEXT, "+KEY_TRANSVERO+" TEXT, "
				+KEY_TRANSVERDATE+" TEXT, "+KEY_LENGTH+" TEXT ,"+KEY_BREADTH+" TEXT ,"
				+KEY_PRICE+"TEXT,"+KEY_NEGOTIABLE+" TEXT,"+KEY_LATITUDE+" TEXT ,"
				+KEY_LONGITUDE+" TEXT, "+KEY_cITY+" TEXT, "+KEY_sTATE+" TEXT, "
				+KEY_aDDRESS+" TEXT, "+KEY_tYPE+" TEXT,"
				+KEY_date+" TEXT )";
		db.execSQL(CREATE_PROPERTY_TABLE);


		// News Table
		String CREATE_NEWS_TABLE = "CREATE TABLE NEWS(" + KEY_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NEWS_ID
				+ " TEXT," + KEY_NEWS_TITLE+ " TEXT," + KEY_REPORTER
				+ " TEXT," + KEY_POSTING_DATE+ " TEXT," + KEY_END_DATE
				+ " TEXT," + KEY_IMAGES+ " TEXT," + KEY_DETAILS+ " TEXT,"
				+ KEY_CATEGORY+ " TEXT," + KEY_COMMENTS_ID+ " TEXT,"
				+ KEY_TYPE+ " TEXT," + KEY_STATUS+ " TEXT,"
				+ KEY_LOCALITY+ " TEXT," + KEY_CITY+ " TEXT,"
				+ KEY_STATE+ " TEXT,"+KEY_COUNTRY+" TEXT,"+
				KEY_ADVERTISE_IMAGE+" TEXT)";

		db.execSQL(CREATE_NEWS_TABLE);
/*
		String CREATE_COMPANY_TABLE = "CREATE TABLE COMPANY(" + KEY_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_COMPANY_ID
				+ " TEXT," + KEY_COMPANY_NAME + " TEXT," + KEY_LOGO + " TEXT,"
				+ KEY_ADDRESS + " TEXT," + KEY_LOCALITY + " TEXT," + KEY_CITY
				+ " TEXT," + KEY_STATE + " TEXT," + KEY_COUNTRY + " TEXT,"
				+ KEY_EMAIL + " TEXT," + KEY_MOBILE_NO + " TEXT," + KEY_MOBILE2
				+ " TEXT," + KEY_CONTACT_PERSON + " TEXT," + KEY_STATUS
				+ " TEXT)";

		db.execSQL(CREATE_COMPANY_TABLE);
*/
		// Contacts Table
		String CREATE_CONTACTS_TABLE = "CREATE TABLE CONTACTS(" + KEY_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_USERNAME
				+ " TEXT," + KEY_MOBILE_NO + " TEXT," + KEY_DETAILS + " TEXT)";

		db.execSQL(CREATE_CONTACTS_TABLE);

		String CREATE_CATEGORY_TABLE = "CREATE TABLE CATEGORY(" + KEY_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_CATEGORY
				+ " TEXT," + KEY_CATEGORY_NAME + " TEXT," + KEY_DETAILS
				+ " TEXT)";

		db.execSQL(CREATE_CATEGORY_TABLE);

		String CREATE_SUBCATEGORY_TABLE = "CREATE TABLE SUBCATEGORY(" + KEY_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_SUBCATEGORY
				+ " TEXT," + KEY_CATEGORY + " TEXT)";

		db.execSQL(CREATE_SUBCATEGORY_TABLE);
		String CREATE_SUBCATEGORY2_TABLE = "CREATE TABLE SUBCATEGORY2("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ KEY_SUBCATEGORY2 + " TEXT," + KEY_SUBCATEGORY + " TEXT)";

		db.execSQL(CREATE_SUBCATEGORY2_TABLE);
		String CREATE_SUBCATEGORY3_TABLE = "CREATE TABLE SUBCATEGORY3("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ KEY_SUBCATEGORY3 + " TEXT," + KEY_SUBCATEGORY2 + " TEXT)";

		db.execSQL(CREATE_SUBCATEGORY3_TABLE);

		String CREATE_EVENTS_TABLE = "CREATE TABLE EVENTS(" + KEY_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_ATTRACTION_POINT
				+ " TEXT," + KEY_EVENT_CATEGORY + " TEXT," + KEY_DETAILS
				+ " TEXT," + KEY_EVENT_ADDRESS + " TEXT,"
				+ KEY_EVENT_END_DATE_TIME + " TEXT," + KEY_EVENT_ID + " TEXT,"
				+ KEY_EVENT_LOCATION + " TEXT," + KEY_EVENT_NAME + " TEXT,"
				+ KEY_EVENT_START_DATE_TIME + " TEXT," + KEY_GOING + " TEXT,"
				+ KEY_IMAGES + " TEXT," + KEY_ORGANIZER_DETAIL + " TEXT,"
				+ KEY_PEOPLE_JOINING + " TEXT,"

				+ KEY_STATUS + " TEXT,"

				+ KEY_CITY + " TEXT," + KEY_STATE + " TEXT," + KEY_COUNTRY
				+ " TEXT)";

		db.execSQL(CREATE_EVENTS_TABLE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
//		db.execSQL("Drop table if exists users");
//		db.execSQL("Drop table if exists events");

		onCreate(db);

	}

	public void addContact(String contactName, String contactNumber,
			String details) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_USERNAME, contactName);
		values.put(KEY_DETAILS, details);
		values.put(KEY_MOBILE_NO, contactNumber);

		db.insert("contacts", null, values);

		// db.close(); // Closing database connection

	}


//
//	public boolean addProperty(ArrayList<PropertyDetails> propertylist) {
//
//		SQLiteDatabase db = this.getWritableDatabase();
//		for (PropertyDetails propertyDetails : propertylist) {
//			Cursor cursor = db.rawQuery(
//					"Select * from propertydetails where uid='"
//							+ User.getInstance().getUserId() + "'", null);
//			if (cursor.getCount() > 0) {
//
//			} else {
//				ContentValues values=new ContentValues();
//
//				values.put(KEY_Id, propertyDetails.getId());
//				values.put(KEY_PROPERTYTYPE, propertyDetails.getPropertyType());
//				values.put(KEY_PROPERTYIMAGE, propertyDetails.getPropertyImage());
//				values.put(KEY_PROPERTYDETAIL, propertyDetails.getPropertyDetail());
//				values.put(KEY_UId, propertyDetails.getUid());
//				values.put(KEY_TRANSVERO, propertyDetails.getTransverto());
//				values.put(KEY_TRANSVERDATE, propertyDetails.getTransferDate());
//				values.put(KEY_LENGTH, propertyDetails.getLength());
//				values.put(KEY_BREADTH, propertyDetails.getBreadth());
//				values.put(KEY_PRICE, propertyDetails.getPrice());
//				values.put(KEY_NEGOTIABLE, propertyDetails.getNegotiable());
//				values.put(KEY_LATITUDE, propertyDetails.getLatitude());
//				values.put(KEY_LONGITUDE, propertyDetails.getLongitude());
//				values.put(KEY_cITY, propertyDetails.getCity());
//				values.put(KEY_sTATE, propertyDetails.getState());
//				values.put(KEY_aDDRESS, propertyDetails.getAddress());
//				values.put(KEY_tYPE, propertyDetails.getType());
//				values.put(KEY_date, propertyDetails.getDate());
//				db.insert("propertydetails",null,values);
//
//
//			}
//		}
//
//		// db.close(); // Closing database connection
//		return true;
//	}
	public void addlogin(Login login){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values=new ContentValues();

		values.put(KEY_Id,login.getId() );
		values.put(KEY_NAME,login.getName());
		values.put(KEY_PASSWORD,login.getPassword());

		db.insert("login", null, values);

	}

	public void addbuilder(Builder builder)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values=new ContentValues();

		values.put(KEY_Id,builder.getId() );
		values.put(KEY_NAME,builder.getName());
		values.put(KEY_PASSWORD,builder.getPassword());
		values.put(KEY_ADDRESS,builder.getAddress() );
		values.put(KEY_MOBILE,builder.getMobile());
		values.put(KEY_EMAIL,builder.getEmail() );
		values.put(KEY_CITY, builder.getCity());
		values.put(KEY_STATE,builder.getState() );
		db.insert("builder",null,values);

	}

	public void addindivisual(Indivisual indivisual)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values=new ContentValues();

		values.put(KEY_Id, indivisual.getId());
		values.put(KEY_NAME,indivisual.getName());
		values.put(KEY_PASSWORD,indivisual.getPassword());
		values.put(KEY_ADDRESS,indivisual.getAddress() );
		values.put(KEY_MOBILE,indivisual.getMobile());
		values.put(KEY_EMAIL,indivisual.getEmail() );
		values.put(KEY_CITY,indivisual.getCity() );
		values.put(KEY_STATE,indivisual.getState() );
		values.put(KEY_TYPE,indivisual.getType());
		db.insert("indivisual", null, values);

	}
	public void addUser(User user) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(KEY_USERID, user.getUserId());
		values.put(KEY_AGE, user.getAge());
		values.put(KEY_ADDRESS, user.getAddress());
		values.put(KEY_CITY, user.getCity());
		values.put(KEY_DATE, user.getDateOfRegistration());
		values.put(KEY_DETAILS, user.getDetails());
		values.put(KEY_EMAIL, user.getEmailAddress());
		values.put(KEY_LOCALITY, user.getLocality());
		values.put(KEY_MOBILE_NO, user.getMobileNo());
		values.put(KEY_PASSWORD, user.getPassword());
		values.put(KEY_PINCODE, user.getPincode());
		values.put(KEY_REFFERAL_ID, user.getRefferalId());
		values.put(KEY_STATE, user.getState());
		values.put(KEY_USERNAME, user.getUsername());
		values.put(KEY_STATUS, "LOGGEDIN");
		values.put(KEY_TYPE, user.getType());
		db.insert("users", null, values);

		// db.close(); // Closing database connection

	}
	public PropertyDetails getpropety(){
		SQLiteDatabase db=this.getWritableDatabase();
		Cursor cursor=db.rawQuery("Select * from propertyDetails", null);
		PropertyDetails propertyDetails =new PropertyDetails();
		if(cursor.getCount()>0) {
			cursor.moveToFirst();
			do {
				propertyDetails.setId(cursor.getString(1));
				propertyDetails.setPropertyType(cursor.getString(2));
				propertyDetails.setPropertyImage(cursor.getString(3));
				propertyDetails.setPropertyDetail(cursor.getString(4));
				propertyDetails.setUid(cursor.getString(5));
				propertyDetails.setTransverto(cursor.getString(6));
				propertyDetails.setTransferDate(cursor.getString(7));
				propertyDetails.setLength(cursor.getString(8));
				propertyDetails.setBreadth(cursor.getString(9));
				propertyDetails.setPrice(cursor.getString(10));
				propertyDetails.setNegotiable(cursor.getString(11));
				propertyDetails.setLatitude(cursor.getString(12));
				propertyDetails.setLongitude(cursor.getString(13));
				propertyDetails.setCity(cursor.getString(14));
				propertyDetails.setState(cursor.getString(15));
				propertyDetails.setAddress(cursor.getString(16));
				propertyDetails.setType(cursor.getString(17));
				propertyDetails.setDate(cursor.getString(18));


			} while (cursor.moveToNext());
		}

return propertyDetails;
	}
	public Builder getbuider(){
		SQLiteDatabase db=this.getWritableDatabase();
		Cursor cursor=db.rawQuery("Select * from builder", null);
		Builder builder=new Builder();

		if(cursor.getCount()>0) {
			cursor.moveToFirst();
			do {
				builder.setId(cursor.getString(1));
				builder.setName(cursor.getString(2));
				builder.setPassword(cursor.getString(3));
				builder.setAddress(cursor.getString(4));
				builder.setMobile(cursor.getString(5));
				builder.setEmail(cursor.getString(6));
				builder.setCity(cursor.getString(7));
				builder.setState(cursor.getString(8));
			} while (cursor.moveToNext());
		}

		return builder;
	}
	public Indivisual getindivisual(){
		SQLiteDatabase db=this.getWritableDatabase();
		Cursor cursor=db.rawQuery("Select * from indivisual", null);
		Indivisual indivisual=new Indivisual();

		if(cursor.getCount()>0) {
			cursor.moveToFirst();
			do {
				indivisual.setId(cursor.getString(1));
				indivisual.setName(cursor.getString(2));
				indivisual.setPassword(cursor.getString(3));
				indivisual.setAddress(cursor.getString(4));
				indivisual.setMobile(cursor.getString(5));
				indivisual.setEmail(cursor.getString(6));
				indivisual.setCity(cursor.getString(7));
				indivisual.setState(cursor.getString(8));
				indivisual.setType(cursor.getString(9));
			} while (cursor.moveToNext());
		}

		return indivisual;
	}
	public Login getlogin(){
		SQLiteDatabase db=this.getWritableDatabase();
		Cursor cursor=db.rawQuery("Select * from indivisual", null);
		Login login=new Login();

		if(cursor.getCount()>0) {
			cursor.moveToFirst();
			do {
				login.setId(cursor.getString(1));
				login.setName(cursor.getString(2));
				login.setPassword(cursor.getString(3));
			} while (cursor.moveToNext());
		}

		return login;
	}







	public User getUser() {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(
				"Select * from users where status='LOGGEDIN'", null);
		User user = User.getInstance();
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			do {
				user.setUserId(cursor.getString(1));

				user.setAddress(cursor.getString(2));
				user.setAge(cursor.getString(3));
				user.setCity(cursor.getString(4));
				user.setDateOfRegistration(cursor.getString(5));
				user.setDetails(cursor.getString(6));
				user.setEmailAddress(cursor.getString(7));

				user.setLocality(cursor.getString(8));
				user.setMobileNo(cursor.getString(9));
				user.setPassword(cursor.getString(10));
				user.setPincode(cursor.getString(11));
				user.setRefferalId(cursor.getString(12));
				user.setState(cursor.getString(13));
				user.setUsername(cursor.getString(14));
				user.setCredit(cursor.getString(15));
				user.setType(cursor.getString(17));
				// break;
			} while (cursor.moveToNext());
		}
		return user;

	}

	public boolean logOut(String userId) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from users");
		db.close();
		return true;

	}

	public synchronized void addNews(ArrayList<News> newsList, String status) {
		try {
			SQLiteDatabase db = this.getWritableDatabase();
			for (News news : newsList) {
				Cursor cursor = db.rawQuery(
						"Select count(*) from news where newsId='"
								+ news.getNewsId() + "'", null);
				// Cursor cursor=db.rawQuery("Select * from newss", null);
				cursor.moveToFirst();
				if (cursor.getInt(0) <= 0) {

					

					ContentValues values = new ContentValues();
					values.put(KEY_NEWS_ID, news.getNewsId());
					values.put(KEY_NEWS_TITLE, news.getTitle());
					values.put(KEY_REPORTER, news.getReporter());
					values.put(KEY_POSTING_DATE, news.getPostingDate());
					values.put(KEY_END_DATE, news.getEndDate());
					values.put(KEY_IMAGES, news.getImages());
					values.put(KEY_DETAILS, news.getDetail());
					values.put(KEY_CATEGORY, news.getCategoryId());
					values.put(KEY_COMMENTS_ID, news.getCommentsId());
					values.put(KEY_TYPE, news.getType());
					values.put(KEY_STATUS, status);
					values.put(KEY_LOCALITY, news.getLocality());
					values.put(KEY_CITY, news.getCity());
					values.put(KEY_STATE, news.getState());
					values.put(KEY_COUNTRY, news.getCountry());
					values.put(KEY_ADVERTISE_IMAGE, news.getAdvertiseImage());
					db.insert("news", null, values);
				}
			}
		} catch (Exception ex) {
			Log.e("Exception in adding news", ex.getMessage());
			File sdcard = Environment.getExternalStorageDirectory();
			File file = new File(sdcard, "ErrorLog.txt");
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(file));
				bw.append(ex.getMessage());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public String getLastNewsId(String category) {
		String lastNewsId = "0";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = null;
		if (category.equals("ALL")) {
			cursor = db
					.rawQuery(
							"select max(newsId) from news where city='"
									+ Location.getInstance().getCity() + "'",
							null);
		} else {
			cursor = db
					.rawQuery(
							"Select max(newsId) from news where categoryId=(Select categoryId from category where category='"
									+ category + "')", null);
		}
		try {

			cursor.moveToFirst();

			lastNewsId = String.valueOf(cursor.getInt(0));
		} catch (Exception ex) {
			return lastNewsId;
		}
		return lastNewsId;
	}

	public ArrayList<News> fetchAllNewNews() {
		ArrayList<News> newsList = new ArrayList<News>();
		SQLiteDatabase db = this.getWritableDatabase();

		Cursor cursor = db
				.rawQuery(
						"select * from news where city='"+Location.getInstance().getCity()+"' and status='NEW'",
						null);

		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			do {
				News news = new News();
				news.setNewsId(cursor.getString(1));
				news.setTitle(cursor.getString(2));
				news.setReporter(cursor.getString(3));
				news.setPostingDate(cursor.getString(4));
				news.setEndDate(cursor.getString(5));
				news.setImages(cursor.getString(6));
				news.setDetail(cursor.getString(7));
				news.setCategory(getCategory(cursor.getString(8)));
				news.setCategoryId(cursor.getString(8));
				news.setCommentsId(cursor.getString(9));
				news.setType(cursor.getString(10));
				news.setStatus(cursor.getString(11));
				news.setLocality(cursor.getString(12));
				news.setCity(cursor.getString(13));
				news.setState(cursor.getString(14));
				news.setCountry(cursor.getString(15));
				news.setAdvertiseImage(cursor.getString(16));
				newsList.add(news);
			} while (cursor.moveToNext());
		}
		return newsList;

	}

	public ArrayList<News> fetchAllNewss(HashMap<String, String> criteria) {
		ArrayList<News> newsList = new ArrayList<News>();
		SQLiteDatabase db = this.getWritableDatabase();

		Cursor cursor = db
				.rawQuery(
						"select * from news  where city='"
								+ Location.getInstance().getCity() + "' order by cast(newsId as INTEGER) desc", null);

		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			do {
				News news = new News();
				news.setNewsId(cursor.getString(1));
				news.setTitle(cursor.getString(2));
				news.setReporter(cursor.getString(3));
				news.setPostingDate(cursor.getString(4));
				news.setEndDate(cursor.getString(5));
				news.setImages(cursor.getString(6));
				news.setDetail(cursor.getString(7));
				news.setCategoryId(cursor.getString(8));
				news.setCategory(getCategory(cursor.getString(8)));
				news.setCommentsId(cursor.getString(9));
				news.setType(cursor.getString(10));
				news.setStatus(cursor.getString(11));
				news.setLocality(cursor.getString(12));
				news.setCity(cursor.getString(13));
				news.setState(cursor.getString(14));
				news.setCountry(cursor.getString(15));
				news.setAdvertiseImage(cursor.getString(16));
				newsList.add(news);
			} while (cursor.moveToNext());
		}
		
		if(criteria!=null && criteria.size()>0)
			newsList=FilterLayer.filteredNewsList(newsList, criteria, context);
		return newsList;

	}

	public News fetchNewsByName(String newsName) {
		News news = new News();
		SQLiteDatabase db = this.getWritableDatabase();

		Cursor cursor = db
				.rawQuery(
						"select * from news where city='"
								+ Location.getInstance().getCity() + "' and newsTitle='"+newsName+"'", null);

		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			do {
				
				news.setNewsId(cursor.getString(1));
				news.setTitle(cursor.getString(3));
				news.setReporter(cursor.getString(2));
				news.setPostingDate(cursor.getString(4));
				news.setEndDate(cursor.getString(5));
				news.setImages(cursor.getString(6));
				news.setDetail(cursor.getString(7));
				news.setCategoryId(cursor.getString(8));
				news.setCommentsId(cursor.getString(9));
				news.setType(cursor.getString(10));
				news.setStatus(cursor.getString(11));
				news.setLocality(cursor.getString(12));
				news.setCity(cursor.getString(13));
				news.setState(cursor.getString(14));
				news.setCountry(cursor.getString(15));
				news.setAdvertiseImage(cursor.getString(16));
			} while (cursor.moveToNext());
			return news;
		}
		else{
			return null;
		}


	}
	public boolean addCategory(ArrayList<Category> categoryList) {

		SQLiteDatabase db = this.getWritableDatabase();
		for (Category category : categoryList) {
			Cursor cursor = db.rawQuery(
					"Select * from category where categoryId='"
							+ category.getId() + "'", null);
			if (cursor.getCount() > 0) {

			} else {
				ContentValues values = new ContentValues();
				values.put(KEY_CATEGORY, category.getId());
				values.put(KEY_CATEGORY_NAME, category.getCategory());
				values.put(KEY_DETAILS, category.getDetail());

				db.insert("category", null, values);
			}
		}

		// db.close(); // Closing database connection
		return true;
	}

	public ArrayList<String> getCategoryList() {
		SQLiteDatabase db = this.getWritableDatabase();
		ArrayList<String> categoryList = new ArrayList<String>();
		Cursor cursor = db.rawQuery("Select * from category", null);
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			do {
				categoryList.add(cursor.getString(2));

			} while (cursor.moveToNext());
		}
		return categoryList;
	}
	public Category getCategory(String categoryId) {
		SQLiteDatabase db = this.getWritableDatabase();
		ArrayList<String> categoryList = new ArrayList<String>();
		Category category=new Category();
		Cursor cursor = db.rawQuery("Select * from category where categoryId='"+categoryId+"'", null);
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			do {
				categoryList.add(cursor.getString(2));
				category.setId(cursor.getString(1));
				category.setCategory(cursor.getString(2));
				category.setDetail(cursor.getString(3));
				break;

			} while (cursor.moveToNext());
		}
		return category;
	}

	public boolean addSubcategory(ArrayList<Subcategory> subcategoryList) {

		SQLiteDatabase db = this.getWritableDatabase();
		for (Subcategory subcategory : subcategoryList) {
			ContentValues values = new ContentValues();
			values.put(KEY_SUBCATEGORY, subcategory.getId());
			values.put(KEY_SUB_CATEGORY_NAME, subcategory.getSubcategory());
			values.put(KEY_CATEGORY, subcategory.getId());

			db.insert("subcategory", null, values);
		}
		// db.close(); // Closing database connection
		return true;
	}

	public boolean addSubcategory2(ArrayList<Subcategory2> subcategory2List) {

		SQLiteDatabase db = this.getWritableDatabase();
		for (Subcategory2 subcategory2 : subcategory2List) {
			ContentValues values = new ContentValues();
			values.put(KEY_SUBCATEGORY2, subcategory2.getId());
			values.put(KEY_SUB_CATEGORY2_NAME, subcategory2.getSubcategory2());
			values.put(KEY_SUBCATEGORY, subcategory2.getSubcategoryId());

			db.insert("subcategory2", null, values);
		}

		// db.close(); // Closing database connection
		return true;
	}

	public boolean addSubcategory3(ArrayList<Subcategory3> subcategory3List) {

		SQLiteDatabase db = this.getWritableDatabase();
		for (Subcategory3 subcategory3 : subcategory3List) {
			ContentValues values = new ContentValues();
			values.put(KEY_SUBCATEGORY3, subcategory3.getId());
			values.put(KEY_SUB_CATEGORY3_NAME, subcategory3.getSubcategory3());
			values.put(KEY_SUBCATEGORY2, subcategory3.getSubcategory2Id());

			db.insert("subcategory3", null, values);
		}
		// db.close(); // Closing database connection
		return true;
	}

	

	public ArrayList<News> getNewssInCity(String city) {
		SQLiteDatabase db = this.getWritableDatabase();
		ArrayList<News> newsList = new ArrayList<News>();

		Cursor cursor = db
				.rawQuery(
						"Select * from news  where city='"
								+ city + "' order by cast(newsId as INTEGER) desc", null);
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();

			do {
				News news=new News();
				news.setNewsId(cursor.getString(1));
				news.setTitle(cursor.getString(2));
				news.setReporter(cursor.getString(3));
				news.setPostingDate(cursor.getString(4));
				news.setEndDate(cursor.getString(5));
				news.setImages(cursor.getString(6));
				news.setDetail(cursor.getString(7));
				news.setCategory(getCategory(cursor.getString(8)));
				news.setCategoryId(cursor.getString(8));
				news.setCommentsId(cursor.getString(9));
				news.setType(cursor.getString(10));
				news.setStatus(cursor.getString(11));
				news.setLocality(cursor.getString(12));
				news.setCity(cursor.getString(13));
				news.setState(cursor.getString(14));
				news.setCountry(cursor.getString(15));
				news.setAdvertiseImage(cursor.getString(16));
				newsList.add(news);
			} while (cursor.moveToNext());
		}
		return newsList;

	}

//	public int getCompanyCount() {
//		SQLiteDatabase db = this.getWritableDatabase();
//		Cursor cursor = db.rawQuery("Select count(*) from company", null);
//		cursor.moveToFirst();
//		return cursor.getInt(0);
//	}
	public ArrayList<String> getLocalityList() {
		SQLiteDatabase db = this.getWritableDatabase();
		ArrayList<String> localityList = new ArrayList<String>();
		Cursor cursor = db.rawQuery("Select distinct(locality) from news where city='"
				+ Location.getInstance().getCity() + "'", null);
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			do {
				localityList.add(cursor.getString(0));

			} while (cursor.moveToNext());
		}
		return localityList;
	}//@Deprecated
//	public ArrayList<String> getCompanyList() {
//		SQLiteDatabase db = getWritableDatabase();
//		ArrayList<String> companyNameList=new ArrayList<String>();
//		Cursor cursor = db.rawQuery("Select distinct(name) from company where city='" + Location.getInstance().getCity()+ "'", null);
//		Company company = new Company();
//		if (cursor.getCount() > 0) {
//			cursor.moveToFirst();
//			do{
//			companyNameList.add(cursor.getString(0  ));
//			}while(cursor.moveToNext());
//				cursor.close();
//		}
//		return companyNameList;
//	}

	public void updateSeenStatus() {
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("update news set status='SEEN'");

	}


	public void updateEventSeenStatus() {
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("update events set status='SEEN'");

	}
	public void deleteproperty(){
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("delete from property where  Uqid= "+5);
		db.close();


	}
	
	public ArrayList<Event> fetchNewEvents() {
		ArrayList<Event> eventList = new ArrayList<Event>();
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(
				"Select * from events where status='NEW' and city='"
						+ Location.getInstance().getCity() + "'", null);

		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			do {

				Event event = new Event();
				event.setAttractionPoint(cursor.getString(1));
				event.setCategory(cursor.getString(2));
				event.setDetail(cursor.getString(3));
				event.setEventAddress(cursor.getString(4));
				event.setEventStartDateTime(cursor.getString(5));
				event.setEventId(cursor.getString(6));
				event.setEventLocation(cursor.getString(7));
				event.setEventName(cursor.getString(8));
				event.setEventStartDateTime(cursor.getString(9));
				event.setGoing(cursor.getString(10));
				event.setImages(cursor.getString(11));
				event.setOrganizerDetail(cursor.getString(12));
				event.setPeopleJoining(cursor.getString(13));
				event.setStatus(cursor.getString(14));
				event.setCity(cursor.getString(15));
				event.setState(cursor.getString(16));
				event.setCountry(cursor.getString(17));
				eventList.add(event);
			} while (cursor.moveToNext());
		}
		return eventList;
	}

	public ArrayList<Event> fetchEvents(String location) {
		ArrayList<Event> eventList = new ArrayList<Event>();
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery("Select * from events where city='"
				+ location + "'", null);

		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			do {

				Event event = new Event();
				event.setAttractionPoint(cursor.getString(1));
				event.setCategory(cursor.getString(2));
				event.setDetail(cursor.getString(3));
				event.setEventAddress(cursor.getString(4));
				event.setEventStartDateTime(cursor.getString(5));
				event.setEventId(cursor.getString(6));
				event.setEventLocation(cursor.getString(7));
				event.setEventName(cursor.getString(8));
				event.setEventStartDateTime(cursor.getString(9));
				event.setGoing(cursor.getString(10));
				event.setImages(cursor.getString(11));
				event.setOrganizerDetail(cursor.getString(12));
				event.setPeopleJoining(cursor.getString(13));
				event.setStatus(cursor.getString(14));
				event.setCity(cursor.getString(15));
				event.setState(cursor.getString(16));
				event.setCountry(cursor.getString(17));
				eventList.add(event);
			} while (cursor.moveToNext());
		}
		return eventList;
	}

	public String getLastEventId() {
		String lastEventId = "0";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = null;

		cursor = db.rawQuery("Select max(eventId) from events where city='"
				+ Location.getInstance().getCity() + "'", null);

		try {

			cursor.moveToFirst();

			lastEventId = String.valueOf(cursor.getInt(0));
		} catch (Exception ex) {
			return lastEventId;
		}
		return lastEventId;
	}
	public void addEvent(ArrayList<Event> eventList, String status) {
		try {
			SQLiteDatabase db = this.getWritableDatabase();
			for (Event event : eventList) {
				Cursor cursor = db.rawQuery(
						"Select * from events where eventId='"
								+ event.getEventId() + "'", null);
				// Cursor cursor=db.rawQuery("Select * from offers", null);
				if (cursor.getCount() <= 0) {

					ContentValues eventValues = new ContentValues();
					eventValues.put(KEY_EVENT_ID, event.getEventId());
					eventValues.put(KEY_EVENT_NAME, event.getEventName());
					eventValues.put(KEY_ATTRACTION_POINT,
							event.getAttractionPoint());
					eventValues.put(KEY_EVENT_CATEGORY, event.getCategory());
					eventValues.put(KEY_DETAILS, event.getDetail());
					eventValues.put(KEY_EVENT_ADDRESS, event.getEventAddress());
					eventValues.put(KEY_EVENT_END_DATE_TIME,
							event.getEventEndDateTime());
					eventValues.put(KEY_EVENT_LOCATION,
							event.getEventLocation());
					eventValues.put(KEY_EVENT_START_DATE_TIME,
							event.getEventStartDateTime());
					eventValues.put(KEY_ORGANIZER_DETAIL,
							event.getOrganizerDetail());
					eventValues.put(KEY_PEOPLE_JOINING,
							event.getPeopleJoining());
					eventValues.put(KEY_GOING, event.getGoing());
					eventValues.put(KEY_IMAGES, event.getImages());
					eventValues.put(KEY_STATUS, status);
					eventValues.put(KEY_CITY, event.getCity());
					eventValues.put(KEY_STATE, event.getState());
					eventValues.put(KEY_COUNTRY, event.getCountry());
					db.insert("events", null, eventValues);

				}
			}
		} catch (Exception ex) {
			Log.e("Exception in adding events", ex.getMessage());
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

	public void deleteClosedNews(String newsIdList) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("Delete from news where newsId IN (" +newsIdList+ ")");
		//db.execSQL(Script.DELETE_EXPIRED_COMPANIES);
	}

}
