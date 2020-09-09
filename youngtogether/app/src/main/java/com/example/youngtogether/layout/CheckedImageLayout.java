package com.example.youngtogether.layout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.ImageView;

@SuppressLint("AppCompatCustomView")
public class CheckedImageLayout extends ImageView implements Checkable{
    private static final String TAG = "CheckedImageLayout";
    private boolean isChecked = false;

    private static final int[] CHECKED_STATE_SET = {
            android.R.attr.state_checked
    };

    public CheckedImageLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public CheckedImageLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CheckedImageLayout(Context context) {
        super(context);
    }

    public void setChecked(boolean check){
        isChecked = check;
        refreshDrawableState();
    }

    public boolean isChecked(){
        return isChecked;
    }

    public void toggle() {
        setChecked(!isChecked);
    }


    @Override
    public int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }
}