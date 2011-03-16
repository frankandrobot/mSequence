package uris.apps.com;

import android.widget.*;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.*;
import android.util.AttributeSet;
import android.os.SystemClock;
import android.graphics.PorterDuff.Mode;

public class ProgressBarView extends GridView {
    private Paint textPaintColor;
    private long startTime;
	
    public ProgressBarView (Context context, AttributeSet ats, int ds) {
	super(context, ats, ds);
	init();
    }

    public ProgressBarView (Context context) {
	super(context);
	init();
    }

    public ProgressBarView (Context context, AttributeSet attrs) {
	super(context, attrs);
	init();
    }

    public void setCount(int c) { 
	count = c; 
	sweepAngle = 360 / c;
    }
    
    public void setCurrent(int c) { 
	current = c; 
	currentAngle = sweepAngle * c;
    }

    public void next() { current++; }

    public void reset() { current = 1; }

    private void init() {
	ringThick = 20;
	switchTime = SystemClock.uptimeMillis() + blinkTime;
	blinkTime = 500;

	//setup canvas

	//use measureText;
	myResources = getResources();
	ringPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	//ringPaint.setColor(myResources.getColor(R.color.clockTextColor));
	ringPaint.setColor(0xffff0000);
	ringPaint.setStyle(Paint.Style.STROKE);
	ringPaint.setStrokeWidth(ringThick);

	blinkPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	blinkPaint.setColor(myResources
				.getColor( R.color.green )
				);
	blinkPaint.setStyle(Paint.Style.STROKE);
	blinkPaint.setStrokeWidth(ringThick);

	curPaint = blinkPaint;
	blinking = true;
	// gradPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	// gradPaint.setStyle(Paint.Style.STROKE);
	// gradPaint.setStrokeWidth(ringThick);
    }

    private void drawArcs(Canvas canvas) {
    }
    
    private void drawCurrent(Canvas canvas,int px, int py, int radius) {
	//hack assumes radius in min of width/height
	int r = radius-ringThick;
	if ( blinking ) {
	    if ( SystemClock.uptimeMillis() > switchTime ) {
		switchTime = SystemClock.uptimeMillis() + blinkTime;
		curPaint = ringPaint;
		blinking = false;
		invalidate();
	    }
	}
	else { 
	    if ( SystemClock.uptimeMillis() > switchTime ) {
		switchTime = SystemClock.uptimeMillis() + blinkTime;
		curPaint = blinkPaint;
		blinking = true;
		invalidate();
	    }
	}
	canvas.drawArc(new RectF(px-r,py-r,px+r,py+r), 
		       -90, currentAngle, 
		       false, 
		       curPaint);
    }

    @Override
	public void onDraw(Canvas canvas) {
	
	//draw ring
	int px = getWidth() / 2;
	int py = getHeight() / 2;
	int radius = Math.min(px,py);
	canvas.drawCircle(px,py,radius-ringThick,ringPaint);

	drawArcs(canvas);

	drawCurrent(canvas,px,py,radius);

	// gradPaint.setShader(new LinearGradient(0, 0, getWidth(), 0, 
	// 				       0x00000000,0xffffffff,
	// 				       Shader.TileMode.CLAMP));

	// Use the TextView to render the text.
	super.onDraw(canvas);
	//canvas.restore();
	//	invalidate();
    }

    private Resources myResources;
    private Paint ringPaint, blinkPaint, curPaint;
    private int ringThick, rotAngle;
    private int sweepAngle, currentAngle;
    private int count, current;
    private long switchTime, blinkTime;
    private boolean blinking=true;
}