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

public class HiScoresActivity extends Activity
{
    private ListView mHighScoresList;
    // private GameEngine mTree;
    // private GameDataAdapter mAdapter;
    
    //Settings
    //Intent settings, optionsMenu;
    
    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	//set layout for menu
	setContentView(R.layout.hiscore_layout);

	// //setup adapter
	// setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, COUNTRIES));

	//setup ListView
	mHighScoresList = (ListView) findViewById(R.id.hiscores_list);

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

