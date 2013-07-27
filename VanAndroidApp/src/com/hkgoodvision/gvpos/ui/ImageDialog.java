package com.hkgoodvision.gvpos.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import com.hkgoodvision.gvpos.activity.R;


public class ImageDialog extends Activity implements OnTouchListener {

    private ImageView mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_popup);

        
        // Make us non-modal, so that others can receive touch events.
        getWindow().setFlags(LayoutParams.FLAG_NOT_TOUCH_MODAL, LayoutParams.FLAG_NOT_TOUCH_MODAL);

        // ...but notify us that it happened.
        getWindow().setFlags(LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);

        
        int pointEarn = getIntent().getIntExtra("point", 1);
        String companyName = getIntent().getStringExtra("companyName");

        mDialog = (ImageView)findViewById(R.id.your_image);
        TextView companyNameTxt = (TextView)findViewById(R.id.popup_companyname_text);
        TextView pointEarnTxt = (TextView)findViewById(R.id.popup_pointearn_text);
        companyNameTxt.setText(companyName);
        pointEarnTxt.setText(""+pointEarn);
        
//        mDialog.setClickable(true);
//
//        
//
//        //finish the activity (dismiss the image dialog) if the user clicks 
//        //anywhere on the image
//        mDialog.setOnClickListener(new OnClickListener() {
//        @Override
//        public void onClick(View v) {
//        	mDialog.setImageResource(R.drawable.food2);
//           // finish();
//        }
//        });

    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
      // If we've received a touch notification that the user has touched
      // outside the app, finish the activity.
      if (MotionEvent.ACTION_OUTSIDE == event.getAction()) {
        finish();
        return true;
      }

      // Delegate everything else to Activity.
      return super.onTouchEvent(event);
    }

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		 // If we've received a touch notification that the user has touched
	      // outside the app, finish the activity.
	      if (MotionEvent.ACTION_OUTSIDE == arg1.getAction()) {
	        finish();
	        return true;
	      }

	      // Delegate everything else to Activity.
	      return super.onTouchEvent(arg1);
	}
}