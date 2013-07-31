package com.hkgoodvision.gvpos.ListAdapter;

import java.util.List;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hkgoodvision.gvpos.activity.R;
import com.hkgoodvision.gvpos.common.BitmapManager;
import com.hkgoodvision.gvpos.common.StringUtils;
import com.hkgoodvision.gvpos.constant.URLs;
import com.hkgoodvision.gvpos.dao.vo.Service;

/**
 * 新闻资讯Adapter类
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class ListViewServiceAdapter extends BaseAdapter {
	private Context 					context;//运行上下文
	private List<Service> 					listItems;//数据集合
	private LayoutInflater 				listContainer;//视图容器
	private int 						itemViewResource;//自定义项视图源 
	private BitmapManager 				bmpManager;
	static class ListItemView{				//自定义控件集合  
	        public TextView title;  
		    public TextView address;
		    public TextView pointearn;
		    
	 }  

	/**
	 * 实例化Adapter
	 * @param context
	 * @param data
	 * @param resource
	 */
	public ListViewServiceAdapter(Context context, List<Service> data,int resource) {
		this.context = context;			
		this.listContainer = LayoutInflater.from(context);	//创建视图容器并设置上下文
		this.itemViewResource = resource;
		this.listItems = data;
		this.bmpManager = new BitmapManager(BitmapFactory.decodeResource(context.getResources(), R.drawable.widget_dface_loading));
	}
	
	public int getCount() {
		return listItems.size();
	}

	public Object getItem(int arg0) {
		return null;
	}

	public long getItemId(int arg0) {
		return 0;
	}
	
	/**
	 * ListView Item设置
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		//Log.d("method", "getView");
		
		//自定义视图
		ListItemView  listItemView = null;
		
		if (convertView == null) {
			//获取list_item布局文件的视图
			convertView = listContainer.inflate(this.itemViewResource, null);
			
			listItemView = new ListItemView();
			//获取控件对象
			listItemView.title = (TextView)convertView.findViewById(R.id.servicelist_listitem_title);
			listItemView.address = (TextView)convertView.findViewById(R.id.servicelist_listitem_address);
			listItemView.pointearn = (TextView)convertView.findViewById(R.id.servicelist_listitem_point);
			
			
			//设置控件集到convertView
			convertView.setTag(listItemView);
		}else {
			listItemView = (ListItemView)convertView.getTag();
		}	
		
		//设置文字和图片
		Service service = listItems.get(position);
		
//		String photoURL = service.getPhoto();
//		if(photoURL == null || photoURL.endsWith("portrait.gif") || StringUtils.isEmpty(photoURL)){
//			listItemView.photo.setImageResource(R.drawable.widget_dface);
//		}else{
//			bmpManager.loadBitmap(URLs.IMAGE_PATH_URL+photoURL, listItemView.photo);
//		}

		listItemView.title.setTag(service);//设置隐藏参数(实体类)
		listItemView.title.setText(service.getOrderDate() + " " + service.getTimeslot());
		listItemView.address.setText(service.getFromLocation() + "-> " +service.getToLocation());
		listItemView.pointearn.setText(""+service.getPrice());
		
		return convertView;
	}
}