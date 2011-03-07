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

 
public class GoScreen extends TextView {

    public TextView (Context context) {
	super(context);
	init();
    }

    public TextView (Context context, AttributeSet attrs) {
	super(context, attrs);
	init();
    }

    private void init () {
	startTime = SystemClock.uptimeMillis();
	readyTime = startTime + 2000;
	setTime = readyTime + 2000;
	goTime = setTime + 2000;
    }
    
    @Override
    public void onDraw(Canvas canvas) {
	long cur = SystemClock.uptimeMillis();
	if ( cur > goTime ) goText.setText("Go!");
	else if ( cur > setTime ) goText.setText("Set");
	super.onDraw(canvas);
	invalidate();
    }

    private long startTime,readyTime,setTime,goTime;
}