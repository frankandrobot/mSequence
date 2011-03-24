package uris.apps.com;

import android.content.res.Resources;
import android.graphics.*;
import android.util.AttributeSet;
import android.os.SystemClock;
import android.widget.*;
import android.widget.AdapterView.*;
import android.app.Activity;
import android.os.Bundle;
import android.content.*;
import android.util.AttributeSet;
import android.view.*;

public class PhotoView extends ImageView {
    public PhotoView (Context context, AttributeSet ats, int ds) {
	super(context, ats, ds);
	init();
    }

    public PhotoView (Context context) {
	super(context);
	init();
    }

    public PhotoView (Context context, AttributeSet attrs) {
	super(context, attrs);
	init();
    }

    private void init() {
	myResources = getResources();
    }

    @Override
	public void onDraw(Canvas canvas) {

	if ( greenFlash ) {
	    if (SystemClock.uptimeMillis() > greenTime) {
		deselect();
	    }
	}
	else if ( redFlash ) {
	    if (SystemClock.uptimeMillis() > redTime) {
		deselect();
	    }
	}

	// Use the TextView to render the text.
	super.onDraw(canvas);
	//canvas.restore();
	//	invalidate();
    }

    public void flashGreen() {
	greenTime = SystemClock.uptimeMillis() + 1000;
	greenFlash = true;
	greenSet = false;

	setColorFilter(0xFF00FF00,  
		       Mode.DARKEN);
	setBackgroundDrawable(
			      myResources.getDrawable
			      (R.drawable.button_green)
			      );
	invalidate();
    }

    public void flashRed() {
	redTime = SystemClock.uptimeMillis() + 1000;
	redFlash = true;
	redSet = false;
	setColorFilter(0xFFFF0000,  
		       Mode.DARKEN);
	setBackgroundDrawable(
			      myResources.getDrawable
			      (R.drawable.button_red)
			      );
	invalidate();
    }

    public void deselect() {
	clearColorFilter();
	setBackgroundDrawable(
			      myResources.getDrawable
			      (R.drawable.button)
			      );
	invalidate();
	redFlash = false;
	greenFlash = false;
    }

    private Resources myResources;
    private int redPosition=0,greenPosition=0;
    private float greenTime=0,redTime=0;
    private boolean greenFlash=false,redFlash=false;
    private boolean greenSet=false,redSet=false;
}