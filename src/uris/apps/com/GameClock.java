package uris.apps.com;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;
import android.os.SystemClock;

public class GameClock extends TextView {

    private Paint textPaintColor;
    private long startTime, timeElapsed, pauseTime, timeShift;
    private float secs;
    private GameEngine mGameEngine;

    public GameClock (Context context, AttributeSet ats, int ds) {
	super(context, ats, ds);
	init();
    }

    public GameClock (Context context) {
	super(context);
	init();
    }
    
    public GameClock (Context context, AttributeSet attrs) {
	super(context, attrs);
	init();
    }

    private void init() {
	// Get a reference to our resource table.
	Resources myResources = getResources();

	// Create the paint brushes we will use in the onDraw method.
	textPaintColor = new Paint(Paint.ANTI_ALIAS_FLAG);
	textPaintColor.setColor(myResources.getColor(R.color.clockTextColor));
	textPaintColor.setTextSize( 50 );

	// // Get the paper background color and the margin width.
	// paperColor = myResources.getColor(R.color.notepad_paper);
	//margin = myResources.getDimension(R.dimen.notepad_margin);
    }

    public void setGameEngine(GameEngine g) { mGameEngine = g; }

    public void startCountdownTimer() {
	startTime = SystemClock.uptimeMillis();
    }

    public float getRunningTime() {
	return secs;
    }

    public long getTimeLeft() {
	timeElapsed = SystemClock.uptimeMillis() - startTime; //in ms
	return mGameEngine.getCountdownDuration() - timeElapsed; //in ms
    }

    public boolean isTimeOver() { 
	return getTimeLeft() < 0;
    }

    public void pauseCountdownTimer() {
	pauseTime = SystemClock.uptimeMillis();
    }

    public void resumeCountdownTimer() {
	//startime=8:00;
	//timeElapsed=timerDuration - (currentTime - startTime); 
	//            30 - (8:10 - 8:00) = 30 - 10 = 20
	//pauseTime=8:10;
	//resumeTime=8:50;
	//so shift everything by resumeTime-pauseTime
	timeShift=SystemClock.uptimeMillis() - pauseTime;
	startTime += timeShift;
    }

    @Override
	public void onDraw(Canvas canvas) {
 
	if ( running ) {
	    //number of milliseconds elapsed
	    long curTime = getTimeLeft();
	    //long curTime = SystemClock.uptimeMillis() - startTime;
	    //convert to seconds
	    secs = curTime / 1000.0f;
	    //Round to 1 decimal place
	    //float p = (float) Math.pow(10,1);
	    secs = (float) (Math.round(secs*10.0f)/10.0f);
	    mGameEngine.setGameOver( isTimeOver() ? true : false );
	}
	canvas.drawText(String.valueOf(secs), 60, 60, textPaintColor);
	
	// Use the TextView to render the text.
	super.onDraw(canvas);
	//canvas.restore();
	invalidate();
    }

    public void stop() { running=false; }

    public void resume() { running=true; }

    private boolean running=true;
}