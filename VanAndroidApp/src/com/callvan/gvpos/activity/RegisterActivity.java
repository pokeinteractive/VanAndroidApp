package com.callvan.gvpos.activity;

import static com.callvan.gvpos.activity.CommonUtilities.SENDER_ID;
import static com.callvan.gvpos.activity.CommonUtilities.SERVER_URL;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.actionbarsherlock.app.SherlockActivity;
import com.google.android.gcm.GCMRegistrar;

public class RegisterActivity extends SherlockActivity {
	// alert dialog manager
	AlertDialogManager alert = new AlertDialogManager();

	// Internet detector
	ConnectionDetector cd;

	// UI elements
	EditText txtName;
	EditText txtEmail;

	// Register button
	Button btnRegister;

	String regIdGCM = null;
	public static String name;
	public static String phone;

	// Asyntask
	AsyncTask<Void, Void, Void> mRegisterTask;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_register);

		cd = new ConnectionDetector(getApplicationContext());

		// Check if Internet present
		if (!cd.isConnectingToInternet()) {
			// Internet Connection is not present
			alert.showAlertDialog(RegisterActivity.this, "Internet Connection Error",
					"Please connect to working Internet connection", false);
			// stop executing code by return
			return;
		}

		// Check if GCM configuration is set
		if (SERVER_URL == null || SENDER_ID == null || SERVER_URL.length() == 0 || SENDER_ID.length() == 0) {
			// GCM sernder id / server url is missing
			alert.showAlertDialog(RegisterActivity.this, "Configuration Error!",
					"Please set your Server URL and GCM Sender ID", false);
			// stop executing code by return
			return;
		}

		// ===================================
		// Make sure the device has the proper dependencies.
		GCMRegistrar.checkDevice(this);

		// Make sure the manifest was properly set - comment out this line
		// while developing the app, then uncomment it when it's ready.
		GCMRegistrar.checkManifest(this);

		// check for register of Messinging cloud first...
		// Get GCM registration id
		final String regId = GCMRegistrar.getRegistrationId(this);

		// Check if regid already presents
		if (!regId.equals("") && GCMRegistrar.isRegisteredOnServer(this)) {

			// Device is already registered on GCM
			// Skips registration.
			/*
			 * Start and bind the imService
			 */
			// startService(new Intent(RegisterActivity.this, IMService.class));

			// Intent i = new Intent(getApplicationContext(),
			// MainActivity.class);
			//
			// // Registering user on our server
			// // Sending registraiton details to MainActivity
			// String driverPhone = KeyPairDB.getDriverPhone(this);
			// i.putExtra("driverPhone", driverPhone);
			//
			// startActivity(i);
			// finish();

			//GCMRegistrar.setRegisteredOnServer(this, false);

		} else {
			// ===================================================

			txtName = (EditText) findViewById(R.id.txtName);
			txtEmail = (EditText) findViewById(R.id.txtEmail);
			btnRegister = (Button) findViewById(R.id.btnRegister);

			/*
			 * Click event on Register button
			 */
			btnRegister.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// Read EditText dat
					name = txtName.getText().toString();
					phone = txtEmail.getText().toString();

					// Check if user filled the form
					if (name.trim().length() > 0 && phone.trim().length() > 0) {
						// Launch Main Activity
						registerVanDriver();

						// Intent i = new Intent(getApplicationContext(),
						// MainActivity.class);
						//
						// // Registering user on our server
						// // Sending registraiton details to MainActivity
						// i.putExtra("name", name);
						// i.putExtra("phone", phone);
						// startActivity(i);
						// finish();
					} else {
						// user doen't filled that data
						// ask him to fill the form
						alert.showAlertDialog(RegisterActivity.this, "Registration Error!",
								"Please enter your details", false);
					}
				}
			});

		}
	}

	private void registerVanDriver() {

		// Make sure the device has the proper dependencies.
		GCMRegistrar.checkDevice(this);

		// Make sure the manifest was properly set - comment out this line
		// while developing the app, then uncomment it when it's ready.
		GCMRegistrar.checkManifest(this);

		// Get GCM registration id
		regIdGCM = GCMRegistrar.getRegistrationId(this);



		// Device is already registered on GCM
		if (GCMRegistrar.isRegisteredOnServer(this)) {
			// Skips registration.
			// Toast.makeText(getApplicationContext(),
			// "Already registered with GCM", Toast.LENGTH_LONG).show();

			// ServerUtilities.unregister(this, regId);
			Intent intent = new Intent(RegisterActivity.this, AndroidViewPagerActivity.class);
			RegisterActivity.this.startActivity(intent);
			

		} else {
			
			// Check if regid already presents
			if (regIdGCM.equals("")) {
				// Registration is not present, register now with GCM
				GCMRegistrar.register(this, SENDER_ID);
			}
			
			// Try to register again, but not in the UI thread.
			// It's also necessary to cancel the thread onDestroy(),
			// hence the use of AsyncTask instead of a raw thread.
			final Context context = this;
			mRegisterTask = new AsyncTask<Void, Void, Void>() {

				@Override
				protected Void doInBackground(Void... params) {
					// Register on our server
					// On server creates a new user
					//ServerUtilities.register(context, name, phone, regIdGCM);

					// enquiry for driver_id by phone number

					Intent intent = new Intent(RegisterActivity.this, AndroidViewPagerActivity.class);
					RegisterActivity.this.startActivity(intent);
					
					finish(); // prevent show the register screen
					
					return null;
				}

				@Override
				protected void onPostExecute(Void result) {
					mRegisterTask = null;
					Intent intent = new Intent(context, AndroidViewPagerActivity.class);
					context.startActivity(intent);

				}

			};
			mRegisterTask.execute(null, null, null);
		}

	}

	
    @Override
   public void onBackPressed() {

        super.onBackPressed();
 
    }
}
