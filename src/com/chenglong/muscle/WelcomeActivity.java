package com.chenglong.muscle;

import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class WelcomeActivity extends Activity {

	private static final int[] pics = { R.drawable.intro_1, R.drawable.intro_2, R.drawable.intro_3,
			R.drawable.intro_4 };
	private ViewPager vp;
	private ImageView[] dots;
	private int curIndex;
	private Button button;
	private long firstTime = 0;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.welcome);

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
				ImageLoader.getInstance().clearMemoryCache();
				finish();
			}
		});
	}

	private void ImageSetting() {
		// TODO Auto-generated method stub
		// List<View> viewList = new ArrayList<View>();
		//
		// LinearLayout.LayoutParams mParams = new
		// LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
		// LinearLayout.LayoutParams.WRAP_CONTENT);
		//
		// /* 图片初始化 */
		// for (int i = 0; i < pics.length; i++) {
		// ImageView iv = new ImageView(this);
		// iv.setLayoutParams(mParams);
		// iv.setImageResource(pics[i]);
		// viewList.add(iv);
		// }

		vp = (ViewPager) findViewById(R.id.viewpager);
		vp.setAdapter(new WelcomeAdapter(pics, this));
		vp.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub	
			}
			
			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				setCurDot(position);
				if (position == pics.length - 1) {
					button.setVisibility(View.VISIBLE);
				} else {
					button.setVisibility(View.GONE);
				}
			}
			
		});
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
					// setCurDot((Integer) v.getTag());
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
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		// return super.onKeyDown(keyCode, event);
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			long secondTime = System.currentTimeMillis();
			if (secondTime - firstTime > 2000) {
				Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
				firstTime = secondTime;
			} else { 
				ImageLoader.getInstance().clearMemoryCache();
				ImageLoader.getInstance().stop();
				finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
