package com.codepath.app6.volunteerbeat.activities;

import java.util.ArrayList;

import com.codepath.app6.volunteerbeat.R;
import com.codepath.app6.volunteerbeat.R.layout;
import com.codepath.app6.volunteerbeat.utils.ExtendedViewPager;
import com.codepath.app6.volunteerbeat.utils.TouchImageView;
import com.squareup.picasso.Picasso;

import android.app.ActionBar;
import android.app.Activity;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

public class ImageViewPagerActivity extends Activity {
	ExtendedViewPager mViewPager;
	ActionBar mActionBar;
	
	/**
	 * Step 1: Download and set up v4 support library:
	 * http://developer.android.com/tools/support-library/setup.html Step 2:
	 * Create ExtendedViewPager wrapper which calls
	 * TouchImageView.canScrollHorizontallyFroyo Step 3: ExtendedViewPager is a
	 * custom view and must be referred to by its full package name in XML Step
	 * 4: Write TouchImageAdapter, located below Step 5. The ViewPager in the
	 * XML should be ExtendedViewPager
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mActionBar = getActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		
		 Intent i = getIntent();
		ArrayList<String> images = i.getStringArrayListExtra("picUris");
		int currentPos = i.getIntExtra("currentPos", 0);
		setContentView(R.layout.activity_image_view_pager);
		 mViewPager = (ExtendedViewPager) findViewById(R.id.view_pager);
		PagerAdapter adapter = new TouchImageAdapter(images, getActionBar());
		mViewPager.setAdapter(adapter);
		mViewPager.setCurrentItem(currentPos);


	}

    @Override
    public void onBackPressed() {
        if (mViewPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        // Respond to the action bar's Up/Home button
        case android.R.id.home:
            super.onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    
	private class TouchImageAdapter extends PagerAdapter {
		ArrayList<String> images;
		ActionBar mActionBar;
		
		public TouchImageAdapter(ArrayList<String> images, ActionBar mActionBar) {
			this.images = images;
			this.mActionBar = mActionBar;
		}
		@Override
		public int getCount() {
			return images.size();
		}

		@Override
		public View instantiateItem(ViewGroup container, int position) {
			TouchImageView img = new TouchImageView(container.getContext());
			img.setScaleType(ScaleType.CENTER);
			Log.e("instantiateItem", images.get(position));
			
			
			container.addView(img, LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.MATCH_PARENT);
			
			mActionBar.setSubtitle(" IMG " + String.valueOf(position) + " of " + String.valueOf(getCount()));
		    // Set the IMMERSIVE flag.
		    // Set the content to appear under the system bars so that the content
		    // doesn't resize when the system bars hide and show.
		    img.setSystemUiVisibility(
		            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
		            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
		            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
		            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
		            | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
		            | View.SYSTEM_UI_FLAG_IMMERSIVE);
		    
			Picasso.with(container.getContext()).load(images.get(position)).into(img);
			return img;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}
	}
}
