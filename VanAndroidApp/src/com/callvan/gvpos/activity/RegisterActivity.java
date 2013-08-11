package com.callvan.gvpos.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.actionbarsherlock.app.SherlockActivity;
import com.google.android.gcm.GCMRegistrar;
import com.vanapp.constant.URLConstant;
import com.vanapp.util.AlertDialogManager;
import com.vanapp.util.ConnectionDetector;

public class RegisterActivity extends SherlockActivity {

	// Internet detector
	ConnectionDetector cd;

	// UI elements
	EditText txtName;
	EditText txtPhone;

	// Register button
	Button btnRegister;

	
	public static String name;
	public static String phone;


	public static Context context;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_register);

		cd = new ConnectionDetector(getApplicationContext());

		context = this;

		// Check if Internet present
		if (!cd.isConnectingToInternet()) {
			// Internet Connection is not present
			AlertDialogManager.showAlertDialog(RegisterActivity.this, "Internet Connection Error",
					"Please connect to working Internet connection", false);
			// stop executing code by return
			return;
		}
		
		// ===================================================

		txtName = (EditText) findViewById(R.id.txtName);
		txtPhone = (EditText) findViewById(R.id.txtEmail);
		btnRegister = (Button) findViewById(R.id.btnRegister);

		/*
		 * Click event on Register button
		 */
		btnRegister.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// Read EditText dat
				name = txtName.getText().toString().trim();
				phone = txtPhone.getText().toString().trim();

				// Check if user filled the form
				if (name.trim().length() > 0 && phone.trim().length() > 0) {
					// Launch Main Activity
					registerVanDriver();

				} else {
					// user doen't filled that data
					// ask him to fill the form
					AlertDialogManager.showAlertDialog(RegisterActivity.this, "Registration Error!",
							"Please enter your details", false);
				}
			}
		});

	}

	private void registerVanDriver() {

		// Make sure the device has the proper dependencies.
		GCMRegistrar.checkDevice(this);
		// Make sure the manifest was properly set - comment out this line
		// while developing the app, then uncomment it when it's ready.
		GCMRegistrar.checkManifest(this);

		GCMRegistrar.register(this, URLConstant.GCM_SENDER_ID);

	}

	@Override
	public void onBackPressed() {

		super.onBackPressed();

	}
}
