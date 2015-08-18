package com.sourabh.appnews.core;


import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sourabh.DTO.RegisterDTO;
import com.sourabh.appnews.core.R;
import com.sourabh.businessService.AuthenticationInterface;
import com.sourabh.businessService.AuthenticationService;
import com.sourabh.entity.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AccountDetailsFragment extends Fragment implements AdapterView.OnItemSelectedListener {
	RegisterDTO registerDTO;
	static String message;
	int pos;
	String value;

	EditText userName;
	EditText password;
	EditText mobileNo;
	EditText emailId;
	EditText confirmPassword;
	EditText refferalCode;
	EditText address;

	EditText city;
	EditText state;
	EditText age;
	private String[] type;
	ArrayAdapter<String> adapter1;

	Spinner spinner_type;
	public Boolean authentic=false;
	User user=User.getInstance();
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		// Retrieving the currently selected item number

		// Creating view correspoding to the fragment
		View v = inflater.inflate(R.layout.account_details_layout, container, false);

		// Getting reference to the TextView of the Fragment

		userName = (EditText)v. findViewById(R.id.signup_userName);
		password = (EditText)v. findViewById(R.id.signup_password);
		mobileNo = (EditText)v. findViewById(R.id.MobileNo);
		emailId = (EditText)v. findViewById(R.id.Email_ID);
		confirmPassword = (EditText)v. findViewById(R.id.confirm_password);
		refferalCode = (EditText)v. findViewById(R.id.refferalCode);
		address = (EditText)v. findViewById(R.id.address);

		city = (EditText)v. findViewById(R.id.city);
		state = (EditText)v. findViewById(R.id.State);
		age = (EditText)v. findViewById(R.id.age);
		type= getResources().getStringArray(R.array.type);
		spinner_type = (Spinner)v.findViewById(R.id.spinner1);
		adapter1 = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item,type);
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_type.setAdapter(adapter1);
		spinner_type.setOnItemSelectedListener(this);
		Button update=(Button)v.findViewById(R.id.update);
		emailId.setText(user.getEmailAddress());
		mobileNo.setText(user.getMobileNo());
		address.setText(user.getAddress());
		city.setText(user.getCity());
		state.setText(user.getState());
		age.setText(user.getAge());
		userName.setText(user.getUsername());
		confirmPassword.setText(user.getConfirmpassword());
		// Setting currently selected river name in the TextView

		password.setText(user.getPassword());

		value= user.getType();
		pos = adapter1.getPosition(value);
		spinner_type.setSelection(pos);


		update.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				registerDTO = new RegisterDTO();
				registerDTO.setId(user.getUserId());
				registerDTO.setEmail(emailId.getText().toString());
				registerDTO.setPassword(password.getText().toString());
				registerDTO.setMobile(mobileNo.getText().toString());
				registerDTO.setName(userName.getText().toString());

				registerDTO.setAddress(address.getText().toString());

				registerDTO.setCity(city.getText().toString());
				registerDTO.setState(state.getText().toString());
				registerDTO.setAge(age.getText().toString());
				registerDTO.setType((String) spinner_type.getSelectedItem().toString());
				if (validateUserInput()) {
					AsyncUpdateUser asyncUpdateUser = new AsyncUpdateUser();
					asyncUpdateUser.execute();
				} else {
					Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
				}
			}
		});

		// Updating the action bar title
		//getActivity().getActionBar().setTitle(rivers[position]);

		return v;
	}
	boolean validateUserInput(){
		if( registerDTO.getName().equals("") ||  registerDTO.getPassword().equals("")  || registerDTO.getMobile().equals("")){
			message="Please fill all required fields.";
			return false;
		}else if(!registerDTO.getPassword().equals(confirmPassword.getText().toString())){
			message="Password and Confirm Password don't match.";
			return false;
		}else if(!isValidMail(registerDTO.getEmail())){
			message="Email ID is not valid.";
			return false;
		}else if(!isValidMobile(registerDTO.getMobile())){
			message="Please enter valid 10 digit mobile no.";
			return false;
		}else{
			return true;
		}
	}
	private boolean isValidMail(String email2)
	{
		boolean check;
		Pattern p;
		Matcher m;

		String EMAIL_STRING = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

		p = Pattern.compile(EMAIL_STRING);

		m = p.matcher(email2);
		check = m.matches();


		return check;
	}
	private boolean isValidMobile(String phone2)
	{
		boolean check;
		if(phone2.length() !=10)
		{
			check = false;

		}
		else
		{
			check = true;
		}
		return check;
	}

	@Override
	public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

	}

	@Override
	public void onNothingSelected(AdapterView<?> adapterView) {

	}

	private class AsyncUpdateUser extends AsyncTask<String, String, String> {
		ProgressDialog Asycdialog = new ProgressDialog(getActivity());
		@Override
		protected String doInBackground(String... arg0) {
			AuthenticationInterface authenticationInterface=new AuthenticationService();
			authentic= authenticationInterface.doRegister(registerDTO).getIsAuthentic();
			return null;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			Asycdialog.setMessage(getResources().getString(
					R.string.UPDATING_INFO));
			Asycdialog.setCanceledOnTouchOutside(false);
			// show dialog
			Asycdialog.show();
//			registerDTO=new RegisterDTO();
//			registerDTO.setEmail(emailId.getText().toString());
//			registerDTO.setPassword(password.getText().toString());
//			registerDTO.setMobile(mobileNo.getText().toString());
//			registerDTO.setName(userName.getText().toString());
//			registerDTO.setAddress(address.getText().toString());
//			registerDTO.setCity(city.getText().toString());
//			registerDTO.setState(state.getText().toString());
//			registerDTO.setAge(age.getText().toString());
//			registerDTO.setType((String) spinner_type.getSelectedItem().toString());
		}

		protected void onPostExecute(String transactionStatus) {
			Asycdialog.hide();
			if(authentic){
				Toast.makeText(getActivity(),"Information Updated",Toast.LENGTH_LONG).show();
			}

		}
	}
}