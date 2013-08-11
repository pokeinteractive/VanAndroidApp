package com.callvan.gvpos.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.vanapp.constant.URLConstant;
import com.vanapp.db.KeyPairDB;
import com.vanapp.util.ServerUtilities;
 
/**
 * Splash screen activity
 *
 * @author Catalin Prata
 */
public class LaunchScreenActivity extends Activity {
 
    // used to know if the back button was pressed in the splash screen activity and avoid opening the next activity
    private boolean mIsBackButtonPressed;
    private static final int SPLASH_DURATION = 2000; // 2 seconds
    String driverId = "";
 
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        setContentView(R.layout.launch_screen);
 
        Handler handler = new Handler();
 
        // run a thread after 2 seconds to start the home screen
        handler.postDelayed(new Runnable() {
 
            @Override
            public void run() {
 
                // make sure we close the splash screen so the user won't come back when it presses back key
 
                finish();
                 
                if (!mIsBackButtonPressed) {
                	// check if the phone is register before
                	if (checkIsRegister()) {
                		
	                	// start the home screen if the back button wasn't pressed already 
	                    Intent intent = new Intent(LaunchScreenActivity.this, AndroidViewPagerActivity.class);
	                    LaunchScreenActivity.this.startActivity(intent);
	                    finish();
                	} else {
                		// show the Register screen
                		
	                    Intent intent = new Intent(LaunchScreenActivity.this, RegisterActivity.class);
	                    LaunchScreenActivity.this.startActivity(intent);
	                    finish();
                	}
                	
               }
                 
            }
 
        }, SPLASH_DURATION); // time in milliseconds (1 second = 1000 milliseconds) until the run() method will be called
 
    }
 
    @Override
   public void onBackPressed() {
 
        // set the flag to true so the next activity won't start up
        mIsBackButtonPressed = true;
        super.onBackPressed();
 
    }
    
    private boolean checkIsRegister() {
    	driverId = KeyPairDB.getDriverId(this);
    	if (driverId != null) {
    		return true;
    	}
    	return false;
    }
}