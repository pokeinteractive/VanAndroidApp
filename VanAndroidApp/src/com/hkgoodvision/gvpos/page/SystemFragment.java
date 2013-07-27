package com.hkgoodvision.gvpos.page;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.actionbarsherlock.app.SherlockFragment;
import com.hkgoodvision.gvpos.activity.R;
import com.hkgoodvision.gvpos.common.UIHelper;

public class SystemFragment extends SherlockFragment {


	ViewGroup currentViewGroup = null;
	ImageView imageViewTemp = null;
	protected int page = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View myFragmentView = null;

//		if (pageFragmentMap.get("" + page) == null) {

			myFragmentView = inflater.inflate(R.layout.system_fragment, container, false);

			// AppSession session = AppSession.getInstance();

			ViewGroup baseGroup = (ViewGroup) myFragmentView.findViewById(R.id.system_layout_id);

			Log.d("FoodMenuFragment", "Page=" + page);
			
			currentViewGroup = baseGroup;
			
			Button aboutus = (Button) baseGroup.findViewById(R.id.system_aboutus_id);
			
			Button account = (Button) baseGroup.findViewById(R.id.system_account_id);
			
			aboutus.setOnClickListener(new View.OnClickListener() {
			    @Override
			    public void onClick(View v) {
			        UIHelper.showAboutUs(v.getContext());
			    }
			});
			
			
			account.setOnClickListener(new View.OnClickListener() {
			    @Override
			    public void onClick(View v) {
			    	UIHelper.showAccount(v.getContext(), 0);
			    }
			});

		return myFragmentView;
	}
	
	

	
}
