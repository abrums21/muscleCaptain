package com.chenglong.muscle.body;

import com.ant.liao.GifView;
import com.ant.liao.GifView.GifImageType;
import com.chenglong.muscle.R;
import com.chenglong.muscle.R.array;
import com.chenglong.muscle.R.id;
import com.chenglong.muscle.R.layout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class MuscleListActivity extends Activity {

	private final static int idNum = 3;
	private String[] name;
	private int[] drawableIds;
	private int[] drawableGIds;
	PopupWindow popup;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.muscle_list);

		Bundle mBundle = getIntent().getExtras();
		int position = mBundle.getInt("position");

		String[] textArray = getResources().getStringArray(R.array.muscle);
		TextView tv = (TextView) findViewById(R.id.muscle_list_tv);
		tv.setText(textArray[position - 1]);

		TypedArray ar = getResources().obtainTypedArray(R.array.body);
		int[] resIds = new int[idNum];
		for (int i = 0; i < idNum; i++)
			resIds[i] = ar.getResourceId((position - 1) * idNum + i, 0);
		ar.recycle();

		/* name */
		name = getResources().getStringArray(resIds[0]);

		/* drawable */
		ar = getResources().obtainTypedArray(resIds[1]);
		drawableIds = new int[ar.length()];
		for (int i = 0; i < ar.length(); i++)
			drawableIds[i] = ar.getResourceId(i, 0);
		ar.recycle();

		/* drawable_g */
		ar = getResources().obtainTypedArray(resIds[2]);
		drawableGIds = new int[ar.length()];
		for (int i = 0; i < ar.length(); i++)
			drawableGIds[i] = ar.getResourceId(i, 0);
		ar.recycle();

		ListView lv = (ListView) findViewById(R.id.musclelist_list);
		lv.setAdapter(new MuscleAdapter(this, name, drawableIds));
		lv.setOnItemClickListener(new ListView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				// AlertDialog.Builder builder = new
				// AlertDialog.Builder(MuscleListActivity.this);
				// //ImageView iv = new ImageView(MuscleListActivity.this);
				// LayoutInflater flater =
				// LayoutInflater.from(MuscleListActivity.this);
				// View v = flater.inflate(R.layout.muscle_listitem_dialog,
				// null);
				//
				// GifView gv =
				// (GifView)v.findViewById(R.id.muscle_listitem_dialog_gv);
				// //GifView gv = new GifView(MuscleListActivity.this);
				// gv.setGifImage(drawableGIds[position]);
				// gv.setGifImageType(GifImageType.COVER);
				// //1iv.setImageResource(drawableGIds[position]);
				// AlertDialog dialog =
				// builder.setTitle(name[position]).setNegativeButton("关闭",
				// null).setView(v).create();
				// //AlertDialog dialog = builder.setNegativeButton("关闭",
				// null).setView(v).create();
				// //AlertDialog dialog = builder.setView(gv).create();
				// WindowManager.LayoutParams params =
				// dialog.getWindow().getAttributes();
				// params.width =
				// android.view.WindowManager.LayoutParams.WRAP_CONTENT;
				// params.height =
				// android.view.WindowManager.LayoutParams.WRAP_CONTENT;
				// params.alpha = 0.7f;
				// dialog.show();
				// dialog.getWindow().setAttributes(params);

				View mView = LayoutInflater.from(MuscleListActivity.this).inflate(R.layout.popup, null);
				popup = new PopupWindow(mView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
				GifView gif = (GifView) mView.findViewById(R.id.popup_gif);
				 TextView tv = (TextView)mView.findViewById(R.id.popup_title);
				 tv.setText(name[position]);
				gif.setGifImage(drawableGIds[position]);
				gif.setGifImageType(GifImageType.COVER);
				popup.showAtLocation(view, Gravity.CENTER, 0, 0);
				setBackgroudAlpha(0.3f);
				// popup.setTouchable(true);
				// popup.setOutsideTouchable(true);
				popup.setFocusable(true);
				popup.setOnDismissListener(new PopupWindow.OnDismissListener() {

					@Override
					public void onDismiss() {
						// TODO Auto-generated method stub
						setBackgroudAlpha(1f);
					}
				});
				
				gif.setOnClickListener(new GifView.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						// finish();
						popup.dismiss();
					}
				});
				
				
				gif.setOnKeyListener(new View.OnKeyListener() {
					
					@Override
					public boolean onKey(View v, int keyCode, KeyEvent event) {
						// TODO Auto-generated method stub
						if (keyCode == KeyEvent.KEYCODE_BACK)
						{
							popup.dismiss();
							return true;
						}
						
						return false;
					}
				});
				
			}
		});
	}

	void setBackgroudAlpha(float value) {
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.alpha = value;
		getWindow().setAttributes(lp);
	}

}
