package uris.apps.com;

import android.widget.*;
import android.widget.AdapterView.*;
import android.app.Activity;
import android.os.Bundle;
import android.view.*;
import android.view.View.*;
import android.content.*;
import android.graphics.PorterDuff.Mode;

/* the PlayGame class coordinates: (1) the TreeGenerator (which is
 * actually the underlying board, I don't know why I called it
 * TreeGenerator), (2) the GameGridView that displays the pictures, (3)
 * the progress bar, (4) the running clock. */

public class PlayGame extends Activity
{
    private GameGridView mGameGridView;
    private TreeGenerator mTree;
    private GameDataAdapter mAdapter;
    private ClockTextView mRunningClock;
    private ProgressBarView mProgress;

    // Menu
    static final private int RESTART = Menu.FIRST;
    static final private int BACK = Menu.FIRST + 1;

    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	//get settings from OptionsMenu
	Intent settings=getIntent();
	int difficulty = settings.getIntExtra("level",
					      TreeGenerator.EASY);
	//create progress bar - must be called before setcontentview
	requestWindowFeature(Window.FEATURE_PROGRESS);

	setContentView(R.layout.play_game);

	//Start GO screen 

	//this starts the GO screen but unfortunately, the rest of the
	//constructor gets called
	Intent goScreen = new 
	    Intent(this,uris.apps.com.GoScreenActivity.class);
	startActivityForResult(goScreen,InterArt.GO_SCREEN);

	//Set up TreeGenerator - this is the underlying game engine
	//difficulty = TreeGenerator.HARD;
	mTree = new TreeGenerator(12, difficulty, 3); // no art pieces,
						     // difficulty, no
						     // of stages
	
	//Set up GridView - this is the View for the TreeGenerator
	mGameGridView = (GameGridView) findViewById(R.id.game_grid_view);
	mAdapter = new GameDataAdapter(this, mTree);
	mGameGridView.setAdapter(mAdapter);

	//Set up RunningClock - this should be part of the View, but it's not
	mRunningClock = (ClockTextView) findViewById(R.id.running_clock);

	//mRunningClock.initStartTime();
	
	//Call this method when user selects an image 
	mGameGridView.setOnItemClickListener(new OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, 
					View v, 
					int position, 
					long id) {
		    TreeGenerator mTree = PlayGame.this.mTree;
		
		    //check answer
		    boolean result = mTree.checkAnswer(position);
		    String text = "Almost!";

		    PlayGame.this.mGameGridView.deselect(); //if any

		    if ( result ) { // if answer correct
			text = "Correct!";
			//update text view
			PlayGame.this.mGameGridView.flashGreen(position);
			PlayGame.this.mProgress.next();
		    }
		    else {
			 PlayGame.this.mGameGridView.flashRed(position);
			 PlayGame.this.mProgress.reset();
		    }

		    //check if game complete
		    if ( mTree.gameComplete() ) {
			PlayGame.this.mRunningClock.stop();
			//tally score/calculate scores
			//: max_score = no_stages * 1000;
			//: ideal_time = 2 * no_stages + 4;
			//: f(time,stage) = 1/time * max_score * ideal_time;

			float time = PlayGame.this.mRunningClock
			    .getRunningTime();
			float max_score = mTree.getStages() * 1000.0f;
			float ideal_time = 2.0f * mTree.getStages() + 4.0f;
			Score.time_bonus = (int) (1.0f 
						  / time * max_score 
						  * ideal_time);

			//Call the score total screen

			//Unfortunately, the rest of this function
			//gets called too
			Intent scoreReport = new 
			    Intent(
				   PlayGame.this, 
				   uris.apps.com.ScoreReport.class
				   );
			scoreReport.putExtra("score",Score.score);
			scoreReport.putExtra("incorrect",Score.incorrect_penal);
			scoreReport.putExtra("timebonus",Score.time_bonus);
			scoreReport.putExtra("noerrorbonus",Score.error_bonus);
			startActivityForResult(scoreReport,InterArt.SCORES);

			PlayGame.this.mGameGridView.reset();
			PlayGame.this.mRunningClock.initStartTime();
			PlayGame.this.mRunningClock.resume();
			mTree.nextGame();
			PlayGame.this.mProgress.
			    setCount( mTree.totalStages()+1 );
			PlayGame.this.mProgress.setCurrent( 1 );
		    }

		    //update pictures
		    PlayGame.this.mGameGridView.updateImages();

		    //update progress bar
		    PlayGame.this.updateProgressBar();
		    
		    //debug
		    MyDebug.PlayGameLogv( 
					 mTree.toString() + "\n" +
					 mTree.currentStage() + " / "
					 + mTree.totalStages()
					 + mAdapter.toString() + "\n" 
					 + Score.mytoString()
					  );
		    MyDebug.PlayGameLogv(text + " " + position);

		}
	    });

	MyDebug.PlayGameLogv("Level: "+String.valueOf(difficulty));
	MyDebug.PlayGameLogv(mTree.toString() );

	//init progress bar
	mProgress = (ProgressBarView) findViewById(R.id.progress_bar);
	mProgress.setCount( mTree.totalStages()+1 );
	mProgress.setCurrent( 1 );

	getWindow().setFeatureInt(Window.FEATURE_PROGRESS, 0);
    }

    public void updateProgressBar() {
	//10000 / no_stages = x / currentStage
	int x = 10000 / mTree.totalStages() 
	    * (mTree.currentStage()-1); 
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
	    mTree.reset();
	    mGameGridView.updateImages();
	    mRunningClock.initStartTime();
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

	mRunningClock.initStartTime();
	mRunningClock.resume();
	
	// // See which child activity is calling us back.
	// switch (requestCode) {
	// case <requestCode>: {
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
    
}
