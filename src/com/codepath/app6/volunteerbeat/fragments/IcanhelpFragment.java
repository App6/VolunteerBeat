package com.codepath.app6.volunteerbeat.fragments;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.app6.volunteerbeat.R;
import com.codepath.app6.volunteerbeat.apiclient.ApiClient;
import com.codepath.app6.volunteerbeat.models.UserProfile;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

public class IcanhelpFragment extends DialogFragment {
	
	private UserProfile profile = new UserProfile();
	private ApiClient apiClient = new ApiClient();
   	private AsyncHttpClient 			client = new AsyncHttpClient();
	
    @Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        
    	View view = inflater.inflate(R.layout.fragment_icanvolunteer, container, false);
		profile.readFromPreference(PreferenceManager.getDefaultSharedPreferences(getActivity()));
    	
		setTextView(view, R.id.tvName, profile.getName());

	    ImageView ivProfileImage = (ImageView)view.findViewById(R.id.ivProfileImage);
    	IcanhelpFragment.setProfileImage(ivProfileImage, profile.getPhotoUri(), getActivity().getContentResolver());

        Button cancelButton = (Button)view.findViewById(R.id.btnCancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        Button sendButton = (Button)view.findViewById(R.id.btnSend);
        sendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
  /*          	int taskId= 2;
            	String message = "i can help";
            	apiClient.applyToTask(getActivity(), taskId, message);
            	*/
            	//test1();   
            	test2();
            }

        });

		return view;
    }
	

	private void test2() {
 
        JSONObject jsonParams = new JSONObject();
        //"email":"vbtest123@gmail.com",
        //"password":"volunteerbeat",
        try {
        	
       
        jsonParams.put("email", "vbtest123@gmail.com");
        jsonParams.put("password", "volunteerbeat");        
        StringEntity entity = new StringEntity(jsonParams.toString());
        
        client.addHeader("Accept", "application/vnd.volunteerbeat-v1+json");
        client.addHeader("Content-Type", "application/json");   
        String	CREATE_SESSION_URL = "http://api.volunteerbeat.com/session";
        client.post(getActivity(), CREATE_SESSION_URL, entity, "application/json", new JsonHttpResponseHandler() {
    		@Override
    		public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
    			System.out.println("onSuccess ");
    			test3();
    		
    		}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				System.out.println("onSuccess ");
				getDialog().dismiss();
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONArray errorResponse) {
				System.out.println("onSuccess ");
				getDialog().dismiss();
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse) {
				System.out.println("onSuccess ");
				getDialog().dismiss();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONArray response) {
				System.out.println("onSuccess ");
				getDialog().dismiss();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				System.out.println("onSuccess ");
				getDialog().dismiss();
			}
    	});

        } catch (Exception e) {
        	e.printStackTrace();
        }
		
	}
	
	protected void test1() {
    	AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Accept", "application/vnd.volunteerbeat-v1+json");
        client.addHeader("Content-Type", "application/json");     
    	client.get("http://api.volunteerbeat.com/tasks", new JsonHttpResponseHandler() {
    		@Override
    		public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
    			System.out.println("onSuccess ");
    			getDialog().dismiss();
    		
    		}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				System.out.println("onSuccess ");
				getDialog().dismiss();
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONArray errorResponse) {
				System.out.println("onSuccess ");
				getDialog().dismiss();
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse) {
				System.out.println("onSuccess ");
				getDialog().dismiss();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONArray response) {
				System.out.println("onSuccess ");
				getDialog().dismiss();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				System.out.println("onSuccess ");
				getDialog().dismiss();
			}
    	});

		
	}

	private void test3() {
		try{
			String	APPLY_TASK_URL = "http://api.volunteerbeat.com/tasks/<TASK_ID>/apply";
			String taskId="2";
			String message=null;
		String applyTaskUrl = APPLY_TASK_URL.replace("<TASK_ID>", taskId);
        JSONObject jsonParams = new JSONObject();
        jsonParams.put("task_id", taskId);
        if (message != null && !message.isEmpty()) {
	        jsonParams.put("text", "message");      
        }  
        StringEntity entity = new StringEntity(jsonParams.toString());
        client.post(getActivity(), applyTaskUrl, entity, "application/json",
        		new JsonHttpResponseHandler() {
    		@Override
    		public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
    			System.out.println("onSuccess ");
    			getDialog().dismiss();
    		}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				System.out.println("onSuccess ");

    			getDialog().dismiss();
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONArray errorResponse) {
				System.out.println("onSuccess ");

    			getDialog().dismiss();
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse) {
				System.out.println("onSuccess ");

    			getDialog().dismiss();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONArray response) {
				System.out.println("onSuccess ");

    			getDialog().dismiss();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				System.out.println("onSuccess ");

    			getDialog().dismiss();
			}
    		
        		}
        );
        
	} catch (Exception e) {
		e.printStackTrace();
	}

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
	
	
}
