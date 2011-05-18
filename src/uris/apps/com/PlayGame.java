package uris.apps.com;

import android.widget.*;
import android.widget.AdapterView.*;
import android.app.Activity;
import android.os.Bundle;
import android.view.*;
import android.view.View.*;
import android.content.*;
import android.graphics.PorterDuff.Mode;

/* the PlayGame class coordinates: (1) the GameEngine (which is
 * actually the underlying board, I don't know why I called it
 * GameEngine), (2) the GameGridView that displays the pictures, (3)
 * the progress bar, (4) the running clock. */

public class PlayGame extends Activity
{
    private GameGridView mGameGridView;
    private GameEngine mGameEngine;
    private GameDataAdapter mGameAdapter;
    private GameClock mGameClock;
    private ProgressBarView mProgress;

    // Menu
    static final private int RESTART = Menu.FIRST;
    static final private int BACK = Menu.FIRST + 1;

    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	//get settings from OptionsMenu
	Intent settings=getIntent();
	int difficulty = settings.getIntExtra("level",
					      GameEngine.EASY);
	//create progress bar - must be called before setcontentview
	requestWindowFeature(Window.FEATURE_PROGRESS);

	setContentView(R.layout.play_game);

	//Set up GameEngine - this is the underlying game engine
	//difficulty = GameEngine.HARD;
	mGameEngine = new GameEngine(12, difficulty, 3); // no art pieces,
						     // difficulty, no
						     // of stages
	//Set up RunningClock - this should be part of the View, but it's not
	mGameClock = (GameClock) findViewById(R.id.running_clock);
	mGameEngine.setGameClock(mGameClock);
	mGameClock.setGameEngine(mGameEngine);
	
	//Set up GridView - this is the View for the GameEngine
	mGameGridView = (GameGridView) findViewById(R.id.game_grid_view);
	mGameAdapter = new GameDataAdapter(this, mGameEngine);
	mGameGridView.setAdapter(mGameAdapter);
	
	//start clock!
	//See onActivityResult();
	//mGameClock.startCountdownTimer();

	//Start GO screen 

	//this starts the GO screen but unfortunately, the rest of the
	//constructor gets called
	Intent goScreen = new 
	    Intent(this,uris.apps.com.GoScreenActivity.class);
	startActivityForResult(goScreen,InterArt.GO_SCREEN);

