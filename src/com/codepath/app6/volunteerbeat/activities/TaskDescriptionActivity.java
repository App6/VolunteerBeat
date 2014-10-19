package com.codepath.app6.volunteerbeat.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.app6.volunteerbeat.R;
import com.codepath.app6.volunteerbeat.clients.VolunteerBeatClient;
import com.codepath.app6.volunteerbeat.fragments.ApplyTaskFragment;
import com.codepath.app6.volunteerbeat.models.TaskItem;
import com.codepath.app6.volunteerbeat.models.UserProfile;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

public class TaskDescriptionActivity extends FragmentActivity implements
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener {

	private static final int LOGIN_ACTIVITY_CODE = 200;
	private boolean mShowApplyTaskDialog = false;

	// private ImageView ivNonProfitOrgLogo;
	private TextView tvNonProfiOrgName;
	private RatingBar rbNonProfitOrgRating;
	private TextView tvTaskName1;
	private TextView tvTaskDueDate;
	private TextView tvTaskDueTime;
	private TextView tvTaskDescription;
	private TextView tvTaskPostedDate;
	private ImageView ivNonProfitOrgLogo;
	private SupportMapFragment mapFragment;
	private GoogleMap map;
	private LocationClient mLocationClient;
	private double gpsLatitude;
	private double gpsLongitude;

	private TaskItem task;
	private ShareActionProvider miShareAction;

	/*
	 * Define a request code to send to Google Play services This code is
	 * returned in Activity.onActivityResult
	 */
	private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
	static final LatLng SANJOSE = new LatLng(37.3209, -121.977);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_description);

		getActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.rgb(0XEF, 0X43, 0X1C)));
		getActionBar().setTitle(
				Html.fromHtml("<font color='#ffffff'>VB </font>"));

		setupReferences();

		task = getIntent().getParcelableExtra("taskInfo");

		if (task != null) {
			tvNonProfiOrgName.setText(task.getOrganization().getOrgName());
			rbNonProfitOrgRating.setRating(task.getOrganization()
					.getOrgRating());
			tvTaskName1.setText(task.getTaskName());
			tvTaskDueDate.setText("Due: " + task.getDueDate());
			tvTaskDueTime.setText(task.getDueTime());
			tvTaskDescription.setText(task.getTaskShortDesc());
			tvTaskPostedDate.setText("Posted: " + task.getPostedDate());
			boolean volunteered = UserProfile.getProfile(getApplicationContext()).isVolunteerdTask(task.getTaskId());
			if(volunteered) {
				Button b = (Button)findViewById(R.id.bVolunteer);
				b.setText("Thanks for Volunteering");
				b.setTextColor(Color.GREEN);
				b.setTextSize(14);
				b.setClickable(false);
			}
			Picasso.with(getApplicationContext())
					.load(task.getOrganization().getOrgLogoUri())
					.into(ivNonProfitOrgLogo);
			gpsLatitude = task.getGpsLatitude();
			gpsLongitude = task.getGpsLongitude();
		}

	}

	private void setupReferences() {
		// ivNonProfitOrgLogo = (ImageView)
		// findViewById(R.id.ivNonProfitOrgLogo);
		tvNonProfiOrgName = (TextView) findViewById(R.id.tvNonProfiOrgName);
		rbNonProfitOrgRating = (RatingBar) findViewById(R.id.rbNonProfitOrgRating);
		tvTaskName1 = (TextView) findViewById(R.id.tvTaskName1);
		tvTaskDueDate = (TextView) findViewById(R.id.tvTaskDueDate);
		tvTaskDueTime = (TextView) findViewById(R.id.tvTaskDueTime);
		tvTaskDescription = (TextView) findViewById(R.id.tvTaskDescription);
		tvTaskPostedDate = (TextView) findViewById(R.id.tvTaskPostedDate);
		ivNonProfitOrgLogo = (ImageView) findViewById(R.id.ivNonProfitOrgLogo);
		mapFragment = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.fgMap));

		mLocationClient = new LocationClient(this, this, this);
		if (mapFragment != null) {
			map = mapFragment.getMap();
			if (map != null) {
				map.setOnMapClickListener(new OnMapClickListener() {

					@Override
					public void onMapClick(LatLng arg0) {
						// String label = task.getOrgName();
						// String uriBegin = "geo:" + arg0.latitude + ","
						// + arg0.longitude;
						// String query = arg0.latitude + "," + arg0.longitude +
						// "("
						// + label + ")";
						// String encodedQuery = Uri.encode(query);
						// String uriString = uriBegin + "?q=" + encodedQuery
						// + "&z=16";
						// Uri uri = Uri.parse(uriString);
						//
						// startActivity(new Intent(
						// android.content.Intent.ACTION_VIEW, uri));

						Intent intent = new Intent(
								android.content.Intent.ACTION_VIEW,
								Uri.parse("http://maps.google.com/maps?daddr="
										+ arg0.latitude + "," + arg0.longitude));
						startActivity(intent);
					}

				});

				map.setOnMapClickListener(new OnMapClickListener() {

					@Override
					public void onMapClick(LatLng arg0) {
						// String label = task.getOrgName();
						// String uriBegin = "geo:" + arg0.latitude + ","
						// + arg0.longitude;
						// String query = arg0.latitude + "," + arg0.longitude +
						// "("
						// + label + ")";
						// String encodedQuery = Uri.encode(query);
						// String uriString = uriBegin + "?q=" + encodedQuery
						// + "&z=16";
						// Uri uri = Uri.parse(uriString);
						//
						// startActivity(new Intent(
						// android.content.Intent.ACTION_VIEW, uri));

						Intent intent = new Intent(
								android.content.Intent.ACTION_VIEW,
								Uri.parse("http://maps.google.com/maps?daddr="
										+ arg0.latitude + "," + arg0.longitude));
						startActivity(intent);
					}

				});
				// Move the camera instantly to hamburg with a zoom of 15.
				map.moveCamera(CameraUpdateFactory.newLatLngZoom(SANJOSE, 15));

				// Zoom in, animating the camera.
				map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);

				Toast.makeText(this, "Map Fragment was loaded properly!",
						Toast.LENGTH_SHORT).show();
				// map.setMyLocationEnabled(true);
				// Marker sanJose = map.addMarker(new MarkerOptions().position(
				// SANJOSE).title("San Jose"));

				// Move the camera instantly to hamburg with a zoom of 15.
				map.moveCamera(CameraUpdateFactory.newLatLngZoom(SANJOSE, 15));

				// Zoom in, animating the camera.
				map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
			} else {
				Toast.makeText(this, "Error - Map was null!!",
						Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(this, "Error - Map Fragment was null!!",
					Toast.LENGTH_SHORT).show();
		}
	}

	// Gets the image URI and setup the associated share intent to hook into the
	// provider
	public void setupShareIntent() {
		// Create share intent as described above
		Intent shareIntent = new Intent();
		shareIntent.setAction(Intent.ACTION_SEND);

		shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Need Volunteer");
		shareIntent.putExtra(
				Intent.EXTRA_TEXT,
				task.getOrganization().getOrgName()
						+ " Need Volunteer for task:-" + "\n\n"
						+ task.getTaskShortDesc() + "\n\n"
						+ "Due date for this task is: " + task.getDueDate());

		shareIntent.setType("text/plain");

		// Attach share event to the menu item provider
		miShareAction.setShareIntent(shareIntent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.task_description, menu);
		// Locate MenuItem with ShareActionProvider
		MenuItem item = menu.findItem(R.id.menu_item_share);
		// Fetch reference to the share action provider
		miShareAction = (ShareActionProvider) item.getActionProvider();

		setupShareIntent();

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.

		return super.onOptionsItemSelected(item);
	}

	/*
	 * Called when the Activity becomes visible.
	 */
	@Override
	protected void onStart() {
		super.onStart();
		// Connect the client.
		if (isGooglePlayServicesAvailable()) {
			mLocationClient.connect();
		}

	}

	/*
	 * Called when the Activity is no longer visible.
	 */
	@Override
	protected void onStop() {
		// Disconnecting the client invalidates it.
		mLocationClient.disconnect();
		super.onStop();
	}

	/*
	 * Handle results returned to the FragmentActivity by Google Play services
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// Decide what to do based on the original request code
		switch (requestCode) {

		case CONNECTION_FAILURE_RESOLUTION_REQUEST:
			/*
			 * If the result code is Activity.RESULT_OK, try to connect again
			 */
			switch (resultCode) {
			case Activity.RESULT_OK:
				mLocationClient.connect();
				break;
			}
			break;
		case LOGIN_ACTIVITY_CODE:
			switch (resultCode) {
			case Activity.RESULT_OK:
				mShowApplyTaskDialog = true;
				break;
			}
			break;
		}
	}

	@Override
	protected void onResumeFragments() {
		// TODO Auto-generated method stub
		super.onResumeFragments();

		if (mShowApplyTaskDialog) {
			mShowApplyTaskDialog = false;
			showApplyTaskDialog();
		} else if(UserProfile.getProfile(getApplicationContext()).isVolunteerdTask(task.getTaskId())) {
				Button b = (Button)findViewById(R.id.bVolunteer);
				b.setClickable(false);
		}		
	}

	private boolean isGooglePlayServicesAvailable() {
		// Check that Google Play services is available
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);
		// If Google Play services is available
		if (ConnectionResult.SUCCESS == resultCode) {
			// In debug mode, log the status
			Log.d("Location Updates", "Google Play services is available.");
			return true;
		} else {
			// Get the error dialog from Google Play services
			Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(
					resultCode, this, CONNECTION_FAILURE_RESOLUTION_REQUEST);

			// If Google Play services can provide an error dialog
			if (errorDialog != null) {
				// Create a new DialogFragment for the error dialog
				ErrorDialogFragment errorFragment = new ErrorDialogFragment();
				errorFragment.setDialog(errorDialog);
				errorFragment.show(getSupportFragmentManager(),
						"Location Updates");
			}

			return false;
		}
	}

	/*
	 * Called by Location Services when the request to connect the client
	 * finishes successfully. At this point, you can request the current
	 * location or start periodic updates
	 */
	@Override
	public void onConnected(Bundle dataBundle) {
		// Display the connection status
		// LatLng latLng = new LatLng(SANJOSE.latitude, SANJOSE.longitude);
		LatLng latLng = new LatLng(gpsLatitude, gpsLongitude);
		map.addMarker(new MarkerOptions().position(latLng).title(
				task.getOrganization().getOrgName()));
		CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng,
				17);
		map.animateCamera(cameraUpdate);

		// Location location = mLocationClient.getLastLocation();
		// if (location != null) {
		// Toast.makeText(this, "GPS location was found!", Toast.LENGTH_SHORT)
		// .show();
		// LatLng latLng = new LatLng(location.getLatitude(),
		// location.getLongitude());
		// CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
		// latLng, 17);
		// map.animateCamera(cameraUpdate);
		// } else {
		// Toast.makeText(this,
		// "Current location was null, enable GPS on emulator!",
		// Toast.LENGTH_SHORT).show();
		// }
	}

	/*
	 * Called by Location Services if the connection to the location client
	 * drops because of an error.
	 */
	@Override
	public void onDisconnected() {
		// Display the connection status
		Toast.makeText(this, "Disconnected. Please re-connect.",
				Toast.LENGTH_SHORT).show();
	}

	/*
	 * Called by Location Services if the attempt to Location Services fails.
	 */
	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		/*
		 * Google Play services can resolve some errors it detects. If the error
		 * has a resolution, try sending an Intent to start a Google Play
		 * services activity that can resolve error.
		 */
		if (connectionResult.hasResolution()) {
			try {
				// Start an Activity that tries to resolve the error
				connectionResult.startResolutionForResult(this,
						CONNECTION_FAILURE_RESOLUTION_REQUEST);
				/*
				 * Thrown if Google Play services canceled the original
				 * PendingIntent
				 */
			} catch (IntentSender.SendIntentException e) {
				// Log the error
				e.printStackTrace();
			}
		} else {
			Toast.makeText(getApplicationContext(),
					"Sorry. Location services not available to you",
					Toast.LENGTH_LONG).show();
		}
	}

	public void onTaskSave(MenuItem item) {
		Toast.makeText(getApplicationContext(), "Store the task for later ...",
				Toast.LENGTH_LONG).show();
	}

	// Define a DialogFragment that displays the error dialog
	public static class ErrorDialogFragment extends DialogFragment {

		// Global field to contain the error dialog
		private Dialog mDialog;

		// Default constructor. Sets the dialog field to null
		public ErrorDialogFragment() {
			super();
			mDialog = null;
		}

		// Set the dialog to display
		public void setDialog(Dialog dialog) {
			mDialog = dialog;
		}

		// Return a Dialog to the DialogFragment.
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			return mDialog;
		}
	}

	public void showOrg(View v) {
		Intent i = new Intent(this, OrganizationActivity.class);
		i.putExtra("organization", task.getOrganization());
		startActivity(i);
	}

	public void onClickVolunteer(View view) {
		if (VolunteerBeatClient.hasDoneLogin()) {
			showApplyTaskDialog();
		} else {
			Intent i = new Intent(this, LoginActivity.class);
			startActivityForResult(i, LOGIN_ACTIVITY_CODE);
		}
	}

	private void showApplyTaskDialog() {
		ApplyTaskFragment applyTaskFragment = new ApplyTaskFragment();
		// Show DialogFragment
		Bundle args = new Bundle();
		args.putString("taskId", String.valueOf(task.getTaskId()));
		applyTaskFragment.setArguments(args);
		applyTaskFragment.show((FragmentManager) getSupportFragmentManager(),
				"Advanced Filters Dialog Fragment");
	}

}
