package com.hkgoodvision.gvpos.activity;

import java.util.UUID;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.google.android.gcm.GCMRegistrar;
import com.hkgoodvision.gvpos.activity.R;
import com.hkgoodvision.gvpos.activity.R.id;
import com.hkgoodvision.gvpos.activity.R.layout;
import com.hkgoodvision.gvpos.activity.R.menu;
import com.hkgoodvision.gvpos.app.AppContext;
import com.hkgoodvision.gvpos.app.AppException;
import com.hkgoodvision.gvpos.app.AppManager;
import com.hkgoodvision.gvpos.common.DeviceUuidFactory;
import com.hkgoodvision.gvpos.common.StringUtils;
import com.hkgoodvision.gvpos.dao.vo.Result;
import com.hkgoodvision.gvpos.page.MyFragmentPagerAdapter;
import com.vanapp.db.KeyPairDB;


public class AndroidViewPagerActivity extends SherlockFragmentActivity {
	ActionBar mActionBar;
	ViewPager mPager;
	int currentTab = 0;
	int currentPage = 0;
	AppContext appContext = null;
	
	TextView pointTextView = null;

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// 添加Activity到堆栈
		AppManager.getAppManager().addActivity(this);

		setContentView(R.layout.activity_main);

		/** Getting a reference to action bar of this activity */
		mActionBar = getSupportActionBar();
		
		getSupportActionBar().setCustomView(R.layout.main_menu_custom);
		getSupportActionBar().setDisplayShowCustomEnabled(true);		
		
		

		/** Set tab navigation mode */
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		/** Getting a reference to ViewPager from the layout */
		mPager = (ViewPager) findViewById(R.id.pager);

		/** Getting a reference to FragmentManager */
		FragmentManager fm = getSupportFragmentManager();

		/** Defining a listener for pageChange */
		ViewPager.SimpleOnPageChangeListener pageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int page) {
				super.onPageSelected(page);
				currentPage = page;
				if (getTabFromPage(page) != currentTab)
					mActionBar.setSelectedNavigationItem(getTabFromPage(page));
			}

		};

		/** Setting the pageChange listner to the viewPager */
		mPager.setOnPageChangeListener(pageChangeListener);

		/** Creating an instance of FragmentPagerAdapter */
		MyFragmentPagerAdapter fragmentPagerAdapter = new MyFragmentPagerAdapter(fm);

		/** Setting the FragmentPagerAdapter object to the viewPager object */
		mPager.setAdapter(fragmentPagerAdapter);

		mActionBar.setDisplayShowTitleEnabled(true);

		/** Defining tab listener */
		ActionBar.TabListener tabListener = new ActionBar.TabListener() {

			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			}

			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {
				currentTab = tab.getPosition();
				if (getTabFromPage(currentPage) != currentTab)
					mPager.setCurrentItem(getPageFromTab(tab.getPosition()));

			}

			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {
			}
		};

		/** Creating Android Tab */
		Tab tab = mActionBar.newTab().setText("現有柯打").setTabListener(tabListener);

		mActionBar.addTab(tab);

		/** Creating Apple Tab */
		tab = mActionBar.newTab().setText("收柯打").setTabListener(tabListener);

		mActionBar.addTab(tab);

		tab = mActionBar.newTab().setText("已做柯打").setTabListener(tabListener);

		mActionBar.addTab(tab);

//		tab = mActionBar.newTab().setText("換獎品").setTabListener(tabListener);
//
//		mActionBar.addTab(tab);

		tab = mActionBar.newTab().setText("系統").setTabListener(tabListener);

		mActionBar.addTab(tab);

		// set appContext
		appContext = (AppContext) getApplication();
		String cookie = appContext.getProperty("cookie");
		if (StringUtils.isEmpty(cookie)) {
			String cookie_name = appContext.getProperty("cookie_name");
			String cookie_value = appContext.getProperty("cookie_value");
			if (!StringUtils.isEmpty(cookie_name) && !StringUtils.isEmpty(cookie_value)) {
				cookie = cookie_name + "=" + cookie_value;
				appContext.setProperty("cookie", cookie);
				appContext.removeProperty("cookie_domain", "cookie_name", "cookie_value", "cookie_version",
						"cookie_path");
			}
		}

		// register account
		DeviceUuidFactory uuidFactory = new DeviceUuidFactory(this);
		UUID uuid = uuidFactory.getDeviceUuid();

		View pointView = (View) getSupportActionBar().getCustomView();
		pointTextView = (TextView) pointView.findViewById(R.id.membership_point_total_text);
		
		appContext.setMatchPointTextView(pointTextView);
		
		Handler handler = this.getLvHandler();

		// add keyUUID to context
		if (uuid != null && !StringUtils.isEmpty(uuid.toString())) {
			registerUUID(uuid.toString(), handler);
			appContext.setUuid(uuid.toString());
			// register to server
			registerVanDriver(uuid.toString());
			
		} else {
			AppManager.getAppManager().finishAllActivity();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.activity_main, menu);
		return false;
	}

	public int getTabFromPage(int page) {
		if (page == 0)
			return 0;
		else if (page == 1)
			return 1;
		else if (page == 2)
			return 2;
		else if (page == 3)
			return 3;
		else if (page == 4)
			return 4;

		return 0;

	}

	public int getPageFromTab(int tab) {
		if (tab == 0)
			return 0;
		else if (tab == 1)
			return 1;
		else if (tab == 2)
			return 2;
		else if (tab == 3)
			return 3;
		else if (tab == 4)
			return 4;

		return 0;

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		// 结束Activity&从堆栈中移除
		AppManager.getAppManager().finishActivity(this);
	}

	private Handler getLvHandler() {
		return new Handler() {
			public void handleMessage(Message msg) {
				if (msg.what >= 0) {
					Result result = (Result)msg.obj;
					pointTextView.setText(result.getParam1());
				} else if (msg.what == -1) {

					((AppException) msg.obj).makeToast(mPager.getContext());
					AppManager.getAppManager().AppExit(appContext);
				}

			}
		};
	}

	private void registerUUID(final String uuid, final Handler handler) {
		// mHeadProgress.setVisibility(ProgressBar.VISIBLE);
		new Thread() {
			public void run() {
				Message msg = new Message();

				try {
					// NewsList list = appContext.getNewsList(catalog,
					// pageIndex, isRefresh);
					Result res = appContext.registerService(uuid);
					msg.what = 1;
					msg.obj = res;
				} catch (AppException e) {
					e.printStackTrace();
					msg.what = -1;
					msg.obj = e;
				}
				// if (curNewsCatalog == catalog)
				handler.sendMessage(msg);
			}
		}.start();
	}
	
	
	String regIdGCM = null;

	Context ctx = null;

	// Asyntask
	AsyncTask<Void, Void, Void> mRegisterTask;
	public static final String SENDER_ID = "569974433968"; 
	
	private void registerVanDriver(final String uuid) {

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
				//Toast.makeText(getApplicationContext(), "Already registered with GCM", Toast.LENGTH_LONG).show();

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
						ServerUtilities.register(context, "New Comer", uuid, regIdGCM);

						// enquiry for driver_id by phone number

						KeyPairDB.setDriverPhone(uuid, context);

						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						mRegisterTask = null;
					}

				};
				mRegisterTask.execute(null, null, null);
			}
		}

	}

}