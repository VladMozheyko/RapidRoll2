package com.example.my_framework.my_framework;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class ExplosionAnimated {
    private Bitmap bitmap;
    private Rect rect;
    private int frameNum;
    private int currentFrame;
    private long frameTicker;
    private int framePeriod;

    private int spriteWidth;
    private int spriteHeight;

    private int x;
    private int y;

    public ExplosionAnimated(Bitmap bitmap, int x, int y, int width, int height, int fps, int frameCount) {
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
        currentFrame = 0;
        frameNum = frameCount;
        spriteWidth = bitmap.getWidth()/frameCount;
        spriteHeight = bitmap.getHeight();
        rect = new Rect(0, 0, width, height);
        framePeriod = 1000/fps;
        frameTicker = 0l;

    }
}
