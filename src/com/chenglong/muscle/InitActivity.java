package com.chenglong.muscle;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

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

		new Handler().postDelayed(new Runnable() {

			Intent initIntent;

			@Override
			public void run() {
				// TODO Auto-generated method stub
				int version = getAppVersion();
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
					//MyTipDB.openDatabase(InitActivity.this); /* just 4 test */
				}
				startActivity(initIntent);
				finish();
			}
		}, DELAY_VALUE);
	}

	private int getAppVersion() {
		// TODO Auto-generated method stub
		PackageManager pm = getPackageManager();
		PackageInfo pi;
		try {
			pi = pm.getPackageInfo(getPackageName(), 0);
			return pi.versionCode;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
}
