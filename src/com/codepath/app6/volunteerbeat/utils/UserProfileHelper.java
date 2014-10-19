package com.codepath.app6.volunteerbeat.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;

import com.codepath.app6.volunteerbeat.R;
import com.codepath.app6.volunteerbeat.models.UserProfile;

public class UserProfileHelper {
	public static void setProfileImage(ImageView ivPhoto, Context context) {
		UserProfile profile = UserProfile.getCurrentUser(context);
		
		String uriStr = profile.getPhotoUri();
		if (uriStr == null || uriStr.isEmpty()) {
			ivPhoto.setImageResource(R.drawable.default_photo);
		} else {
			try {
				Uri photoUri = Uri.parse(uriStr);
				Bitmap selectedImage = MediaStore.Images.Media.getBitmap(
						context.getContentResolver(), photoUri);
				// Load the selected image into a preview
				ivPhoto.setImageBitmap(selectedImage);
			} catch (Exception e) {
				ivPhoto.setImageResource(R.drawable.default_photo);
			}
		}

	}
}
