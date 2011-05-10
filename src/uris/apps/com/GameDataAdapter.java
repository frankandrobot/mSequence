package uris.apps.com;

import android.widget.*;
import android.widget.AdapterView.*;
import android.app.Activity;
import android.os.Bundle;
import android.content.*;
import android.view.*;
import android.content.res.Resources;

public class GameDataAdapter extends BaseAdapter {
    private Context mContext;
    private GameEngine mTree;
    private int[] mChoices;

    public GameDataAdapter(Context c) {
        mContext = c;
    }

    public GameDataAdapter(Context c, GameEngine g) {
        mContext = c;
	mTree = g;
	mDisplayIds = new Integer[mTree.getNumberOfChoices()];	
	getGameButtons();
	changeGameButtons();
	notifyDataSetChanged();
    }

    public void getGameButtons() {}

    public void changeGameButtons() { //based on current choices
	if ( !mTree.isLevelComplete() ) {
	    //mTree.setCurrentChoices();
	    for( int i=0; i<mTree.getNumberOfChoices(); i++ ) {
		mDisplayIds[i] = mThumbIds[ mTree.getCurrentChoice(i) ];
	    }
	}
    }

    public String toString() {
	String tmp="Thumbs: ";
	for( int i=0; i<mTree.getNumberOfChoices(); i++ ) {
	    tmp += mTree.getCurrentChoice(i) + " ";
	    tmp += mDisplayIds[i] + " | ";
	}
	tmp += "\n";
	return tmp;
    }

    public int getCount() {
        return mDisplayIds.length;
    }

    public Object getItem(int position) {
        return mDisplayIds[position];
    }

    public long getItemId(int position) {
        return position;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) { // if it's not recycled, initialize
				   // some attributes
            imageView = new GameButtonView(mContext);
        } else {
            imageView = (GameButtonView) convertView;
        }
	Resources myResources = imageView.getResources();
	imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
	imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
	imageView.setPadding(8, 8, 8, 8);
	imageView.setBackgroundDrawable(
					myResources.getDrawable
					(R.drawable.button)
					);
        imageView.setImageResource(mDisplayIds[position]);
        return imageView;
    }

    public Integer getPictureId(int p) {
	return mDisplayIds[p];
    }

    // references to our images
    private Integer[] mThumbIds = {
	R.drawable.sample_0, R.drawable.sample_1,
	R.drawable.sample_2, R.drawable.sample_3,
	R.drawable.sample_4, R.drawable.sample_5,
	R.drawable.sample_6, R.drawable.sample_7,
	R.drawable.sample_8, R.drawable.sample_9,
	R.drawable.sample_10,R.drawable.sample_11
    };
    private Integer[] mDisplayIds;
    // = {
    // 	R.drawable.sample_0, R.drawable.sample_1
    // };
}