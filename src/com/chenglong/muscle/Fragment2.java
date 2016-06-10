package com.chenglong.muscle;

import java.util.Calendar;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

public class Fragment2 extends Fragment {

	private final int[] trainImages = {R.drawable.sunday,
			                           R.drawable.monday, 
			                           R.drawable.tuesday, 
			                           R.drawable.wednesday,
			                           R.drawable.thursday,
			                           R.drawable.friday,
			                           R.drawable.satday};
	
	private ImageView training;
	private DisplayImageOptions options;
	private ImageLoader imageLoader = ImageLoader.getInstance();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.frag_2, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		Calendar cal = Calendar.getInstance();
		int today = cal.get(Calendar.DAY_OF_WEEK);
		
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
		
		training = (ImageView)getActivity().findViewById(R.id.trainning_frag2);
		Spinner spinner = (Spinner) getActivity().findViewById(R.id.today_frag2);
		ArrayAdapter<String> sa = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.weekLession));
		spinner.setAdapter(sa);
		
		spinner.setSelection(today - 1, true);
		//training.setImageResource(trainImages[today-1]);
		imageLoader.displayImage("drawable://"+trainImages[today-1], training, options);
		
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				//training.setImageResource(trainImages[position]);
				imageLoader.displayImage("drawable://"+trainImages[position], training, options);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});

	}

}
