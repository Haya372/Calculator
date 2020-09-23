package com.myApp.scientificcaliculator;

import android.content.Context;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;



public class MyEditText extends androidx.appcompat.widget.AppCompatEditText {
    public MyEditText(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
        setCustomSelectionActionModeCallback(new NoSelectionMode());
    }

    @Override
    public boolean performClick(){
        Log.d("test","clicked");
        cancelLongPress();
        return super.performClick();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        switch(event.getAction()){
            case MotionEvent.ACTION_UP:
                performClick();
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }


    static class NoSelectionMode implements ActionMode.Callback {
        @Override public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // ダブルタップ時のキーボード表示抑止
            return false;
        }

        @Override public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return false;
        }

        @Override public void onDestroyActionMode(ActionMode mode) {
        }
    }
}
