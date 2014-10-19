package com.codepath.app6.volunteerbeat.fragments;

import org.json.JSONObject;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.app6.volunteerbeat.R;
import com.codepath.app6.volunteerbeat.clients.VolunteerBeatClient;
import com.codepath.app6.volunteerbeat.models.UserProfile;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class ApplyTaskFragment extends DialogFragment {
	private static String	APPLY_TASK_URL = "tasks/<TASK_ID>/apply";
	
	private UserProfile profile = new UserProfile();
	
	
    @Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        
    	View view = inflater.inflate(R.layout.fragment_task_apply, container, false);
		profile.readFromPreference(PreferenceManager.getDefaultSharedPreferences(getActivity()));
    	
		setTextView(view, R.id.tvName, profile.getName());

	    ImageView ivProfileImage = (ImageView)view.findViewById(R.id.ivProfileImage);
    	ApplyTaskFragment.setProfileImage(ivProfileImage, profile.getPhotoUri(), getActivity().getContentResolver());

    	Button  btnSend = (Button)view.findViewById(R.id.btnSend);
    	btnSend.setOnClickListener(new OnClickListener() {
    		@Override
    		public void onClick(View v) {
    			onSend(v);
    		}
    	});
    	
    	Button  btnCancel = (Button)view.findViewById(R.id.btnCancel);
    	btnCancel.setOnClickListener(new OnClickListener() {
    		@Override
    		public void onClick(View v) {
    			onCancel(v);
    		}
    	});
    	return view;
    }

	private void setTextView(View view, int id, String text) {
		if (text != null && !text.isEmpty()) {
			((TextView)view.findViewById(id)).setText(text);	
		}
	}
	
	public static void setProfileImage(ImageView ivPhoto, String uriStr, ContentResolver contentResolver) {
		if (uriStr == null || uriStr.isEmpty()) {
			ivPhoto.setImageResource(R.drawable.default_photo);
		} else {
			try {
				Uri photoUri = Uri.parse(uriStr);
				Bitmap selectedImage = MediaStore.Images.Media.getBitmap(contentResolver, photoUri);
		        // Load the selected image into a preview
		        ivPhoto.setImageBitmap(selectedImage);		
			} catch (Exception e) {
				ivPhoto.setImageResource(R.drawable.default_photo);			
			}
		}
		
	}
	
	public void onCancel(View v) {
		getDialog().dismiss();
	}
	
	public void onSend(View v) {
		String taskId="2";
		String message=null;
		
		RequestParams params = new RequestParams();
		params.put("task_id", taskId);
		if (message != null && !message.isEmpty()) {
			params.put("text", message);

		}
		
		String url = APPLY_TASK_URL.replace("<TASK_ID>", taskId);
		VolunteerBeatClient.post(url, params,
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, JSONObject arg1) {
						Toast.makeText(getActivity(),
								"Successfully applied", Toast.LENGTH_SHORT)
								.show();
						getDialog().dismiss();
					}

					@Override
					public void onFailure(Throwable arg0, String arg1) {
						Toast.makeText(getActivity(),
								"login failed - " + arg1 + "try again",
								Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onFailure(Throwable arg0, JSONObject arg1) {
						Log.e("LoginActivity",
								"Login failed: " + arg1.toString());
						Toast.makeText(getActivity(),
								"login failed - try again", Toast.LENGTH_SHORT)
								.show();
					}
				});
	}
}