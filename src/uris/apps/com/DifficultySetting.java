package uris.apps.com;

import android.widget.*;
import android.widget.AdapterView.*;
import android.app.Activity;
import android.os.Bundle;
import android.view.*;
import android.view.View.*;
import android.content.*;

public class DifficultySetting extends Activity
{
    // private GridView mGridView;
    // private TextView mTextView;
    // private TreeGenerator mTree;
    // private ImageAdapter mAdapter;
    private Spinner difficultySpinner;
    private ArrayAdapter adapter;

    // Settings
    Intent settings;

    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	
	setContentView(R.layout.main_menu);

	// difficultySpinner = (Spinner) 
	//     findViewById(R.id.options_difficulty_spinner);
	
	//Setup spinner 
	// adapter = new ArrayAdapter(this, 
	// 			   android.R.layout.simple_spinner_item, 
	// 			   R.array.difficulty_array);        
		
	// difficultySpinner.setAdapter(adapter);
	// playButton.setOnClickListener(new OnClickListener() {
	// 	public void onClick(View v) {
	// 	    // settings.putExtra("play", "go");
	// 	    // setResult(RESULT_OK, settings);
	// 	    // finish();
	// 	}
	//     });
    }
}
