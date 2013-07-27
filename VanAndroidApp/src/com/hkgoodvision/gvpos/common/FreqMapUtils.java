package com.hkgoodvision.gvpos.common;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.location.Location;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.hkgoodvision.gvpos.app.AppContext;
import com.hkgoodvision.gvpos.app.AppException;
import com.hkgoodvision.gvpos.dao.vo.Result;
import com.hkgoodvision.gvpos.ui.ImageDialog;

public class FreqMapUtils {
	/**
	 * 450 19379.8828125 
	 * 451 19422.94921875 1
	 * 452 19466.015625 1 
	 * 453 19509.08203125 1
	 * 454 19552.1484375 1 
	 * 455 19595.21484375 1
	 * 456 19638.28125 2
	 * 457 19681.34765625 2 
	 * 458 19724.4140625 2 
	 * 459 19767.48046875 2
	 * 460 19810.546875 2 
	 * 461 19853.61328125 3 
	 * 462 19896.6796875 3 
	 * 463 19939.74609375 3
	 * 464 19982.8125 3 
	 * 465 20025.87890625 3 
	 * 466 20068.9453125 4
	 * 467 20112.01171875 4
	 * 468 20155.078125 4
	 * 469 20198.14453125 4 
	 * 470 20241.2109375 4
	 * 471 20284.27734375 5
	 * 472 20327.34375 5 
	 * 473 20370.41015625 5
	 * 474 20413.4765625 5 
	 * 475 20456.54296875 5
	 * 476 20499.609375 6 
	 * 477 20542.67578125 6 
	 * 478 20585.7421875 6 
	 * 479 20628.80859375 6
	 * 480 20671.875 6
	 * 481 20714.94140625 7 
	 * 482 20758.0078125 7 
	 * 483 20801.07421875 7
	 * 484 20844.140625 7 
	 * 485 20887.20703125 7 
	 * 486 20930.2734375  
	 * 487 20973.33984375 
	 * 488 21016.40625
	 */
	
	
	
	public static final int START_FREQ = 450;
	public static final int END_FREQ = 486;
	public static final double SIGNAL_LEVEL = 0.5;
	
	private static final int START_FREQ_OF_CENTER = 453;
	private static final int FREQ_STEP = 5;
	
	List<String> audiokey = new ArrayList<String>();
	LocationTrackUtils locationTracker;
	private boolean isSendingRequest = false;
	
	public void init() {
		// init internal key
		audiokey.add("2351");
		audiokey.add("3561");
		audiokey.add("2356");
		audiokey.add("4521");
		audiokey.add("4551");
	}
	
	public FreqMapUtils(AppContext appContext, LocationTrackUtils location) {
		this.appContext = appContext;
		this.locationTracker = location;
	}
	
	AppContext appContext = null;
	final int TOTAL_GROUP = 9;
	final int GROUP_REACH_LEVEL = 12;
	final int TOTAL_SAMPLE = 200; // 20s, 22 sample in 1s
	boolean startAnalysis = false;
	int[] sampleList = new int[TOTAL_SAMPLE];
	int[] resultGroupList = new int[((int) TOTAL_SAMPLE / GROUP_REACH_LEVEL) + 2];
	int sampleCounter = 0;

	public void addSample(int input) {
		int res = resolveGroupByFFT(input);
		if (res > 0) {
			//Log.d("resolveGroupByFFT=", "GROUP="+res);
			sampleList[sampleCounter] = res;
		}
	}
	
	boolean notExistYet(int[] list , int input) {
		for (int i = 0; i < list.length; i++) {
			if (list[i] == input)
				return false;
		}
		return true;
	}

