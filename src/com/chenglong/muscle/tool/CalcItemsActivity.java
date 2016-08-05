package com.chenglong.muscle.tool;

import java.io.BufferedWriter;

import com.chenglong.muscle.R;

import android.R.integer;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

public class CalcItemsActivity extends Activity{

	private int level = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	
	    Bundle mBundle = getIntent().getExtras();
	    int position = mBundle.getInt("position");
	    
	    int resId;
	    String title;
	    switch(position)
	    {
	        case 0:
	        {
	        	item_0_calculation();
	        	break;
	        }
	        case 1:
	        {
	        	item_1_calculation();
	        	break;
	        }
	        case 2:
	        {
	        	item_2_calculation();
	        	break;
	        }
	        case 3:
	        {
	        	item_3_calculation();
	        	break;
	        }
	        default:{
	        	break;
	        }
	    }	    
	}
	
	private void item_3_calculation() {
		// TODO Auto-generated method stub
		setContentView(R.layout.calc_item_3);
	    setTitle("美队健身：基础代谢率计算");
		
		Spinner work = (Spinner)findViewById(R.id.calc_item3_level);
		work.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				level = position;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				level = 0;
			}
		});
		
		Button button = (Button)findViewById(R.id.calc_item3_button);
		button.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditText heiEd = (EditText)findViewById(R.id.calc_item3_height);
				EditText weiEd = (EditText)findViewById(R.id.calc_item3_weight);
				RadioGroup rg = (RadioGroup)findViewById(R.id.calc_item3_radio);
				EditText ageEt = (EditText)findViewById(R.id.calc_item3_age);
				RadioButton radio = (RadioButton)findViewById(rg.getCheckedRadioButtonId());
				TextView tv = (TextView)findViewById(R.id.calc_item3_result);
				
				String sex = radio.getText().toString();
				float height = Float.parseFloat(heiEd.getText().toString());
				float weight = Float.parseFloat(weiEd.getText().toString());
				int age = Integer.parseInt(ageEt.getText().toString());
				float rate[] = {1.15f, 1.3f, 1.4f, 1.6f, 1.8f};
				
				int result;
				if(sex.equals("女"))
				{
					result = (int)(rate[level] * (661 + 9.6*weight + 1.72*height - 4.7*age));
				}
				else {
					result = (int)(rate[level] * (67 + 13.73*weight + 5*height - 6.9*age));
				}
				tv.setText("基础代谢率为："+result+"千焦");	
			}
		});
	}

	private void item_2_calculation() {
		// TODO Auto-generated method stub
		setContentView(R.layout.calc_item_2);
	    setTitle("美队健身：最大心率计算");
	    
	    Button button = (Button)findViewById(R.id.calc_item2_button);
	    button.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditText ageEt = (EditText)findViewById(R.id.calc_item2_age);
				TextView tv = (TextView)findViewById(R.id.calc_item2_result);
				
				int age = Integer.parseInt(ageEt.getText().toString());
				
				int max = 220 - age;
				int rangeMin = (int)((float)max * 0.6f);
				int rangeMax = (int)((float)max * 0.8f);
				
				tv.setText("最大心率: "+max+"次/分钟\n靶心率："+rangeMin+"~"+rangeMax+"次/分钟");
			}
		});
	}

	private void item_1_calculation() {
		// TODO Auto-generated method stub
		setContentView(R.layout.calc_item_1);
	    setTitle("美队健身：标准三围计算");
	    
	    Button button = (Button)findViewById(R.id.calc_item1_button);
	    button.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditText heiEd = (EditText)findViewById(R.id.calc_item1_height);
				RadioGroup rg = (RadioGroup)findViewById(R.id.calc_item1_radio);
				RadioButton radio = (RadioButton)findViewById(rg.getCheckedRadioButtonId());
				TextView tv = (TextView)findViewById(R.id.calc_item1_result);
				
				String sex = radio.getText().toString();
				float height = Float.parseFloat(heiEd.getText().toString());
			    float arg1;
			    float arg2;
			    float arg3;
				
				if(sex.equals("女"))
				{
					arg1 = height * 0.51f;
					arg2 = height * 0.34f;
					arg3 = height * 0.542f;
				}
				else {
					arg1 = height * 0.61f;
					arg2 = height * 0.42f;
					arg3 = height * 0.64f;
				}
				tv.setText("标准三维：\n性别: "+sex +"\n胸围（cm）："+arg1+"\n腰围（cm）："+arg2+"\n臀围（cm）："+arg3);
			}
		});
	}

	private void item_0_calculation() {
		// TODO Auto-generated method stub
		setContentView(R.layout.calc_item_0);
	    setTitle("美队健身：BMI计算");
	    
	    Button button = (Button)findViewById(R.id.calc_item0_button);
	    button.setOnClickListener(new Button.OnClickListener() {
	    	
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditText heiEd = (EditText)findViewById(R.id.calc_item0_height);
				EditText weiEd = (EditText)findViewById(R.id.calc_item0_weight);
				TextView tv = (TextView)findViewById(R.id.calc_item0_result);
				
				float height = Float.parseFloat(heiEd.getText().toString()) / 100;
				float weight = Float.parseFloat(weiEd.getText().toString());
				
				if (0f == height)
				{
					tv.setText("请输入正确的值");
				}
				else {
					float result = calcBMI(height, weight);
					String type = getBMIType(result);
					tv.setText("BMI指数："+result+"\n国标："+type);
				}

			}

		});
	}
	
	private float calcBMI(float height, float weight) {
		// TODO Auto-generated method stub
		return weight / (height * height);
	}
	
	private String getBMIType(float bmi) {
		// TODO Auto-generated method stub
		
		String result;
		
		if(bmi < 18.5f)
		{
		  result = "偏瘦";
		}
		else if (bmi < 24)
		{
			result = "正常";
		}
		else if (bmi < 28)
		{
			result = "偏胖";
		}
		else {
			result = "肥胖";
		}
		
		return result;
	}
}
