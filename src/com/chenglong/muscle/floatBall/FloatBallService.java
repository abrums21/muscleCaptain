package com.chenglong.muscle.floatBall;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;

public class FloatBallService extends Service{

	private SharedPreferences shareaPare;
	private FloatViewManager manager;
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		shareaPare = getSharedPreferences("phone", MODE_PRIVATE);
		manager = FloatViewManager.getInstance(this);
		if (1 == shareaPare.getInt("floatBallShow", 1))
		{
		    manager.showFloatBall();
		}
		
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		//onCreate();
		if (1 == shareaPare.getInt("floatBallShow", 1))
		{
		    manager.showFloatBall();
		}
		super.onDestroy();
	}

//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//    	// TODO Auto-generated method stub
//    	Bundle bundle = intent.getExtras();
//    	floatBallShow = bundle.getInt("floatBallShow", 1);
//    	return super.onStartCommand(intent, flags, startId);
//    }
}
