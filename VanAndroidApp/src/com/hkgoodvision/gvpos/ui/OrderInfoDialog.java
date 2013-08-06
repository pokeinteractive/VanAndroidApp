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
import com.callvan.gvpos.activity.AlertDialogManager;
import com.callvan.gvpos.activity.R;
import com.callvan.gvpos.activity.ServerUtilities;
import com.hkgoodvision.gvpos.app.AppContext;
import com.hkgoodvision.gvpos.app.AppException;
import com.hkgoodvision.gvpos.common.BitmapManager;
import com.hkgoodvision.gvpos.common.UIHelper;
import com.hkgoodvision.gvpos.dao.vo.Service;
import com.vanapp.constant.URLConstant;

public class OrderInfoDialog extends SherlockActivity implements OnTouchListener {

	private ImageView mDialog;
	TextView phone;
	Service service = null;
	
	protected Context context = null;
	protected AppContext appContext;

	private Handler listViewServiceHandler;
	
	private Handler showPhoneHandler;

	private ProgressBar order_info_progress;

	private Service serviceData = null;

	BitmapManager bmpManager = null;

	String orderId = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_popup);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		appContext = (AppContext) this.getApplicationContext();
		context = this;
		orderId = getIntent().getStringExtra("orderId");
		
		order_info_progress = (ProgressBar) findViewById(R.id.order_info_progress);

		initFrameListView();


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
	 * 初始化所有ListView
	 */
	private void initFrameListView() {
		// 初始化listview控件
		this.initNewsListView();
		// 加载listview数据
		this.initFrameListViewData();
	}

	/**
	 * 初始化所有ListView数据
	 */
	private void initFrameListViewData() {
		// 初始化Handler
		listViewServiceHandler = this.getLvHandler();
		
		showPhoneHandler = getShowPhoneHandler();

		// 加载资讯数据
		if (serviceData == null) {
			loadLvNewsData(orderId, 0, listViewServiceHandler, UIHelper.LISTVIEW_ACTION_INIT);
		}
	}

	/**
	 * 初始化新闻列表
	 */
	private void initNewsListView() {

	}

	private Handler getLvHandler() {
		return new Handler() {
			public void handleMessage(Message msg) {
				if (msg.what >= 0) {
					// listview数据处理
					handleLvData(msg.what, msg.obj, msg.arg2, msg.arg1);

				} else if (msg.what == -1) {

					((AppException) msg.obj).makeToast(context);
				}

				order_info_progress.setVisibility(ProgressBar.GONE);
				
				
			}
		};
	}
	
	private Handler getShowPhoneHandler() {
		return new Handler() {
			public void handleMessage(Message msg) {
				if (msg.what >= 0) {
					
					phone.setVisibility(View.VISIBLE);

				} else if (msg.what == -1) {

					AlertDialogManager.showAlertDialog(context, "Error in accept Order", "Cannot accpet order", false);
					
				}

			}
		};
	}

	/**
	 * listview数据处理
	 * 
	 * @param what
	 *            数量
	 * @param obj
	 *            数据
	 * @param objtype
	 *            数据类型
	 * @param actiontype
	 *            操作类型
	 * @return notice 通知信息
	 */
	private void handleLvData(int what, Object obj, int objtype, int actiontype) {
		service = (Service) obj;

	
		TextView remark = (TextView) findViewById(R.id.cust_remark_id);
		phone = (TextView) findViewById(R.id.cust_phone_id);
		Button acceptButton = (Button) findViewById(R.id.order_accept_id);
		Button backButton = (Button) findViewById(R.id.order_back_id);

		phone.setVisibility(View.INVISIBLE);
		remark.setText(service.getRemark());
		phone.setText(service.getCustPhone());
		
		
		acceptButton.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		    	
		    	// send to confirm order
		    	confirmOrder(service.getOrderId(), appContext.getDriverId());
		    	
		    }
		});
		
		backButton.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		    	finish();
		    	
		    }
		});
		

		
		return;
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

	/**
	 * 线程加载新闻数据
	 * 
	 * @param catalog
	 *            分类
	 * @param pageIndex
	 *            当前页数
	 * @param handler
	 *            处理器
	 * @param action
	 *            动作标识
	 */
	private void loadLvNewsData(final String orderId, final int pageIndex, final Handler handler, final int action) {
		// mHeadProgress.setVisibility(ProgressBar.VISIBLE);
		new Thread() {
			public void run() {
				Message msg = new Message();
				boolean isRefresh = false;
				if (action == UIHelper.LISTVIEW_ACTION_REFRESH || action == UIHelper.LISTVIEW_ACTION_SCROLL)
					isRefresh = true;
				try {
					// NewsList list = appContext.getNewsList(catalog,
					// pageIndex, isRefresh);
					Service list = appContext.getService(orderId, isRefresh);
					msg.what = 1;
					msg.obj = list;
				} catch (AppException e) {
					e.printStackTrace();
					msg.what = -1;
					msg.obj = e;
				}
				msg.arg1 = action;
				msg.arg2 = UIHelper.LISTVIEW_DATATYPE_NEWS;
				// if (curNewsCatalog == catalog)
				handler.sendMessage(msg);
			}
		}.start();
	}

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
		return false;
	}

}