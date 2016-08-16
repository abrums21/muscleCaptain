package com.chenglong.muscle.tool;

import com.chenglong.muscle.R;
import com.chenglong.muscle.R.id;
import com.chenglong.muscle.R.layout;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class DietActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.diet);
		setTitle("美队健身：餐饮食谱");
		
		ListView lv = (ListView)findViewById(R.id.diet_list);
		lv.setAdapter(new DietAdapter(this));
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
    	ImageLoader.getInstance().clearMemoryCache();
    	ImageLoader.getInstance().stop();
    	System.gc();
	}
	
}
