package com.chenglong.muscle;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class Fragment1 extends Fragment {
	
	private final int introNum = 10;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//Toast.makeText(getActivity(), "WELCOME TO FRAGMENT1", Toast.LENGTH_SHORT).show();
		return inflater.inflate(R.layout.frag_1, container, false);
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		((ListView)getActivity().findViewById(R.id.lv_frag1)).setAdapter(new DietAdapter(getActivity()));
        
        TextView tvIntro = (TextView)getActivity().findViewById(R.id.tv_intro_frag1);
        tvIntro.setText(getIntro());
        tvIntro.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((TextView)v).setText(getIntro());
			}
		});
        
        /* 目前不涉及点击事件  */
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
