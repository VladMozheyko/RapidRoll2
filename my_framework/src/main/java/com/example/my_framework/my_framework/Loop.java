package com.example.my_framework.my_framework;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Loop extends SurfaceView implements Runnable {

    private final float FPS = 60;
    private final float SECOND = 1000000000;
    private final float UPDATE_TIME = SECOND/FPS;

    private boolean running = false;
    private Thread gameThread;
    private Core core;
    private Bitmap frameBuffer;
    private SurfaceHolder surfaceHolder;
    private Canvas canvas;
    private Rect rect;

    public Loop(Core core, Bitmap bitmap){
        super(core);
        this.core = core;
        this.frameBuffer = bitmap;
        this.surfaceHolder = getHolder();
        canvas = new Canvas();
        rect = new Rect();

    }

    private long timer;

    @Override
    public void run() {
        float lastTime = System.nanoTime();
        float delta = 0;
        timer = System.currentTimeMillis();
        while (running){
            float nowTime = System.nanoTime();
            float elapsedTIme = nowTime - lastTime;
            lastTime = nowTime;
            delta += elapsedTIme/UPDATE_TIME;
            if(delta>1){
                update();
                drawing();
                delta--;
            }


        }



    }

    public void update(){
        core.getCurrentScene().update();
    }

    public void drawing(){
        if(surfaceHolder.getSurface().isValid()){
            canvas = surfaceHolder.lockCanvas();
            canvas.getClipBounds(rect);
            canvas.drawBitmap(frameBuffer, null, rect, null);
            core.getCurrentScene().drawing();
            surfaceHolder.unlockCanvasAndPost(canvas);

        }
    }

    public void startGame(){
        if(running == true){
            return;
        }
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void stopGame(){
        if(running == false){
            return;
        }
        running = false;
        try {
            gameThread.join();
        }
        catch (InterruptedException ex){
            ex.printStackTrace();
        }
    }
}
