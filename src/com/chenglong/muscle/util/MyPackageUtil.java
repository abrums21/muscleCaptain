package com.chenglong.muscle.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

public class MyPackageUtil {

	public static int getAppVersionCode(Context context) {
		// TODO Auto-generated method stub
		PackageInfo pi;
		try {
			pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return pi.versionCode;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	public static String getAppVersionName(Context context) {
		// TODO Auto-generated method stub
		PackageInfo pi;
		try {
			pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return pi.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "x.0";
	}
}
