package com.chenglong.muscle.tool;

import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class AlbumAdapter extends PagerAdapter {

	//private List<View> viewLists;
	private List<ImageView> images;
	private Context context;

	/* 以原始素材作为来源  */
	public AlbumAdapter(List<ImageView> images, Context context) {
		// TODO Auto-generated constructor stub
		this.images = images;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return images.size();
		//return Integer.MAX_VALUE;
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
		container.removeView((View)object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		 //return super.instantiateItem(container, position);
		ImageView iv = images.get(position);
		//ImageView iv = images.get(position % images.size());
		iv.setScaleType(ScaleType.FIT_XY);		
		container.addView(iv, 0);
		return iv;
	}

}
