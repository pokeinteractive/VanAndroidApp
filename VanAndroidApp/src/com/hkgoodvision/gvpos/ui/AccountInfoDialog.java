package com.hkgoodvision.gvpos.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.callvan.gvpos.activity.R;
import com.hkgoodvision.gvpos.app.AppContext;
import com.hkgoodvision.gvpos.app.AppException;
import com.hkgoodvision.gvpos.common.BitmapManager;
import com.hkgoodvision.gvpos.common.UIHelper;
import com.hkgoodvision.gvpos.dao.vo.Service;
import com.vanapp.constant.URLConstant;
import com.vanapp.db.KeyPairDB;
import com.vanapp.util.AlertDialogManager;
import com.vanapp.util.ServerUtilities;

public class AccountInfoDialog extends SherlockActivity implements OnTouchListener {

	protected Context context = null;
	protected AppContext appContext;
	private Handler showPhoneHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account_popup);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		appContext = (AppContext) this.getApplicationContext();
		context = this;

		TextView accountIdText = (TextView) findViewById(R.id.account_id_text);
		TextView accountPhoneText = (TextView) findViewById(R.id.account_phone_text);

		accountIdText.setText(KeyPairDB.getDriverId(this));
		accountPhoneText.setText(KeyPairDB.getDriverPhone(this));
		

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// app icon in action bar clicked; go home
			finish();
		}
		return true;
	}

	

	/**
	 * 初始化新闻列表
	 */
	private void initNewsListView() {

	}

	
	private Handler getShowPhoneHandler() {
		return new Handler() {
			public void handleMessage(Message msg) {
				if (msg.what >= 0) {
					
//					phone.setVisibility(View.VISIBLE);
//					acceptButton.setVisibility(View.INVISIBLE);

				} else if (msg.what == -1) {

					AlertDialogManager.showAlertDialog(context, "Error in accept Order", "Cannot accpet order", false);
					
				}

			}
		};
	}

	
	
	private void confirmOrder(final String orderId, final String driverId) {
		new Thread() {
			public void run() {
				String result = ServerUtilities.sendHttpRequest(URLConstant.URL_MATCH_ORDER + orderId+"/"+driverId, "");
				if (!"ok".equals(result)) {
					Message msg = new Message();
					msg.what = -1;
					showPhoneHandler.sendMessage(msg);
				} else {
					Message msg = new Message();
					msg.what = 1;
					showPhoneHandler.sendMessage(msg);
				}
			}
		}.start();
	}

	
	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
		return false;
	}

}