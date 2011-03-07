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
    }
    
    @Override
    public void onDraw(Canvas canvas) {
	long cur = SystemClock.uptimeMillis();
	if ( cur > quitTime ) { 
	    Activity activity = (Activity) getContext();
	    activity.finish();
	}
	else if ( cur > goTime ) setText("Go!");
	else if ( cur > setTime ) setText("Set");
	super.onDraw(canvas);
	invalidate();
    }

    private long startTime,readyTime,setTime,goTime,quitTime;
}