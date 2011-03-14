package uris.apps.com;

import android.widget.*;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.*;
import android.util.AttributeSet;
import android.os.SystemClock;
import android.graphics.PorterDuff.Mode;

public class GameGridView extends GridView {
    private Paint textPaintColor;
    private long startTime;
	
    public GameGridView (Context context, AttributeSet ats, int ds) {
	super(context, ats, ds);
	init();
    }

    public GameGridView (Context context) {
	super(context);
	init();
    }

    public GameGridView (Context context, AttributeSet attrs) {
	super(context, attrs);
	init();
    }

    private void init() {
	//setup canvas
	ringThick = 20;
	//use measureText;
	myResources = getResources();
	ringPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	//ringPaint.setColor(myResources.getColor(R.color.clockTextColor));
	ringPaint.setColor(0xffff0000);
	ringPaint.setStyle(Paint.Style.STROKE);
	ringPaint.setStrokeWidth(ringThick);
	gradPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	gradPaint.setStyle(Paint.Style.STROKE);
	gradPaint.setStrokeWidth(ringThick);

    }

    public void deselect() {
	ImageView sel;
	for(int i=0; i<getChildCount(); i++) {
	    sel = (ImageView) getChildAt(i);
	    sel.clearColorFilter();
	} 
	red = false;
	green = false;
    }

    @Override
	public void onDraw(Canvas canvas) {

	ImageView sel;

	if ( green ) {
	    if (SystemClock.uptimeMillis() < greenTime) {
		sel = (ImageView) getChildAt(greenPosition);
		sel.setColorFilter(0xFF00FF00,  
				   Mode.DARKEN);
		invalidate();
	    }
	    else {
		sel = (ImageView) getChildAt(greenPosition);
		sel.clearColorFilter();
		invalidate();
	    }
	}
	if ( red ) {
	    if (SystemClock.uptimeMillis() < redTime) {
		sel = (ImageView) getChildAt(redPosition);
		sel.setColorFilter(0xFFFF0000,  
				   Mode.DARKEN);
		invalidate();
	    }
	    else {
		sel = (ImageView) getChildAt(redPosition);
		sel.clearColorFilter();
		invalidate();
	    }
	}

	//draw rotating ring
	int px = getWidth() / 2;
	int py = getHeight() / 2;
	int radius = Math.max(px,py);
	canvas.drawCircle(px,py,radius-ringThick,ringPaint);
	gradPaint.setShader(new LinearGradient(0, 0, getWidth(), 0, 
					       0x00000000,0xffffffff,
					       Shader.TileMode.CLAMP));

	// canvas.drawText(String.valueOf(secs), 10, 10, textPaintColor);
	// // Color as paper
	// // canvas.drawColor(paperColor);

	// // // Draw ruled lines
	// canvas.drawLine(0, 0, getMeasuredHeight(), 0, linePaint);
	// canvas.drawLine(0, getMeasuredHeight(), 
	//                    getMeasuredWidth(), getMeasuredHeight(), 
	//                    linePaint);

	// // Draw margin
	// canvas.drawLine(margin, 0, margin, getMeasuredHeight(), marginPaint);

	// // Move the text across from the margin
	// canvas.save();
	// canvas.translate(margin, 0);

	// Use the TextView to render the text.
	super.onDraw(canvas);
	//canvas.restore();
	//	invalidate();
    }

    public void updateImages() {
	ImageAdapter ia = 
	    (ImageAdapter) getAdapter();
	ia.updateImages();
    }

    public void flashGreen(int position) {
	greenTime = SystemClock.uptimeMillis() + 2000;
	greenPosition = position;
	green = true;
    }

    public void flashRed(int position) {
	redTime = SystemClock.uptimeMillis() + 1000;
	redPosition = position;
	red = true;
    }

    public void reset() { deselect(); }

    private int redPosition=0,greenPosition=0;
    private float greenTime=0,redTime=0;
    private boolean green=false,red=false;
    private Resources myResources;
    private Paint ringPaint, gradPaint;
    private int ringThick, rotAngle;
}