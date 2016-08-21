package com.chenglong.muscle;

import java.util.List;

import com.chenglong.muscle.R.drawable;
import com.chenglong.muscle.floatBall.FloatBallService;
import com.chenglong.muscle.floatBall.FloatViewManager;
import com.chenglong.muscle.util.MyCommonUtil;
import com.chenglong.muscle.util.MyPackageUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements OnClickListener, OnPageChangeListener {
	
	private final int ITEM1 = Menu.FIRST;
	//private final int ITEM2 = Menu.FIRST + 1;
	private ViewPager viewPager;
	private ImageView[] images;
	private int curIndex;
	private MuscleFrag1 frag1 = new MuscleFrag1();
	private ToolFrag2 frag2 = new ToolFrag2();
	//private Fragment3 frag3 = new Fragment3();
	//private final Fragment[] frags = { frag1, frag2, frag3 };
	private final Fragment[] frags = { frag1, frag2};
	private final int[] imageId = { R.id.image1, R.id.image2};
	private final String[] titleInfo = {"美队健身：训练", "美队健身：工具"};
	private final String[] floatBallInfo = {"关闭悬浮球", "开启悬浮球"};
	private int floatBallShow = 1;
	private SharedPreferences shareaPare;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// requestWindowFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.main);	
		
		// setProgressBarVisibility(true);
		// setProgress(50);

		// web = (WebView) findViewById(R.id.web);
		// web.getSettings().setJavaScriptEnabled(true);
		// // String url = "http://www.baidu.com/";
		// String url =
		// "http://baike.baidu.com/link?url=bXxnPqP2AFL9Jx6V6t__xey9-bzUpBz9TFJwSR0TX0tQ1Z8rgo8lVkk_9J8-zl1jpS_KTB-v4T6mrZfGHc_JneBKmu7GImoRU_e8NU-u6qu";
		// web.loadUrl(url);
		// web.setWebViewClient(new WebViewClient()); /* 防止打开第三方浏览器打开 */
		
		startService();
		initSetting();
		tabSetting();
		viewPagerSetting();
	}
	
	private void initSetting() {
		// TODO Auto-generated method stub
		curIndex = 0;
        setCurTitle(curIndex);
	}

	private void viewPagerSetting() {
		// TODO Auto-generated method stub
		viewPager = (ViewPager) findViewById(R.id.vp_main);
		viewPager.setOffscreenPageLimit(frags.length);
//		List<Fragment> fragList = new ArrayList<Fragment>();
//
//		for (Fragment frag : frags) {
//			fragList.add(frag); /* 一处使用不涉及Fragment内存重复申请 */
//		}

		FragmentAdapter fragAdapter = new FragmentAdapter(getSupportFragmentManager(), frags);
		viewPager.setAdapter(fragAdapter);
		viewPager.setCurrentItem(curIndex);
		viewPager.setOnPageChangeListener(this);
	}

	private void tabSetting() {
		// TODO Auto-generated method stub
		images = new ImageView[imageId.length];
		for(int tmp=0;tmp<imageId.length;tmp++)
		{
			images[tmp] = (ImageView)findViewById(imageId[tmp]);
			images[tmp].setTag(tmp);
			images[tmp].setEnabled(true);
			images[tmp].setOnClickListener(this);
		}
		
		images[curIndex].setEnabled(false);
	}

	private void setCurImage(int position) {
		if ((position < 0) || (position > imageId.length - 1) || (curIndex == position)) {
			return;
		}

		/* 设置标记 */
		images[position].setEnabled(false);
		images[curIndex].setEnabled(true);
		curIndex = position;
		setCurTitle(position);
	}
	
	private void setCurFragment(int position) {
		if ((position < 0) || (position > imageId.length - 1) || (curIndex == position)) {
			return;
		}

		/* 设置图像 */
		viewPager.setCurrentItem(position);
		setCurTitle(position);
	}
	
	
	private void setCurTitle(int position) {
		setTitle(titleInfo[position]);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// if (web.canGoBack()) {
			// web.goBack();
			// } else /* 若需要连续点击两次推出需要修改此处 */
			{
				AlertDialog.Builder adBuilder = new AlertDialog.Builder(this);
				AlertDialog dialog = adBuilder.setMessage("是否确定离开").setPositiveButton("下次再来", new AlertDialog.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						MainActivity.this.finish();
						
					}
				}).setNegativeButton("再看看", null).create();
				
				WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
		    	lp.alpha = 0.7f;
		    	dialog.getWindow().setAttributes(lp);	
		    	
		    	dialog.show();
			}
			return true;
		}

		return super.onKeyDown(keyCode, event); /* 目前仅处理回退按键 */
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		super.onCreateOptionsMenu(menu);  
		getMenuInflater().inflate(R.menu.menu_main, menu);
		//menu.add(0, ITEM1, 0, "关于");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		
