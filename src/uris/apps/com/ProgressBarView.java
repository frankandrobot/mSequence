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
	sweepAngle = -1f * 360f / (float) c;
    }
    
    public void setCurrent(int c) { 
	current = c; 
	currentAngle = sweepAngle * c;
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

    private void drawArcs(Canvas canvas) {
    }

    private void drawCurrent(Canvas canvas) {
	canvas.drawArc(new RectF(0,0,getWidth(),getHeight()), 
		       0, currentAngle, 
		       false, 
		       ringPaint);
    }

    @Override
	public void onDraw(Canvas canvas) {

	//draw ring
	int px = getWidth() / 2;
	int py = getHeight() / 2;
	int radius = Math.min(px,py);
	canvas.drawCircle(px,py,radius-ringThick,ringPaint);

	drawArcs(canvas);

	drawCurrent(canvas);

	// gradPaint.setShader(new LinearGradient(0, 0, getWidth(), 0, 
	// 				       0x00000000,0xffffffff,
	// 				       Shader.TileMode.CLAMP));

	// Use the TextView to render the text.
	super.onDraw(canvas);
	//canvas.restore();
	//	invalidate();
    }

    private Resources myResources;
    private Paint ringPaint, gradPaint;
    private int ringThick, rotAngle;
    private float sweepAngle, currentAngle;
    private int count, current;
}