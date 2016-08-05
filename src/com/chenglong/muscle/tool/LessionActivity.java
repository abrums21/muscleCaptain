package com.chenglong.muscle.tool;

import java.util.Calendar;

import com.chenglong.muscle.R;
import com.chenglong.muscle.R.array;
import com.chenglong.muscle.R.drawable;
import com.chenglong.muscle.R.id;
import com.chenglong.muscle.R.layout;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

public class LessionActivity extends Activity {

	private final int[] trainImages = { R.drawable.sunday, R.drawable.monday, R.drawable.tuesday, R.drawable.wednesday,
			R.drawable.thursday, R.drawable.friday, R.drawable.satday };

	private ImageView training;
	private DisplayImageOptions options;
	private ImageLoader imageLoader = ImageLoader.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lession);
		setTitle("美队健身：训练课程");

		Calendar cal = Calendar.getInstance();
		int today = cal.get(Calendar.DAY_OF_WEEK);

		options = new DisplayImageOptions.Builder()
				// .showImageOnLoading(R.drawable.ic_launcher)//设置图片在下载期间显示的图片
				.showImageOnFail(R.drawable.ic_launcher)// 设置图片加载/解码过程中错误时候显示的图片
				.delayBeforeLoading(0)// 设置延时多少时间后开始下载
				.cacheInMemory(false)// 设置下载的资源是否缓存在SD卡中
				.bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(false)// 是否考虑JPEG图像EXIF参数（旋转，翻转）
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT)// 设置图片以何种编码方式显示
				// .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
				// .displayer(new FadeInBitmapDisplayer(1000))//是否图片加载好后渐入的动画时间
				.build();

		training = (ImageView)findViewById(R.id.lession_training);
		Spinner spinner = (Spinner)findViewById(R.id.lession_spinner);
		ArrayAdapter<String> sa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.weekLession));
		spinner.setAdapter(sa);

		spinner.setSelection(today - 1, true);
		// training.setImageResource(trainImages[today-1]);
		imageLoader.displayImage("drawable://" + trainImages[today - 1], training, options);

		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				imageLoader.displayImage("drawable://" + trainImages[position], training, options);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}
