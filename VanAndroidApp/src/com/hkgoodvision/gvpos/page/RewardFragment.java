package com.hkgoodvision.gvpos.page;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.actionbarsherlock.app.SherlockFragment;
import com.hkgoodvision.gvpos.ListAdapter.ListViewRewardAdapter;
import com.hkgoodvision.gvpos.activity.R;
import com.hkgoodvision.gvpos.app.AppContext;
import com.hkgoodvision.gvpos.app.AppException;
import com.hkgoodvision.gvpos.common.UIHelper;
import com.hkgoodvision.gvpos.dao.vo.Reward;
import com.hkgoodvision.gvpos.dao.vo.RewardList;
import com.hkgoodvision.gvpos.ui.RewardApplyDialog;

public class RewardFragment extends SherlockFragment {

	

	protected AppContext appContext;

	GridView serviceListView = null;
	
	private ListViewRewardAdapter listViewServiceAdapter;

	private Handler listViewServiceHandler;
	
	private final int REWARD_CAT_ID = 823;
	
	//private TextView lvNews_foot_more;
	//private ProgressBar lvNews_foot_progress;
	private int lvNewsSumData;
	private List<Reward> serviceListData = new ArrayList<Reward>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View myFragmentView = null;

		myFragmentView = inflater.inflate(R.layout.reward_fragment, container, false);

		myFragmentView.setBackgroundResource(android.R.color.background_light);

		
		appContext = (AppContext) this.getActivity().getApplicationContext();


		serviceListView = (GridView) myFragmentView.findViewById(R.id.reward_gridview_id);
		// fill the list with data
		this.initFrameListView();

		

		return serviceListView;
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
		listViewServiceHandler = this.getLvHandler(serviceListView, listViewServiceAdapter, 50);

		// 加载资讯数据
		if (serviceListData.isEmpty()) {
			loadLvNewsData(1, 0, listViewServiceHandler, UIHelper.LISTVIEW_ACTION_INIT);
		}
	}

	/**
	 * 初始化新闻列表
	 */
	private void initNewsListView() {

		listViewServiceAdapter = new ListViewRewardAdapter(this.getActivity(), serviceListData,
				R.layout.reward_list_item);
		//lvNews_foot_more = (TextView) currentViewGroup.findViewById(R.id.listview_foot_more);
		//lvNews_foot_progress = (ProgressBar) currentViewGroup.findViewById(R.id.listview_foot_progress);

		serviceListView.setAdapter(listViewServiceAdapter);
		
		serviceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
				Reward service = serviceListData.get(position); // due to header is the 1st
				if (service != null) {
					
					Intent intent = new Intent(view.getContext(), RewardApplyDialog.class);
					intent.putExtra("rewardId", service.getRewardId());
					intent.putExtra("title", service.getTitle());
					intent.putExtra("point", service.getPoint());
					view.getContext().startActivity(intent);
				}
			
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
	private Handler getLvHandler(final GridView lv, final BaseAdapter adapter, final int pageSize) {
		return new Handler() {
			public void handleMessage(Message msg) {
				if (msg.what >= 0) {
					// listview数据处理
					handleLvData(msg.what, msg.obj, msg.arg2, msg.arg1);

					if (msg.what < pageSize) {
						lv.setTag(UIHelper.LISTVIEW_DATA_FULL);
						adapter.notifyDataSetChanged();
						//more.setText(R.string.load_full);
					} else if (msg.what == pageSize) {
						lv.setTag(UIHelper.LISTVIEW_DATA_MORE);
						adapter.notifyDataSetChanged();
						//more.setText(R.string.load_more);

					}
					
				} else if (msg.what == -1) {
					// 有异常--显示加载出错 & 弹出错误消息
					lv.setTag(UIHelper.LISTVIEW_DATA_MORE);
					/*more.setText(R.string.load_error);
					((AppException) msg.obj).makeToast(currentViewGroup.getContext());*/
				}
				if (adapter.getCount() == 0) {
					lv.setTag(UIHelper.LISTVIEW_DATA_EMPTY);
					//more.setText(R.string.load_empty);
				}
//				progress.setVisibility(ProgressBar.GONE);
				//mHeadProgress.setVisibility(ProgressBar.GONE);
//				if (msg.arg1 == UIHelper.LISTVIEW_ACTION_REFRESH) {
//					lv.onRefreshComplete(getString(R.string.pull_to_refresh_update) + new Date().toLocaleString());
//					lv.setSelection(0);
//				} else if (msg.arg1 == UIHelper.LISTVIEW_ACTION_CHANGE_CATALOG) {
//					lv.onRefreshComplete();
//					lv.setSelection(0);
//				}
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
				RewardList nlist = (RewardList) obj;
				//notice = nlist.getNotice();
				lvNewsSumData = what;
				if (actiontype == UIHelper.LISTVIEW_ACTION_REFRESH) {
					if (serviceListData.size() > 0) {
						for (Reward news1 : nlist.getRewardList()) {
							boolean b = false;
							for (Reward news2 : serviceListData) {
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
				serviceListData.addAll(nlist.getRewardList());
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
//		case UIHelper.LISTVIEW_ACTION_SCROLL:
//			switch (objtype) {
//			case UIHelper.LISTVIEW_DATATYPE_NEWS:
//				SubjectList list = (SubjectList) obj;
//				notice = list.getNotice();
//				lvNewsSumData += what;
//				if (lvNewsData.size() > 0) {
//					for (Subject news1 : list.getSubjectList()) {
//						boolean b = false;
//						for (Subject news2 : lvNewsData) {
//							if (news1.getId() == news2.getId()) {
//								b = true;
//								break;
//							}
//						}
//						if (!b)
//							lvNewsData.add(news1);
//					}
//				} else {
//					lvNewsData.addAll(list.getSubjectList());
//				}
//				break;
//
//			}
//			break;
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
	private void loadLvNewsData(final int catalog, final int pageIndex, final Handler handler, final int action) {
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
					RewardList list = appContext.getRewardList(REWARD_CAT_ID, pageIndex, isRefresh);
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
	
	
	//================
	
	
	



}
