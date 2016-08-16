package com.chenglong.muscle.puzzle;

import java.util.ArrayList;
import java.util.List;

import com.chenglong.muscle.R;
import com.chenglong.muscle.util.MyScreenUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.DisplayMetrics;

public class MyGame {

	private Context context;
	private int colums;
	private Bitmap oldBitmap;

	private List<MyBlock> blockList = new ArrayList<MyBlock>();
	private MyBlock blankBlock;
	private Bitmap lastBitmap;
	private Bitmap newBitmap;
	private Bitmap showBitmap = null;
	private BitmapFactory.Options options;

	public MyGame(Context context, int colums, Bitmap oldBitmap) {
		// TODO Auto-generated constructor stub
		this.colums = colums;
		this.context = context;
		this.oldBitmap = oldBitmap;
		
		options = new BitmapFactory.Options();
		options.inPreferredConfig = Bitmap.Config.RGB_565;

		createPuzzleBitmap();
	}

	public void getGameBitmap(List<Bitmap> bpList) {
		int len = bpList.size();

		for (int i = 0; i < len; i++) {
			bpList.remove(0);
		}

		for (int i = 0; i < blockList.size(); i++) {
			bpList.add(blockList.get(i).getBitmap());
		}

		if (isFinish()) {
			bpList.remove(colums * colums - 1);
			bpList.add(lastBitmap);
		}
	}

	public void swapBlank(int from) {
		blockList.get(from).swapBlock(blankBlock);
		blankBlock = blockList.get(from);
	}

	public boolean isFinish() {
		for (MyBlock my : blockList) {
			if ((my.getBitmapId() != 0) && (my.getBitmapId() == my.getItemId())) {
				continue;
			} else if ((my.getBitmapId() == 0) && (colums * colums == my.getItemId())) {
				continue;
			} else {
				return false;
			}
		}

		return true;
	}

	public Boolean isMove(int position) {
		int blankId = blankBlock.getItemId() - 1;

		if ((colums == Math.abs(position - blankId))
				|| ((position / colums == blankId / colums) && (1 == Math.abs(position - blankId)))) {
			return true;
		}

		return false;
	}

	public Bitmap getShowBitmap() {
		if (null == showBitmap) {
			Matrix matrix = new Matrix();
			matrix.postScale(0.9f, 0.9f);

			showBitmap = Bitmap.createBitmap(newBitmap, 0, 0, newBitmap.getWidth(), newBitmap.getHeight(), matrix,	true);
		}
		
		return showBitmap;
	}

	public void createPuzzleBitmap() {
		resizeBitmap();         /* 调整图片大小 */
		getPuzzleBitmap();      /* 对图片进行切割 */
		puzzleGenerator();      /* 打乱顺序 */
		while (!checkPuzzleValid()) {  /* 检查打乱顺序是否可拼接 */
			puzzleGenerator();
		}
	}

	public void resetPuzzleBitmap() {
		puzzleGenerator();
		while (!checkPuzzleValid()) {
			puzzleGenerator();
		}
	}

	private void getWidthHeight(Context context, float[] data) {
		
		DisplayMetrics metric = MyScreenUtil.getScreenMetrics(context);
		float width = metric.widthPixels;
		float height = metric.heightPixels - 190 * metric.density;

		data[0] = width;
		data[1] = height;
	}

	private void resizeBitmap() {
		float[] data = { 0f, 0f };

		getWidthHeight(context, data);

		Matrix matrix = new Matrix();
		matrix.postScale(data[0] / oldBitmap.getWidth(), data[1] / oldBitmap.getHeight());

		newBitmap = Bitmap.createBitmap(oldBitmap, 0, 0, oldBitmap.getWidth(), oldBitmap.getHeight(), matrix, true);

		oldBitmap.recycle();
		oldBitmap = null;
		System.gc();
		return;
	}

	private void getPuzzleBitmap() {
		Bitmap tmp = null;

		int itemWidth = newBitmap.getWidth() / colums;
		int itemHeight = newBitmap.getHeight() / colums;

		for (int i = 1; i <= colums; i++) {
			for (int j = 1; j <= colums; j++) {
				/* 序号从0开始 */
				tmp = Bitmap.createBitmap(newBitmap, (j - 1) * itemWidth, (i - 1) * itemHeight, itemWidth, itemHeight);
				blockList.add(new MyBlock((i - 1) * colums + j, (i - 1) * colums + j, tmp));
			}
		}

		lastBitmap = tmp;
		blockList.remove(colums * colums - 1);

		tmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.puzzle_blank, options);
		tmp = Bitmap.createBitmap(tmp, 0, 0, itemWidth, itemHeight);

		blockList.add(new MyBlock(colums * colums, 0, tmp));
		blankBlock = blockList.get(colums * colums - 1);
	}

	private void puzzleGenerator() {
		int seed;

		for (int i = 0; i < blockList.size(); i++) {
			seed = (int) (Math.random() * colums * colums);
			blockList.get(seed).swapBlock(blankBlock);
			blankBlock = blockList.get(seed);
		}
	}

	private Boolean checkPuzzleValid() {
		int sum = getCheckSum(blockList);

		if (0 == sum) {
			return false;
		}

		if (1 == blockList.size() % 2) {
			return sum % 2 == 0;
		} else {
			if (1 == ((blankBlock.getItemId() - 1) / colums) % 2) {
				return sum % 2 == 0;
			} else {
				return sum % 2 == 1;
			}
		}
	}

	private static int getCheckSum(List<MyBlock> data) {
		int sum = 0;
		int iDataId;
		int jDataId;

		for (int i = 0; i < data.size(); i++) {
			for (int j = i + 1; j < data.size(); j++) {
				iDataId = data.get(i).getBitmapId();
				jDataId = data.get(j).getBitmapId();
				if ((jDataId != 0) && (jDataId < iDataId)) {
					sum++;
				}
			}
		}

		return sum;
	}
	
	public void destroyPuzzle() {
		// TODO Auto-generated method stub
		recycleBitmap(oldBitmap);
		recycleBitmap(newBitmap);
		recycleBitmap(showBitmap);
		recycleBitmap(blankBlock.getBitmap());
		System.gc();
		blockList.clear();
	}
	
	private void recycleBitmap(Bitmap bitmap) {
			if (bitmap != null && !bitmap.isRecycled()) {
				bitmap.recycle();
				bitmap = null;
			}
	}
}
