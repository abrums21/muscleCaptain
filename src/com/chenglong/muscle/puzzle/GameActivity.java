package com.chenglong.muscle.puzzle;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.chenglong.muscle.MainActivity;
import com.chenglong.muscle.R;
import com.chenglong.muscle.WelcomeActivity;
import com.chenglong.muscle.util.MyBitmapUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RadioGroup.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends Activity implements android.view.View.OnClickListener {

	private final static String IMAGE_PATH = "ImagePath";
	private final static String GAME_TYPE = "gameType";
	private final static String SELLECTED_IMAGE_ID = "SellectedImageID";
	private int colums = 0;
	private Bitmap myBitmap;

	private int stepNum = 0;
	private int secondNum = 0;
	private TextView step;
	private TextView second;
	private Handler mHandler;
	private Timer mTimer;
	private final static int TIMEOUT = 1;
	private final static int START_MILLIS = 1000;
	private final static int INTERVAL_MILLIS = 1000;

	private MyGridAdapter myGridAdapter;
	private List<Bitmap> list = new ArrayList<Bitmap>();

	private MyGame myGame;
	private ImageView iv;
	// private MyGridView myGridView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.puzzle_game);
		setTitle("美队健身：拼图游戏");

		// int top =
		// getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();

		Intent intent = getIntent();
		Bundle mBundle = intent.getExtras();
		String mImagePath = mBundle.getString(IMAGE_PATH, "");
		int mSellectedID = mBundle.getInt(SELLECTED_IMAGE_ID, -1);
		colums = mBundle.getInt(GAME_TYPE, -1);

		if (!mImagePath.isEmpty()) {
			myBitmap = MyBitmapUtil.decodeBitmapByPath(this, mImagePath);
			// myBitmap = BitmapFactory.decodeFile(mImagePath,options);
		} else if (mSellectedID != -1) {
			myBitmap = MyBitmapUtil.decodeBitmapByRes(this, mSellectedID);
			// myBitmap = BitmapFactory.decodeResource(getResources(),
			// mSellectedID, options);
		} else {
			Toast.makeText(this, "找不到选择的照片", Toast.LENGTH_SHORT).show();
			return;
		}

		if (-1 == colums) {
			Toast.makeText(this, "难度信息丢失", Toast.LENGTH_SHORT).show();
			colums = 3;
		}

		step = (TextView) findViewById(R.id.game_steps);
		second = (TextView) findViewById(R.id.game_seconds);
		Button button1 = (Button) findViewById(R.id.game_button1);
		Button button2 = (Button) findViewById(R.id.game_button2);
		Button button3 = (Button) findViewById(R.id.game_button3);
		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
		button3.setOnClickListener(this);
		clearStep();
		clearSecond();

		initMyGridView();
		initShowImage();
		initHandlerProc();
		initTimerProc();
	}

	private void initShowImage() {

		RelativeLayout ll = (RelativeLayout) findViewById(R.id.game_layout);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		// ImageView iv = new ImageView(this);
		params.addRule(RelativeLayout.CENTER_IN_PARENT);
		iv = new ImageView(this);
		iv.setImageBitmap(myGame.getShowBitmap());
		iv.setLayoutParams(params);
		iv.setVisibility(View.GONE);
		iv.setScaleType(ScaleType.FIT_XY);
		iv.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				iv.setVisibility(View.GONE);
			}
		});
		iv.setOnKeyListener(new View.OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				iv.setVisibility(View.GONE);
				return false;
			}
		});
		ll.addView(iv);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.game_button1: {
			if (View.GONE == iv.getVisibility()) {
				Toast.makeText(this, "笨，还要看原图", Toast.LENGTH_SHORT).show();
				iv.setVisibility(View.VISIBLE);
			} else {
				iv.setVisibility(View.GONE);
			}
			break;
		}
		case R.id.game_button2: {
			freshMyGridView();
			clearStep();
			clearSecond();
			break;
		}
		case R.id.game_button3: {
			goBack();
			break;
		}
		default: {
			break;
		}
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mTimer.cancel();
		recycleBitmap();
		myGame.destroyPuzzle();
		System.gc();
	}

	private void initHandlerProc() {
		// TODO Auto-generated method stub
		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				if (msg.what == TIMEOUT) {
					secondNum++;
					second.setText("" + secondNum);
				}
			}
		};
	}

	private void initTimerProc() {
		// TODO Auto-generated method stub
		mTimer = new Timer(true);
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message message = Message.obtain();
				message.what = TIMEOUT;
				mHandler.sendMessage(message);
			}
		};

		mTimer.schedule(task, START_MILLIS, INTERVAL_MILLIS);
	}

	private void freshStep() {
		stepNum++;
		step.setText("" + stepNum);
	}

	private void clearStep() {
		stepNum = 0;
		step.setText("0");
	}

	private void clearSecond() {
		secondNum = 0;
		second.setText("0");
	}

	private void initMyGridView() {

		myGame = new MyGame(this, colums, myBitmap);

		GridView mGridView = (GridView) findViewById(R.id.game_grad);
		mGridView.setNumColumns(colums);

		myGame.getGameBitmap(list);
		myGridAdapter = new MyGridAdapter(this, colums, list);

		mGridView.setAdapter(myGridAdapter);
		mGridView.setOnItemClickListener(new GridView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				if (myGame.isMove(position)) {
					myGame.swapBlank(position);
					myGame.getGameBitmap(list);
					MyGridAdapter sa = (MyGridAdapter) parent.getAdapter();
					sa.notifyDataSetChanged();
					freshStep();
					if (myGame.isFinish()) {
						mTimer.cancel();
						Toast.makeText(GameActivity.this, "恭喜你完成拼图", Toast.LENGTH_LONG).show();
					}

				}
			}
		});
	}

	private void freshMyGridView() {
		myGame.resetPuzzleBitmap();
		myGame.getGameBitmap(list);
		myGridAdapter.notifyDataSetChanged();
	}

	private void recycleBitmap() {
		for (Bitmap bitmap : list) {
			if (bitmap != null && !bitmap.isRecycled()) {
				bitmap.recycle();
				bitmap = null;
			}
		}
		list.clear();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (View.VISIBLE == iv.getVisibility()) {
				iv.setVisibility(View.GONE);
			} else {
				goBack();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void goBack() {
		AlertDialog.Builder adBuilder = new AlertDialog.Builder(this);
		AlertDialog dialog = adBuilder.setMessage("是否离开游戏").setPositiveButton("离开", new AlertDialog.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				finish();

			}
		}).setNegativeButton("继续", null).create();

		WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
		lp.alpha = 0.7f;
		dialog.getWindow().setAttributes(lp);

		dialog.show();
	}

}
