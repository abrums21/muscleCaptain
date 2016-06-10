//package com.chenglong.muscle;
//
//import com.chenglong.muscle.DietAdapter.ViewHolder;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//public class LessionAdapter extends BaseAdapter{
//
//	private Context context;
//	
//	public LessionAdapter(Context context)
//	{
//		this.context = context;
//	}
//	
//	@Override
//	public int getCount() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public Object getItem(int position) {
//		// TODO Auto-generated method stub
//		return position;
//	}
//
//	@Override
//	public long getItemId(int position) {
//		// TODO Auto-generated method stub
//		return position;
//	}
//
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		// TODO Auto-generated method stub
//		if (null == convertView)
//		{
//			convertView = LayoutInflater.from(context).inflate(R.layout.frag_1_listitem, parent, false);
//			viewHolder = new ViewHolder();
//			viewHolder.image = (ImageView) convertView.findViewById(R.id.iv_frag1);
//			viewHolder.dinner = (TextView) convertView.findViewById(R.id.tv_frag1);
//			viewHolder.food = (TextView) convertView.findViewById(R.id.tv2_frag1);
//			convertView.setTag(viewHolder);
//		}
//		else
//		{
//			viewHolder = (ViewHolder)convertView.getTag();
//		}
//		
//		imageLoader.displayImage("drawable://"+imgs[position], viewHolder.image, options);
//		//viewHolder.dinner.setText((context.getResources().getStringArray(R.array.dinners))[position]);
//		//viewHolder.food.setText((context.getResources().getStringArray(R.array.foods))[position]);
//		viewHolder.dinner.setText(dinners[position]);
//		viewHolder.food.setText(foods[position]);
//		
//		return convertView;
//	}
//	
//	ArrayAdapter<String> sa = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.weekLession));
//
//}
