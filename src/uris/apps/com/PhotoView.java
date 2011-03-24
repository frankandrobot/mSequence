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
	greenFrameCounter = 8;
	invalidate();
    }

    public void flashRed() {
	redFrameCounter = 8;
	invalidate();
    }

    private int greenFrameCounter = -1;
    private int redFrameCounter = -1;

}