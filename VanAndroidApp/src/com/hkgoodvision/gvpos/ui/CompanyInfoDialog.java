package com.hkgoodvision.gvpos.ui;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.hkgoodvision.gvpos.activity.R;
import com.hkgoodvision.gvpos.app.AppContext;
import com.hkgoodvision.gvpos.app.AppException;
import com.hkgoodvision.gvpos.common.BitmapManager;
import com.hkgoodvision.gvpos.common.StringUtils;
import com.hkgoodvision.gvpos.common.UIHelper;
import com.hkgoodvision.gvpos.constant.URLs;
import com.hkgoodvision.gvpos.dao.vo.Service;

public class CompanyInfoDialog extends SherlockActivity implements OnTouchListener {

	private ImageView mDialog;

	ViewGroup currentViewGroup = null;
	protected AppContext appContext;

	private Handler listViewServiceHandler;

	private ProgressBar compnay_info_progress;

	private Service serviceData = null;

	BitmapManager bmpManager = null;

	int serviceId = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.company_popup);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

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

		findViewById(R.id.company_info_layout_id);
		
		appContext = (AppContext) this.getApplicationContext();
		
		serviceId = getIntent().getIntExtra("serviceId", 0);
		
		compnay_info_progress = (ProgressBar) findViewById(R.id.company_info_progress);

		initFrameListView();

		// mDialog.setClickable(true);
		//
		//
		//
		// //finish the activity (dismiss the image dialog) if the user clicks
		// //anywhere on the image
		// mDialog.setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// mDialog.setImageResource(R.drawable.food2);
		// // finish();
		// }
		// });

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

		// 加载资讯数据
		if (serviceData == null) {
			loadLvNewsData(serviceId, 0, listViewServiceHandler, UIHelper.LISTVIEW_ACTION_INIT);
		}
	}

	/**
	 * 初始化新闻列表
	 */
	private void initNewsListView() {

	}

	/**
	 * 获取listview的初始化Handler
	 * 
	 * @param lv
	 * @param adapter
	 * @return
	 */
	private Handler getLvHandler() {
		return new Handler() {
			public void handleMessage(Message msg) {
				if (msg.what >= 0) {
					// listview数据处理
					handleLvData(msg.what, msg.obj, msg.arg2, msg.arg1);

				} else if (msg.what == -1) {

					((AppException) msg.obj).makeToast(currentViewGroup.getContext());
				}

				compnay_info_progress.setVisibility(ProgressBar.GONE);
				
				// mHeadProgress.setVisibility(ProgressBar.GONE);
				// if (msg.arg1 == UIHelper.LISTVIEW_ACTION_REFRESH) {
				// lv.onRefreshComplete(getString(R.string.pull_to_refresh_update)
				// + new Date().toLocaleString());
				// lv.setSelection(0);
				// } else if (msg.arg1 ==
				// UIHelper.LISTVIEW_ACTION_CHANGE_CATALOG) {
				// lv.onRefreshComplete();
				// lv.setSelection(0);
				// }
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
		Service service = (Service) obj;
		bmpManager = new BitmapManager(BitmapFactory.decodeResource(this.getResources(),
				R.drawable.widget_dface_loading));

		ImageView photo = (ImageView) findViewById(R.id.company_info_image_id);
		TextView name = (TextView) findViewById(R.id.company_info_name_id);
		TextView address = (TextView) findViewById(R.id.company_info_address_id);
		TextView desc = (TextView) findViewById(R.id.company_info_desc_id);
		TextView promo = (TextView) findViewById(R.id.company_info_promo_id);
		TextView promolb = (TextView) findViewById(R.id.company_info_promo_lb);
		TextView desclb = (TextView) findViewById(R.id.company_info_desc_lb);

//		String photoURL = service.getPhoto();
//		if (photoURL == null || photoURL.endsWith("portrait.gif") || StringUtils.isEmpty(photoURL)) {
//			photo.setImageResource(R.drawable.widget_dface);
//		} else {
//			bmpManager.loadBitmap(URLs.IMAGE_PATH_URL + photoURL, photo);
//		}
//
//		name.setText(service.getServiceName());
//		address.setText(service.getAddress());
//		desc.setText(service.getDescription());
//		promo.setText(service.getPromoDesc());
//		if (!StringUtils.isEmpty(service.getDescription()))
//			desclb.setText("簡介");
//		if (!StringUtils.isEmpty(service.getPromoDesc()))
//			promolb.setText("推廣活動");
		
		return;
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
	private void loadLvNewsData(final int catalog, final int pageIndex, final Handler handler, final int action) {
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
					Service list = appContext.getService(catalog, isRefresh);
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