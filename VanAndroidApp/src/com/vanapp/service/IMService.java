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

import java.net.URLEncoder;
import java.util.Timer;
import java.util.TimerTask;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import com.callvan.gvpos.activity.AndroidViewPagerActivity;
import com.callvan.gvpos.activity.ServerUtilities;
import com.vanapp.constant.URLConstant;
import com.vanapp.db.KeyPairDB;

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

	private final int UPDATE_TIME_PERIOD = 5000;

	private final IBinder mBinder = new IMBinder();

	private String driverId;

	protected static IMService imService = null;

	// timer to take the updated data from server
	private Timer timer;

	private boolean isRunGPSSender = false;

	LocationManager locationManager = null;
	MyLocationListener myLocationListener = null;

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
		// Timer is used to take the friendList info every UPDATE_TIME_PERIOD;
		timer = new Timer();

		imService = this;

		if (KeyPairDB.getGPSUpdaterStatus(AndroidViewPagerActivity.context)) {
			// send GPS to Server
			driverId = KeyPairDB.getDriverId(this);
			if (driverId != null && !"".equals(driverId)) {
				sendGPSLocaiton(driverId);
			}
		}

		AndroidViewPagerActivity.imService = this;

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

		isRunGPSSender = true;

		addLocationListener();

	}

	public void exitAll() {
		Log.d("exitAll", "NOW STOP SEND GPS to SERVER ALL TOTALLY");
		isRunGPSSender = false;
		timer.cancel();
		this.stopSelf();
		imService = null;
	}

	@Override
	public void onDestroy() {
		Log.i("IMService is being destroyed", "...");
		imService = null;
		super.onDestroy();
	}

	public void stopGPSLocation() {
		Log.d("stopGPSLocation", "NOW STOP SEND GPS to SERVER");
		isRunGPSSender = false;
		timer.cancel();
		timer = new Timer();
		if (locationManager != null)
			locationManager.removeUpdates(myLocationListener);
	}

	/*----------Listener class to get coordinates ------------- */
	private void addLocationListener() {
		// timer.scheduleAtFixedRate(new TimerTask() {
		timer.schedule(new TimerTask() {
			public void run() {
				try {
					if (isRunGPSSender) {
						Looper.prepare();// Initialise the current thread as a
											// looper.
						Log.d("LOC_SERVICE", "Location sending start");

						locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

						Criteria c = new Criteria();
						c.setAccuracy(Criteria.ACCURACY_COARSE);

						final String PROVIDER = locationManager.getBestProvider(c, true);

						myLocationListener = new MyLocationListener();
						locationManager.requestLocationUpdates(PROVIDER, 600, 0, myLocationListener);
						Log.d("LOC_SERVICE", "Location sending end");
						Looper.loop();
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}
		}, UPDATE_TIME_PERIOD);

		// Thread triggerService = new Thread(new Runnable() {
		// public void run() {
		// try {
		// Looper.prepare();// Initialise the current thread as a
		// // looper.
		// LocationManager lm = (LocationManager)
		// getSystemService(Context.LOCATION_SERVICE);
		//
		// Criteria c = new Criteria();
		// c.setAccuracy(Criteria.ACCURACY_COARSE);
		//
		// final String PROVIDER = lm.getBestProvider(c, true);
		//
		// MyLocationListener myLocationListener = new MyLocationListener();
		// lm.requestLocationUpdates(PROVIDER, 600000, 0, myLocationListener);
		// Log.d("LOC_SERVICE", "Service RUNNING!");
		// Looper.loop();
		// } catch (Exception ex) {
		// ex.printStackTrace();
		// }
		// }
		// }, "LocationThread");
		// triggerService.start();
	}

	public void updateLocation(Location loc) {
		// Context appCtx = MyApplication.getAppContext();

		if (isRunGPSSender) {
			// latitude = location.getLatitude();
			// longitude = location.getLongitude();
			if (loc != null) {
				String params = "action=loc&id=" + URLEncoder.encode(driverId) + "&lat="
						+ URLEncoder.encode("" + loc.getLatitude()) + "&long="
						+ URLEncoder.encode("" + loc.getLongitude());

				ServerUtilities.sendHttpRequest(URLConstant.URL_BASE + params, "");

			}
		}

		// Intent filterRes = new Intent();
		// filterRes.setAction("xxx.yyy.intent.action.LOCATION");
		// filterRes.putExtra("latitude", latitude);
		// filterRes.putExtra("longitude", longitude);
		// appCtx.sendBroadcast(filterRes);
	}

	class MyLocationListener implements LocationListener {

		@Override
		public void onLocationChanged(Location location) {
			updateLocation(location);
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub

		}
	}

}