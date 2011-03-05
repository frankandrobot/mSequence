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
    private GoScreenLayout scores;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
	//Debug.waitForDebugger();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.go_screen);

    // 	scores = (GoScreenLayout) findViewById(R.id.scores);
    // 	scores.setScores( s );

    // 	Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Helwoodica.otf");
    //     ScoreLayout parentView = (ScoreLayout)findViewById(R.id.scores);
    // 	TextView myScores;
    //     for (int i=0; i<parentView.getChildCount(); i++) {
    // 	    myScores = (TextView) parentView.getChildAt(i);
    // 	    myScores.setTypeface(font);
    // 	}
    // 	//and final total score
    //     LinearLayout totalView = (LinearLayout)findViewById(R.id.myfinal_layout);
    //     for (int i=0; i<totalView.getChildCount(); i++) {
    // 	    myScores = (TextView) totalView.getChildAt(i);
    // 	    myScores.setTypeface(font);
    // 	}
    // 	// myScores = (TextView) findViewById(R.id.total);
    // 	// myScores.setTypeface(font);
    // }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
	if (first) { scores.touched(); first=false;}
	else { finish(); }
	return super.onTouchEvent(event);
    }

    private boolean first=true;
}

