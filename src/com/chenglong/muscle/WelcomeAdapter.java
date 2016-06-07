package com.chenglong.muscle;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class WelcomeAdapter extends PagerAdapter {

	private List<View> viewLists;

	public WelcomeAdapter(List<View> Lists) {
		// TODO Auto-generated constructor stub
		viewLists = Lists;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return viewLists.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		 //super.destroyItem(container, position, object);
		container.removeView(viewLists.get(position));
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		 //return super.instantiateItem(container, position);
		container.addView(viewLists.get(position), 0);
		return viewLists.get(position);
	}

}
