/* 
 * Copyright (C) 2007 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.vanapp.service;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import com.callvan.gvpos.activity.AndroidViewPagerActivity;
import com.hkgoodvision.gvpos.common.LocationTrackUtils;
import com.vanapp.constant.URLConstant;
import com.vanapp.db.KeyPairDB;
import com.vanapp.util.ServerUtilities;

/**
 * This is an example of implementing an application service that runs locally
 * in the same process as the application. The {@link LocalServiceController}
 * and {@link LocalServiceBinding} classes show how to interact with the
 * service.
 * 
 * <p>
 * Notice the use of the {@link NotificationManager} when interesting things
 * happen in the service. This is generally how background services should
 * interact with the user, rather than doing something more disruptive such as
 * calling startActivity().
 */
public class IMService extends Service {
	// private NotificationManager mNM;

	private final int UPDATE_TIME_PERIOD = 1000*60*10;

	private final IBinder mBinder = new IMBinder();

	private String driverId;

	protected static IMService imService = null;

	private boolean isRunGPSSender = false;

	AlarmManager am;
	final static private long ONE_SECOND = 1000;
	final static private long TWENTY_SECONDS = ONE_SECOND * 5;
	PendingIntent pi;
	BroadcastReceiver br;

	private Context context = null;

	public static IMService getInstance() {

		return imService;
	}

	public class IMBinder extends Binder {
		public IMService getService() {
			return IMService.this;
		}

	}

	@Override
	public void onCreate() {

		Log.i("IMService onCreate", "...");

		imService = this;
		context = AndroidViewPagerActivity.context;

		if (readGPSUpdaterStatus()) {
			// send GPS to Server
			driverId = KeyPairDB.getDriverId(this);
			if (driverId != null && !"".equals(driverId)) {
				sendGPSLocaiton(driverId);
			}
		}

		AndroidViewPagerActivity.imService = this;

	}

	public void writeGPSUpdaterStatus(boolean status) {
		try {
			FileOutputStream fos = openFileOutput("GPSStatusFile", Context.MODE_PRIVATE);
			fos.write(status ? "Y".getBytes() : "N".getBytes());
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean readGPSUpdaterStatus() {
		String status = "N";

		try {
			InputStream instream = openFileInput("GPSStatusFile");

			// prepare the file for reading
			InputStreamReader inputreader = new InputStreamReader(instream);
			BufferedReader buffreader = new BufferedReader(inputreader);

			// read every line of the file into the line-variable, on line at
			// the time
			String line = "";
			while ((line = buffreader.readLine()) != null) {
				status = line;
			}

			// close the file again
			instream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if ("Y".equals(status))
			return true;
		else
			return false;

	}

	/*
	 * @Override public void onDestroy() { // Cancel the persistent
	 * notification. mNM.cancel(R.string.local_service_started);
	 * 
	 * // Tell the user we stopped. Toast.makeText(this,
	 * R.string.local_service_stopped, Toast.LENGTH_SHORT).show(); }
	 */

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	public boolean isRunningGPSSender() {
		return isRunGPSSender;
	}

	public void sendGPSLocaiton(String driverId) {
		Log.i("sendGPSLocaiton is being start", "...");

		this.driverId = driverId;
		context = AndroidViewPagerActivity.context;

		isRunGPSSender = true;

		addLocationListener();

	}

	public void stopGPSLocation() {
		Log.d("stopGPSLocation", "NOW STOP SEND GPS to SERVER");
		context = AndroidViewPagerActivity.context;

		isRunGPSSender = false;
		if (am != null)
			am.cancel(pi);
		if (br != null)
			unregisterReceiver(br);
		am = null;
	}

	public void exitAll() {
		Log.d("exitAll", "NOW STOP SEND GPS to SERVER ALL TOTALLY");
		isRunGPSSender = false;
		this.stopSelf();
		imService = null;
	}

	@Override
	public void onDestroy() {
		Log.i("IMService is being destroyed", "...");
		imService = null;
		if (am != null)
			am.cancel(pi);
		if (br != null)
			unregisterReceiver(br);
		super.onDestroy();
	}

	/*----------Listener class to get coordinates ------------- */
	private void addLocationListener() {

		if (am == null) {
			setup();
		}
		am.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + TWENTY_SECONDS,
				UPDATE_TIME_PERIOD, pi);

	}

	private void setup() {
		br = new BroadcastReceiver() {
			@Override
			public void onReceive(Context c, Intent i) {
				// Toast.makeText(c, "Rise and Shine!",
				// Toast.LENGTH_LONG).show();
				LocationTrackUtils a = new LocationTrackUtils(context);
				Location d = a.getLocation();
				if (d != null)
					updateLocation(d);
				// am.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
				// SystemClock.elapsedRealtime() + TWENTY_SECONDS, pi);
			}
		};
		registerReceiver(br, new IntentFilter("com.callvan.updateGPS"));
		pi = PendingIntent.getBroadcast(this, 0, new Intent("com.callvan.updateGPS"), 0);
		am = (AlarmManager) (this.getSystemService(Context.ALARM_SERVICE));
	}

	public void updateLocation(final Location loc) {

		new Thread() {
			public void run() {
				Log.d("Send GPS start", "step 1");
				if (isRunGPSSender) {

					if (loc != null) {
						String params = "id=" + URLEncoder.encode(driverId) + "&lat="
								+ URLEncoder.encode("" + loc.getLatitude()) + "&long="
								+ URLEncoder.encode("" + loc.getLongitude());

						ServerUtilities.sendHttpRequest(URLConstant.URL_UPDATE_GPS_LOCATION + params, "");

					}
				}
			}
		}.start();

	}

}