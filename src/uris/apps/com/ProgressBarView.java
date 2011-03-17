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
    
    public int getCount() { return count; }

    public void setCurrent(int c) { 
	current = c; 
	currentAngle = sweepAngle * c;
    }

    public int getCurrent() { return current; }

    public void next() { 
	current++; 
	currentAngle = sweepAngle * current;
    }

    public void reset() { 
	current = 1; 
	currentAngle = sweepAngle;
    }

    private void init() {
	//Eventually these need to be replaced w/ device independent
	//pixels
	ringThick = 20;
	tickWeight = 2;
	htickWeight = tickWeight / 2;
	//end
	blinkTime = 250;
	switchTime = SystemClock.uptimeMillis() + blinkTime;

	//setup canvas

	myResources = getResources();

	ringPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	ringPaint.setColor(myResources
			   .getColor( R.color.red ) );
	ringPaint.setStyle(Paint.Style.STROKE);
	ringPaint.setStrokeWidth(ringThick);

	blinkPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	blinkPaint.setColor(myResources
			    .getColor( R.color.green ));
	blinkPaint.setStyle(Paint.Style.STROKE);
	blinkPaint.setStrokeWidth(ringThick);

	curPaint = blinkPaint;
	blinking = true;

	progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	progressPaint.setColor(myResources
			    .getColor( R.color.orange ));
	progressPaint.setStyle(Paint.Style.STROKE);
	progressPaint.setStrokeWidth(ringThick);

	tickPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	tickPaint.setColor(myResources
			   .getColor( R.color.white )
			   );
	tickPaint.setStyle(Paint.Style.STROKE);
	tickPaint.setStrokeWidth(ringThick);
	// gradPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	// gradPaint.setStyle(Paint.Style.STROKE);
	// gradPaint.setStrokeWidth(ringThick);
    }

    private void drawArcs(Canvas canvas) {
    }
    
    private void drawCurrent(Canvas canvas) {
	//hack assumes radius in min of width/height
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
	canvas.save();
	//draw up to current
	for(int i=0; i<getCurrent()-1; i++) {
	    canvas.drawArc(box,
			   -90+htickWeight,sweepAngle-htickWeight,
			   false,
			   progressPaint);
	    canvas.rotate(sweepAngle,px,py);
	}
	//draw current
	canvas.drawArc(box,
		       -90+htickWeight,sweepAngle-htickWeight,
		       false,
		       curPaint);
	canvas.restore();
    }

    private void drawTicks(Canvas canvas) {
	canvas.save();
	for(int i=0; i<getCount(); i++) {
	    canvas.drawArc(box, 
			   -90-htickWeight,tickWeight, 
			   false, 
			   tickPaint);
	    canvas.rotate(sweepAngle,px,py);
	}
	canvas.restore();
    }

    @Override
	public void onDraw(Canvas canvas) {
	
	//Eventually these need to move to a function that gets called
	//only when the view is resized
	px = getWidth() / 2;
	py = getHeight() / 2;
	radius = Math.min(px,py);
	int radiusmod = radius-ringThick; //actual radius adjusting
					  //for the thickness of the
					  //radius
	box = new RectF(px-radiusmod,py-radiusmod,
			px+radiusmod,py+radiusmod);
	//end

	canvas.drawCircle(px,py,radiusmod,ringPaint);

	drawTicks(canvas);

	drawCurrent(canvas);

	super.onDraw(canvas);
    }

    private Resources myResources;
    private Paint ringPaint, tickPaint, blinkPaint, curPaint, progressPaint;
    private int ringThick, rotAngle, tickWeight, htickWeight;
    private int sweepAngle, currentAngle;
    private int count, current;
    private long switchTime, blinkTime;
    private boolean blinking=true;

    //"global variables" for drawing
    private int px,py,radius;
    private RectF box;
}