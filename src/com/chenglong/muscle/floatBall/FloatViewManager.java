package com.chenglong.muscle.floatBall;

import java.lang.reflect.Field;

import com.chenglong.muscle.util.MyScreenUtil;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.provider.Settings;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

public class FloatViewManager implements OnTouchListener {

	private Context context;
	private FloatBallView view;
	private WindowManager wm;
	private static FloatViewManager instance = null;
	private float startX;
	private float startY;
	private LayoutParams lp;
	private LayoutParams lpBar;
	private float x0;
	private float y0;
	private final float MIN_MOVE = 5;
	private float minMove;
	private FloatBarView bar;

	private FloatViewManager(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		view = new FloatBallView(context);
		wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		view.setOnTouchListener(this);
		view.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				wm.removeView(view);
				showFloatBar();
				bar.startAniation();
			}
		});
		minMove = MyScreenUtil.getScreenDensity(context) * MIN_MOVE;
		bar = new FloatBarView(context);
	}

	public void showFloatBall() {
		// TODO Auto-generated method stub
		if (lp == null) {
			lp = new LayoutParams();
			lp.width = view.getWidth();
			lp.height = view.getHeight();
			lp.x = 0;
			lp.y = wm.getDefaultDisplay().getHeight() / 2;
			lp.gravity = Gravity.TOP | Gravity.LEFT;
			lp.flags = LayoutParams.FLAG_NOT_FOCUSABLE | LayoutParams.FLAG_NOT_TOUCH_MODAL;
			lp.type = LayoutParams.TYPE_PHONE;
			//lp.type = LayoutParams.TYPE_SYSTEM_ALERT;
			lp.format = PixelFormat.RGBA_8888;
		}
		if ((Build.VERSION.SDK_INT >= 23) && (!Settings.canDrawOverlays(context)))
		{
		    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
			//Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);
		}
		else
		{
			try {
				wm.addView(view, lp);
			} catch (Exception e) {
				return;
			}
		    
		}
	}
	
	public void hideFloatBar() {
		// TODO Auto-generated method stub
		try {
			wm.removeView(bar);
		} catch (Exception e) {
			return;
		}
		
	}
	
	public void hideFloatBall() {
		// TODO Auto-generated method stub
		try {
			wm.removeView(view);
		} catch (Exception e) {
			return;
		}
	}
	
	public void showFloatBar() {
		// TODO Auto-generated method stub
		if (lpBar == null) {
			lpBar = new LayoutParams();
			lpBar.width = (int)getScreenWidth();
			lpBar.height = (int)getScreenHeight() - getStatusBarHeight();
			lpBar.x = 0;
			lpBar.y = 0;
			lpBar.gravity = Gravity.BOTTOM | Gravity.LEFT;
			lpBar.flags = LayoutParams.FLAG_NOT_FOCUSABLE | LayoutParams.FLAG_NOT_TOUCH_MODAL;
			lpBar.type = LayoutParams.TYPE_PHONE;
			//lp.type = LayoutParams.TYPE_SYSTEM_ALERT;
			lpBar.format = PixelFormat.RGBA_8888;
		}
		
		if ((Build.VERSION.SDK_INT >= 23) && (!Settings.canDrawOverlays(context)) )
		{
			Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);
		}
		else
		{
			try {
				wm.addView(bar, lpBar);
			} catch (Exception e) {
				return;
			}
		    
		}
	}

	public static FloatViewManager getInstance(Context context) {
		if (null == instance) {
			synchronized (FloatViewManager.class) {
				if (null == instance) {
					instance = new FloatViewManager(context);
				}
			}
		}
		return instance;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			startX = event.getRawX();
			startY = event.getRawY();
			x0 = startX;
			y0 = startY;
			break;
		case MotionEvent.ACTION_MOVE:
			float x = event.getRawX();
			float y = event.getRawY();
			float dx = x - startX;
			float dy = y - startY;
			lp.x += dx;
			lp.y += dy;
			startX = x;
			startY = y;
			view.setDragState(true);
			wm.updateViewLayout(view, lp);
			break;
		case MotionEvent.ACTION_UP:
			float x1 = event.getRawX();
			float y1 = event.getRawY();
			if (event.getRawX() <= getScreenWidth() / 2) {
				lp.x = 0;
			} else {
				lp.x = (int)getScreenWidth() - view.getWidth();
			}
			view.setDragState(false);
			wm.updateViewLayout(view, lp);
			if ((Math.abs(x1 - x0) < minMove) || (Math.abs(y1 - y0) < minMove))
			{
				return false;
			}
			else {
				return true;
			}
		default:
			break;
		}

		return false;

	}
	
	private float getScreenWidth()
	{
		return wm.getDefaultDisplay().getWidth();
	}
	
	private float getScreenHeight()
	{
		return wm.getDefaultDisplay().getHeight();
	}
	
	private int getStatusBarHeight()
	{
		try
		{
		Class<?> c = Class.forName("com.android.internal.R$dimen");
		Object o = c.newInstance();
		Field field = c.getField("status_bar_height");
		int x = (Integer)field.get(o);
		return context.getResources().getDimensionPixelSize(x);
		}
		catch(Exception e)
		{
			return 38;
		}
	}
	
	
}
