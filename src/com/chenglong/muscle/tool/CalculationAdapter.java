package com.chenglong.muscle.tool;

import com.chenglong.muscle.R;
import com.chenglong.muscle.R.array;
import com.chenglong.muscle.R.drawable;
import com.chenglong.muscle.R.id;
import com.chenglong.muscle.R.layout;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CalculationAdapter extends BaseAdapter {

	private String[] items;
    private Context context;
    private ViewHolder viewHolder;
    
    private class ViewHolder {
		public TextView itemName;
	}
	
    public CalculationAdapter(Context context, String[] items)
    {
        this.context = context;
        this.items = items;
    }
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (null == convertView)
		{
			convertView = LayoutInflater.from(context).inflate(R.layout.calculation_listitem, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.itemName = (TextView) convertView.findViewById(R.id.calculation_listitem_name);
			convertView.setTag(viewHolder);
		}
		else
		{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		
		viewHolder.itemName.setText(items[position]);
		
		return convertView;
	}
	
}
