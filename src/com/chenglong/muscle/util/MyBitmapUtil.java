package com.chenglong.muscle.util;

import com.chenglong.muscle.R;
import com.chenglong.muscle.R.drawable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.widget.Toast;

public class MyBitmapUtil {
	private final static int MAX_PIXELS = 120 * 1024 * 2 * 8;   /* 80k */

	public static Bitmap decodeBitmapByPath(Context context, String path) {
		Bitmap bitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();

		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);/* 这里返回的bmp是null */
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		DisplayMetrics metrics = MyScreenUtil.getScreenMetrics(context);
		options.inSampleSize = computeSampleSize(options, Math.min(metrics.widthPixels, metrics.heightPixels), MAX_PIXELS);
		options.inJustDecodeBounds = false;
		try {
			bitmap = BitmapFactory.decodeFile(path, options);
		} catch (OutOfMemoryError e) {
			;
		} finally {
			if (bitmap == null) {
				bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher, options);
			}
		}

		return bitmap;
	}

	public static Bitmap decodeBitmapByRes(Context context, int resId) {
		Bitmap bitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();

		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(context.getResources(), resId, options);
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		DisplayMetrics metrics = MyScreenUtil.getScreenMetrics(context);
		options.inSampleSize = computeSampleSize(options, Math.min(metrics.widthPixels, metrics.heightPixels), MAX_PIXELS);
		options.inJustDecodeBounds = false;
		try {
			bitmap = BitmapFactory.decodeResource(context.getResources(), resId, options);
		} catch (OutOfMemoryError e) {
			;
		} finally {
			if (bitmap == null) {
				bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher, options);
			}
		}
		return bitmap;
	}
	
	private static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
	    int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);
	    int roundedSize;
	    if (initialSize <= 8) {
	        roundedSize = 1;
	        while (roundedSize < initialSize) {
	            roundedSize <<= 1;
	        }
	    } else {
	        roundedSize = (initialSize + 7) / 8 * 8;
	    }
	    return roundedSize;
	}

	private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
	    double w = options.outWidth;
	    double h = options.outHeight;
	    int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
	    int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math.floor(w / minSideLength), Math.floor(h / minSideLength));
	    if (upperBound < lowerBound) {
	        // return the larger one when there is no overlapping zone.
	        return lowerBound;
	    }
	    if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
	        return 1;
	    } else if (minSideLength == -1) {
	        return lowerBound;
	    } else {
	        return upperBound;
	    }
	} 
}
