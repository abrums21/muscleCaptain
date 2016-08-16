package com.chenglong.muscle.body;

import com.chenglong.muscle.R;
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

public class MuscleAdapter extends BaseAdapter {
    private Context context;
    private ViewHolder viewHolder;
    private DisplayImageOptions options;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private String[] name;
    private int[] drawableIds;
    
    private class ViewHolder {
    	public ImageView image;
		public TextView lession;
	}
	
    public MuscleAdapter(Context context, String[] name, int[] drawableIds)
    {
        this.context = context;
        this.name = name;
        this.drawableIds = drawableIds;
        
		/* 设置选项  */
		options = new DisplayImageOptions.Builder()
//				.showImageOnLoading(R.drawable.ic_launcher)//设置图片在下载期间显示的图片  
				.showImageOnFail(R.drawable.ic_launcher)//设置图片加载/解码过程中错误时候显示的图片
				.delayBeforeLoading(0)//设置延时多少时间后开始下载
				.cacheInMemory(false)// 设置下载的资源是否缓存在SD卡中
				.bitmapConfig(Bitmap.Config.RGB_565)
				.considerExifParams(false)// 是否考虑JPEG图像EXIF参数（旋转，翻转）
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT)//设置图片以何种编码方式显示
//				.displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
//				.displayer(new FadeInBitmapDisplayer(1000))//是否图片加载好后渐入的动画时间
				.build();	
    }
   
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return drawableIds.length;
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
			convertView = LayoutInflater.from(context).inflate(R.layout.muscle_listitem, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.image = (ImageView) convertView.findViewById(R.id.muscle_listitem_iv);
			viewHolder.lession = (TextView) convertView.findViewById(R.id.muscle_listitem_tv);
			convertView.setTag(viewHolder);
		}
		else
		{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		
		imageLoader.displayImage("drawable://"+drawableIds[position], viewHolder.image, options);
		//viewHolder.dinner.setText((context.getResources().getStringArray(R.array.dinners))[position]);
		//viewHolder.food.setText((context.getResources().getStringArray(R.array.foods))[position]);
		viewHolder.lession.setText(name[position]);
		
		return convertView;
	}
	
}
