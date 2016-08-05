package com.chenglong.muscle.tool;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.chenglong.muscle.R;
import com.chenglong.muscle.puzzle.MyScreenUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract.Directory;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.View;
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
//	private ViewPager pager;
	private LazyViewPager pager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.album);
		setTitle("美队健身：生活记录");

		Toast.makeText(this, "通过照片记录你的健身历程", Toast.LENGTH_SHORT).show();

		ALBUM_PATH = this.getExternalFilesDir(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/album/";

		album = new Album(this, ALBUM_PATH);

		if (!album.createAlbum()) {
			return;
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
			one.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.album_one));
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
			if (0 != subFile.length) {
				for (File iv : subFile) {
					if (iv.isDirectory()) {
						continue;
					}
					if (iv.getName().toLowerCase().endsWith(ALBUM_SUFFIX)) {
						ImageView image = new ImageView(context);
						Bitmap bp = resizeBitmap(BitmapFactory.decodeFile(iv.getAbsolutePath()));
						// BitmapFactory.decodeFile(iv.getAbsolutePath());
						image.setImageBitmap(bp);
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

		private Bitmap resizeBitmap(Bitmap oldBitmap) {

			DisplayMetrics metric = MyScreenUtil.getScreenMetrics(context);
			float width = metric.widthPixels;
			float height = metric.heightPixels;

			Matrix matrix = new Matrix();
			matrix.postScale(width / oldBitmap.getWidth(), height / oldBitmap.getHeight());

			return Bitmap.createBitmap(oldBitmap, 0, 0, oldBitmap.getWidth(), oldBitmap.getHeight(), matrix,
					true);
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.album_add: {
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(album.createAlbumFile()));
			startActivityForResult(intent, CAPTURE_CODE);
			// album.getAlbum();
			// mAdapter.notifyDataSetChanged();
			break;
		}
		case R.id.album_del: {
			AlertDialog.Builder builder = new AlertDialog.Builder(AlbumActivity.this);
			builder.setTitle("是否确认删除当前图片").setNegativeButton("取消", null).setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
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
					Toast.makeText(AlbumActivity.this, "123", Toast.LENGTH_SHORT).show();
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

}
