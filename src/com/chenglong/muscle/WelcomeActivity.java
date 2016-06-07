package com.chenglong.muscle;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class WelcomeActivity extends Activity implements OnClickListener, OnPageChangeListener {

	private static final int[] pics = {R.drawable.intro_1, R.drawable.intro_2, R.drawable.intro_3, R.drawable.intro_4};
	private ViewPager vp;
	private ImageView[] dots;
	private int curIndex;
	private Button button;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.welcome);

		/* 定时器跳转 */
		// setContentView(R.layout.welcome);
		//
		// start = new Intent(this, MainActivity.class);
		//
		// new Timer().schedule(new TimerTask() {
		//
		// @Override
		// public void run() {
		// // TODO Auto-generated method stub
		// startActivity(start);
		// finish();
		// }
		// }, 5000);
		
		ImageSetting();
		
		DotsSetting();
		
		switchSetting();
	}

	private void switchSetting() {
		// TODO Auto-generated method stub
		button = (Button) findViewById(R.id.button);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent startIntent = new Intent(WelcomeActivity.this, MainActivity.class);
				startActivity(startIntent);
				finish();
			}
		});
	}

	private void ImageSetting() {
		// TODO Auto-generated method stub
		List<View> viewList = new ArrayList<View>();

		LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);

		/* 图片初始化 */
		for (int i = 0; i < pics.length; i++) {
			ImageView iv = new ImageView(this);
			iv.setLayoutParams(mParams);
			iv.setImageResource(pics[i]);
			viewList.add(iv);
		}

		vp = (ViewPager)findViewById(R.id.viewpager);
		WelcomeAdapter adapter = new WelcomeAdapter(viewList);
		vp.setAdapter(adapter);
		vp.setOnPageChangeListener(this);
	}

	private void DotsSetting() {
		// TODO Auto-generated method stub
		LinearLayout ll = (LinearLayout) findViewById(R.id.ll);

		dots = new ImageView[pics.length];

		for (int tmp = 0; tmp < dots.length; tmp++) {
			dots[tmp] = (ImageView) ll.getChildAt(tmp);
			dots[tmp].setEnabled(true);
			dots[tmp].setTag(tmp);
			dots[tmp].setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					setCurViewPager((Integer) v.getTag());
					//setCurDot((Integer) v.getTag());
				}
			});

		}

		curIndex = 0;
		dots[curIndex].setEnabled(false); // 初始被选中
	}

	private void setCurDot(int position) {
		if ((position < 0) || (position > pics.length - 1) || (curIndex == position)) {
			return;
		}

		/* 设置dots标记 */
		dots[position].setEnabled(false);
		dots[curIndex].setEnabled(true);
		curIndex = position;
	}
	
	private void setCurViewPager(int position) {
		if ((position < 0) || (position > pics.length - 1) || (curIndex == position)) {
			return;
		}
		/* 设置图像 */
		vp.setCurrentItem(position);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		setCurDot(arg0);
		if (arg0 == pics.length - 1) {
			button.setVisibility(View.VISIBLE);
		} else {
			button.setVisibility(View.GONE);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	}

}
