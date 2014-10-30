package com.codepath.app6.volunteerbeat.activities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.plattysoft.leonids.ParticleSystem;
import com.plattysoft.leonids.modifiers.ScaleModifier;
import com.squareup.picasso.Picasso;

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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

public class TaskDescriptionFragment extends Fragment implements
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener,
		ApplyDialogListener {
	private static final int LOGIN_ACTIVITY_CODE = 200;
	private static final int ADD_EVENT_ACTIVITY_CODE = 300;

	private boolean mShowApplyTaskDialog = false;

	// private ImageView ivNonProfitOrgLogo;
	private RelativeLayout rlOrgHeader;
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
	private MapView mapView;
	private GoogleMap map;
	private LocationClient mLocationClient;
	private double gpsLatitude;
	private double gpsLongitude;

	private Task task;

	/*
	 * Define a request code to send to Google Play services This code is
	 * returned in Activity.onActivityResult
	 */
	private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
	static final LatLng SANJOSE = new LatLng(37.3209, -121.977);

	// Creates a new fragment given an int and title
	// DemoFragment.newInstance(5, "Hello");
	public static TaskDescriptionFragment newInstance(Task task) {
		TaskDescriptionFragment fragmentDemo = new TaskDescriptionFragment();
		Bundle args = new Bundle();
		args.putParcelable("task", task);
		((Fragment) fragmentDemo).setArguments(args);
		return fragmentDemo;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		task = getArguments().getParcelable("task");
		super.onCreate(savedInstanceState);
	}

	// This event fires 3rd, and is the first time views are available in the
	// fragment
	// The onCreateView method is called when Fragment should create its View
	// object hierarchy.
	// Use onCreateView to get a handle to views as soon as they are freshly
	// inflated
	@Override
	public View onCreateView(LayoutInflater inf, ViewGroup parent,
			Bundle savedInstanceState) {
		
	    Log.e("onCreateView", "task : " + task.getTaskName());

		View v =  inf.inflate(R.layout.task_description_fragment, parent, false);
		setupReferences(v);
		mapView.onCreate(savedInstanceState);
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
			Picasso.with(getActivity())
					.load(task.getOrganization().getOrgLogoUri())
					.into(ivNonProfitOrgLogoBG);
			Picasso.with(getActivity())
					.load(task.getOrganization().getOrgLogoUri())
					.into(ivNonProfitOrgLogo);
			gpsLatitude = task.getGpsLatitude();
			gpsLongitude = task.getGpsLongitude();
		}
		return v;
	}

	/**** The mapfragment's id must be removed from the FragmentManager
	 **** or else if the same it is passed on the next time then 
	 **** app will crash ****/
	@Override
	public void onDestroyView() {
	    super.onDestroyView();
	    Log.e("onDestroyView", "task : " + task.getTaskName());
		mapView.onDestroy();

/*	    if (mapFragment != null) {
	    	TaskDescriptionActivity.fragmentManager.beginTransaction()
	            .remove(mapFragment).commitAllowingStateLoss();
		    Log.e("onDestroyView", "Removed map fragment");

	        map = null;
	        mapFragment = null;
	    }
	    */
	}
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if (mapView != null) {
			mapView.onPause();
		}
	}
	

	private void displayVolunteered() {
		
		bVolunteer.setText("Thanks for Volunteering");
		bVolunteer.setTextColor(Color.GREEN);
		bVolunteer.setTextColor(getResources().getColor(R.color.vbgreen));
		bVolunteer.setTextSize(18);
		bVolunteer.setBackgroundColor(Color.TRANSPARENT);
		bVolunteer.setClickable(false);
	}

	private void setupReferences(View v) {
		tvNonProfiOrgName = (TextView) v.findViewById(R.id.tvHeaderOrgName);
		rbNonProfitOrgRating = (RatingBar) v.findViewById(R.id.rbHeaderOrgRating);
		tvOrgLocation = (TextView) v.findViewById(R.id.tvHeaderOrgLocation);
		tvTaskName1 = (TextView) v.findViewById(R.id.tvTaskName1);
		tvTaskDueDate = (TextView) v.findViewById(R.id.tvTaskDueDate);
		tvTaskDueTime = (TextView) v.findViewById(R.id.tvTaskDueTime);
		tvTaskDescription = (TextView) v.findViewById(R.id.tvTaskDescription);
		tvTaskPostedDate = (TextView) v.findViewById(R.id.tvTaskPostedDate);
		ivNonProfitOrgLogo = (ImageView) v.findViewById(R.id.ivHeaderOrgLogo);
		ivNonProfitOrgLogoBG = (ImageView) v.findViewById(R.id.ivHeaderOrgLogoBG);

		rlOrgHeader = (RelativeLayout) v.findViewById(R.id.top_header);
		if (task != null) {
			rlOrgHeader.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i = new Intent(getActivity(),
							OrganizationActivity.class);
					i.putExtra("organization", task.getOrganization());
					getActivity().startActivity(i);
					getActivity().overridePendingTransition(
							R.anim.from_middle, R.anim.to_middle);
				}
			});
		}
		bVolunteer = (Button) v.findViewById(R.id.bVolunteer);
		bVolunteer.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				onClickVolunteer(v);
			}

		});
        mapView = ((MapView) v.findViewById(R.id.mapView));
		mLocationClient = new LocationClient(getActivity(), this, this);

        // Let's setup maps in onStart()
	}
	
	public void setUpMap() {
		
		if (mapView != null) {
	        MapsInitializer.initialize(getActivity());
			map = mapView.getMap();
	        
			if (map != null) {
//				Toast.makeText(getActivity(), "Created map", Toast.LENGTH_SHORT).show();
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
				
//				Toast.makeText(getActivity(), "Error - Couldn't load Maps",	Toast.LENGTH_SHORT).show();
			}
		} else {
//			Toast.makeText(getActivity(), "Error - Map View was null!!", Toast.LENGTH_SHORT).show();
		}
	}



	/*
	 * Called when the Activity becomes visible.
	 */
	@Override
	public void onStart() {
		super.onStart();
		// Connect the client.
		if (isGooglePlayServicesAvailable()) {
			setUpMap();
			mLocationClient.connect();
		}

	}

	/*
	 * Called when the Activity is no longer visible.
	 */
	@Override
	public void onStop() {
		// Disconnecting the client invalidates it.
		mLocationClient.disconnect();
		super.onStop();
	}

	/*
	 * Handle results returned to the FragmentActivity by Google Play services
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
		case ADD_EVENT_ACTIVITY_CODE:
			showFireworks();
			break;
		}

	}
	

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (mShowApplyTaskDialog) {
			mShowApplyTaskDialog = false;
			showApplyTaskDialog();
		} else if (UserProfile.getCurrentUser().isVolunteeredTask(
				task.getTaskId())) {
			bVolunteer.setClickable(false);
		}
		if (mapView != null) {
			mapView.onResume();
		}
	}

	
	private boolean isGooglePlayServicesAvailable() {
		// Check that Google Play services is available
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getActivity());
		// If Google Play services is available
		if (ConnectionResult.SUCCESS == resultCode) {
			// In debug mode, log the status
			Log.d("Location Updates", "Google Play services is available.");
			return true;
		} else {
			// Get the error dialog from Google Play services
			Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(
					resultCode, getActivity(), CONNECTION_FAILURE_RESOLUTION_REQUEST);

			// If Google Play services can provide an error dialog
			if (errorDialog != null) {
				// Create a new DialogFragment for the error dialog
				ErrorDialogFragment errorFragment = new ErrorDialogFragment();
				errorFragment.setDialog(errorDialog);
				errorFragment.show(TaskDescriptionActivity.fragmentManager,
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
		if (map == null) {
			setUpMap();
		}
		
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
		Toast.makeText(getActivity(), "Location Services Disconnected. Please re-connect.",
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
				connectionResult.startResolutionForResult(getActivity(),
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
			Toast.makeText(getActivity(),
					"Sorry. Location services not available to you",
					Toast.LENGTH_LONG).show();
		}
	}

	public void onTaskSave(MenuItem item) {
//		Toast.makeText(getActivity(), "Store the task for later ...",
//				Toast.LENGTH_LONG).show();
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
		Intent i = new Intent(getActivity(), OrganizationActivity.class);
		i.putExtra("organization", task.getOrganization());
		startActivity(i);
	}

	public void onClickVolunteer(View view) {
		UserProfile profile = UserProfile.getCurrentUser();
		if (profile.isLoggedIn()) {
			showApplyTaskDialog();
		} else {
			Intent i = new Intent(getActivity(), LoginActivity.class);
			startActivityForResult(i, LOGIN_ACTIVITY_CODE);
		}
	}

	private void showApplyTaskDialog() {
		ApplyTaskFragment applyTaskFragment = new ApplyTaskFragment();
		applyTaskFragment.setListner(this);
		// Show DialogFragment
		Bundle args = new Bundle();
		args.putString("taskId", String.valueOf(task.getTaskId()));
		args.putString("orgName", task.getOrganization().getOrgName());
		args.putString("orgLogoUri", task.getOrganization().getOrgLogoUri());
		applyTaskFragment.setArguments(args);
		applyTaskFragment.show((FragmentManager) TaskDescriptionActivity.fragmentManager,
				"Advanced Filters Dialog Fragment");
	}

	@Override
	public void onFinishEditDialog(boolean applied) {
		if (applied) {
			displayVolunteered();
			addCalendarEvent();
			// showFireworks();
		}
	}


	public void addCalendarEvent() {
		try {
			Date date = new SimpleDateFormat("yyyy/MM/dd hh:mm a").parse(task
					.getDueDate() + " " + task.getDueTime());

			Intent intent = new Intent(Intent.ACTION_EDIT);
			intent.setType("vnd.android.cursor.item/event");
			intent.putExtra("beginTime", date.getTime());
			intent.putExtra("endTime", date.getTime() + 60 * 60 * 1000);
			intent.putExtra("title", task.getOrganization().getOrgName().trim()
					+ ": " + task.getTaskName());
			intent.putExtra("description", task.getTaskShortDesc());
			// startActivity(intent);
			startActivityForResult(intent, ADD_EVENT_ACTIVITY_CODE);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void showFireworks() {
		final ParticleSystem ps = new ParticleSystem(getActivity(), 200,
				R.drawable.star_pink, 650);
		ps.setScaleRange(0.7f, 1.3f);
		ps.setSpeedRange(0.1f, 0.3f);
		ps.setRotationSpeedRange(90, 180);
		ps.setFadeOut(100, new AccelerateInterpolator(0.2f));

		final ParticleSystem ps2 = new ParticleSystem(getActivity(), 200,
				R.drawable.rate_star_big_on_vb, 650);
		ps2.setScaleRange(0.7f, 1.3f);
		ps2.setSpeedRange(0.1f, 0.3f);
		ps2.setRotationSpeedRange(90, 180);
		ps2.setFadeOut(100, new AccelerateInterpolator(0.2f));

		final ParticleSystem ps3 = new ParticleSystem(getActivity(), 200,
				R.drawable.star_stars, 650);
		ps3.setScaleRange(0.7f, 1.3f);
		ps3.setSpeedRange(0.1f, 0.3f);
		ps3.setRotationSpeedRange(90, 180);
		ps3.setFadeOut(100, new AccelerateInterpolator(0.2f));

		ps2.oneShot(bVolunteer, 80);
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			public void run() {
				ps.oneShot(bVolunteer, 120);
			}
		}, 300);

		Handler handler2 = new Handler();
		handler2.postDelayed(new Runnable() {
			public void run() {
				ps3.oneShot(bVolunteer, 120);
			}
		}, 600);

	}

}
