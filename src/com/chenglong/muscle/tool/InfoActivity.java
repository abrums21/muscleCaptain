package com.chenglong.muscle.tool;

import com.chenglong.muscle.MyTipDB;
import com.chenglong.muscle.R;
import com.chenglong.muscle.R.id;
import com.chenglong.muscle.R.layout;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class InfoActivity extends Activity{

	private final int introNum = 10;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.info);
		setTitle("美队健身：健身贴士");
		
        TextView tvIntro = (TextView)findViewById(R.id.info_tv);
        tvIntro.setText(getIntro());
        tvIntro.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((TextView)v).setText(getIntro());
			}
		});
	}
	
	protected String getIntro() {
		// TODO Auto-generated method stub
        int rand = (int)(Math.random() * introNum + 1);
        MyTipDB db = new MyTipDB();
        String intro = db.query(rand);
        if (intro.isEmpty())
        {
        	intro = "不要在露肉的季节选择逃避 ，在奋斗的年龄选择安逸";
        }
        db.close();
        
		return "【健康小贴士】"+intro+"【点击文字可浏览下一条】";
	}
}
