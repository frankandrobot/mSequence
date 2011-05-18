package uris.apps.com;

import android.widget.LinearLayout;
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

 
public class ScoreLayout extends LinearLayout {

    public ScoreLayout (Context context) {
	super(context);
	init();
    }

    public ScoreLayout (Context context, AttributeSet attrs) {
	super(context, attrs);
	init();
    }

    private void init () {
    }
    
    private int sumScores() {
	int tmp=0;
	for(int i = 0; i<scores.length; i++) {
     	    if ( scores[i] > 0 ) tmp += scores[i]-1;
    	    else if ( scores[i] < 0 ) tmp += scores[i]+1;
    	}
	return tmp;
    }

    @Override
    public void dispatchDraw(Canvas canvas) {
	//if game over
	if ( ScoreReport.GAME_OVER ) {
	    //unhide gameover 
    	    FrameLayout gameoverFrame = (FrameLayout) getParent();
	    gameoverFrame.findViewById(R.id.gameover)
		.setVisibility(VISIBLE);

	    //get gameover text
	    TextView gameoverView = (TextView) 
		gameoverFrame.findViewById(R.id.gameover_text);

	    //rotate gameover text
  	    Activity activity = (Activity) getContext();
    	    Animation rotate = AnimationUtils.
    	    	loadAnimation(activity, R.anim.score_rotate);
    	    gameoverView.startAnimation(rotate);
	    super.dispatchDraw(canvas);
	    return;
	}

	//If we already updated all the scores
	//then update total scores
    	if ( cur_score == scores.length) { 
    	    int t = sumScores();
	    FrameLayout 

    	    Activity activity = (Activity) getContext();
    	    FrameLayout gameoverFrame = (FrameLayout) getParent();
    	    LinearLayout total = (LinearLayout) 
		gameoverFrame.findViewById(R.id.myfinal_layout);
    	    total.setVisibility(VISIBLE);

	    TextView finalText = (TextView) 
		gameoverFrame.findViewById(R.id.myfinal_score);
	    TextView totalText = (TextView) gameoverFrame.findViewById(R.id.total);
    	    totalText.setText(String.valueOf(t));
    	    Animation rotate = AnimationUtils.
    	    	loadAnimation(activity, R.anim.score_rotate);
    	    finalText.startAnimation(rotate);
	    totalText.startAnimation(rotate);
    	    cur_score++;
	    //update current score with new total
	    Score.current_score = t;
    	    super.dispatchDraw(canvas);
	    if (MyDebug.scoreLayout == false) {
		//finish activity
	    }
    	    return;
     	}
    	if ( cur_score > scores.length ) {
    	    super.dispatchDraw(canvas);
    	    return;
    	}
    	//update one score at a time
    	//update cur_score

    	//positive score
    	if ( scores[cur_score] > 0 ) {
    	    if ( ctr[cur_score] < scores[cur_score]-1 ) {
    		TextView score = (TextView) getChildAt(2*cur_score+1);
    		ctr[cur_score] = Math.min( 
    					  scores[cur_score]-1, 
    					  ctr[cur_score]+inc
    					   );
    		score.setText(String.valueOf( ctr[cur_score] ));
    		inc += inc;
		UriSound.playChime("once");
    	    }
    	    else {
    		cur_score++;
    		initInc();
    	    }
    	}
    	//negative score
    	else if ( scores[cur_score] < 0 ) {
    	    if ( ctr[cur_score] > scores[cur_score]+1 ) {
    		TextView score = (TextView) getChildAt(2*cur_score+1);
    		ctr[cur_score] = Math.max( 
    					  scores[cur_score]+1, 
    					  ctr[cur_score]-inc
    					   );
    		score.setText(String.valueOf( ctr[cur_score] ));
    		inc += inc;
		UriSound.playChime("once");
    	    }
    	    else {
    		cur_score++;
    		initInc();
    	    }
    	}
	// 0 score
	else if ( scores[cur_score] == 0 ) {
	    cur_score++;
	    UriSound.playThunk("once");
	}
    	super.dispatchDraw(canvas);
    	//score.invalidate();
    	invalidate();
    }
    
    private void initInc() {
	inc = 1;
    }

    public void setScores(int[] s) {
	cur_score = 0;
    	scores = new int[s.length];
    	ctr = new int[s.length];
    	System.arraycopy( s, 0, scores, 0, s.length );
    	for(int i=0; i<scores.length; i++) {
    	    if ( scores[i] > 0 ) scores[i]++;
    	    else if ( scores[i] < 0 ) scores[i]--;
    	}
    	initInc();
    }

    //    @Override
    //public boolean onTouchEvent(MotionEvent event) {
    public void touched() {
	if ( cur_score<scores.length ) {
	    System.arraycopy( scores, 0, ctr, 0, scores.length );
	    for(int i=0; i<scores.length; i++) {
		TextView score = (TextView) getChildAt(2*i+1);
		if ( scores[i] > 0 )
		    score.setText(String.valueOf( scores[i]-1 ));
		else
		    score.setText(String.valueOf( scores[i]+1 ));
	    }
	    invalidate();
	}
	UriSound.playAnvil();
	//first=false;
	//return super.onTouchEvent(event);
    }

    private int cur_score=0, inc=0;
    private int ctr[];
    private int scores[];
    private boolean first=true;
}