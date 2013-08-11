package com.callvan.gvpos.activity;

import java.util.UUID;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.hkgoodvision.gvpos.app.AppContext;
import com.hkgoodvision.gvpos.app.AppManager;
import com.hkgoodvision.gvpos.common.DeviceUuidFactory;
import com.hkgoodvision.gvpos.common.StringUtils;
import com.hkgoodvision.gvpos.page.MyFragmentPagerAdapter;
import com.vanapp.constant.URLConstant;
import com.vanapp.db.KeyPairDB;
import com.vanapp.service.IMService;
import com.vanapp.util.AlertDialogManager;
import com.vanapp.util.ServerUtilities;

public class AndroidViewPagerActivity extends SherlockFragmentActivity {
	ActionBar mActionBar;
	ViewPager mPager;
	int currentTab = 0;
	int currentPage = 0;
	AppContext appContext = null;
	public static IMService imService;
	public static Context context = null;

	private Handler showAccountBalanceHandler;
	
	TextView pointTextView = null;

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
		context = this;

		// 添加Activity到堆栈
		AppManager.getAppManager().addActivity(this);

		setContentView(R.layout.activity_main);
		
		if (IMService.getInstance() == null) {
			Log.i("IMService going to onCreate", "...");
			startService(new Intent(AndroidViewPagerActivity.this, IMService.class));
		}

		/** Getting a reference to action bar of this activity */
		mActionBar = getSupportActionBar();
		
		getSupportActionBar().setCustomView(R.layout.main_menu_custom);
		getSupportActionBar().setDisplayShowCustomEnabled(true);		
		
		showAccountBalanceHandler = getShowAccountBalanceHandler();

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

		View pointView = (View) getSupportActionBar().getCustomView();
		pointTextView = (TextView) pointView.findViewById(R.id.membership_point_total_text);
		
		appContext.setMatchPointTextView(pointTextView);
		
		String driverId = KeyPairDB.getDriverId(this);
		appContext.setDriverId(driverId);
		
		showAccountBanalce(driverId);
		
		
	}

	@Override
	public void onResume() {
		super.onResume();
		AppContext appContext = (AppContext) getApplication();
		if (appContext.getShowTabPage() >= 0) {

			mPager.setCurrentItem(getPageFromTab(appContext.getShowTabPage()));
			appContext.setShowTabPage(-1); // reset the show tab page
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
	
	private Handler getShowAccountBalanceHandler() {
		return new Handler() {
			public void handleMessage(Message msg) {
				if (msg.what >= 0) {
					
					String driverAcctBal = KeyPairDB.getDriverAccountBalance(context);
					pointTextView.setText(driverAcctBal);

				} else if (msg.what == -1) {

					AlertDialogManager.showAlertDialog(context, "Error in getting Account Balance", "Account Balance error!", false);
					
				}

			}
		};
	}
	
	private void showAccountBanalce(final String driverId) {
		new Thread() {
			public void run() {
				// check the driver account balance...
				String balance = ServerUtilities.sendHttpRequest(URLConstant.URL_GET_DRIVER_ACCOUNT_BALANCE + driverId, "");
				KeyPairDB.setDriverAccountBalance(balance, context);
				
				if ("".equals(balance)) {
					Message msg = new Message();
					msg.what = -1;
					showAccountBalanceHandler.sendMessage(msg);
				} else {
					Message msg = new Message();
					msg.what = 1;
					showAccountBalanceHandler.sendMessage(msg);
				}
			}
		}.start();
	}


	private ServiceConnection mConnection = new ServiceConnection() {
		public void onServiceConnected(ComponentName className, IBinder service) {
			// This is called when the connection with the service has been
			// established, giving us the service object we can use to
			// interact with the service. Because we have bound to a explicit
			// service that we know is running in our own process, we can
			// cast its IBinder to a concrete class and directly access it.
			imService = ((IMService.IMBinder) service).getService();

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