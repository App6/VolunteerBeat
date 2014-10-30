package com.codepath.app6.volunteerbeat.activities;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ShareActionProvider;

import com.codepath.app6.volunteerbeat.R;
import com.codepath.app6.volunteerbeat.models.Task;

public class TaskDescriptionActivity extends FragmentActivity  {
	
	ArrayList<Task> tasks;
	   /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

	private ShareActionProvider miShareAction;

	public static FragmentManager fragmentManager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.activity_task_description);

		fragmentManager = getSupportFragmentManager();
		
		tasks = new ArrayList<Task>();
		tasks = getIntent().getParcelableArrayListExtra("tasks");
		int pos = getIntent().getIntExtra("position", 0);
        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new TaskDetailsScreenAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(pos);
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int pos) {
				// TODO Auto-generated method stub
				setupShareIntent(pos);
				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.task_description, menu);
		// Locate MenuItem with ShareActionProvider
		MenuItem item = menu.findItem(R.id.menu_item_share1);

		// Fetch reference to the share action provider
		miShareAction = (ShareActionProvider) item.getActionProvider();
		setupShareIntent(mPager.getCurrentItem());
		return true;
	}

	// Gets the image URI and setup the associated share intent to hook into the
	// provider
	public void setupShareIntent(int pos) {
		// Create share intent as described above
		Intent shareIntent = new Intent();
		shareIntent.setAction(Intent.ACTION_SEND);

		Task task = tasks.get(pos);
		Log.e("setupShareIntent", "Current task : " + task.getTaskName() + ", Page : " + mPager.getCurrentItem());

		shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Need Volunteer");
		shareIntent.putExtra(
				Intent.EXTRA_TEXT,
				task.getOrganization().getOrgName()
						+ "\n\nNeed Volunteer for task:-" + "\n\n"
						+ task.getTaskShortDesc() + "\n\n"
						+ "Due date for this task is: " + task.getDueDate());

		shareIntent.setType("text/plain");

		// Attach share event to the menu item provider
		if (miShareAction != null) {
			miShareAction.setShareIntent(shareIntent);
		}
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


    @Override
    public void onBackPressed() {
 //       if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
    		overridePendingTransition(R.anim.open_main, R.anim.close_next);
/*
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
*/
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class TaskDetailsScreenAdapter extends FragmentStatePagerAdapter {
        public TaskDetailsScreenAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return TaskDescriptionFragment.newInstance(tasks.get(position));
        }

        @Override
        public int getCount() {
            return tasks.size();
        }
    }

}
