package com.codepath.app6.volunteerbeat.clients;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class VolunteerBeatClient {

	private static final String VB_BASE_URL = "http://api.volunteerbeat.com/";

	private static AsyncHttpClient client = new AsyncHttpClient();;

	public static void post(String url, RequestParams params,
			AsyncHttpResponseHandler responseHandler) {
		client.addHeader("Accept", "application/vnd.volunteerbeat-v1+json");
		client.addHeader("Content-Type", "application/json");
		client.post(getAbsoluteUrl(url), params, responseHandler);
	}

	private static String getAbsoluteUrl(String relativeUrl) {
		return VB_BASE_URL + relativeUrl;
	}

}
