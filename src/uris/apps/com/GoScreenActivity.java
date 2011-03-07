package uris.apps.com;

import java.lang.String;
import android.app.Activity;
import android.os.Bundle;
import android.graphics.Canvas;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.graphics.Typeface;
import android.content.Intent;
import android.view.MotionEvent;

import android.os.Debug;

public class GoScreenActivity extends Activity
{
    //  private GoScreenLayout scores;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
	//Debug.waitForDebugger();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.go_screen);

     	goText = (TextView) findViewById(R.id.go_text);
    // 	scores.setScores( s );
	
     	Typeface font = Typeface.createFromAsset(getAssets(), 
						 "fonts/Helwoodica.otf");
	goText.setTypeface(font);
	initStartTime();
	readyTime = startTime + 2000;
	setTime = readyTime + 2000;
	goTime = setTime + 2000;
    }

    @Override
    public void onDraw(Canvas canvas) {
	int long cur = SystemClock.uptimeMillis();
	if ( cur > goTime ) goText.setText("Go!");
	else if ( cur > setTime ) goText.setText("Set");
	super.onDraw(canvas);
	invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
	// if (first) { scores.touched(); first=false;}
	// else { finish(); }
	return super.onTouchEvent(event);
    }

    private void initStartTime() {
	startTime = SystemClock.uptimeMillis();
    }

    private boolean first=true;
    private long startTime,readyTime,setTime,goTime;
    private TextView goText;
}

