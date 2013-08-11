package com.hkgoodvision.gvpos.page;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.actionbarsherlock.app.SherlockFragment;
import com.callvan.gvpos.activity.R;
import com.google.android.gcm.GCMRegistrar;
import com.hkgoodvision.gvpos.common.UIHelper;
import com.vanapp.db.KeyPairDB;

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
			
			Button cancelAccount = (Button) baseGroup.findViewById(R.id.system_cancel_account_id);
			
			aboutus.setOnClickListener(new View.OnClickListener() {
			    @Override
			    public void onClick(View v) {
			        UIHelper.showAboutUs(v.getContext());
			    }
			});
			
			
			account.setOnClickListener(new View.OnClickListener() {
			    @Override
			    public void onClick(View v) {
			    	UIHelper.showAccount(v.getContext());
			    }
			});
			
			cancelAccount.setOnClickListener(new View.OnClickListener() {
			    @Override
			    public void onClick(View v) {
			    	GCMRegistrar.setRegisteredOnServer(v.getContext(), false);
					KeyPairDB.setDriverId(null, v.getContext());
					KeyPairDB.setDriverPhone(null, v.getContext());
			    	
			    }
			});

		return myFragmentView;
	}
	
	

	
}
