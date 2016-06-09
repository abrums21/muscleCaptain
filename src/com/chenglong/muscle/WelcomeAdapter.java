package com.chenglong.muscle;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class WelcomeAdapter extends PagerAdapter {

	//private List<View> viewLists;
	private int[] imgId;
	private Context context;
	private DisplayImageOptions options;
	private ImageLoader imageLoader = ImageLoader.getInstance();

	/* 以原始素材作为来源  */
	public WelcomeAdapter(int[] imgId, Context context) {
		// TODO Auto-generated constructor stub
		
		/* 解决内存List暂不使用 */
		//viewLists = Lists;
		this.context = context;
		this.imgId = imgId;
		
		/* 设置选项  */
		options = new DisplayImageOptions.Builder()
//				.showImageOnLoading(R.drawable.ic_launcher)//设置图片在下载期间显示的图片  
				.showImageOnFail(R.drawable.ic_launcher)//设置图片加载/解码过程中错误时候显示的图片
				.delayBeforeLoading(0)//设置延时多少时间后开始下载
				.cacheInMemory(true)// 设置下载的资源是否缓存在SD卡中
				.considerExifParams(false)// 是否考虑JPEG图像EXIF参数（旋转，翻转）
				.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)//设置图片以何种编码方式显示
//				.displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
//				.displayer(new FadeInBitmapDisplayer(1000))//是否图片加载好后渐入的动画时间
				.build();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return imgId.length;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		 //super.destroyItem(container, position, object);
		container.removeView((View)object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		 //return super.instantiateItem(container, position);
		ImageView iv = new ImageView(context);
		imageLoader.displayImage("drawable://"+imgId[position], iv, options);
		
		container.addView(iv, 0);
		return iv;
	}

}
