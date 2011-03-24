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

	// if (greenFrameCounter > 0) {
	//     canvas.drawColor(Color.GREEN);
	//     greenFrameCounter--;
	//     invalidate();
	// }
	// else if (redFrameCounter > 0) {
	//     canvas.drawColor(Color.RED);
	//     redFrameCounter--;
	//     invalidate();
	// }

	super.onDraw(canvas);
	//canvas.restore();
	//	invalidate();
    }

    public void flashGreen() {
	greenTime = SystemClock.uptimeMillis() + 1000;
	greenPosition = position;
	greenFlash = true;
	greenSet = false;
	invalidate();
    }

    public void flashRed() {
	redTime = SystemClock.uptimeMillis() + 1000;
	redPosition = position;
	redFlash = true;
	redSet = false;
	invalidate();
    }

    public void deselect() {
	sel.clearColorFilter();
	sel.setBackgroundDrawable(
				  myResources.getDrawable
				  (R.drawable.button)
				  );

    }

    private Resources myResources;
    private int greenFrameCounter = -1;
    private int redFrameCounter = -1;

}