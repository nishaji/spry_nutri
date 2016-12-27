package com.Spry.Swipe;


import android.app.Activity;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

import com.Spry.nutritionix.Go.ListViewAdapter;


public class ActivitySwipeDetector implements View.OnTouchListener  {

    static final String logTag = "ActivitySwipeDetector";
    private Activity activity;
    boolean mSwiping = false,move_pass=false;
    MotionEvent eventgesture;

    Boolean Left=false,Right=false;
    boolean mItemPressed = false;
    static final int MIN_DISTANCE =800;

    ListView lv;
    ListViewAdapter listViewAdapter;
    BackgroundContainer mbackContainer;
    boolean bleft,bright;
    public GestureDetector gestureDetector;


    public ActivitySwipeDetector(Activity activity,BackgroundContainer Background,boolean left,boolean right){
        this.activity = activity;
        this.mbackContainer=Background;
        this.bleft=left;
        this.bright=right;
        gestureDetector=new GestureDetector(activity,new SingleTapConfirm());
    }
    private class SingleTapConfirm extends SimpleOnGestureListener {

        @Override
        public boolean onSingleTapConfirmed(MotionEvent event) {

            singleTap();

            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            // TODO Auto-generated method stub
            LongTap();
            super.onLongPress(e);
        }

	/*@Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        boolean result = false;
        try {
        	Log.i("Now The Fling", "Handling the Fling as Well");
            float diffY = e2.getY() - e1.getY();
            float diffX = e2.getX() - e1.getX();
            if (Math.abs(diffX) > Math.abs(diffY)) {
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                    	if(bright)
                    	onLeftToRightSwipe();
                    } else {
                    	if(bleft)
                    	onRightToLeftSwipe();
                    }
                }
            } else {
                if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        onTopToBottomSwipe();
                    } else {
                        onBottomToTopSwipe();
                    }
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }*/

    }
    public void onRightToLeftSwipe(){

    }
    public void singleTap() {
        // TODO Auto-generated method stub

    }
    public void LongTap() {
        // TODO Auto-generated method stub

    }
    public void onLeftToRightSwipe(){
        // TODO Auto-generated method stub
    }

    public void onTopToBottomSwipe(){
        // TODO Auto-generated method stub
    }

    public void onBottomToTopSwipe(){
        // TODO Auto-generated method stub
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // TODO Auto-generated method stub
        return false;
    }

/*@Override
@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public boolean onTouch(final View v, final MotionEvent event) {
	   this.eventgesture=event;
       int mSwipeSlop = -1;

       if (gestureDetector.onTouchEvent(event)) {
           // single tap

           return true;
       } else {
           // your code for move and drag



       switch(event.getAction()){
        case MotionEvent.ACTION_DOWN: {
            downX = event.getX();
            downY = event.getY();
            return true;
        }
        case MotionEvent.ACTION_CANCEL:
            v.setAlpha(1);
            v.setTranslationX(0);
            mItemPressed = false;
            break;
        case MotionEvent.ACTION_MOVE:
            {
                float x = event.getX() + v.getTranslationX();
                float deltaX = x - downX;
                float deltaXAbs = Math.abs(deltaX);
                Log.i("Value of Delta",String.valueOf(deltaXAbs)+"\t"+String.valueOf(deltaX));
                if ((Integer.valueOf(String.format("%.0f",deltaXAbs))) != 0) {
                    if (deltaXAbs > mSwipeSlop) {
                        mSwiping = true;
                        mbackContainer.showBackground(v.getTop(), v.getHeight());
                        Log.i("Move", "Move");
                    }
                }

                if (mSwiping) {
                    v.setTranslationX((x - downX));
                    v.setAlpha(1 - deltaXAbs / v.getWidth());
                    Log.i("MOving", "Moving");
                }
            }
            break;

        case MotionEvent.ACTION_UP: {

            // User let go - figure out whether to animate the view out, or back into place
            if (mSwiping) {
                float x = event.getX() + v.getTranslationX();
                float deltaX = x - downX;
                float deltaXAbs = Math.abs(deltaX);
                float fractionCovered;
                float endX;
                float endAlpha;
                final boolean remove;
                if (deltaXAbs > v.getWidth() / 4) {
                    // Greater than a quarter of the width - animate it out
                    fractionCovered = deltaXAbs / v.getWidth();

                    endX = deltaX < 0 ? -v.getWidth() : v.getWidth();
                    endAlpha = 0;
                    Log.i("Fraction covered df", String.valueOf(fractionCovered)+"\t"+String.valueOf(v.getWidth())
                    		+"\t"+String.valueOf(deltaX)
                    		);
                    if(deltaX<0 && deltaX<(-v.getWidth()+MIN_DISTANCE))
                    {
                    	Right=true;
                    	Left=false;
                     }
                    if(deltaX>0 && deltaX>(v.getWidth()-MIN_DISTANCE))
                    {


                    		Left=true;
                    		Right=false;

                    }
                    remove = true;
                } else {
                    // Not far enough - animate it back
                    fractionCovered = 1 - (deltaXAbs / v.getWidth());
                   Log.i("Fraction covered", String.valueOf(fractionCovered)+String.valueOf(v.getWidth()));
                    endX = 0;
                    endAlpha = 1;
                    remove = false;


                }
                // Animate position and alpha of swiped item
                // NOTE: This is a simplified version of swipe behavior, for the
                // purposes of this demo about animation. A real version should use
                // velocity (via the VelocityTracker class) to send the item off or
                // back at an appropriate speed.

                long duration = (int) ((1 - fractionCovered) * SWIPE_DURATION);

                if( android.os.Build.VERSION.SDK_INT>=18){
                	if(bleft)
                	{
                	v.animate().setDuration(duration).
                        alpha(endAlpha).translationX(endX).
                        withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                // Restore animated values
                                v.setAlpha(1);
                                v.setTranslationX(0);
                                if (remove) {

                                	if(Right)
                                	onLeftToRightSwipe();
                                if(Left)
                                	onRightToLeftSwipe();

                                } else {
                                	mbackContainer.hideBackground();
                                    mSwiping = false;

                               }
                            }
                        });

                }
                else
                	{
                	  if (remove) {
                     	 onLeftToRightSwipe();
                     	 onRightToLeftSwipe();
                     } else {
                     	mbackContainer.hideBackground();
                         mSwiping = false;

                     }
                	}
                	}




                if( android.os.Build.VERSION.SDK_INT>=18){
            	if(bright)
            	{
            	v.animate().setDuration(duration).
                    alpha(endAlpha).translationX(endX).
                    withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            // Restore animated values
                            v.setAlpha(1);
                            v.setTranslationX(0);
                            if (remove) {

                            	if(Right)
                            	onLeftToRightSwipe();
                            if(Left)
                            	onRightToLeftSwipe();

                            } else {
                            	mbackContainer.hideBackground();
                                mSwiping = false;

                           }
                        }
                    });

            }
            else
            	{
            	  if (remove) {
                 	 onLeftToRightSwipe();
                 	 onRightToLeftSwipe();
                 } else {
                 	mbackContainer.hideBackground();
                     mSwiping = false;

                 }
            	}
            }
            }
            else
            {
            	mSwiping=false;
            }
        }
        mItemPressed = false;
        break;
    default:
        return false;
    }
       }
	return mItemPressed;
}
*/

}