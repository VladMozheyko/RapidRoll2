package com.example.my_framework.my_framework;

import android.view.MotionEvent;
import android.view.View;

public class TouchListener implements View.OnTouchListener {

    private float touchX;
    private float touchY;
    private boolean isTouchDown = false;
    private boolean isTouchUp = false;
    private float sceneWidth;
    private float sceneHeight;


    public TouchListener (View view, float sceneWidth, float sceneHeight){
        view.setOnTouchListener(this);
        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        synchronized (this){
            isTouchDown = false;
            isTouchUp = false;
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    touchX = v.getX()*sceneWidth;
                    touchY = v.getY()*sceneHeight;
                    isTouchDown = true;
                    isTouchUp = false;
                    break;
                case MotionEvent.ACTION_UP:
                    touchX = v.getX()*sceneWidth;
                    touchY = v.getY()*sceneHeight;
                    isTouchUp = true;
                    isTouchDown = false;
                    break;

            }
        }
        return false;
    }
    public boolean getTouchUp(int x, int y, int touchWidth, int touchHeight){
        if(isTouchUp == true){
            if(touchX >= x && touchX <= x + touchWidth-1 && touchY <= y && touchY >= y - touchHeight-1);
            isTouchUp = false;
            return true;
        }
        return false;
    }
    public boolean getTouchDown(int x, int y, int touchWidth, int touchHeight){
        if(isTouchDown == true){
            if(touchX >= x && touchX <= x + touchWidth-1 && touchY <= y && touchY >= y - touchHeight-1);
            isTouchDown = false;
            return true;
        }
        return false;
    }
}
