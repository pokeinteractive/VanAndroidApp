package com.hkgoodvision.gvpos.page;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.actionbarsherlock.app.SherlockFragment;
import com.callvan.gvpos.activity.R;
import com.hkgoodvision.gvpos.dao.Food;
import com.hkgoodvision.gvpos.dao.SQLiteManager;

public class FoodMenuFragment extends SherlockFragment {

	//static Map<String, View> pageFragmentMap = new HashMap<String, View>();
	private List<ImageButton> foodPhotoButtonList = new ArrayList<ImageButton>();
	private List<ImageButton> foodOrderButtonList = new ArrayList<ImageButton>();

	ViewGroup currentViewGroup = null;
	ImageView imageViewTemp = null;
	protected int page = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View myFragmentView = null;

//		if (pageFragmentMap.get("" + page) == null) {

			myFragmentView = inflater.inflate(R.layout.food_menu_fragment, container, false);

			// AppSession session = AppSession.getInstance();

			ViewGroup baseGroup = (ViewGroup) myFragmentView.findViewById(R.id.food_menu_layout_id);

			Log.d("FoodMenuFragment", "Page=" + page);

			if (page == 0) {

				myFragmentView.setBackgroundResource(android.R.color.background_light);
			} else if (page == 1) {

				myFragmentView.setBackgroundResource(android.R.color.background_dark);
			} else if (page == 2) {

				myFragmentView.setBackgroundResource(android.R.color.holo_green_light);
			} else if (page == 3) {

				myFragmentView.setBackgroundResource(android.R.color.holo_red_light);
			} else if (page == 4) {

				myFragmentView.setBackgroundResource(android.R.color.holo_orange_light);
			}


			// get the position of food photo and add order button
			SQLiteManager sqlManager = new SQLiteManager(getActivity());
			List<Food> list = sqlManager.getFoodByPage(page);

			
			currentViewGroup = baseGroup;

		return myFragmentView;
	}
	
	

	
}
