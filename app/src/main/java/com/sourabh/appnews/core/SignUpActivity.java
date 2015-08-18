package com.sourabh.appnews.core;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.sourabh.DTO.RegisterDTO;
import com.sourabh.businessService.AuthenticationInterface;
import com.sourabh.businessService.AuthenticationService;
import com.sourabh.constants.AllScreens;
import com.sourabh.appnews.core.R;
import com.sourabh.entity.User;
import com.sourabh.utility.ConnectionDetector;
import com.sourabh.utility.Utilities;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class SignUpActivity extends Activity implements AdapterView.OnItemSelectedListener {
	private RegisterDTO registerDTO;


		 EditText userName;
		EditText password;
		 EditText mobileNo;
		 EditText emailId;
		 EditText confirmPassword;
		 EditText refferalCode;
		 EditText address;
		 EditText locality;
		 EditText city;
		 EditText state;
		 EditText age;
	ConnectionDetector connectionDetector;
	private String[] type;
	ArrayAdapter<String> adapter1;

	Spinner	spinner_type;
	AuthenticationInterface authenticationInterface;
	Intent navigationIntent;

	public Boolean authentic=false;
	public RegisterDTO getRegisterDTO() {
		return registerDTO;
	}

	public void setRegisterDTO(RegisterDTO registerDTO) {
		this.registerDTO = registerDTO;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signupform);
		connectionDetector=new ConnectionDetector(getApplicationContext());
		authenticationInterface=new AuthenticationService();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sign_up, menu);
		return true;
	}
	protected void showLoginDialog() {
		// Utilities.saveToFile(getApplicationContext(),"RegisterDTO",registerDTO);
		//  step="verification";
		//  Utilities.writeToSharedPref(getApplicationContext(),"step",step);
		String tm=String.valueOf(System.currentTimeMillis());
		String consta=null;
		try {
			consta = tm.substring(tm.length() - 4, tm.length());
		}
		catch(Exception ex){
			Toast.makeText(getApplicationContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
		}
		String msg="Your%20mobile%20verification%20code%20is%20"+consta+".Please%20enter%20when%20asked.Team%20CoreApps";
		//String consta="1234";
		Utilities.writeToSharedPref(getApplicationContext(),"verificationCode",consta);
		try {
			new RequestTask().execute("http://sms1.dogmaindia.com/sendsms.jsp?user=core123&password=core123&mobiles=" + registerDTO.getMobile() + "&sms=" + msg + "&verion=3");
		}catch (Exception ex){
			Toast.makeText(getApplicationContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
		}
		final AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
		// Get the layout inflater
		LayoutInflater inflater = SignUpActivity.this.getLayoutInflater();



		// Inflate and set the layout for the dialog
		// Pass null as the parent view because its going in the dialog layout

		View v=inflater.inflate(R.layout.verificationcode, null);
		builder.setView(v);
		final AlertDialog dialog= builder.create();
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
		// Add action buttons
		final EditText verify;
		verify = (EditText) v.findViewById(R.id.verificationCode);
		Button verifyNow=(Button)v.findViewById(R.id.verifyNow);
		verifyNow.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(verify.getText().toString().equals(Utilities.readFromSharedPref(getApplicationContext(), "verificationCode"))){
					dialog.dismiss();
					AsyncSignUp asyncSignUp = new AsyncSignUp();
					asyncSignUp.execute();
				}
				else{
					Toast.makeText(getApplicationContext(), getResources().getString(R.string.VERIFICATION_CODE), Toast.LENGTH_LONG).show();
				}
			}
		});


	}
	public void initialize() {
		userName = (EditText) findViewById(R.id.signup_userName);
		password = (EditText) findViewById(R.id.signup_password);
		mobileNo = (EditText) findViewById(R.id.MobileNo);
		emailId = (EditText) findViewById(R.id.Email_ID);
		confirmPassword = (EditText) findViewById(R.id.confirm_password);
		refferalCode = (EditText) findViewById(R.id.refferalCode);
		address = (EditText) findViewById(R.id.address);

		city = (EditText) findViewById(R.id.city);
		state = (EditText) findViewById(R.id.State);
		age = (EditText) findViewById(R.id.age);
		type= getResources().getStringArray(R.array.type);
		spinner_type = (Spinner)findViewById(R.id.spinner1);
		adapter1 = new ArrayAdapter<String>(getApplicationContext(),
				android.R.layout.simple_spinner_item,type);
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_type.setAdapter(adapter1);
		spinner_type.setOnItemSelectedListener(this);
	}
	
	public void signUpUser(View view){
		initialize();
	Boolean flag=validateUserInput();
		if(connectionDetector.isConnectingToInternet()) {
			if (flag == true) {
				assembleDTO();
				showLoginDialog();


			}
		}else{
		Toast.makeText(getApplicationContext(),"Please connect to internet before Signing Up",Toast.LENGTH_LONG).show();
	}

		
		
	}
	void assembleDTO(){
		registerDTO=new RegisterDTO();
		initialize();
		registerDTO.setName(userName.getText().toString());
		registerDTO.setPassword(password.getText().toString());

		registerDTO.setMobile(mobileNo.getText().toString());
		registerDTO.setEmail(emailId.getText().toString());
		//registerDTO.setRefferalCode(refferalCode.getText().toString());
		registerDTO.setAddress(address.getText().toString());
		registerDTO.setCity(city.getText().toString());
		registerDTO.setState(state.getText().toString());
		registerDTO.setType((String)spinner_type.getSelectedItem().toString());

		registerDTO.setAge(age.getText().toString());


		
	}
	Boolean validateUserInput(){
		Boolean flag;
		Validation validation=new Validation(getApplicationContext());
		String email=emailId.getText().toString();
		String City=city.getText().toString();
		String State=state.getText().toString();
		String Address  =address.getText().toString();
		String Username=userName.getText().toString();
		String Password=password.getText().toString();
		String Confirmpassword=confirmPassword.getText().toString();

		String blankarray[]={City,State,Address,Username,Password,Confirmpassword};
		String mobileno=mobileNo.getText().toString();
		if(validation.blank(blankarray)==true &&validation.email(email)==true &&   validation.mobileNo(mobileno)==true )
		{
			flag=true;
		}
		else {
			flag=false;
		}
		return flag;



		
	}

	@Override
	public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

	}

	@Override
	public void onNothingSelected(AdapterView<?> adapterView) {

	}


	private class AsyncSignUp extends AsyncTask<String, Void, Void>{
		ProgressDialog Asycdialog = new ProgressDialog(SignUpActivity.this);
		@Override
		protected Void doInBackground(String... arg0) {
			authentic= authenticationInterface.doRegister(registerDTO).getIsAuthentic();
			return null;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			Asycdialog.setMessage(getResources().getString(
					R.string.CREATING_ACCOUNT));
			Asycdialog.setCanceledOnTouchOutside(false);
			// show dialog
			Asycdialog.show();
		}
		protected void onPostExecute(Void result) {
			Asycdialog.hide();


			//Utilities.writeToSharedPref(getApplicationContext(),"step","registration");
			Utilities.writeToSharedPref(getApplicationContext(), "verificationCode", "");
			if(authentic){
				Toast.makeText(getApplicationContext(), getResources().getString(R.string.Registration_Successfull), Toast.LENGTH_LONG).show();
				navigationIntent=new Intent(getApplicationContext(),MainActivity.class);
				startActivity(navigationIntent);
			}

		}


	}

	class RequestTask extends AsyncTask<String, String, String>{

		@Override
		protected String doInBackground(String... uri) {
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response;
			String responseString = null;
			try {
				response = httpclient.execute(new HttpGet(uri[0]));
				StatusLine statusLine = response.getStatusLine();
				if(statusLine.getStatusCode() == HttpStatus.SC_OK){
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					response.getEntity().writeTo(out);
					responseString = out.toString();
					out.close();
				} else{
					//Closes the connection.
					response.getEntity().getContent().close();
					throw new IOException(statusLine.getReasonPhrase());
				}
			}catch (Exception ex){
				Toast.makeText(getApplicationContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
			}
			return responseString;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			//Do anything with response..
		}
	}



	

}
