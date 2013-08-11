package com.hkgoodvision.gvpos.page;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.actionbarsherlock.app.SherlockFragment;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

	final int PAGE_COUNT = 4;

	/** Constructor of the class */
	public MyFragmentPagerAdapter(FragmentManager fm) {
		super(fm);

	}

	/** This method will be invoked when a page is requested to create */
	@Override
	public Fragment getItem(int arg0) {

		SherlockFragment androidFragment = null;
		if (arg0 == 0) {
			androidFragment = new PointListFragment();
		} else if (arg0 == 1) {
			androidFragment = new DetectPointFragment();
		} else if (arg0 == 2) {
			androidFragment = new OrderHistoryFragment();;
		} else if (arg0 == 3) {
//			androidFragment = new RewardFragment();
//		} else {
			androidFragment = new SystemFragment();
		}

		return androidFragment;

	}

	/** Returns the number of pages */
	@Override
	public int getCount() {
		return PAGE_COUNT;
	}

}
