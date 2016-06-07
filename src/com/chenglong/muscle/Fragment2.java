package com.chenglong.muscle;

import java.util.Calendar;

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

	private final String[] week = {"星期日 课程", "星期一 课程", "星期二 课程", "星期三 课程", "星期四 课程", "星期五 课程", "星期六 课程" };
	private final int[] trainImages = {R.drawable.sunday,
			                           R.drawable.monday, 
			                           R.drawable.tuesday, 
			                           R.drawable.wednesday,
			                           R.drawable.thursday,
			                           R.drawable.friday,
			                           R.drawable.satday};
	
	private ImageView training;
	
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
		
		training = (ImageView)getActivity().findViewById(R.id.trainning_frag2);
		Spinner spinner = (Spinner) getActivity().findViewById(R.id.today_frag2);
		ArrayAdapter<String> sa = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, week);
		spinner.setAdapter(sa);
		spinner.setSelection(today - 1, true);
		training.setImageResource(trainImages[today-1]);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				training.setImageResource(trainImages[position]);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});

	}

}
