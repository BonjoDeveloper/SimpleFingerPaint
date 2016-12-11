package com.jcasey;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.ColorDrawable;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

	public int[] colorButtonIds = { R.id.buttonDRed, R.id.buttonDGreen,
			R.id.buttonRed, R.id.buttonGreen, R.id.buttonViolet,
			R.id.buttonBlue, R.id.buttonHLBlue, R.id.buttonYellow, };

	public int[] shapeButtonIds = { R.id.ButtonCircle, R.id.ButtonRectangle,
			R.id.ButtonTriangle, };

	public int[] functionButtonIds = { R.id.ButtonSave, R.id.ButtonReset, R.id.ButtonEmail, R.id.ButtonExit, };

	public enum Shape {
		Circle, Triangle, Rectangle
	};

	public int currentColor = Color.GREEN;
	public Shape currentShape = Shape.Circle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		for (int i = 0; i < colorButtonIds.length; i++) {
			Button b = (Button) this.findViewById(colorButtonIds[i]);
			b.setOnClickListener(this);
		}
		for (int i = 0; i < shapeButtonIds.length; i++) {
			Button b = (Button) this.findViewById(shapeButtonIds[i]);
			b.setOnClickListener(this);
		}
		for (int i = 0; i < functionButtonIds.length; i++) {
			Button b = (Button) this.findViewById(functionButtonIds[i]);
			b.setOnClickListener(this);
		}	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private boolean InGroup(int idView, int[] arrayIDs) {
		for (int i = 0; i < arrayIDs.length; i++) {
			if (idView == arrayIDs[i])
				return true;
		}
		return false;
	}

	@SuppressLint("NewApi")
	@Override
	public void onClick(View arg0) {

		int idView = arg0.getId();
		if (InGroup(idView, colorButtonIds)) {
			Button btnColor = (Button) arg0;
			ColorDrawable buttonColor = (ColorDrawable) btnColor
					.getBackground();
			currentColor = buttonColor.getColor();
		}
		if (InGroup(idView, shapeButtonIds)) {
			if (idView == R.id.ButtonTriangle)
				currentShape = Shape.Triangle;
			if (idView == R.id.ButtonCircle)
				currentShape = Shape.Circle;
			if (idView == R.id.ButtonRectangle)
				currentShape = Shape.Rectangle;
		}
		if (InGroup(idView, functionButtonIds)) {
			if (idView == R.id.ButtonSave) {
				PaintView pView = (PaintView) this.findViewById(R.id.paintView);
				pView.Save();
			}
			if (idView == R.id.ButtonReset) {
				PaintView pView = (PaintView) this.findViewById(R.id.paintView);
				pView.Reset();			
			}
			if (idView == R.id.ButtonEmail) {
				PaintView pView = (PaintView) this.findViewById(R.id.paintView);
				pView.Reset();
			}
			if (idView == R.id.ButtonExit) {
				System.exit(0);
			}
		}
	}

	public void DrawShape(Canvas currentCanvas, Paint paint, MotionEvent event) {
		// TODO Auto-generated method stub
		float x = event.getX();
		float y = event.getY();
		float shapeSize = 20 * (event.getPressure() + 1);

		paint.setColor(currentColor);
		if (currentShape == Shape.Triangle) {
			Path path = new Path();
			path.moveTo(x, y - shapeSize);
			path.lineTo(x - shapeSize, y + shapeSize);
			path.lineTo(x + shapeSize, y + shapeSize);
			path.lineTo(x, y - shapeSize);
			path.close();
			currentCanvas.drawPath(path, paint);
		}
		if (currentShape == Shape.Circle) {
			// draw a red circle at the x,y coordinates specified by the user
			currentCanvas.drawCircle(x, y, shapeSize, paint);
		}
		if (currentShape == Shape.Rectangle) {
			currentCanvas.drawRect(x - shapeSize, y - shapeSize, x + shapeSize,
					y + shapeSize, paint);
		}
	}
}
