package com.codepath.app6.volunteerbeat.clients;

import java.util.List;

import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

public class VolunteerBeatClient {

	private static final String VB_BASE_URL = "http://api.volunteerbeat.com/";

	private static AsyncHttpClient client = new AsyncHttpClient();;
	private static boolean cookieRestored = false;
	
	public static void post(Context context, String url, RequestParams params,
			AsyncHttpResponseHandler responseHandler) {
		if (!cookieRestored) {
			PersistentCookieStore cookieStore = new PersistentCookieStore(context);
			client.setCookieStore(cookieStore);
			cookieRestored = true;
		}
		client.addHeader("Accept", "application/vnd.volunteerbeat-v1+json");
		client.addHeader("Content-Type", "application/json");
		client.post(getAbsoluteUrl(url), params, responseHandler);
	}

	private static String getAbsoluteUrl(String relativeUrl) {
		return VB_BASE_URL + relativeUrl;
	}

	public static boolean isClientLoggedIn() {
		DefaultHttpClient httpClient = (DefaultHttpClient)(client.getHttpClient());		
		List<Cookie> cookies = httpClient.getCookieStore().getCookies();
		if (cookies == null || cookies.isEmpty()) {
			return false;
		}
		return true;
	}

	public static void persistSessionCookie() {

		DefaultHttpClient httpClient = (DefaultHttpClient)(client.getHttpClient());		
		List<Cookie> cookies = httpClient.getCookieStore().getCookies();
		
		List<Cookie> cookies2 = cookies;
	}

	public static void initSession(Context context) {
		PersistentCookieStore cookieStore = new PersistentCookieStore(context);
		client.setCookieStore(cookieStore);
		
	}
}
