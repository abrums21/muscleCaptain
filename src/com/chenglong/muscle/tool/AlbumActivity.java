package com.chenglong.muscle.tool;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.chenglong.muscle.R;
import com.chenglong.muscle.puzzle.SettingActivity;
import com.chenglong.muscle.util.MyBitmapUtil;
import com.chenglong.muscle.util.MyCommonUtil;
import com.chenglong.muscle.util.MyPermissionUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class AlbumActivity extends Activity implements OnClickListener {

	// private static final String ALBUM_PATH =
	// "/data/data/com.chenglong.muscle/album/";
	private String ALBUM_PATH;
	// private static final String ALBUM_PATH =
	// Environment.getExternalStorageDirectory().getPath() + "/temp.png";
	private static final String ALBUM_SUFFIX = ".jpg";
	private static final int CAPTURE_CODE = 1000;
	private Album album;
	private boolean visable = false;
	private AlbumAdapter mAdapter;
	private int position = 0;
	// private ViewPager pager;
	private LazyViewPager pager;
	private boolean unUsed = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.album);
		setTitle("美队健身：健身记录");

		Toast.makeText(this, "点击图片可进行拍照和删除", Toast.LENGTH_LONG).show();

		try {
			ALBUM_PATH = this.getExternalFilesDir(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/album/";
		} catch (NullPointerException e)
		{
			// TODO: handle exception
			unUsed = true;
			ALBUM_PATH = "";
		}
		
		album = new Album(this, ALBUM_PATH);

		if (!album.createAlbum()) {
			unUsed = true;
		}

		pager = (LazyViewPager) findViewById(R.id.album_viewpager);
		pager.setOffscreenPageLimit(0);
		LinearLayout layout = (LinearLayout) findViewById(R.id.album_ll);
		ImageView addIv = (ImageView) findViewById(R.id.album_add);
		ImageView delIv = (ImageView) findViewById(R.id.album_del);
		addIv.setOnClickListener(this);
		delIv.setOnClickListener(this);
		// layout.setVisibility(View.VISIBLE);
		List<ImageView> list = updatePager();

		mAdapter = new AlbumAdapter(list, this);
		pager.setAdapter(mAdapter);
		pager.setOnPageChangeListener(new LazyViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				position = arg0;
				LinearLayout layout = (LinearLayout) findViewById(R.id.album_ll);
				layout.setVisibility(View.GONE);
				visable = false;
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
			}
		});

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		album.destroyAlbum();
		System.gc();
	}

	class Album {
		private List<ImageView> images;
		private Context context;
		private String path;
		private File dir;
		private ImageView one;

		public Album(Context context, String path) {
			// TODO Auto-generated constructor stub
			this.context = context;
			this.path = path;

			images = new ArrayList<ImageView>();
			one = new ImageView(context);
			one.setImageBitmap(MyBitmapUtil.decodeBitmapByRes(context, R.drawable.album_one));
			// one.setImageBitmap(BitmapFactory.decodeResource(context.getResources(),
			// R.drawable.album_one, options));
		}

		public boolean createAlbum() {
			dir = new File(path);
			if (!dir.exists()) {
				if (dir.mkdir()) {
					return true;
				} else {
					return false;
				}
			}

			return true;
		}

		public File createAlbumFile() {
			File file = new File(ALBUM_PATH + new Long(System.currentTimeMillis()).intValue() + ALBUM_SUFFIX);

			return file;
		}

		public List<ImageView> getAlbum() {
			clearAlbum();

			images.add(one);

			File[] subFile = dir.listFiles();
			if ( (subFile != null ) && (0 != subFile.length)) {
				for (File iv : subFile) {
					if (iv.isDirectory()) {
						continue;
					}
					if (iv.getName().toLowerCase().endsWith(ALBUM_SUFFIX)) {
						ImageView image = new ImageView(context);
						// Bitmap bp =
						// resizeBitmap(BitmapFactory.decodeFile(iv.getAbsolutePath(),
						// options));
						// BitmapFactory.decodeFile(iv.getAbsolutePath());
						image.setImageBitmap(MyBitmapUtil.decodeBitmapByPath(context, iv.getAbsolutePath()));
						// image.setImageBitmap(bp);
						String filename = getFileName(iv.getAbsolutePath());
						image.setId(Integer.valueOf(filename));
						// image.setImageBitmap(BitmapFactory.decodeFile(iv.getName()));
						images.add(image);
					}
				}
			}

			return images;
		}

		private void clearAlbum() {
//			for (ImageView image : images) {
//
//				Drawable drawable = image.getDrawable();
//				if (drawable != null && drawable instanceof BitmapDrawable) {
//					Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
//					if (bitmap != null && !bitmap.isRecycled()) {
//						bitmap.recycle();
//						bitmap = null;
//					}
//
//				}
//			}
			images.clear();
//			System.gc();

		}

		public void destroyAlbum() {
			images.clear();
		}

		public void deleteAlbumFileByPos(int position) {

			String name = String.valueOf(images.get(position).getId());
			String path = setFileName(name);

			File[] subFile = dir.listFiles();
			if (0 != subFile.length) {
				for (File iv : subFile) {
					if (iv.isDirectory()) {
						continue;
					}
					if (getFileName(iv.getName()).equals(name)) {
						iv.delete();
						// getAlbum();
					}
				}
			}
		}

		private String getFileName(String path) {
			String tmp = path.substring(0, path.lastIndexOf("."));
			return tmp.substring(tmp.lastIndexOf("/") + 1);
		}

		private String setFileName(String name) {
			return ALBUM_PATH + name + ALBUM_SUFFIX;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.album_add: {
			if (true == unUsed) {
				MyCommonUtil.getDialog4Unuse(AlbumActivity.this, "存储操作被禁止，影响相机功能，请确认");
				break;
			}
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(album.createAlbumFile()));
			startActivityForResult(intent, CAPTURE_CODE);
			// album.getAlbum();
			// mAdapter.notifyDataSetChanged();
			break;
		}
		case R.id.album_del: {
			AlertDialog.Builder builder = new AlertDialog.Builder(AlbumActivity.this);
			builder.setTitle("是否确认删除当前图片").setNegativeButton("取消", null).setPositiveButton("确定",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							album.deleteAlbumFileByPos(position);
							Toast.makeText(AlbumActivity.this, "已删除", Toast.LENGTH_SHORT).show();

							List<ImageView> list = updatePager();
							// List<ImageView> list = album.getAlbum();
							mAdapter.notifyDataSetChanged();
							int location = (position + 1) % list.size();
							pager.setCurrentItem(location);

						}
					});
			builder.create().show();
			break;
		}
		default: {
			break;
		}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (RESULT_OK == resultCode) {
			switch (requestCode) {
			case CAPTURE_CODE: {
				updatePager();
				mAdapter.notifyDataSetChanged();
				break;
			}
			}
		}
	}

	private List<ImageView> updatePager() {
		List<ImageView> list = album.getAlbum();
		for (ImageView iv : list) {
			iv.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					LinearLayout layout = (LinearLayout) findViewById(R.id.album_ll);
					ImageView delIv = (ImageView) findViewById(R.id.album_del);
					if (true != visable) {
						layout.setVisibility(View.VISIBLE);
						visable = true;
						if (0 == position) {
							delIv.setVisibility(View.GONE);
						} else {
							delIv.setVisibility(View.VISIBLE);
						}
					} else {
						layout.setVisibility(View.GONE);
						visable = false;
					}
				}
			});
		}
		return list;
	}

	@Override
	public void startActivityForResult(Intent intent, int requestCode) {
		// TODO Auto-generated method stub
		if (CAPTURE_CODE == requestCode) { // Any way to judge that this is to
											// sead an email
			List<ResolveInfo> activities = getPackageManager().queryIntentActivities(intent, 0);
			boolean flag = MyPermissionUtil.isCameraGranted(this);
			if (activities == null || activities.size() == 0 || !flag) {
				// Do anything you like, or just return
				// Toast.makeText(AlbumActivity.this, "并没有可用的照相软件",
				// Toast.LENGTH_LONG).show();
				MyCommonUtil.getDialog4Unuse(this, "照相功能不可用，请确认");
				return;
			}
		}

		super.startActivityForResult(intent, requestCode);
	}
}
