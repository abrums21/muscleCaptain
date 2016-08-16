package com.chenglong.muscle.util;

import com.chenglong.muscle.R;

import android.app.AlertDialog;
import android.content.Context;
import android.view.WindowManager;

public class MyCommonUtil {

	public  static void getDialog4Unuse(Context context, String alert)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		AlertDialog dialog = builder.setTitle("美队提示").setIcon(R.drawable.menu_main_1).setMessage(alert)
				.setNegativeButton("关闭", null).create();
		WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
		lp.alpha = 0.8f;
		dialog.getWindow().setAttributes(lp);
		dialog.show();
	}
}
