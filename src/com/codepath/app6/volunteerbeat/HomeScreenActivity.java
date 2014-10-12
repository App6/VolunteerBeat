package com.codepath.app6.volunteerbeat;

<<<<<<< HEAD
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
=======
import com.codepath.app6.volunteerbeat.activities.ProfileActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
>>>>>>> dcdb7e9e4d163f100e9565a82e1dfb3ca48e5403

import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

<<<<<<< HEAD
public class HomeScreenActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_screen);

		final ActionBar actionBar = getSupportActionBar();
		actionBar.setCustomView(R.layout.action_profile_view);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		/*Replace this with the custom Image location on disk*/
//		String url = "http://2.gravatar.com/avatar/858dfac47ab8176458c005414d3f0c36?s=256&d=&r=G";
		String url = "http://a5.files.biography.com/image/upload/c_fill,dpr_1.0,g_face,h_300,q_80,w_300/MTE4MDAzNDEwNzg5ODI4MTEw.jpg";
		CircularImageView ivactionbarLogo = (CircularImageView) actionBar.getCustomView().findViewById(R.id.actionBarLogo);
/*		ivactionbarLogo.setBorderColor(getResources().getColor(R.color.vb_red));
		ivactionbarLogo.setBorderWidth(10);
		ivactionbarLogo.addShadow();
		*/
		Picasso.with(this)
				.load(url)
//				.resize(30, 30)
				.into(ivactionbarLogo);
	
		ivactionbarLogo.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(HomeScreenActivity.this, "Profile Click", Toast.LENGTH_SHORT).show();
			}
		});
	}
}
=======
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
    }
    
    public void viewProfile(View v) {
    	Intent i = new Intent(this, ProfileActivity.class);
    	startActivity(i);
    }
}
>>>>>>> dcdb7e9e4d163f100e9565a82e1dfb3ca48e5403
