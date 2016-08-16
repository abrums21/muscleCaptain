package com.chenglong.muscle;

import android.graphics.Matrix;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class MyAnimation extends Animation{
	
	private int mWidth;
	private int mHeight;
	
	@Override
	public void initialize(int width, int height, int parentWidth, int parentHeight) {
		// TODO Auto-generated method stub
		super.initialize(width, height, parentWidth, parentHeight);
		setDuration(1500);
		setFillAfter(true);
		setInterpolator(new AccelerateDecelerateInterpolator());
		mWidth = width/2;
		mHeight = height/2;
	}
	
	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		// TODO Auto-generated method stub
		//super.applyTransformation(interpolatedTime, t);
		Matrix matrix = t.getMatrix();
		matrix.preScale(1, interpolatedTime , mWidth, mHeight);
	}
	
}