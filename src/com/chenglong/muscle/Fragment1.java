package com.chenglong.muscle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class Fragment1 extends Fragment {
	
	private final int introNum = 10;
	private final String[] itemTitle = {"imag", "dinner", "food"};
	private final int[] itemId = {R.id.iv_frag1, R.id.tv_frag1, R.id.tv2_frag1};
	private final int[] imags = {R.drawable.time_0, 
			             R.drawable.time_1, 
			             R.drawable.time_2, 
			             R.drawable.time_3, 
			             R.drawable.time_4,
			             R.drawable.time_5,
			             R.drawable.time_6,
			             R.drawable.time_7};
	private final int[] dinners = {R.string.dinner_0,
                           R.string.dinner_1,
                           R.string.dinner_2,
                           R.string.dinner_3,
                           R.string.dinner_4,
                           R.string.dinner_5,
                           R.string.dinner_6,
                           R.string.dinner_7};
	private final int[] foods = {R.string.food_0,
                         R.string.food_1,
                         R.string.food_2,
                         R.string.food_3,
                         R.string.food_4,
                         R.string.food_5,
                         R.string.food_6,
                         R.string.food_7};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//Toast.makeText(getActivity(), "WELCOME TO FRAGMENT1", Toast.LENGTH_SHORT).show();
		return inflater.inflate(R.layout.frag_1, container, false);
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		ListView lv = (ListView)getActivity().findViewById(R.id.lv_frag1);
		List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>();
		
		for (int tmp=0; tmp<foods.length; tmp++)
		{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("imag", imags[tmp]);
			map.put("dinner", getResources().getString(dinners[tmp]));
			map.put("food", getResources().getString(foods[tmp]));
			dataList.add(map);
		}
		
        SimpleAdapter adapter = new SimpleAdapter(getActivity(), dataList, R.layout.frag_1_listitem, itemTitle, itemId);
        lv.setAdapter(adapter);
        
        
        TextView tvIntro = (TextView)getActivity().findViewById(R.id.tv_intro_frag1);
        String intro = getIntro();
        tvIntro.setText(intro);
        tvIntro.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String intro = getIntro();
				((TextView)v).setText(intro);
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
