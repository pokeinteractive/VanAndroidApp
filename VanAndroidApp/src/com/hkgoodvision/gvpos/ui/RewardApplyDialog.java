package com.hkgoodvision.gvpos.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.callvan.gvpos.activity.R;
import com.hkgoodvision.gvpos.app.AppContext;
import com.hkgoodvision.gvpos.app.AppException;
import com.hkgoodvision.gvpos.common.BitmapManager;
import com.hkgoodvision.gvpos.common.UIHelper;
import com.hkgoodvision.gvpos.dao.vo.Result;
import com.hkgoodvision.gvpos.dao.vo.Service;

public class RewardApplyDialog extends SherlockActivity implements OnTouchListener {

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
	String title = "";
	int point = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reward_apply_popup);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		
		currentView = findViewById(R.id.reward_apply_layout_id);
		
		//getSupportActionBar().setTitle("MatchPoint");

		// Make us non-modal, so that others can receive touch events.
		// getWindow().setFlags(LayoutParams.FLAG_NOT_TOUCH_MODAL,
		// LayoutParams.FLAG_NOT_TOUCH_MODAL);
		// getWindow().setFlags(LayoutParams.FLAG_,
		// LayoutParams.FLAG_NOT_TOUCH_MODAL);
		//
		// // ...but notify us that it happened.
		// getWindow().setFlags(LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
		// LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);

		appContext = (AppContext) this.getApplicationContext();
		
		compnay_info_progress = (ProgressBar) findViewById(R.id.reward_apply_progress);
		compnay_info_progress.setVisibility(ProgressBar.GONE);
		
		rewardId = getIntent().getIntExtra("rewardId", 0);
		title = getIntent().getStringExtra("title");
		point = getIntent().getIntExtra("point", 10000);
		
		TextView titleTextView = (TextView) findViewById(R.id.reward_apply_title_text);
		titleTextView.setText("申請以" +point+ "點MatchPoint換"+title);
		
		
		emailText = (EditText) findViewById(R.id.reward_apply_email);
		
		handler = this.getLvHandler();
		
		Button reward_apply_button = (Button) findViewById(R.id.reward_apply_button);
		reward_apply_button.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		    	
		    	if (!isSending) {
			    	isSending = true;
			    	compnay_info_progress.setVisibility(ProgressBar.VISIBLE);
			    	rewardApply(appContext.getUuid(),emailText.getText().toString(), rewardId,handler);
		    	}
		    }
		});
		
		
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




	private Handler getLvHandler() {
		return new Handler() {
			public void handleMessage(Message msg) {
				
				compnay_info_progress.setVisibility(ProgressBar.GONE);
				
				if (msg.what >= 0) {
					Result result = (Result)msg.obj;
					
					if ("OK".equals(result.getReturnCode())) {
						// change the title bar point
						appContext.getMatchPointTextView().setText(result.getParam1());
						//Toast.makeText(currentView.getContext(), R.string.reward_apply_success, Toast.LENGTH_LONG).show();
						UIHelper.showRewardApplySuccess(currentView.getContext());
					} else {
						Toast.makeText(currentView.getContext(), R.string.reward_apply_fail, Toast.LENGTH_LONG).show();
					}
					
					finish();
					
				} else if (msg.what == -1) {

					Toast.makeText(currentView.getContext(), R.string.reward_apply_fail, Toast.LENGTH_LONG).show();
				}
				
				isSending = false;

			}
		};
	}

	private void rewardApply(final String uuid, final String email, final int rewardId, final Handler handler) {
		// mHeadProgress.setVisibility(ProgressBar.VISIBLE);
		new Thread() {
			public void run() {
				Message msg = new Message();

				try {

					Result res = appContext.rewardApply(uuid, email, rewardId);
					msg.what = 1;
					msg.obj = res;
				} catch (AppException e) {
					e.printStackTrace();
					msg.what = -1;
					msg.obj = e;
				}

				handler.sendMessage(msg);
			}
		}.start();
	}
	

}