package com.hkgoodvision.gvpos.page;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.callvan.gvpos.activity.R;
import com.hkgoodvision.gvpos.ListAdapter.ListViewOrderHistoryAdapter;
import com.hkgoodvision.gvpos.ListAdapter.ListViewServiceAdapter;
import com.hkgoodvision.gvpos.app.AppContext;
import com.hkgoodvision.gvpos.app.AppException;
import com.hkgoodvision.gvpos.common.StringUtils;
import com.hkgoodvision.gvpos.common.UIHelper;
import com.hkgoodvision.gvpos.dao.vo.Service;
import com.hkgoodvision.gvpos.dao.vo.ServiceList;
import com.hkgoodvision.gvpos.widget.PullToRefreshListView;

public class OrderHistoryFragment extends SherlockFragment {

	int CATALOG_ID = 2; // 1:current order, 2:order History
	
	double lat = 15;
	double log = 22;

	ViewGroup currentViewGroup = null;
	protected AppContext appContext;
	private View lvNews_footer;
	//private ProgressBar mHeadProgress;
	PullToRefreshListView serviceListView = null;
	
	private ListViewOrderHistoryAdapter listViewOrderHistoryAdapter;

	private Handler listViewServiceHandler;
	
	private TextView lvNews_foot_more;
	private ProgressBar lvNews_foot_progress;
	private int lvNewsSumData;
	private List<Service> serviceListData = new ArrayList<Service>();
	
//	LocationTrackUtils locaitonTracker = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View myFragmentView = null;

		myFragmentView = inflater.inflate(R.layout.point_listing_fragment, container, false);

		myFragmentView.setBackgroundResource(android.R.color.background_light);

		ViewGroup baseGroup = (ViewGroup) myFragmentView.findViewById(R.id.point_listing_layout_id);

		lvNews_footer = inflater.inflate(R.layout.listview_footer, null);
		
		currentViewGroup = baseGroup;
		appContext = (AppContext) this.getActivity().getApplicationContext();

//		locaitonTracker = new LocationTrackUtils(this.getActivity());
//		Location location = locaitonTracker.getLocation();
//		if (location != null) {
//			Log.d("PointList", "lat=" + location.getLatitude() + "long=" + location.getLongitude());
//			lat = location.getLatitude();
//			log = location.getLongitude();
//			
//		}

		serviceListView = (PullToRefreshListView) myFragmentView.findViewById(R.id.point_listing_list_id);
		// fill the list with data
		this.initFrameListView();
		
		

		return currentViewGroup;
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
	 * Auto refresh the data in list
	 */
	@Override
	public void onResume() {
	    super.onResume();
	    loadLvNewsData(CATALOG_ID, lat, log, 0, listViewServiceHandler, UIHelper.LISTVIEW_ACTION_INIT);
	}

	/**
	 * 初始化所有ListView数据
	 */
	private void initFrameListViewData() {
		// 初始化Handler
		listViewServiceHandler = this.getLvHandler(serviceListView, listViewOrderHistoryAdapter, lvNews_foot_more, lvNews_foot_progress, AppContext.PAGE_SIZE);

		// 加载资讯数据
		if (serviceListData.isEmpty()) {
			loadLvNewsData(CATALOG_ID, lat, log, 0, listViewServiceHandler, UIHelper.LISTVIEW_ACTION_INIT);
		}
	}

