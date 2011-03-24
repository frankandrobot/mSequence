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
    }

    @Override
	public void onDraw(Canvas canvas) {

	super.onDraw(canvas);
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