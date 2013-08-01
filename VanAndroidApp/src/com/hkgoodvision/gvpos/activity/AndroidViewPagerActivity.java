package com.hkgoodvision.gvpos.activity;

import java.util.UUID;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
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
import com.vanapp.service.IAppManager;
import com.vanapp.service.IMService;


public class AndroidViewPagerActivity extends SherlockFragmentActivity {
	ActionBar mActionBar;
	ViewPager mPager;
	int currentTab = 0;
	int currentPage = 0;
	AppContext appContext = null;
	public static IAppManager imService;

	
	TextView pointTextView = null;

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// 添加Activity到堆栈
		AppManager.getAppManager().addActivity(this);

		setContentView(R.layout.activity_main);
		
		startService(new Intent(AndroidViewPagerActivity.this, IMService.class));

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
		String driverId = KeyPairDB.getDriverId(this);
		appContext.setDriverId(driverId);
		// add keyUUID to context
		if (uuid != null && !StringUtils.isEmpty(uuid.toString())) {
			//registerUUID(uuid.toString(), handler);
			appContext.setUuid(uuid.toString());
			// register to server
//			registerVanDriver(uuid.toString());
			
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
	
	

	
	private ServiceConnection mConnection = new ServiceConnection() {
		public void onServiceConnected(ComponentName className, IBinder service) {
			// This is called when the connection with the service has been
			// established, giving us the service object we can use to
			// interact with the service. Because we have bound to a explicit
			// service that we know is running in our own process, we can
			// cast its IBinder to a concrete class and directly access it.
			imService = ((IMService.IMBinder) service).getService();

			if (imService != null && imService.isRunningGPSSender()) {
				appContext.setImService(imService);
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

}