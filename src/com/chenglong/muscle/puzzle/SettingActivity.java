package com.chenglong.muscle.puzzle;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.chenglong.muscle.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

public class SettingActivity extends Activity {

	private final static int RESULT_IMAGE = 100;
	private final static int RESULT_CAMERA = 200;
	private String TEMP_IMAGE_PATH;
	private String TEMP_IMAGE;
	private final static String IMAGE_PATH = "ImagePath";
	private final static String GAME_TYPE = "gameType";
	private final static String SELLECTED_IMAGE_ID = "SellectedImageID";
	private final static String[] sellectItem = { "相册中选取", "拍照" };
	private final static String[] gameMode = { "简单模式：2 X 2", "普通模式：3 X 3", "困难模式：4 X 4" };
	private final static int colums = 3;
	private static int[] imageId;
	private int gameType = 3;
    private long firstTime = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.puzzle_setting);
		setTitle("美队健身：拼图游戏设置");
		
		Toast.makeText(this, "请选择模式和图片开始游戏", Toast.LENGTH_SHORT).show();
		
		TEMP_IMAGE_PATH = this.getExternalCacheDir().getAbsolutePath() + "/puzzle/";
		
		if(!createDir(TEMP_IMAGE_PATH))
		{
			return;
		}
		TEMP_IMAGE = TEMP_IMAGE_PATH + "tmp.png";
		
		initGridView();
		initSpinner();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void showCustormDialog() {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setItems(sellectItem, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				switch (which) {
				case 0: {
					/* 从相册中打开 */
					Intent intent = new Intent(Intent.ACTION_PICK);
					intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
					startActivityForResult(intent, RESULT_IMAGE);
					break;
				}
				case 1: {
					/* 拍照 */
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(TEMP_IMAGE)));
					startActivityForResult(intent, RESULT_CAMERA);
					break;
				}
				default: {
					break;
				}
				}
			}
		});
		builder.create().show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (RESULT_OK == resultCode) {
			switch (requestCode) {
			case RESULT_IMAGE: {
				if (data != null) {
					Cursor mCursor = this.getContentResolver().query(data.getData(), null, null, null, null);
					mCursor.moveToFirst();
					String imagePath = mCursor.getString(mCursor.getColumnIndex("_data"));

					Toast.makeText(this, "正在生成拼图，请等待", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(SettingActivity.this, GameActivity.class);
					intent.putExtra(IMAGE_PATH, imagePath);
					intent.putExtra(GAME_TYPE, gameType);
					startActivity(intent);
				}
				break;
			}
			case RESULT_CAMERA: {
				Toast.makeText(this, "正在生成拼图，请等待", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(SettingActivity.this, GameActivity.class);
				intent.putExtra(IMAGE_PATH, TEMP_IMAGE);
				intent.putExtra(GAME_TYPE, gameType);
				startActivity(intent);
				break;
			}
			default: {
				break;
			}
			}
		}
	}
	
//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		// TODO Auto-generated method stub
//		if (keyCode == KeyEvent.KEYCODE_BACK) {
//			long secondTime = System.currentTimeMillis();
//			if (secondTime - firstTime > 2000) {
//				Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
//				firstTime = secondTime;
//			} else { 
//				SettingActivity.this.finish();
//			}
//			return true;
//		}
//		return super.onKeyDown(keyCode, event);
//	}
	
	private void initGridView() {
		// TODO Auto-generated method stub
		
		TypedArray ar = getResources().obtainTypedArray(R.array.puzzle_image);
		imageId = new int[ar.length()];
		for (int i = 0; i < ar.length(); i++)
		{
			imageId[i] = ar.getResourceId(i, 0);
		}
		ar.recycle();
		
		GridView mGridView = (GridView) findViewById(R.id.setting_grid);
		mGridView.setOnItemClickListener(new GridView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				if (imageId.length - 1 == position) {
					showCustormDialog();
				} else {
					Toast.makeText(SettingActivity.this, "正在生成拼图，请等待", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(SettingActivity.this, GameActivity.class);
					intent.putExtra(SELLECTED_IMAGE_ID, imageId[position]);
					intent.putExtra(GAME_TYPE, gameType);
					startActivity(intent);
				}
			}
		});
		
		List<Bitmap> list = new ArrayList<Bitmap>();
		Bitmap bp;
		for (int id : imageId) {
			bp = BitmapFactory.decodeResource(getResources(), id);
			list.add(bp);
		}
		mGridView.setNumColumns(colums);
		mGridView.setAdapter(new MyGridAdapter(this, colums, list));
	}
	
	private void initSpinner()
	{
		Spinner mode = (Spinner) findViewById(R.id.setting_spinner);
		ArrayAdapter<String> sa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,
				gameMode);
		mode.setAdapter(sa);
		mode.setSelection(1, true);
		mode.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				gameType = position + 2;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
	}
	
	public boolean createDir(String path) {
		File dir = new File(path);
		if (!dir.exists()) {
			if (dir.mkdir()) {
				return true;
			} else {
				return false;
			}
		}

		return true;
	}
}
