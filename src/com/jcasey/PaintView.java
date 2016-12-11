package com.jcasey;

import java.io.File;
import java.io.FileOutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class PaintView extends View implements OnTouchListener {


	final Paint paint = new Paint();

	Bitmap offScreenBitmap;
	Canvas offScreenCanvas;

	// define additional constructors so that PaintView will work with out
	// layout file

	public PaintView(Context context) {
		super(context);

		setup();
	}

	public PaintView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		setup();
	}

	public PaintView(Context context, AttributeSet attrs) {
		super(context, attrs);

		setup();
	}

	public void setup() {
		setOnTouchListener(this); // define event listener and start
									// intercepting events
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// draw the off screen bitmap
		canvas.drawBitmap(offScreenBitmap, 0, 0, paint);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		// get the x,y coordinates of the MotionEvent.ACTION_MOVE event
		MainActivity a = (MainActivity) this.getContext();
		a.DrawShape(offScreenCanvas, paint, event);
		invalidate(); // force a screen re-draw
		
		return true; // actually consume the event
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		// create / re-create the off screen bitmap to capture the state of our
		// drawing
		// this operation will reset the user's drawing

		offScreenBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		offScreenCanvas = new Canvas(offScreenBitmap);
	}

	public void Save() {
		// TODO Auto-generated method stub
		try {
			File ExtDir = Environment.getExternalStorageDirectory();
			File MyDir = new File(ExtDir, "MobileProgramming");
			MyDir.mkdirs();
			File MyPng = new File(MyDir, "1.png");

			FileOutputStream fOut = new FileOutputStream(MyPng);
			offScreenBitmap.compress(Bitmap.CompressFormat.PNG, 75, fOut);
			fOut.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

	}

	public void Email() {}
			
	public void Reset() {
		// TODO Auto-generated method stub
		int w = this.getWidth();
		int h = this.getHeight();
		offScreenBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		offScreenCanvas = new Canvas(offScreenBitmap);
		invalidate();
	}
}
