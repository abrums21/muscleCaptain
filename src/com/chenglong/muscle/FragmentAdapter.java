package com.chenglong.muscle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class FragmentAdapter extends FragmentPagerAdapter{

	private Fragment[] frag;
	public FragmentAdapter(FragmentManager fm, Fragment[] frag) {
		super(fm);
		// TODO Auto-generated constructor stub
		this.frag = frag;
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return frag[arg0];
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return frag.length;
	}

}
