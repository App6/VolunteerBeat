package com.codepath.app6.volunteerbeat.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.app6.volunteerbeat.R;
import com.codepath.app6.volunteerbeat.fragments.ApplyTaskFragment;
import com.codepath.app6.volunteerbeat.fragments.ApplyTaskFragment.ApplyDialogListener;
import com.codepath.app6.volunteerbeat.models.Task;
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
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.plattysoft.leonids.ParticleSystem;
import com.plattysoft.leonids.modifiers.ScaleModifier;
import com.squareup.picasso.Picasso;

public class TaskDescriptionActivity extends FragmentActivity implements
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener,
		ApplyDialogListener {

	private static final int LOGIN_ACTIVITY_CODE = 200;
	private boolean mShowApplyTaskDialog = false;

	// private ImageView ivNonProfitOrgLogo;
	private TextView tvNonProfiOrgName;
	private RatingBar rbNonProfitOrgRating;
	private TextView tvOrgLocation;
	private TextView tvTaskName1;
	private TextView tvTaskDueDate;
	private TextView tvTaskDueTime;
	private TextView tvTaskDescription;
	private TextView tvTaskPostedDate;
	private Button bVolunteer;
	private ImageView ivNonProfitOrgLogo;
	private ImageView ivNonProfitOrgLogoBG;
	private SupportMapFragment mapFragment;
	private GoogleMap map;
	private LocationClient mLocationClient;
	private double gpsLatitude;
	private double gpsLongitude;

	private Task task;
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
		getActionBar().setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.activity_task_description);

		setupReferences();

		task = getIntent().getParcelableExtra("taskInfo");

		if (task != null) {
			tvNonProfiOrgName.setText(task.getOrganization().getOrgName());
			rbNonProfitOrgRating.setRating(task.getOrganization()
					.getOrgRating());
			tvOrgLocation.setText(task.getOrganization().getOrgLocation());
			tvTaskName1.setText(task.getTaskName());
			tvTaskDueDate.setText("Due: " + task.getDueDate());
			tvTaskDueTime.setText(task.getDueTime());
			tvTaskDescription.setText(task.getTaskShortDesc());
			tvTaskPostedDate.setText("Posted: " + task.getPostedDate());
			boolean volunteered = UserProfile.getCurrentUser()
					.isVolunteeredTask(task.getTaskId());
			if (volunteered) {
				displayVolunteered();
			}
			Picasso.with(getApplicationContext())
					.load(task.getOrganization().getOrgLogoUri())
					.into(ivNonProfitOrgLogoBG);
			Picasso.with(getApplicationContext())
					.load(task.getOrganization().getOrgLogoUri())
					.into(ivNonProfitOrgLogo);
			gpsLatitude = task.getGpsLatitude();
			gpsLongitude = task.getGpsLongitude();
		}

	}

	private void displayVolunteered() {
		Button b = (Button) findViewById(R.id.bVolunteer);
		b.setText("Thanks for Volunteering");
		b.setTextColor(Color.GREEN);
		b.setTextSize(14);
		b.setClickable(false);
	}

	private void setupReferences() {
		tvNonProfiOrgName = (TextView) findViewById(R.id.tvHeaderOrgName);
		rbNonProfitOrgRating = (RatingBar) findViewById(R.id.rbHeaderOrgRating);
		tvOrgLocation = (TextView) findViewById(R.id.tvHeaderOrgLocation);
		tvTaskName1 = (TextView) findViewById(R.id.tvTaskName1);
		tvTaskDueDate = (TextView) findViewById(R.id.tvTaskDueDate);
		tvTaskDueTime = (TextView) findViewById(R.id.tvTaskDueTime);
		tvTaskDescription = (TextView) findViewById(R.id.tvTaskDescription);
		tvTaskPostedDate = (TextView) findViewById(R.id.tvTaskPostedDate);
		ivNonProfitOrgLogo = (ImageView) findViewById(R.id.ivHeaderOrgLogo);
		ivNonProfitOrgLogoBG = (ImageView) findViewById(R.id.ivHeaderOrgLogoBG);

		bVolunteer = (Button) findViewById(R.id.bVolunteer);
		bVolunteer.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				onClickVolunteer(v);
			}

		});

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
						+ "\n\nNeed Volunteer for task:-" + "\n\n"
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
		MenuItem item = menu.findItem(R.id.menu_item_share1);

		// Fetch reference to the share action provider
		miShareAction = (ShareActionProvider) item.getActionProvider();
		setupShareIntent();
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			overridePendingTransition(R.anim.open_main, R.anim.close_next);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
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
		} else if (UserProfile.getCurrentUser().isVolunteeredTask(
				task.getTaskId())) {
			Button b = (Button) findViewById(R.id.bVolunteer);
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

		map.clear();

		// Define color of marker icon
		BitmapDescriptor defaultMarker = BitmapDescriptorFactory
				.defaultMarker(BitmapDescriptorFactory.HUE_RED);

		// Display the connection status
		// LatLng latLng = new LatLng(SANJOSE.latitude, SANJOSE.longitude);
		LatLng latLng = new LatLng(gpsLatitude, gpsLongitude);
		Marker marker = map
				.addMarker(new MarkerOptions().position(latLng)
						.title(task.getOrganization().getOrgName())
						.icon(defaultMarker));

		dropPinEffect(marker);

		// Move the camera instantly to hamburg with a zoom of 15.
		CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng,
				15);

		// Zoom in, animating the camera.
		map.animateCamera(cameraUpdate, 1500, null);

	}

	private void dropPinEffect(final Marker marker) {
		// Handler allows us to repeat a code block after a specified delay
		final android.os.Handler handler = new android.os.Handler();
		final long start = SystemClock.uptimeMillis();
		final long duration = 1500;

		// Use the bounce interpolator
		final android.view.animation.Interpolator interpolator = new BounceInterpolator();

		// Animate marker with a bounce updating its position every 15ms
		handler.post(new Runnable() {
			@Override
			public void run() {
				long elapsed = SystemClock.uptimeMillis() - start;
				// Calculate t for bounce based on elapsed time
				float t = Math.max(
						1 - interpolator.getInterpolation((float) elapsed
								/ duration), 0);
				// Set the anchor
				marker.setAnchor(0.5f, 1.0f + 4 * t);

				if (t > 0.0) {
					// Post this event again 15ms from now.
					handler.postDelayed(this, 15);
				} else { // done elapsing, show window
					marker.showInfoWindow();
				}
			}
		});
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
		UserProfile profile = UserProfile.getCurrentUser();
		if (profile.isLoggedIn()) {
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
		args.putString("orgName", task.getOrganization().getOrgName());
		args.putString("orgLogoUri", task.getOrganization().getOrgLogoUri());
		applyTaskFragment.setArguments(args);
		applyTaskFragment.show((FragmentManager) getSupportFragmentManager(),
				"Advanced Filters Dialog Fragment");
	}

	public void onAnimButtonClick(View v) {
		final ParticleSystem ps = new ParticleSystem(this, 200, R.drawable.star_pink,
				650);
		ps.setScaleRange(0.7f, 1.3f);
		ps.setSpeedRange(0.1f, 0.3f);
		ps.setRotationSpeedRange(90, 180);
		ps.setFadeOut(100, new AccelerateInterpolator(0.2f));

		final ParticleSystem ps2 = new ParticleSystem(this, 200,
				R.drawable.rate_star_big_on_vb, 650);
		ps2.setScaleRange(0.7f, 1.3f);
		ps2.setSpeedRange(0.1f, 0.3f);
		ps2.setRotationSpeedRange(90, 180);
		ps2.setFadeOut(100, new AccelerateInterpolator(0.2f));

		final ParticleSystem ps3 = new ParticleSystem(this, 200,
				R.drawable.star_stars, 650);
		ps3.setScaleRange(0.7f, 1.3f);
		ps3.setSpeedRange(0.1f, 0.3f);
		ps3.setRotationSpeedRange(90, 180);
		ps3.setFadeOut(100, new AccelerateInterpolator(0.2f));

		ps2.oneShot(findViewById(R.id.button1), 80);
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			public void run() {
				ps.oneShot(findViewById(R.id.button1), 120);
			}
		}, 300);

		Handler handler2 = new Handler();
		handler2.postDelayed(new Runnable() {
			public void run() {
				ps3.oneShot(findViewById(R.id.button1), 120);
			}
		}, 600);
	}

	@Override
	public void onFinishEditDialog(boolean applied) {
		if (applied) {
			displayVolunteered();
			if (task.getTaskId() == 2) {
				// new ParticleSystem(this, 100, R.drawable.star_pink, 800)
				// .setSpeedRange(0.2f, 0.5f).oneShot(
				// findViewById(R.id.bVolunteer), 100);
				// new ParticleSystem(this, 4, R.drawable.dust, 3000)
				// .setSpeedByComponentsRange(-0.07f, 0.07f, -0.18f, -0.24f)
				// .setAcceleration(0.00003f, 30)
				// .setInitialRotationRange(0, 360)
				// .addModifier(new AlphaModifier(255, 0, 1000, 3000))
				// .addModifier(new ScaleModifier(0.5f, 2f, 0, 1000))
				// .oneShot(findViewById(R.id.emiter_bottom), 4);
				new ParticleSystem(this, 100, R.drawable.animated_confetti,
						5000).setSpeedRange(0.2f, 0.5f)
						.setRotationSpeedRange(90, 180)
						.setInitialRotationRange(0, 360)
						.oneShot(findViewById(R.id.bVolunteer), 100);
			} else if (task.getTaskId() == 1) {
				ParticleSystem ps = new ParticleSystem(this, 100,
						R.drawable.star_stars, 2000);
				ps.setScaleRange(0.7f, 1.3f);
				ps.setSpeedRange(0.2f, 0.5f);
				ps.setAcceleration(0.0002f, 90);
				ps.setRotationSpeedRange(90, 180);
				ps.setFadeOut(200, new AccelerateInterpolator());
				ps.oneShot(findViewById(R.id.bVolunteer), 100);
			} else if (task.getTaskId() == 3) {
				new ParticleSystem(this, 80, R.drawable.confeti3, 10000)
						.setSpeedModuleAndAngleRange(0f, 0.3f, 180, 180)
						.setRotationSpeed(144).setAcceleration(0.00005f, 90)
						.emit(findViewById(R.id.emiter_top_right), 8);
				new ParticleSystem(this, 80, R.drawable.confeti2, 10000)
						.setSpeedModuleAndAngleRange(0f, 0.3f, 0, 0)
						.setRotationSpeed(144).setAcceleration(0.00005f, 90)
						.emit(findViewById(R.id.emiter_top_left), 8);
			} else if (task.getTaskId() == 4) {
				for (int i = 0; i < 10; i++) {
					ParticleSystem ps = new ParticleSystem(this, 100,
							R.drawable.star_stars, 1500);
					ps.setScaleRange(0.7f, 1.3f);
					ps.setSpeedRange(0.2f, 0.5f);
					ps.setRotationSpeedRange(90, 180);
					ps.setFadeOut(200, new AccelerateInterpolator());
					ps.oneShot(findViewById(R.id.bVolunteer), 70);
					ParticleSystem ps2 = new ParticleSystem(this, 100,
							R.drawable.star_white, 1500);
					ps2.setScaleRange(0.7f, 1.3f);
					ps2.setSpeedRange(0.2f, 0.5f);
					ps.setRotationSpeedRange(90, 180);
					ps2.setFadeOut(200, new AccelerateInterpolator());
					ps2.oneShot(findViewById(R.id.bVolunteer), 70);
				}
			} else if (task.getTaskId() == 5) {
				new ParticleSystem(this, 10, R.drawable.star, 3000)
						.setSpeedByComponentsRange(-0.3f, 0.3f, -0.3f, 0.1f)
						.setAcceleration(0.00001f, 90)
						.setInitialRotationRange(0, 360).setRotationSpeed(120)
						.setFadeOut(2000)
						.addModifier(new ScaleModifier(0f, 1.5f, 0, 1500))
						.oneShot(findViewById(R.id.bVolunteer), 10);
			}

		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition(R.anim.open_main, R.anim.close_next);
	}

}