	//Call this method when user selects an image 
	mGameGridView.setOnItemClickListener(new OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, 
					View v, 
					int position, 
					long id) {
		    GameEngine mGameEngine = PlayGame.this.mGameEngine;

		    PlayGame.this.mGameGridView.deselect(); //if any
		
		    //check answer
		    boolean result = mGameEngine.checkAnswer(position);
		    String text = "";
		    if ( result ) { // if answer correct
			text = "Correct!";
			PlayGame.this.mGameEngine.updateScores();
			PlayGame.this.mGameEngine.gotoNextStage();
			//update text view
			PlayGame.this.mGameGridView.flashGreen(position);
			PlayGame.this.mProgress.next();
			}
		    else {
			text = "Almost!";
			PlayGame.this.mGameEngine.updateScores();
			PlayGame.this.mGameEngine.resetCurrentLevel();
			PlayGame.this.mGameGridView.flashRed(position);
			PlayGame.this.mProgress.reset();
		    }

		    //check if level complete
		    if ( mGameEngine.isLevelComplete() ) {
			PlayGame.this.mGameClock.stop();

			//Report scores for score screen
			Score.reportScores(false, 
					   (int) PlayGame.this
					   .mGameClock.getTimeLeft(),
					   PlayGame.this
					   .mGameEngine.getGuessPts()
					   );
			Score.setTotalScore();
			//Call the score total screen

			//Unfortunately, the rest of this function
			//gets called too

			//Create intent
			Intent scoreReport = new 
			    Intent(
				   PlayGame.this, 
				   uris.apps.com.ScoreReport.class
				   );
			scoreReport.putExtra("currentscore",
					     Score.current_score);
			scoreReport.putExtra("timebonus",Score.time_bonus);
			scoreReport.putExtra("guessbonus",Score.guess_bonus);
			scoreReport.putExtra("totalscore",Score.total_score);
			
			//start score screen
			startActivityForResult(scoreReport,InterArt.SCORES);
			
			//Post-processing
			PlayGame.this.mGameEngine.gotoNextLevel();
			PlayGame.this.mGameGridView.reset();
			//GameClock is in onActivityResult()
			PlayGame.this.mProgress.
			    setCount( mGameEngine.totalStages()+1 );
			PlayGame.this.mProgress.setCurrent( 1 );

		    }

		    //update pictures
		    PlayGame.this.mGameGridView.updateGameButtons();

		    //update progress bar
		    PlayGame.this.updateProgressBar();
		    
		    //debug
		    MyDebug.PlayGameLogv( 
					 mGameEngine.toString() + "\n" +
					 mGameEngine.currentStage() + " / "
					 + mGameEngine.totalStages()
					 + mGameAdapter.toString() + "\n" 
					 + Score.mytoString()
					  );
		    MyDebug.PlayGameLogv(text + " " + position);

		}
	    });

	MyDebug.PlayGameLogv("Level: "+String.valueOf(difficulty));
	MyDebug.PlayGameLogv(mGameEngine.toString() );

	//init progress bar
	mProgress = (ProgressBarView) findViewById(R.id.progress_bar);
	mProgress.setCount( mGameEngine.totalStages()+1 );
	mProgress.setCurrent( 1 );

	getWindow().setFeatureInt(Window.FEATURE_PROGRESS, 0);
    }

    public void updateProgressBar() {
	//10000 / no_stages = x / currentStage
	int x = 10000 / mGameEngine.totalStages() 
	    * (mGameEngine.currentStage()-1); 
	getWindow().setFeatureInt(
				  Window.FEATURE_PROGRESS, 
				  x);
		    
    }

    //----------------------------------
    //Setup menus
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
	super.onCreateOptionsMenu(menu);
	
	menu.add(0, RESTART, Menu.NONE, "Restart");
	menu.add(0, BACK, Menu.NONE, "Back");

	return true;
    }

    @Override
	public boolean onPrepareOptionsMenu(Menu menu) {
	super.onPrepareOptionsMenu(menu);
	
	menu.findItem(RESTART).setVisible(true);
	menu.findItem(BACK).setVisible(true);
	return true;
    }

    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
	super.onOptionsItemSelected(item);
	
	switch (item.getItemId()) {
	case (RESTART): {
	    //mGameEngine.reset();
	    mGameGridView.updateGameButtons();
	    //mGameClock.initStartTime();
	    updateProgressBar();
	    return true;
	}
	case (BACK) : {
	    Intent dummy = new Intent();
	    setResult(RESULT_OK,dummy);
	    finish();
	    return true;
	}

	}
	
	return false;
    }

    //This code gets after the start screen or the score screen
    @Override
    protected void onActivityResult(int requestCode, 
				    int resultCode, 
				    Intent returnedData) {

	//mGameClock.initStartTime();
	//mGameClock.resume();
	
	// See which child activity is calling us back.
	switch (requestCode) {
	case InterArt.GO_SCREEN: {
	    mGameClock.startCountdownTimer();
	}
	case InterArt.SCORES: {
	    if ( resultCode == InterArt.GAME_OVER ) {
		// launch game over screen and end this game
		Toast.makeText(PlayGame.this, "Game over! booya", Toast.LENGTH_SHORT).show();
	    }
	    else {
		PlayGame.this.mGameClock.restart();
		PlayGame.this.mGameClock.resume();
	    }
	}
	default: break;
	}
	//     if (resultCode == RESULT_CANCELED){
	// 	//
	//     } 
	//     if (resultCode == RESULT_OK) {
	// 	//get returned data
	// 	int intValue= returnedData.getIntExtra(<key>,<defaultValue>);
	// 	String stringValue = returnedData.getStringExtra(<key>);
	//     }
	// }
	// default:
	//     break;
	// }
    }

    public void endGame() {
	Score.reportScores(GameEngine.GAME_OVER,
			   (int) mGameClock.getTimeLeft(),
			   mGameEngine.getGuessPts()
			   );
	Score.setTotalScore();
	
	//create intent for score screen
	Intent scoreReport = new 
	    Intent(
		   this, 
		   uris.apps.com.ScoreReport.class
		   );
	scoreReport.putExtra("currentscore",
			     Score.current_score);
	scoreReport.putExtra("timebonus",Score.time_bonus);
	scoreReport.putExtra("guessbonus",Score.guess_bonus);
	scoreReport.putExtra("totalscore",Score.total_score);
	scoreReport.putExtra("gameover",InterArt.GAME_OVER);	

	//launch score screen
	startActivityForResult(scoreReport,InterArt.SCORES);
    }
    
}
