package com.hkgoodvision.gvpos.ui;

import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.callvan.gvpos.activity.R;
import com.hkgoodvision.gvpos.app.AppContext;
import com.hkgoodvision.gvpos.common.BitmapManager;
import com.hkgoodvision.gvpos.dao.vo.Service;

public class RewardApplySuccessDialog extends SherlockActivity implements OnTouchListener {

	private ImageView mDialog;

	View currentView = null;
	protected AppContext appContext;

	private Handler listViewServiceHandler;

	private ProgressBar compnay_info_progress;

	private Service serviceData = null;
	EditText emailText = null; 
	BitmapManager bmpManager = null;
	Handler handler = null;
	boolean isSending = false;
	int rewardId = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reward_apply_success_popup);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		appContext = (AppContext) this.getApplicationContext();
		
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
	

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
		return false;
	}



}