	/**
	 * 初始化新闻列表
	 */
	private void initNewsListView() {

		listViewOrderHistoryAdapter = new ListViewOrderHistoryAdapter(this.getActivity(), serviceListData,
				R.layout.order_history_list_item);
		
		//mHeadProgress = (ProgressBar) findViewById(R.id.frame_servicelist_head_progress);
		
		lvNews_foot_more = (TextView) lvNews_footer.findViewById(R.id.listview_foot_more);
		lvNews_foot_progress = (ProgressBar) lvNews_footer.findViewById(R.id.listview_foot_progress);
		
		serviceListView.addFooterView(lvNews_footer);// 添加底部视图 必须在setAdapter前
		
		serviceListView.setAdapter(listViewOrderHistoryAdapter);
		
		serviceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// show popup the reward detail and shop detail
				if (serviceListData.size() > position-1 && CATALOG_ID == 1) {
					Service service = serviceListData.get(position-1); // due to header is the 1st
					if (service != null) {
						int a=0;
						UIHelper.showOrderInfo(view.getContext(), service.getOrderId());
					}
				}
				
			}
		});
		serviceListView.setOnScrollListener(new AbsListView.OnScrollListener() {
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				serviceListView.onScrollStateChanged(view, scrollState);

				// 数据为空--不用继续下面代码了
				if (serviceListData.isEmpty())
					return;

				// 判断是否滚动到底部
				boolean scrollEnd = false;
				try {
					if (view.getPositionForView(lvNews_footer) == view.getLastVisiblePosition())
						scrollEnd = true;
				} catch (Exception e) {
					scrollEnd = false;
				}

				int lvDataState = StringUtils.toInt(serviceListView.getTag());
				if (scrollEnd && lvDataState == UIHelper.LISTVIEW_DATA_MORE) {
					serviceListView.setTag(UIHelper.LISTVIEW_DATA_LOADING);
					lvNews_foot_more.setText(R.string.load_ing);
					lvNews_foot_progress.setVisibility(View.VISIBLE);
					// 当前pageIndex
					int pageIndex = lvNewsSumData / AppContext.PAGE_SIZE;
					loadLvNewsData(CATALOG_ID, lat, log, pageIndex, listViewServiceHandler, UIHelper.LISTVIEW_ACTION_SCROLL);
				}
			}

			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				serviceListView.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
			}
		});
		serviceListView.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
			public void onRefresh() {
				loadLvNewsData(CATALOG_ID, lat, log, 0,  listViewServiceHandler, UIHelper.LISTVIEW_ACTION_REFRESH);
			}
		});
	}

	/**
	 * 获取listview的初始化Handler
	 * 
	 * @param lv
	 * @param adapter
	 * @return
	 */
	private Handler getLvHandler(final PullToRefreshListView lv, final BaseAdapter adapter, final TextView more,
			final ProgressBar progress, final int pageSize) {
		return new Handler() {
			public void handleMessage(Message msg) {
				if (msg.what >= 0) {
					// listview数据处理
					handleLvData(msg.what, msg.obj, msg.arg2, msg.arg1);

					if (msg.what < pageSize) {
						lv.setTag(UIHelper.LISTVIEW_DATA_FULL);
						adapter.notifyDataSetChanged();
						more.setText(R.string.load_full);
					} else if (msg.what == pageSize) {
						lv.setTag(UIHelper.LISTVIEW_DATA_MORE);
						adapter.notifyDataSetChanged();
						more.setText(R.string.load_more);

					}
					
				} else if (msg.what == -1) {
					// 有异常--显示加载出错 & 弹出错误消息
					lv.setTag(UIHelper.LISTVIEW_DATA_MORE);
					more.setText(R.string.load_error);
					((AppException) msg.obj).makeToast(currentViewGroup.getContext());
				}
				if (adapter.getCount() == 0) {
					lv.setTag(UIHelper.LISTVIEW_DATA_EMPTY);
					more.setText(R.string.load_empty);
				}
				progress.setVisibility(ProgressBar.GONE);
				//mHeadProgress.setVisibility(ProgressBar.GONE);
				if (msg.arg1 == UIHelper.LISTVIEW_ACTION_REFRESH) {
					lv.onRefreshComplete(getString(R.string.pull_to_refresh_update) + new Date().toLocaleString());
					lv.setSelection(0);
				} else if (msg.arg1 == UIHelper.LISTVIEW_ACTION_CHANGE_CATALOG) {
					lv.onRefreshComplete();
					lv.setSelection(0);
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
		
		switch (actiontype) {
		case UIHelper.LISTVIEW_ACTION_INIT:
		case UIHelper.LISTVIEW_ACTION_REFRESH:
		case UIHelper.LISTVIEW_ACTION_CHANGE_CATALOG:
			int newdata = 0;// 新加载数据-只有刷新动作才会使用到
			switch (objtype) {
			case UIHelper.LISTVIEW_DATATYPE_NEWS:
				ServiceList nlist = (ServiceList) obj;
				//notice = nlist.getNotice();
				lvNewsSumData = what;
				if (actiontype == UIHelper.LISTVIEW_ACTION_REFRESH) {
					if (serviceListData.size() > 0) {
						for (Service news1 : nlist.getServiceList()) {
							boolean b = false;
							for (Service news2 : serviceListData) {
								if (news1.getId() == news2.getId()) {
									b = true;
									break;
								}
							}
							if (!b)
								newdata++;
						}
					} else {
						newdata = what;
					}
				}
				serviceListData.clear();// 先清除原有数据
				serviceListData.addAll(nlist.getServiceList());
				break;

			}
			if (actiontype == UIHelper.LISTVIEW_ACTION_REFRESH) {
//				// 提示新加载数据
//				if (newdata > 0) {
//					NewDataToast.makeText(this, getString(R.string.new_data_toast_message, newdata),
//							appContext.isAppSound()).show();
//				} else {
//					NewDataToast.makeText(this, getString(R.string.new_data_toast_none), false).show();
//				}
			}
			break;
		case UIHelper.LISTVIEW_ACTION_SCROLL:
			switch (objtype) {
			case UIHelper.LISTVIEW_DATATYPE_NEWS:
				ServiceList list = (ServiceList) obj;
				
				lvNewsSumData += what;
				if (serviceListData.size() > 0) {
					for (Service news1 : list.getServiceList()) {
						boolean b = false;
						for (Service news2 : serviceListData) {
							if (news1.getId() == news2.getId()) {
								b = true;
								break;
							}
						}
						if (!b)
							serviceListData.add(news1);
					}
				} else {
					serviceListData.addAll(list.getServiceList());
				}
				break;

			}
			break;
		}
		return ;
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
	private void loadLvNewsData(final int catalog, final double lat, final double log, final int pageIndex, final Handler handler, final int action) {
		//mHeadProgress.setVisibility(ProgressBar.VISIBLE);
		new Thread() {
			public void run() {
				Message msg = new Message();
				boolean isRefresh = false;
				if (action == UIHelper.LISTVIEW_ACTION_REFRESH || action == UIHelper.LISTVIEW_ACTION_SCROLL)
					isRefresh = true;
				try {
					// NewsList list = appContext.getNewsList(catalog,
					// pageIndex, isRefresh);
					ServiceList list = appContext.getServiceList(catalog, lat, log, pageIndex, isRefresh);
					msg.what = list.getCount();
					msg.obj = list;
				} catch (AppException e) {
					e.printStackTrace();
					msg.what = -1;
					msg.obj = e;
				}
				msg.arg1 = action;
				msg.arg2 = UIHelper.LISTVIEW_DATATYPE_NEWS;
//				if (curNewsCatalog == catalog)
				handler.sendMessage(msg);
			}
		}.start();
	}

}
