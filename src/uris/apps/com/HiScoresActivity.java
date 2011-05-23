// Gameplan:

//HI scores:
// 1. score level
// 2. ....

package uris.apps.com;

import android.widget.*;
import android.widget.AdapterView.*;
import android.app.Activity;
import android.os.Bundle;
import android.view.*;
import android.view.View.*;
import android.content.*;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.content.Context;
import java.lang.System;
import java.lang.String;
             
public class HiScoresActivity extends Activity
{
    private ListView mHiScoresList;
    private ArrayAdapter mHiScoresAdapter;
    static final String[] HI_SCORES = new String[50];
    static final String HI_SCORES_FILE = "hiScores";
    // private GameEngine mTree;
    // private GameDataAdapter mAdapter;
    
    //Settings
    //Intent settings, optionsMenu;
    
    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	//set layout for menu
	setContentView(R.layout.hiscore_table);

	//setup ListView
	mHiScoresList = (ListView) findViewById(R.id.hiscores_list);

	//get hi scores list
	final int finalScore = Score.total_score;
	SharedPreferences hiScores = getSharedPreferences(HI_SCORES_FILE, 0);
	boolean newHighScore=false;
	int hiLen=0;
	for (int i=0; i<50; i++) HI_SCORES[i] = "-";

	
	for (int i=0; i<50; i++) {
	    HI_SCORES[i] = hiScores.getString("hiscore"
					      + String.valueOf(i), 
					      "-");
	    if ( HI_SCORES[i] == "-" ) newHighScore=true;
	    else if ( Score.total_score >= Integer.parseInt( HI_SCORES[i] ))  
		newHighScore=true;
	    if ( !newHighScore ) ++hiLen;
	}
	if ( newHighScore ) {
	    
	    //copy hi scores up to new
	    final String[] newHighScores = new String[50];
	    System.arraycopy(HI_SCORES,0,newHighScores,0,hiLen);
	    //add new hi score
	    newHighScores[hiLen] = String.valueOf(finalScore);
	    //copy rest of hi scores
	    System.arraycopy(HI_SCORES,hiLen,newHighScores,hiLen+1,50-hiLen);

	    SharedPreferences.Editor editor = hiScores.edit();
	    for (int i=0; i<50; i++) {
		editor.putString("hiscore"
				 + String.valueOf(i), 
				 newHighScores[i]);
	    }
	    // Commit the edits!
	    editor.commit();
	    System.arraycopy(newHighScores,0,HI_SCORES,0,50);

	}
	    
	//setup adapter
	mHiScoresList = (HiScoresListView) findViewById(R.id.hiscores_list);
	mHiScoresAdapter = new ArrayAdapter<String>
	    (this,android.R.layout.simple_list_item_1,HI_SCORES);
	mHiScoresList.setAdapter(mHiScoresAdapter);


 	//default settings
	// settings = new Intent(this, uris.apps.com.PlayGame.class);
	// settings.putExtra("level", GameEngine.EASY);
	
	// //create View for Difficulty setting
	// optionsMenu = new Intent(this, uris.apps.com.OptionsMenu.class);

	// final ImageView title = (ImageView) findViewById(R.id.mytitle);

	// //click listeners for buttons
	// final Button playButton = (Button) findViewById(R.id.play);
	// final Button optionsButton = (Button) findViewById(R.id.options);
		
	// playButton.setOnClickListener(new OnClickListener() {
	// 	public void onClick(View v) {
	// 	    ScoreReport.GAME_OVER=false;
	// 	    startActivityForResult(settings, PLAY_GAME);
	// 	}
	//     });

	// optionsButton.setOnClickListener(new OnClickListener() {
	// 	public void onClick(View v) {
	// 	    startActivityForResult(optionsMenu, OPTIONS_MENU);
	// 	}
	//     });
    }

    // @Override
    // 	protected void onActivityResult(int requestCode, 
    // 					int resultCode, 
    // 					Intent data){
    // 	// See which child activity is calling us back.
    // 	switch (requestCode) {
    //     case OPTIONS_MENU: {
    // 	    if (resultCode == RESULT_CANCELED){
    //             Toast.makeText(InterArt.this, "Cancelled", Toast.LENGTH_SHORT).show();
    //        } 
    //         if (resultCode == RESULT_OK) {
    //             //getSettings();
    // 		int level= data.getIntExtra("level",GameEngine.EASY);
    // 		Toast.makeText(this, String.valueOf(level), 
    // 			       Toast.LENGTH_SHORT).show();
    // 		settings.putExtra("level", level);
    //         }
    // 	}
    //     default:
    //         break;
    // 	}
    // }
}

