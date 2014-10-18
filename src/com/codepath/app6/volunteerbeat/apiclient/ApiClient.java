package com.codepath.app6.volunteerbeat.apiclient;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;


public class ApiClient {
	private static AsyncHttpClient client = null;
	public static String	CREATE_SESSION_URL = "http://api.volunteerbeat.com/session";
	public static String	APPLY_TASK_URL = "http://api.volunteerbeat.com/tasks/<TASK_ID>/apply";

	private boolean initDone = false;
	private boolean postDone = false;
	public void createSession(Context context) {
		try {
			client = new AsyncHttpClient();
	        JSONObject jsonParams = new JSONObject();
	        //"email":"vbtest123@gmail.com",
	        //"password":"volunteerbeat",
	        jsonParams.put("email", "vbtest123@gmail.com");
	        jsonParams.put("password", "volunteerbeat");        
	        StringEntity entity = new StringEntity(jsonParams.toString());
	        
	        initDone = false;
	        client.addHeader("Accept", "application/vnd.volunteerbeat-v1+json");
	        client.addHeader("Content-Type", "application/json");     
	        client.post(context, CREATE_SESSION_URL, entity, "application/json",
	        		new JsonHttpResponseHandler() {
	            		@Override
	            		public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
	            			System.out.println("onSuccess ");
	            			initDone = true;
	            		}

						@Override
						public void onFailure(int statusCode, Header[] headers,
								String responseString, Throwable throwable) {
							System.out.println("onSuccess ");
							initDone = true;
						}

						@Override
						public void onFailure(int statusCode, Header[] headers,
								Throwable throwable, JSONArray errorResponse) {
							System.out.println("onSuccess ");
							initDone = true;
						}

						@Override
						public void onFailure(int statusCode, Header[] headers,
								Throwable throwable, JSONObject errorResponse) {
							System.out.println("onSuccess ");
							initDone = true;
						}

						@Override
						public void onSuccess(int statusCode, Header[] headers,
								JSONArray response) {
							System.out.println("onSuccess ");
							initDone = true;
						}

						@Override
						public void onSuccess(int statusCode, Header[] headers,
								String responseString) {
							System.out.println("onSuccess ");
							initDone = true;
						}
	            		
	            		

	        		}
	        );
	        
	        while (!initDone) {
	        	Thread.sleep(3000);
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void ensureSession(Context context) {
		if (client == null) {
			createSession(context);
		}
	}
	
	public void applyToTask(Context context, int taskId, String message) {
		try {
			ensureSession(context);
			
			String applyTaskUrl = APPLY_TASK_URL.replace("<TASK_ID>", Integer.toString(taskId));
	        JSONObject jsonParams = new JSONObject();
	        jsonParams.put("task_id", taskId);
	        if (message != null && !message.isEmpty()) {
		        jsonParams.put("text", "message");      
	        }  
	        StringEntity entity = new StringEntity(jsonParams.toString());
	        postDone = false;
	        client.post(context, applyTaskUrl, entity, "application/json",
	        		new JsonHttpResponseHandler() {
        		@Override
        		public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        			System.out.println("onSuccess ");
        			postDone = true;
        		}

				@Override
				public void onFailure(int statusCode, Header[] headers,
						String responseString, Throwable throwable) {
					System.out.println("onSuccess ");

        			postDone = true;
				}

				@Override
				public void onFailure(int statusCode, Header[] headers,
						Throwable throwable, JSONArray errorResponse) {
					System.out.println("onSuccess ");

        			postDone = true;
				}

				@Override
				public void onFailure(int statusCode, Header[] headers,
						Throwable throwable, JSONObject errorResponse) {
					System.out.println("onSuccess ");

        			postDone = true;
				}

				@Override
				public void onSuccess(int statusCode, Header[] headers,
						JSONArray response) {
					System.out.println("onSuccess ");

        			postDone = true;
				}

				@Override
				public void onSuccess(int statusCode, Header[] headers,
						String responseString) {
					System.out.println("onSuccess ");

        			postDone = true;
				}
        		
	        		}
	        );
	        
	        while (!postDone) {
	        	Thread.sleep(3000);
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
