package com.chenglong.muscle;

import com.chenglong.muscle.R;
import com.chenglong.muscle.R.drawable;
import com.chenglong.muscle.R.id;
import com.chenglong.muscle.R.layout;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Window;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class InitActivity extends Activity {

	private static final int DELAY_VALUE = 3000;   /* 3s延迟  */
	private SharedPreferences shareaPare;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.init);
		shareaPare = getSharedPreferences("phone", MODE_PRIVATE);
		
		ImageView img = (ImageView)findViewById(R.id.init_img);
		img.setImageResource(R.drawable.init);
		img.startAnimation(new MyAnimation());
		
		MyTipDB.openDatabase(this);

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				int version = getAppVersion();
				
				Intent initIntent;				
				if (shareaPare.getBoolean("firstStart", true) || (version != shareaPare.getInt("curVersion", 0))) {
					/* 首次启动 */
					Editor editor = shareaPare.edit();
					editor.putBoolean("firstStart", false);
					editor.putInt("curVersion", version);
					editor.commit();
					initIntent = new Intent(InitActivity.this, WelcomeActivity.class);
					/* 临时添加  */
					//MyTipDB.stub_tipsDbStore();
					MyTipDB.openDatabase(InitActivity.this);
				} else {
					/* 非首次启动 */
					initIntent = new Intent(InitActivity.this, MainActivity.class);
					//initIntent = new Intent(InitActivity.this, WelcomeActivity.class);  /* just 4 test */
				}
				
				InitActivity.this.startActivity(initIntent);
				//overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				
		    	ImageLoader.getInstance().clearMemoryCache();
		    	ImageLoader.getInstance().stop();
				InitActivity.this.finish();
			}
		}, DELAY_VALUE);
	}

	private int getAppVersion() {
		// TODO Auto-generated method stub
		//PackageManager pm = getPackageManager();
		PackageInfo pi;
		try {
			pi = getPackageManager().getPackageInfo(getPackageName(), 0);
			return pi.versionCode;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		//return super.onKeyDown(keyCode, event);
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
	
}
