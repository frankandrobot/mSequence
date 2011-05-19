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

//Files needed:

//assets/fonts
//res/anim/score_rotate.xml
//res/main/score_table.xml
//src/ScoreLayout.java
//src/UriSound.java

//To change the Scoring screen, change:
//1. Update the XML
//2. Update the array here
//3. Update ScoreLayout
//4. Update PlayGame
//5. Update GameEngine

public class ScoreReport extends Activity
{
    private ScoreLayout scores;
    static public boolean GAME_OVER=false;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
	//Debug.waitForDebugger();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_table);

	Intent userScores = getIntent();
	int s[] = new int[4];
	s[0] = userScores.getIntExtra("currentscore",Score.current_score);
	s[1] = userScores.getIntExtra("timebonus",Score.time_bonus);
	s[2] = userScores.getIntExtra("guessbonus",Score.guess_bonus);
	s[3] = userScores.getIntExtra("totalscore",Score.total_score);
	//Calculator final score
	Score.setTotalScore();
	if ( userScores.getIntExtra("gameover",0 ) == InterArt.GAME_OVER ){
	    ScoreReport.GAME_OVER=true;
	    setResult(InterArt.GAME_OVER);
	}
	scores = (ScoreLayout) findViewById(R.id.scores);
	scores.setScores( s );

	Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Helwoodica.otf");
        ScoreLayout parentView = (ScoreLayout)findViewById(R.id.scores);
	TextView myScores;
        for (int i=0; i<parentView.getChildCount(); i++) {
	    myScores = (TextView) parentView.getChildAt(i);
	    myScores.setTypeface(font);
	}
	//and final total score
        LinearLayout totalView = (LinearLayout)
	    findViewById(R.id.gameover);
        for (int i=0; i<totalView.getChildCount(); i++) {
	    myScores = (TextView) totalView.getChildAt(i);
	    myScores.setTypeface(font);
	}
	// myScores = (TextView) findViewById(R.id.total);
	// myScores.setTypeface(font);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
	if (firstclick) { 
	    //update scores all at once
	    scores.touched(); 
	    firstclick=false;
	}
	else { 
	    if (secondclick) {	
		//need two clicks to exit normally
		//otherwise need three clicks if game over
		if ( ScoreReport.GAME_OVER ) secondclick=false;
		else finish();
	    }
	    else finish();
	}
	return super.onTouchEvent(event);
    }
    private boolean firstclick=true, secondclick=true;
}

