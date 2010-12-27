package uris.apps.com;

import android.widget.*;
import android.widget.AdapterView.*;
import android.app.Activity;
import android.os.Bundle;
import android.view.*;
import android.view.View.*;
import android.content.*;

public class OptionsMenu extends Activity
{
    // private GridView mGridView;
    // private TextView mTextView;
    // private TreeGenerator mTree;
    // private ImageAdapter mAdapter;
    private Spinner difficultySpinner;
    private ArrayAdapter<CharSequence> adapter;

    // Settings
    Intent settings;
    protected int level=TreeGenerator.EASY;

    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	setContentView(R.layout.options_difficulty);

	//Setup adapter for spinner 
	adapter = ArrayAdapter.createFromResource
	     (this,
	      R.array.difficulty_array,
	     android.R.layout.simple_spinner_item);
	adapter.setDropDownViewResource
	    ( android.R.layout.simple_spinner_dropdown_item );
	
	//Setup actual spinner
	difficultySpinner = (Spinner) 
	    findViewById(R.id.options_difficulty_spinner);
	difficultySpinner.setAdapter( adapter );
	difficultySpinner.setOnItemSelectedListener
	    ( new OptionsMenuSelect(this,adapter) );

	//Setup OK button
	settings = new Intent();
	final Button okDiffOptions = 
	    (Button) findViewById(R.id.options_difficulty_ok);
	okDiffOptions.setOnClickListener(new OnClickListener() {
		public void onClick(View v) {
		    OptionsMenu.this.settings.putExtra
			("level", OptionsMenu.this.level);
		    setResult(RESULT_OK, OptionsMenu.this.settings);
		    finish();
		}
	     });
    }

    public class OptionsMenuSelect implements OnItemSelectedListener {
        ArrayAdapter<CharSequence> mLocalAdapter;
        Activity mLocalContext;
        public OptionsMenuSelect(Activity c, 
				 ArrayAdapter<CharSequence> ad) {
	    this.mLocalContext = c; /* Do I need this? */
	    this.mLocalAdapter = ad;
        }
        public void onItemSelected(AdapterView<?> parent, 
				   View v, int pos, long row) {
            //OptionsMenu.this.mPos = pos;
            String selection = 
		parent.getItemAtPosition(pos).toString();
	    if ( selection.contentEquals("Easy") ) {
		OptionsMenu.this.level = TreeGenerator.EASY;
	    }
	    else if ( selection.contentEquals("Medium") ) {
		OptionsMenu.this.level = TreeGenerator.MEDIUM;
	    }
	    else if ( selection.contentEquals("Hard") ) {
		OptionsMenu.this.level = TreeGenerator.HARD;
	    }
	    Toast.makeText(OptionsMenu.this, selection, Toast.LENGTH_SHORT).show();
            // TextView resultText = (TextView)findViewById(R.id.SpinnerResult);
            // resultText.setText(SpinnerActivity.this.mSelection);
        }
        public void onNothingSelected(AdapterView<?> parent) { }
    }
}
