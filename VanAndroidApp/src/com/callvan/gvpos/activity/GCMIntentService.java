package com.callvan.gvpos.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;
import com.hkgoodvision.gvpos.app.AppContext;
import com.vanapp.bean.ClientOrder;
import com.vanapp.constant.URLConstant;
import com.vanapp.db.KeyPairDB;
import com.vanapp.parser.OrderMessageParser;
import com.vanapp.util.AlertDialogManager;
import com.vanapp.util.ServerUtilities;

public class GCMIntentService extends GCMBaseIntentService {

	private static final String TAG = "GCMIntentService";

	public GCMIntentService() {
		super(URLConstant.GCM_SENDER_ID);
	}

	/**
	 * Method called on device registered
	 **/
	@Override
	protected void onRegistered(Context context, String registrationId) {
		Log.i(TAG, "Device registered: regId = " + registrationId);

		ServerUtilities.register(context, RegisterActivity.name, RegisterActivity.phone, registrationId);

		String driverId = KeyPairDB.getDriverId(context);

		if (driverId != null && driverId.length() < 6) {
			Intent intent = new Intent(RegisterActivity.context, AndroidViewPagerActivity.class);
			RegisterActivity.context.startActivity(intent);
			((RegisterActivity) (RegisterActivity.context)).finish();
			
		} else {
			AlertDialogManager.showAlertDialog(context, "Error Register", "Cannot Register", false);
		}

	}

	/**
	 * Method called on device un registred
	 * */
	@Override
	protected void onUnregistered(Context context, String registrationId) {
		Log.i(TAG, "Device unregistered");
		ServerUtilities.unregister(context, registrationId);
	}

	/**
	 * Method called on Receiving a new message
	 * */
	@Override
	protected void onMessage(Context context, Intent intent) {
		Log.i(TAG, "Received message");
		String message = intent.getExtras().getString("price");
		// notifies user
		generateNotification(context, message);
	}

	/**
	 * Method called on receiving a deleted message
	 * */
	@Override
	protected void onDeletedMessages(Context context, int total) {
		Log.i(TAG, "Received deleted messages notification");
		String message = getString(R.string.gcm_deleted, total);
		// notifies user
		generateNotification(context, message);
	}

	/**
	 * Method called on Error
	 * */
	@Override
	public void onError(Context context, String errorId) {
		Log.i(TAG, "Received error: " + errorId);
	}

	@Override
	protected boolean onRecoverableError(Context context, String errorId) {
		// log message
		Log.i(TAG, "Received recoverable error: " + errorId);
		return super.onRecoverableError(context, errorId);
	}

	/**
	 * Issues a notification to inform the user that server has sent a message.
	 */
	private static void generateNotification(Context context, String message) {
		int icon = R.drawable.ic_launcher;
		long when = System.currentTimeMillis();

		// parse the Message to Message Object
		ClientOrder order = OrderMessageParser.parseMessage(message);

		if (order == null)
			return;

		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = new Notification(icon, order.getMessage(), when);

		String title = context.getString(R.string.app_name);

		Intent notificationIntent = new Intent(context, AndroidViewPagerActivity.class);
		// set intent so it does not start a new activity
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent intent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
		notification.setLatestEventInfo(context, title, order.getMessage(), intent);
		notification.flags |= Notification.FLAG_AUTO_CANCEL;

		// Play default notification sound
		notification.defaults |= Notification.DEFAULT_SOUND;

		// notification.sound = Uri.parse("android.resource://" +
		// context.getPackageName() + "your_sound_file_name.mp3");

		// Vibrate if vibrate is enabled
		notification.defaults |= Notification.DEFAULT_VIBRATE;
		notificationManager.notify(0, notification);

		AppContext appContext = (AppContext) context.getApplicationContext();
		appContext.setShowTabPage(0);

	}

}
