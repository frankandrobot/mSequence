package uris.apps.com;

import android.widget.*;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.*;
import android.util.AttributeSet;
import android.os.SystemClock;
import android.graphics.PorterDuff.Mode;

/* Game Screen */

public class GameGridView extends GridView {
    private Paint textPaintColor;
    private long startTime;
	
    public GameGridView (Context context, AttributeSet ats, int ds) {
	super(context, ats, ds);
	init();
    }

    public GameGridView (Context context) {
	super(context);
	init();
    }

    public GameGridView (Context context, AttributeSet attrs) {
	super(context, attrs);
	init();
    }

    private void init() {
	//setup canvas
	myResources = getResources();
    }

    public void deselect() {
	for(int i=0; i<getChildCount(); i++) {
	    button = (PhotoView) getChildAt(i);
	    button.deselect();
	} 
	redFlash = false;
	greenFlash = false;
    }

    @Override
	public void onDraw(Canvas canvas) {

	ImageView sel;

	if ( greenFlash ) {
	    if (SystemClock.uptimeMillis() < greenTime) {
		if ( greenSet == false ) {
		    sel = (ImageView) getChildAt(greenPosition);
		    sel.setColorFilter(0xFF00FF00,  
				   Mode.DARKEN);
		    sel.setBackgroundDrawable(
					      myResources.getDrawable
					      (R.drawable.button_green)
					      );
		    invalidate();
		    greenSet = true;
		}
	    }
	    else {
		sel = (ImageView) getChildAt(greenPosition);
		sel.clearColorFilter();
		sel.setBackgroundDrawable(
					  myResources.getDrawable
					  (R.drawable.button)
					  );
		invalidate();
	    }
	}
	if ( redFlash ) {
	    if (SystemClock.uptimeMillis() < redTime) {
		if ( redSet == false ) {
		    sel = (ImageView) getChildAt(redPosition);
		    sel.setColorFilter(0xFFFF0000,  
				   Mode.DARKEN);
		    sel.setBackgroundDrawable(
					      myResources.getDrawable
					      (R.drawable.button_red)
					      );
		    invalidate();
		    redSet = true;
		}
	    }
	    else {
		sel = (ImageView) getChildAt(redPosition);
		sel.clearColorFilter();
		sel.setBackgroundDrawable(
					  myResources.getDrawable
					  (R.drawable.button)
					  );
		invalidate();
	    }
	}

	// Use the TextView to render the text.
	super.onDraw(canvas);
	//canvas.restore();
	//	invalidate();
    }

    public void updateImages() {
	ImageAdapter ia = 
	    (ImageAdapter) getAdapter();
	ia.updateImages();
    }

    public void flashGreen(int position) {
	button = (PhotoView) getChildAt(position);
	button.flashGreen();
    }

    public void flashRed(int position) {
	button = (PhotoView) getChildAt(position);
	button.flashRed();
    }

    public void reset() { deselect(); }

    private Resources myResources;
    private PhotoView button;
}