package com.chenglong.muscle.floatBall;

import com.chenglong.muscle.R;
import com.chenglong.muscle.util.MyBitmapUtil;
import com.chenglong.muscle.util.MyScreenUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.view.View;

public class FloatBallView extends View {

	private static final float RADIUS_PX = 20f;
	private final String text = "21";
	private Context context;
	private boolean dragState = false;

	private int radius = 0;
	private Paint ballPaint;
	private Paint textPaint;
	private Bitmap bitmap;
	private Bitmap bitmap2;

	public FloatBallView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		float density = MyScreenUtil.getScreenDensity(context);
		radius = (int) (density * RADIUS_PX);

		ballPaint = new Paint();
		ballPaint.setColor(Color.GRAY);
		ballPaint.setAlpha(150);
		// Shader sd = new Shader();
		// mPaint.setShader(sd);
		// bitmap = MyBitmapUtil.decodeBitmapByRes(context,
		// R.drawable.icon_small);
		// BitmapShader shader = new BitmapShader(bitmap, TileMode.CLAMP,
		// TileMode.CLAMP);
		// ballPaint.setShader(shader);
		ballPaint.setAntiAlias(true);

		textPaint = new Paint();
		textPaint.setColor(Color.BLACK);
		FontMetrics metrics = textPaint.getFontMetrics();
		textPaint.setTextSize(40);

		Bitmap src = MyBitmapUtil.decodeBitmapByRes(context, R.drawable.icon_small);
		bitmap = Bitmap.createScaledBitmap(src, 2 * radius, 2 * radius, true);
		src = MyBitmapUtil.decodeBitmapByRes(context, R.drawable.floatball_move);
		bitmap2 = Bitmap.createScaledBitmap(src, 2 * radius, 2 * radius, true);
		src.recycle();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(2 * radius, 2 * radius);
		// setMeasuredDimension(bitmap.getWidth(), bitmap.getHeight());
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		// super.onDraw(canvas);
		// canvas.drawCircle(radius, radius, radius, ballPaint);
		// Bitmap bitmap = MyBitmapUtil.decodeBitmapByRes(context,
		// R.drawable.icon_small);
		if (dragState == true) {
			canvas.drawBitmap(bitmap2, 0, 0, null);
		} else {
			canvas.drawBitmap(bitmap, 0, 0, ballPaint);
			float textWidth = textPaint.measureText(text);
			float x = radius - textWidth / 2;
			FontMetrics metrics = textPaint.getFontMetrics();
			float y = radius - (metrics.descent + metrics.ascent) / 2;
			//canvas.drawText(text, x, y, textPaint);
		}
	}

	public void setDragState(boolean state) {
		this.dragState = state;
		invalidate();
	}

}
