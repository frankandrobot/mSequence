package uris.apps.com;

import android.widget.TextView;
import android.util.AttributeSet;
import android.content.Context;
import android.graphics.Canvas;

import android.app.Activity;
import android.widget.*;
import android.view.MotionEvent;
import android.widget.TextView;
import android.content.res.Resources;
import android.graphics.Paint;
import android.os.SystemClock;
import java.lang.Math;
import android.graphics.Typeface;
import android.view.ViewParent;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation;
import android.graphics.RadialGradient;
import android.graphics.LinearGradient;
import android.graphics.Shader;
 
public class GoScreen extends TextView {

    public GoScreen (Context context) {
	super(context);
	init();
    }

    public GoScreen (Context context, AttributeSet attrs) {
	super(context, attrs);
	init();
    }

    private void init () {
	startTime = SystemClock.uptimeMillis();
	setTime = startTime + 1000;
	goTime = setTime + 1000;
	quitTime = goTime + 1000;
	
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
    
    @Override
    public void onDraw(Canvas canvas) {
	long cur = SystemClock.uptimeMillis();
	if ( cur > quitTime ) { 
	    Activity activity = (Activity) getContext();
	    activity.finish();
	}
	else if ( cur > goTime ) {
	    setText("Go!");
	    setTextColor(myResources.getColor(R.color.green));
	    invalidate();
	}
	else if ( cur > setTime ) { 
	    setText("Set");
	    setTextColor(myResources.getColor(R.color.yellow));
	    invalidate();
	}
	//draw rotating ring
	int px = getWidth() / 2;
	int py = getHeight() / 2;
	int radius = Math.min(px,py);
	gradPaint.setShader(new LinearGradient(0, 0, getWidth(), 0, 
					       0x00000000,0xffffffff,
					       Shader.TileMode.CLAMP));
	// ringPaint.setShader(new RadialGradient(px,py,radius+5,
	// 				       0x00000000,0xffffffff,
	// 				       Shader.TileMode.CLAMP));
	
	canvas.drawCircle(px,py,radius-ringThick,ringPaint);
	canvas.save();
	int i = 0;
	i += 10;
	i %= 360;
	canvas.rotate(i,px,py);
	canvas.drawCircle(px,py,radius-ringThick,gradPaint);
	canvas.restore();
	super.onDraw(canvas);
	invalidate();
    }

    //Called by parent. Gives the available dimensions for this view.
    //Then setMeasureDimension sets the measurement specs.
    @Override 
    protected void onMeasure(int widthMeasureSpec,int heightMeasureSpec){ 
	int measuredHeight=measure(heightMeasureSpec); 
	int measuredWidth=measure(widthMeasureSpec); 
	int d = Math.min(measuredHeight,measuredWidth);
	setMeasuredDimension(d,d); 
    }
  
    private int measure(int measureSpec) { 
	int specMode=MeasureSpec.getMode(measureSpec); 
	int specSize=MeasureSpec.getSize(measureSpec); 
      
	//Default size
	int result=500; 
	if(specMode==MeasureSpec.AT_MOST ||
	   specMode==MeasureSpec.EXACTLY) { 
	    result=specSize; 
	} 
	return result;
    }

    private long startTime,readyTime,setTime,goTime,quitTime;
    private Resources myResources;
    private Paint ringPaint, gradPaint;
    private int ringThick;
}