package com.sourabh.appnews.core;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sourabh.DTO.LoginDTO;
import com.sourabh.businessService.AuthenticationInterface;
import com.sourabh.businessService.AuthenticationService;
import com.sourabh.database.Thedb;
import com.sourabh.entity.User;
import com.sourabh.utility.ConnectionDetector;

public class LoginActivity extends Activity {
	
	AuthenticationInterface authenticationInterface;
	Boolean authentic=false;
	Intent navigationIntent;
	private LoginDTO loginDTO;
	ConnectionDetector connectionDetector;
	public LoginDTO getLoginDTO() {
		return loginDTO;
	}

	public void setLoginDTO(LoginDTO loginDTO) {
		this.loginDTO = loginDTO;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loginscreen);
		 connectionDetector=new ConnectionDetector(getApplicationContext());
		initialize();
		
		
	}
	public void forgotPassword(View view){

		if(connectionDetector.isConnectingToInternet()){
			showLoginDialog();
		}
		else{
			Toast.makeText(getApplicationContext(),"Please switch Mobile Data On or connect to a Wifi before proceeding",Toast.LENGTH_LONG).show();
		}




	}
	protected void showLoginDialog() {

		final AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
		// Get the layout inflater
		LayoutInflater inflater = LoginActivity.this.getLayoutInflater();



		// Inflate and set the layout for the dialog
		// Pass null as the parent view because its going in the dialog layout

		View v=inflater.inflate(R.layout.forgotpwd, null);
		builder.setView(v);
		final AlertDialog dialog= builder.create();
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
		// Add action buttons
		final EditText usern;
		usern= (EditText) v.findViewById(R.id.usernameforgotpwd);
		Button submit=(Button)v.findViewById(R.id.submitforgotpwd);
		submit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(usern.getText()!=null){
					AsyncForgotPwd asyncForgotPwd=new AsyncForgotPwd();
					asyncForgotPwd.execute(usern.getText().toString());

				}
			}
		});


	}

	private void initialize() {
		loginDTO=new LoginDTO();
		authenticationInterface=new AuthenticationService();
//		Intent alarmIntent = new Intent(this, AlarmReciever.class);
//		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
//		AlarmManager manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
//	    int interval = 10000;
//
//	    manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
	   
	}

	private boolean checkIfUserSignedIn() {
		boolean flag=false;
		Thedb databaseHandler;
		databaseHandler=Thedb.getInstance(getApplicationContext());;
		String userId=databaseHandler.getUser().getUserId();
		if(userId!=null)
		flag=true;
		else
		flag=false;
		return flag;
	}

	
	public void signUpForm(View view){
		navigationIntent=new Intent(getApplicationContext(),SignUpActivity.class);
		startActivity(navigationIntent);
		
	}
	public void signIn(View view){
		if(connectionDetector.isConnectingToInternet()) {
			if(assembleDTO().equals(true))
			{
				AsyncSignIn asynSignIn = new AsyncSignIn();
			asynSignIn.execute();}
			else{
				Toast.makeText(getApplicationContext(), "Please enter all the fields", Toast.LENGTH_LONG).show();
			}


		}else{
			Toast.makeText(getApplicationContext(),"Please connect to internet before logging in",Toast.LENGTH_LONG).show();
		}
//		Intent i=new Intent(LoginActivity.this,MainActivity.class);
//		startActivity(i);

		
	}
	public Boolean assembleDTO(){
		
		EditText userName=(EditText)findViewById(R.id.username);
		EditText password=(EditText)findViewById(R.id.password);
		loginDTO.setUsername(userName.getText().toString());
		loginDTO.setPassword(password.getText().toString());
		if (loginDTO.getPassword()!= null && !loginDTO.getPassword().trim().isEmpty() && loginDTO.getUsername() != null && !loginDTO.getUsername().trim().isEmpty())
			return true;
		else
			return false;

		}
	private class AsyncForgotPwd extends AsyncTask<String, Void, String>{
		ProgressDialog Asycdialog = new ProgressDialog(LoginActivity.this);
		@Override
		protected String doInBackground(String... arg0) {

			Thedb databaseHandler;
			try{
				String result= authenticationInterface.forgotPassword(arg0[0]);

				return result;
			}catch(Exception ex){
				return "failed";
			}

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			Asycdialog.setMessage(getResources().getString(
					R.string.RETRIEVING_PWD));
			Asycdialog.setCanceledOnTouchOutside(false);
			// show dialog
			Asycdialog.show();
		}

		protected void onPostExecute(String result) {
			Asycdialog.hide();
			if(result.equals("success")) {
				Toast.makeText(getApplicationContext(), "Password sent via email", Toast.LENGTH_LONG).show();
			}else {
				Toast.makeText(getApplicationContext(), "Username does not exists", Toast.LENGTH_LONG).show();

			}

		}

	}
	
	private class AsyncSignIn extends AsyncTask<String, Void, Boolean>{
		ProgressDialog Asycdialog = new ProgressDialog(LoginActivity.this);
		Boolean flag;
		@Override
		protected Boolean doInBackground(String... arg0) {

			Thedb databaseHandler;
			try {
				authentic = authenticationInterface.doLogin(loginDTO).getIsAuthentic();
				if(authentic) {
					databaseHandler = Thedb.getInstance(getApplicationContext());

					databaseHandler.addUser(User.getInstance());
					flag = true;
				}else{
					flag=false;
				}
			}
			catch(Exception ex){
				flag=false;
			}
			return flag;
		}
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			Asycdialog.setMessage(getResources().getString(
					R.string.LOGIN_ACCOUNT));
			Asycdialog.setCanceledOnTouchOutside(false);
			// show dialog
			Asycdialog.show();
		}
		protected void onPostExecute(Boolean result) {
			Asycdialog.hide();
			if(result){
			Toast.makeText(getApplicationContext(), getResources().getString(R.string.Login_Successfull), Toast.LENGTH_LONG).show();
			if(authentic){
				navigationIntent=new Intent(getApplicationContext(),MainActivity.class);
				startActivity(navigationIntent);
				finish();
			}
			}else{
				Toast.makeText(getApplicationContext(), "Invalid User", Toast.LENGTH_LONG).show();
			}
		}
		
	}
	
}