//		if (id == R.id.action_settings) {
//			return true;
//		}
//		return super.onOptionsItemSelected(item);
		
		switch (id)
		{
		    case R.id.menu_main_1:
		    {
		    	View aboutView = LayoutInflater.from(this).inflate(R.layout.about, null);
		    	aboutView.getBackground().setAlpha(150);
		    	
		    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
		    	AlertDialog dialog = builder.setTitle("关于美队健身v"+MyPackageUtil.getAppVersionName(this))
		    	.setIcon(R.drawable.menu_main_1)
		    	       .setView(aboutView)
		    	       .setNegativeButton("关闭", null)
		    	       .create();
		    	WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
		    	lp.alpha = 0.8f;
		    	dialog.getWindow().setAttributes(lp);	 	
		    	dialog.show();
			    break;
		    }
		    case R.id.menu_main_2:
		    {
		    	View aboutView = LayoutInflater.from(this).inflate(R.layout.about, null);
		    	aboutView.getBackground().setAlpha(150);
		    	
		    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
		    	AlertDialog dialog = builder.setIcon(R.drawable.menu_main_1)
		    			.setSingleChoiceItems(floatBallInfo, floatBallShow, new AlertDialog.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								if (which == floatBallShow)
									return;
								floatBallShow = which;
								if (0 == floatBallShow) /* 关闭 */
								{
									FloatViewManager manager = FloatViewManager.getInstance(MainActivity.this);
									manager.hideFloatBall();
								}
								else
								{
									FloatViewManager manager = FloatViewManager.getInstance(MainActivity.this);
									manager.showFloatBall();
								}
							}
						})
		    			.create();
		    	WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
		    	lp.alpha = 0.8f;
		    	dialog.getWindow().setAttributes(lp);	 	
		    	dialog.show();
			    break;
		    }
		    default:
		    	break;
		}
		
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		/*
		 * FragmentManager fm = getSupportFragmentManager(); FragmentTransaction
		 * ft = fm.beginTransaction();
		 * 
		 * switch (v.getId()) { case R.id.image1: // if (null ==
		 * fm.findFragmentByTag("Fragment1")) // { // //ft.add(R.id.frame, fr1);
		 * // ft.replace(R.id.vp_main, new Fragment1(),"Fragment1"); // } if
		 * (null == frag1) { Toast.makeText(this, "frag1 == null",
		 * Toast.LENGTH_SHORT).show(); } else {
		 * 
		 * ft.replace(R.id.vp_main, frag1); ft.addToBackStack(null).commit(); }
		 * break;
		 * 
		 * case R.id.image2: // if (null == fm.findFragmentByTag("Fragment2"))
		 * // { // //ft2.add(R.id.frame, fr2); // ft.replace(R.id.vp_main, new
		 * Fragment2(), "Fragment2"); // } if (null == frag2) {
		 * Toast.makeText(this, "frag2 == null", Toast.LENGTH_SHORT).show(); }
		 * else { ft.replace(R.id.vp_main, frag2);
		 * ft.addToBackStack(null).commit(); }
		 * 
		 * break;
		 * 
		 * case R.id.image3: // if (null == fm.findFragmentByTag("Fragment3"))
		 * // { // //ft3.add(R.id.frame, fr3); // ft.replace(R.id.vp_main, new
		 * Fragment3(),"Fragment3"); // } if (null == frag3) {
		 * Toast.makeText(this, "frag3 == null", Toast.LENGTH_SHORT).show(); }
		 * else { ft.replace(R.id.vp_main, frag3);
		 * ft.addToBackStack(null).commit(); }
		 * 
		 * break;
		 * 
		 * default: break; }
		 */
		setCurFragment((Integer) v.getTag());	
		//setCurImage((Integer) v.getTag());	
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
		setCurImage(arg0);
	}
	
	public void startActivity(Intent intent) {
		String uri = intent.toString();
	    if (uri.indexOf("mailto") != -1) { // Any way to judge that this is to sead an email
	        List<ResolveInfo> activities = getPackageManager().queryIntentActivities(intent, 0);
	        if (activities == null || activities.size() == 0) {
	            // Do anything you like, or just return
	    		MyCommonUtil.getDialog4Unuse(this, "发送EMAIL功能不可用，请确认");	        	
	        	return;
	        }
	    }
	    else if (uri.indexOf("tel") != -1) { 
	    	List<ResolveInfo> activities = getPackageManager().queryIntentActivities(intent, 0);
	        if (activities == null || activities.size() == 0) {
	            // Do anything you like, or just return
	    		MyCommonUtil.getDialog4Unuse(this, "电话功能不可用，请确认");	  
	        	return;
	        }
	    }
	   
	    super.startActivity(intent);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		Editor editor = shareaPare.edit();
		editor.putInt("floatBallShow", floatBallShow);
		editor.commit();
		super.onDestroy();
    	ImageLoader.getInstance().clearMemoryCache();
    	ImageLoader.getInstance().stop();
    	System.gc();
	}
	
	void startService()
	{
		shareaPare = getSharedPreferences("phone", MODE_PRIVATE);
		floatBallShow = shareaPare.getInt("floatBallShow", 1);
		Intent intent = new Intent(this, FloatBallService.class);
		//intent.putExtra("floatBallShow", floatBallShow);
		startService(intent);
	}
		
}
