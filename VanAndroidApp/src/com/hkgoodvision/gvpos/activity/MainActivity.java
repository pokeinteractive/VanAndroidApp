package com.hkgoodvision.gvpos.activity;

import static com.hkgoodvision.gvpos.activity.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static com.hkgoodvision.gvpos.activity.CommonUtilities.EXTRA_MESSAGE;
import static com.hkgoodvision.gvpos.activity.CommonUtilities.SENDER_ID;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;
import com.hkgoodvision.gvpos.activity.R;
import com.vanapp.bean.ClientOrder;
import com.vanapp.constant.URLConstant;
import com.vanapp.db.KeyPairDB;
import com.vanapp.parser.OrderMessageParser;
import com.vanapp.service.IAppManager;
import com.vanapp.service.IMService;

public class MainActivity extends Activity {

	private static final int REMOVE_REG_GCM_ID = Menu.FIRST;
	private static final int EXIT_APP_ID = Menu.FIRST + 1;

	private IAppManager imService;

	String regIdGCM = null;

	Context ctx = null;
	Button btnStartRevJob;

	
	// Asyntask
	AsyncTask<Void, Void, Void> mRegisterTask;

	// Alert dialog manager
	AlertDialogManager alert = new AlertDialogManager();

	// Connection detector
	ConnectionDetector cd;

	static String name;
	static String phone;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ctx = this;
		setContentView(R.layout.activity_main);

		cd = new ConnectionDetector(getApplicationContext());

		// Check if Internet present
		if (!cd.isConnectingToInternet()) {
			// Internet Connection is not present
			alert.showAlertDialog(MainActivity.this, "Internet Connection Error",
					"Please connect to working Internet connection", false);
			// stop executing code by return
			return;
		}

		// Getting name, email from intent
		Intent i = getIntent();

		String driverPhone = i.getStringExtra("driverPhone");
		name = i.getStringExtra("name");
		phone = i.getStringExtra("phone");

		registerReceiver(mHandleMessageReceiver, new IntentFilter(DISPLAY_MESSAGE_ACTION));
		
		if (driverPhone == null) {
			// the driver is not auto login
			registerVanDriver();
		}

		//btnStartRevJob = (Button) findViewById(R.id.btnStartGPS);

		/*
		 * Click event on Register button
		 */
		btnStartRevJob.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if ("Start Rev Job".equals(btnStartRevJob.getText())) {
					// send GPS to Server
					String driverPhone = KeyPairDB.getDriverPhone(ctx);
					if (driverPhone != null && !"".equals(driverPhone)) {
						imService.sendGPSLocaiton(driverPhone);
						btnStartRevJob.setText("Stop Rev Job");
					}

				} else {
					imService.stopGPSLocation();
					btnStartRevJob.setText("Start Rev Job");
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

		// Get GCM registration id
		regIdGCM = GCMRegistrar.getRegistrationId(this);

		// Check if regid already presents
		if (regIdGCM.equals("")) {
			// Registration is not present, register now with GCM
			GCMRegistrar.register(this, SENDER_ID);
		} else {
			// Device is already registered on GCM
			if (GCMRegistrar.isRegisteredOnServer(this)) {
				// Skips registration.
				Toast.makeText(getApplicationContext(), "Already registered with GCM", Toast.LENGTH_LONG).show();

				// ServerUtilities.unregister(this, regId);

			} else {
				// Try to register again, but not in the UI thread.
				// It's also necessary to cancel the thread onDestroy(),
				// hence the use of AsyncTask instead of a raw thread.
				final Context context = this;
				mRegisterTask = new AsyncTask<Void, Void, Void>() {

					@Override
					protected Void doInBackground(Void... params) {
						// Register on our server
						// On server creates a new user
						ServerUtilities.register(context, name, phone, regIdGCM);

						// enquiry for driver_id by phone number
		                String driverId = ServerUtilities.sendHttpRequest(URLConstant.URL_GET_DRIVER_ID_BY_PHONE + params, "");
		                KeyPairDB.setDriverId(driverId, context);
		                
						KeyPairDB.setDriverPhone(phone, context);

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

	}

	/**
	 * Receiving push messages
	 * */
	private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
			// Waking up mobile if it is sleeping
			WakeLocker.acquire(getApplicationContext());

			/**
			 * Take appropriate action on this message depending upon your app
			 * requirement For now i am just displaying it on the screen
			 * */
			// parse the Message to Message Object
			ClientOrder order = OrderMessageParser.parseMessage(newMessage);
			showOrderDialog(order);

			// Showing received message
			// lblMessage.append(newMessage + "\n");
			// Toast.makeText(getApplicationContext(), "New Message: " +
			// newMessage, Toast.LENGTH_LONG).show();

			// Releasing wake lock
			WakeLocker.release();
		}
	};

	@Override
	protected void onDestroy() {
		if (mRegisterTask != null) {
			mRegisterTask.cancel(true);
		}
		try {
			unregisterReceiver(mHandleMessageReceiver);
			GCMRegistrar.onDestroy(this);
		} catch (Exception e) {
			Log.e("UnRegister Receiver Error", "> " + e.getMessage());
			e.printStackTrace();
		}
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		unbindService(mConnection);
		super.onPause();
	}

	@Override
	protected void onResume() {
		bindService(new Intent(MainActivity.this, IMService.class), mConnection, Context.BIND_AUTO_CREATE);

		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		boolean result = super.onCreateOptionsMenu(menu);

		menu.add(0, REMOVE_REG_GCM_ID, 0, R.string.options_unregister);

		menu.add(0, EXIT_APP_ID, 0, R.string.options_exit);

		return result;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {

		switch (item.getItemId()) {
		case REMOVE_REG_GCM_ID: {
			ServerUtilities.unregister(this, regIdGCM);
			return true;
		}
		case EXIT_APP_ID: {
			imService.exitAll();
			finish();
			return true;
		}
		}

		return super.onMenuItemSelected(featureId, item);
	}

	private ServiceConnection mConnection = new ServiceConnection() {
		public void onServiceConnected(ComponentName className, IBinder service) {
			// This is called when the connection with the service has been
			// established, giving us the service object we can use to
			// interact with the service. Because we have bound to a explicit
			// service that we know is running in our own process, we can
			// cast its IBinder to a concrete class and directly access it.
			imService = ((IMService.IMBinder) service).getService();

			if (imService != null && imService.isRunningGPSSender()) {

				btnStartRevJob.setText("Stop Rev Job");
			}

		}

		public void onServiceDisconnected(ComponentName className) {
			// This is called when the connection with the service has been
			// unexpectedly disconnected -- that is, its process crashed.
			// Because it is running in our same process, we should never
			// see this happen.
			imService = null;

		}
	};

	public void showOrderDialog() {
		ClientOrder order = KeyPairDB.getOrder(this);

		showOrderDialog(order);
	}

	public void showOrderDialog(final ClientOrder order) {

		if (order != null) {
			String orderString = order.getMessage();
			DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					switch (which) {
					case DialogInterface.BUTTON_POSITIVE:
						// Yes button clicked
						// send accept client order
						((AlertDialog) dialog).setMessage("Call to " + order.getPhone());

						// dialog.dismiss();
						break;

					case DialogInterface.BUTTON_NEGATIVE:
						// No button clicked

						dialog.dismiss();
						break;
					}
				}
			};

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(orderString).setPositiveButton("Accept", dialogClickListener)
					.setNegativeButton("Reject", dialogClickListener).show();

			KeyPairDB.setOrderString(null, this);

		}
	}

}
