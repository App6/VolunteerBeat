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

public class LoginActivity extends Activity {
	private static final String SESSION_URL = "session";
	private EditText etEmailAddress;
	private EditText etPassword;
	private Button bSignIn;
	private Button bGotoCreateAccount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		setupReferences();
	}

	private void setupReferences() {
		etEmailAddress = (EditText) findViewById(R.id.etEmailAddress);
		etPassword = (EditText) findViewById(R.id.etPassword);
		bSignIn = (Button) findViewById(R.id.bSignIn);
		bGotoCreateAccount = (Button) findViewById(R.id.bGotoCreateAccount);

		// Listening to SignIn click
		bSignIn.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				onLoginGo(v);
			}
		});

		// Listening to register new account link
		bGotoCreateAccount.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				// Switching to Register screen
				Intent i = new Intent(getApplicationContext(),
						RegisterActivity.class);
				startActivity(i);
			}
		});
	}

	private void onLoginGo(View v) {
		RequestParams params = new RequestParams();
		params.put("email", etEmailAddress.getText().toString());
		params.put("password", etPassword.getText().toString());

		VolunteerBeatClient.post(SESSION_URL, params,
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, JSONObject arg1) {
						try {
							Log.e("LoginActivity", "Successfull login - id = "
									+ arg1.getInt("id"));
							Toast.makeText(getApplicationContext(),
									"Successfull login", Toast.LENGTH_SHORT)
									.show();
							saveCurrentUser(arg1.getInt("id"));
							VolunteerBeatClient.doneLogin();
						} catch (JSONException e) {
							e.printStackTrace();
						} finally {
							Toast.makeText(getApplicationContext(), "welcome",
									Toast.LENGTH_SHORT).show();
						}
						Intent i = new Intent();
						setResult(RESULT_OK, i);
						finish();
					}

					@Override
					public void onFailure(Throwable arg0, String arg1) {
						Log.e("LoginActivity", "Login failed: " + arg1);
						Toast.makeText(getApplicationContext(),
								"login failed - " + arg1 + "try again",
								Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onFailure(Throwable arg0, JSONObject arg1) {
						Log.e("LoginActivity",
								"Login failed: " + arg1.toString());
						Toast.makeText(getApplicationContext(),
								"login failed - try again", Toast.LENGTH_SHORT)
								.show();
					}
				});
	}

	private void saveCurrentUser(int id) {
		UserProfile profile = new UserProfile();
		profile.readFromPreference(PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext()));

		// How to get data from server?
		// For now use some dummt data
		//profile.setName("VB Test");
		//profile.setAddress("Sunnyvale, CA");
		//profile.setPhone("415-123-4567");
		profile.setEmail(etEmailAddress.getText().toString());
		profile.setId(id);
		//profile.setAboutMe("About me");
		//profile.setHobbies("My Hobbies");

		profile.writeToPreference(PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext()));
	}
}