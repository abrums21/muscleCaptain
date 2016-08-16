package com.chenglong.muscle.util;

import android.content.Context;
import android.hardware.Camera;

public class MyPermissionUtil {

	@SuppressWarnings("deprecation")
	public static boolean isCameraGranted(Context context) {
		/* 不通用 仅限6.0以下版本使用 */
		//return (context.checkSelfPermission(permission.CAMERA) == PackageManager.PERMISSION_GRANTED); 
	    
		/* 较为通用 */
		try{
	    	Camera.open(0).release();
	    }
	    catch (Exception e)
	    {
	    	return false;
	    }
	    return true;
	}

}
