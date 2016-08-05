package com.chenglong.muscle;

import com.chenglong.muscle.body.MuscleListActivity;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

public class MuscleFrag1 extends Fragment implements OnClickListener, AnimatorListener{
	
	private static TextView iv1;
	private static TextView iv2;
	private static TextView iv3;
	private static TextView iv4;
	private static TextView iv5;
	private static TextView iv6;
	private static ImageView center;
	private static boolean initFlag = true;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//Toast.makeText(getActivity(), "WELCOME TO FRAGMENT1", Toast.LENGTH_SHORT).show();
		return inflater.inflate(R.layout.muscle, container, false);
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		center = (ImageView) getActivity().findViewById(R.id.muscle_center);
		iv1 = (TextView) getActivity().findViewById(R.id.muscle_1);
		iv2 = (TextView) getActivity().findViewById(R.id.muscle_2);
		iv3 = (TextView) getActivity().findViewById(R.id.muscle_3);
		iv4 = (TextView) getActivity().findViewById(R.id.muscle_4);
		iv5 = (TextView) getActivity().findViewById(R.id.muscle_5);
		iv6 = (TextView) getActivity().findViewById(R.id.muscle_6);
		
		center.setOnClickListener(this);
		iv1.setOnClickListener(this);
		iv2.setOnClickListener(this);
		iv3.setOnClickListener(this);
		iv4.setOnClickListener(this);
		iv5.setOnClickListener(this);
		iv6.setOnClickListener(this);
		
		Animation centerAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.body_botton);
		center.startAnimation(centerAnimation);
	}

	void newActivity(int position)
	{
		Intent intent = new Intent(getActivity(), MuscleListActivity.class);
		intent.putExtra("position", position);
		startActivity(intent);
	}
	
	void setVisable(boolean startFlag)
	{
		if ((false == initFlag) &&(true == startFlag))
		{
			iv1.setVisibility(View.VISIBLE);
			iv2.setVisibility(View.VISIBLE);
			iv3.setVisibility(View.VISIBLE);
			iv4.setVisibility(View.VISIBLE);
			iv5.setVisibility(View.VISIBLE);
			iv6.setVisibility(View.VISIBLE);
		}
		else if ((true == initFlag) &&(false == startFlag))
		{
			iv1.setVisibility(View.GONE);
			iv2.setVisibility(View.GONE);
			iv3.setVisibility(View.GONE);
			iv4.setVisibility(View.GONE);
			iv5.setVisibility(View.GONE);
			iv6.setVisibility(View.GONE);
		}
	}

	void startAnimation() {
		ObjectAnimator an0;
		ObjectAnimator an1;
		ObjectAnimator an2;
		ObjectAnimator an3;
		ObjectAnimator an4;
		ObjectAnimator an5;
		ObjectAnimator an6;
		ObjectAnimator an7;
		ObjectAnimator an8;
		ObjectAnimator an9;
		ObjectAnimator an10;

		if (true == initFlag) {
			an0 = ObjectAnimator.ofFloat(center, "alpha", 1F, 0.5F);
			
			an1 = ObjectAnimator.ofFloat(iv1, "translationX", -150F);
			an2 = ObjectAnimator.ofFloat(iv1, "translationY", -400F);
			
			an3 = ObjectAnimator.ofFloat(iv2, "translationX", 150F);
			an4 = ObjectAnimator.ofFloat(iv2, "translationY", -400F);
			
			an5 = ObjectAnimator.ofFloat(iv3, "translationX", -200F);
			
			an6 = ObjectAnimator.ofFloat(iv4, "translationX", 200F);
			
			an7 = ObjectAnimator.ofFloat(iv5, "translationX", -150F);
			an8 = ObjectAnimator.ofFloat(iv5, "translationY", 400F);
			
			an9 = ObjectAnimator.ofFloat(iv6, "translationX", 150F);
			an10 = ObjectAnimator.ofFloat(iv6, "translationY", 400F);
			
			initFlag = false;
		} else {
			an0 = ObjectAnimator.ofFloat(center, "alpha", 0.5F, 1F);
			an1 = ObjectAnimator.ofFloat(iv1, "translationX", 0F);
			an2 = ObjectAnimator.ofFloat(iv1, "translationY", 0F);
			an3 = ObjectAnimator.ofFloat(iv2, "translationX", 0F);
			an4 = ObjectAnimator.ofFloat(iv2, "translationY", 0F);
			an5 = ObjectAnimator.ofFloat(iv3, "translationX", 0F);
			an6 = ObjectAnimator.ofFloat(iv4, "translationX", 0F);
			an7 = ObjectAnimator.ofFloat(iv5, "translationX", 0F);
			an8 = ObjectAnimator.ofFloat(iv5, "translationY", 0F);
			an9 = ObjectAnimator.ofFloat(iv6, "translationX", 0F);
			an10 = ObjectAnimator.ofFloat(iv6, "translationY", 0F);
			initFlag = true;
		}

		AnimatorSet set = new AnimatorSet();
		set.addListener(this);
		set.setInterpolator(new BounceInterpolator());
		set.playTogether(an0, an1, an2, an3, an4, an5, an6, an7, an8, an9, an10);
		set.setDuration(1000);
		set.start();		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.muscle_center:
		{
			center.clearAnimation();
			startAnimation();
			break;
		}
		case R.id.muscle_1:
		{
			newActivity(1);
			break;
		}
		case R.id.muscle_2:
		{
			newActivity(2);
			break;
		}
		case R.id.muscle_3:
		{
			newActivity(3);
			break;
		}
		case R.id.muscle_4:
		{
			newActivity(4);
			break;
		}
		case R.id.muscle_5:
		{
			newActivity(5);
			break;
		}
		case R.id.muscle_6:
		{
			newActivity(6);
			break;
		}
		default:
			break;
		}
	}

	@Override
	public void onAnimationStart(Animator animation) {
		// TODO Auto-generated method stub
		setVisable(true);
	}

	@Override
	public void onAnimationEnd(Animator animation) {
		// TODO Auto-generated method stub
		setVisable(false);
	}

	@Override
	public void onAnimationCancel(Animator animation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAnimationRepeat(Animator animation) {
		// TODO Auto-generated method stub
		
	}
	
	
}