	public void nextSample() {
		sampleCounter = sampleCounter + 1;
		if (sampleCounter >= TOTAL_SAMPLE) {
			// enough sample. do the analysis
			startAnalysis = true;
			// reset to fill again
			sampleCounter = 0;
		}

		if (startAnalysis && sampleCounter % 50 == 1) {
			
//			for (int i = 0; i < TOTAL_SAMPLE; i++) {
//				Log.d("sample", i +"," + sampleList[i]);
//			}
			
			// find out the group which more than a level
			int resultCount = 0;
			int[] groupArray = new int[TOTAL_GROUP+1];
			
			for (int i = 0; i < TOTAL_SAMPLE; i++) {
				if (sampleList[i] > 0) {
					// count to group level
					groupArray[sampleList[i]] = groupArray[sampleList[i]] +1;
					
					// check if reach level
					if (groupArray[sampleList[i]] >= GROUP_REACH_LEVEL && notExistYet(resultGroupList, sampleList[i])) {
						resultGroupList[resultCount] = sampleList[i]; 
						resultCount = resultCount + 1;
					}
					
				}
			}	
			
			for (int a = 0; a < resultGroupList.length; a++) {
				//Log.d("resultGroupList=", a+":"+resultGroupList[a]);
			}
			
		
			

			// show result list
			String resultKey = "";
			for (int i = 0; i < resultGroupList.length; i++) {
				if (resultGroupList[i] > 0) {
					
					resultKey = resultKey+resultGroupList[i];
				}
			}
			
			if (resultKey.length() < 4)
				return;
			
			Log.d("result signal", "" + resultKey);
			
//			for (int i = 0; i < TOTAL_SAMPLE; i++) {
//				Log.d("sample", i +"," + sampleList[i]);
//			}
			
			boolean isInternalMatched = false;
			// check for internal matching first...
			for (String key : audiokey) {
				if (resultKey.indexOf(key) >= 0) {
					isInternalMatched = true;
					break;
				}
			}
			
			//TODO: hack for testing williamho
			isInternalMatched = true;
			
			// if internal matched, check for server side
			if (isInternalMatched) {
				Handler handler = getLvHandler();
				Location loc = locationTracker.getLocation();
				double log = -1;
				double lat = -1;
				if (loc != null) {
					log = loc.getLongitude();
					lat = loc.getLatitude();
				} 
				
				if (!isSendingRequest)
					sendAudioToCheck(appContext.getUuid(),resultKey, log, lat, handler);
			}
				
		

		}
	}

	public int resolveGroupByFFT(int input) {
		int starting = START_FREQ_OF_CENTER;
		for (int i = 1; i <= TOTAL_GROUP; i++) {
			if (isNear(input, starting))
				return i;

			starting = starting + FREQ_STEP;
		}

		return -1;
	}

	public boolean isNear(int i, int ref) {
		int range = (int) FREQ_STEP /2;
		if (i <= ref + range && i >= ref - range)
			return true;
		else
			return false;
	}

	

	private Handler getLvHandler() {
		return new Handler() {
			public void handleMessage(Message msg) {
				if (msg.what >= 0) {
					Result res = (Result) msg.obj;
					int earnPoint = Integer.parseInt(res.getParam1());
					int currentPoint = Integer.parseInt(""+appContext.getMatchPointTextView().getText());
					
					int newPoint = currentPoint + earnPoint;
					appContext.getMatchPointTextView().setText(""+(newPoint));
					
					Intent intent = new Intent(appContext, ImageDialog.class);
					intent.putExtra("point", res.getParam1());
					intent.putExtra("companyName", res.getParam2());
					appContext.startActivity(intent);
					
					
				} else if (msg.what == -1) {

					((AppException) msg.obj).makeToast(appContext);
					
				}
				
				isSendingRequest = false;

			}
		};
	}

	
	private void sendAudioToCheck(final String uuid, final String audiokey,final double log,final double lag, final Handler handler) {
		// mHeadProgress.setVisibility(ProgressBar.VISIBLE);
		isSendingRequest = true;
		new Thread() {
			public void run() {
				Message msg = new Message();

				try {
					// NewsList list = appContext.getNewsList(catalog,
					// pageIndex, isRefresh);
					Result res = appContext.audioCheck(uuid, audiokey, log, lag);
					msg.what = 1;
					msg.obj = res;
				} catch (AppException e) {
					e.printStackTrace();
					msg.what = -1;
					msg.obj = e;
				}
				// if (curNewsCatalog == catalog)
				handler.sendMessage(msg);
			}
		}.start();
	}
}
