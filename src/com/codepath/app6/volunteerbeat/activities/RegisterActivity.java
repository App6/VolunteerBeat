package com.codepath.app6.volunteerbeat.activities;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.app6.volunteerbeat.R;
import com.codepath.app6.volunteerbeat.clients.VolunteerBeatClient;
import com.codepath.app6.volunteerbeat.models.UserProfile;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class RegisterActivity extends Activity {
	private static final String REGISTER_URL = "register";
	private EditText etPassword;
	private EditText etEmailAddress;
	private EditText etUserName;
	private Button bGotoSignIn;
	private Button bRegister;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		setupReferences();
	}

	private void setupReferences() {
		etUserName = (EditText) findViewById(R.id.etUserName);
		etEmailAddress = (EditText) findViewById(R.id.etEmailAddress);
		etPassword = (EditText) findViewById(R.id.etPassword);
		bGotoSignIn = (Button) findViewById(R.id.bGotoSignIn);
		bRegister = (Button) findViewById(R.id.bRegister);

		// Listening to Register click
		bRegister.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				onRegister(v);
			}
		});
		
		// Listening to Login Screen link
		bGotoSignIn.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				// Closing registration screen
				// Switching to Login Screen/closing register screen
				finish();
			}
		});
	}

	private void onRegister(View v) {
		RequestParams params = new RequestParams();
		params.put("display_name", etUserName.getText().toString());
		params.put("email", etEmailAddress.getText().toString());
		params.put("password", etPassword.getText().toString());

		VolunteerBeatClient.post(REGISTER_URL, params,
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, JSONObject arg1) {
						try {
//							Log.e("RegisterActivity", "Registered - id = "
//									+ arg1.getInt("id"));
//							Toast.makeText(getApplicationContext(),
//									"Successfull registration",
//									Toast.LENGTH_SHORT).show();
							saveCurrentUser(arg1.getInt("id"));
						} catch (JSONException e) {
							e.printStackTrace();
						} finally {
//							Toast.makeText(getApplicationContext(), "welcome",
//									Toast.LENGTH_SHORT).show();
						}
						Intent i = new Intent();
						i.putExtra("email", etEmailAddress.getText().toString());
						setResult(RESULT_OK, i);
						finish();

					}

					@Override
					public void onFailure(Throwable arg0, String arg1) {
						Log.e("RegisterActivity", "Register failed: " + arg1);
						Toast.makeText(getApplicationContext(),
								"Register failed - " + arg1 + "try again",
								Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onFailure(Throwable arg0, JSONObject arg1) {
						Log.e("RegisterActivity",
								"Register failed: " + arg1.toString());
						Toast.makeText(getApplicationContext(),
								"Register failed - try again",
								Toast.LENGTH_SHORT).show();
					}
				});
	}

	private void saveCurrentUser(int id) {
		UserProfile profile = UserProfile.getInstance(this);;

		// Prompt user to enter all data at register? or let user edit later
		// For now use some dummy data
		profile.setName(etUserName.getText().toString());
		//profile.setAddress("Address");
		//profile.setPhone("000-000-000");
		profile.setEmail(etEmailAddress.getText().toString());
		profile.setId(id);
		//profile.setAboutMe("About me");
		//profile.setHobbies("My Hobbies");

		profile.writeToPreference(PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext()));
	}

}
