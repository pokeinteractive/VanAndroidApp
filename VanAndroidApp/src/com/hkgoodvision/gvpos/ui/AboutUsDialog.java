package com.hkgoodvision.gvpos.ui;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.callvan.gvpos.activity.R;
import com.hkgoodvision.gvpos.app.AppContext;

public class AboutUsDialog extends SherlockActivity implements OnTouchListener {

	private ImageView mDialog;

	ViewGroup currentViewGroup = null;
	protected AppContext appContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aboutus_popup);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		getSupportActionBar().setTitle("有運行");

		findViewById(R.id.company_info_layout_id);
		
		appContext = (AppContext) this.getApplicationContext();

		WebView wv;
        

        wv = (WebView) findViewById(R.id.wv);
        wv.loadUrl("http://van.poke.com.hk/aboutus");
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