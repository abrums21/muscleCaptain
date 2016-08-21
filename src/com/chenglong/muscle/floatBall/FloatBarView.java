package com.chenglong.muscle.floatBall;


import com.chenglong.muscle.R;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FloatBarView extends LinearLayout implements OnClickListener{

	private TranslateAnimation animatinIn;
	private TranslateAnimation animatinOut;
	private int counter = 0;
	private TextView text;
	private View root;
	private Context context;
	private LinearLayout ll;
	
	public FloatBarView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		root = View.inflate(getContext(), R.layout.floatball_bar, null);
		ll = (LinearLayout) root.findViewById(R.id.floatball_bar_ll);
		animatinIn = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0);
		animatinIn.setDuration(500);
		animatinIn.setFillAfter(true);
		animatinOut = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1.0f);
		animatinOut.setDuration(500);
		animatinOut.setFillAfter(true);
		animatinOut.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				FloatViewManager manager = FloatViewManager.getInstance(getContext());
				manager.hideFloatBar();
				manager.showFloatBall();
			}
		});
		//an.setFillAfter(true);
		//an.setFillBefore(true);
		//ll.setAnimation(an);
		addView(root);
		
		TextView clearButton = (TextView) root.findViewById(R.id.floatball_bar_tv2);
		text = (TextView) root.findViewById(R.id.floatball_bar_tv3);
		clearButton.setOnClickListener(this);
		text.setOnClickListener(this);
		
		root.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				hideView();
				return false;
			}
		});
	}
	
	public void startAniation()
	{
//		an = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0);
//		an.setDuration(500);
//		an.setRepeatCount(2);
		//an.setFillAfter(true);
		//an.setFillBefore(true);
		ll.startAnimation(animatinIn);
		//an.start();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.floatball_bar_tv2:
			counter = 0;
			setCounter();
			break;
        case R.id.floatball_bar_tv3:
        	counter++;
        	setCounter();
			break;
        case R.id.floatball_bar_img:
        	hideView();
			break;
		default:
			break;
		}
	}
	
	private void setCounter()
	{
		text.setText(""+counter);
	}
	
	private void hideView()
	{
		ll.startAnimation(animatinOut);
//		FloatViewManager manager = FloatViewManager.getInstance(context);
//		manager.hideFloatBar();
//		manager.showFloatBall();
		
	}

}
