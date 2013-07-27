package com.hkgoodvision.gvpos.page;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.actionbarsherlock.app.SherlockFragment;
import com.hkgoodvision.gvpos.activity.R;
import com.hkgoodvision.gvpos.app.AppContext;
import com.hkgoodvision.gvpos.common.FreqMapUtils;
import com.hkgoodvision.gvpos.common.LocationTrackUtils;
import com.vanapp.service.IAppManager;
import com.vanapp.service.IMService;

public class DetectPointFragment extends SherlockFragment {

	// show PopUp
	TextView pointTextView = null;
	
	TextView scanPointTextView = null;

	FreqMapUtils freqAnalysis = null;
	AppContext appContext = null;
	ViewGroup currentViewGroup = null;
	ImageView imageViewTemp = null;
	protected int page = 0;
	LocationTrackUtils locaitonTracker = null;
	
	ToggleButton startStopAudioButton = null;
	
	long counter = 1;

//	protected void startEarnPoint() {
//
//		// show pop-up
//		Intent intent = new Intent(this.getActivity().getApplicationContext(), ImageDialog.class);
//		intent.putExtra("point", 100);
//		intent.putExtra("companyName", "美聯社公司");
//		startActivity(intent);
//
//		//earnPoint();
//
//		// add point to menubar
//		pointTextView.setText("2050");
//
//	}
	
	void startGPS() {
	

		startStopAudioButton.setChecked(true);
		scanPointTextView.setText("正在找尋MatchPoint中......");
	}
	
	void stopGPS() {
	

		startStopAudioButton.setChecked(false);
		scanPointTextView.setText("按下開關找尋 MatchPoint");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View myFragmentView = null;

		View pointView = (View) this.getSherlockActivity().getSupportActionBar().getCustomView();

		pointTextView = (TextView) pointView.findViewById(R.id.membership_point_total_text);
		
		myFragmentView = inflater.inflate(R.layout.detect_point_fragment, container, false);



		appContext = (AppContext) this.getActivity().getApplicationContext();

		ViewGroup baseGroup = (ViewGroup) myFragmentView.findViewById(R.id.detect_point_layout_id);

		scanPointTextView = (TextView) baseGroup.findViewById(R.id.scan_point_text);

		currentViewGroup = baseGroup;
		
		locaitonTracker = new LocationTrackUtils(this.getActivity());
		Location location = locaitonTracker.getLocation();
		if (location != null)
			Log.d("Detection", "lat=" + location.getLatitude() + "long=" + location.getLongitude());

		// init...

		startStopAudioButton = (ToggleButton) baseGroup.findViewById(R.id.start_scan_button_id);
		
		startStopAudioButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

				if (isChecked) {
					startGPS();
					
				} else {
					stopGPS();
					// TODO: hack for testing only
					//startEarnPoint();
				}

			}

		});


		return myFragmentView;
	}


	private IAppManager imService;

	private ServiceConnection mConnection = new ServiceConnection() {
		public void onServiceConnected(ComponentName className, IBinder service) {
			// This is called when the connection with the service has been
			// established, giving us the service object we can use to
			// interact with the service. Because we have bound to a explicit
			// service that we know is running in our own process, we can
			// cast its IBinder to a concrete class and directly access it.
			imService = ((IMService.IMBinder) service).getService();

			if (imService != null && imService.isRunningGPSSender()) {

				//btnStartRevJob.setText("Stop Rev Job");
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
