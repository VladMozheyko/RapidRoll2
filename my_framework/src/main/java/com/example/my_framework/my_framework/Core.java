package com.example.my_framework.my_framework;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class Core extends AppCompatActivity {
    private final float SCREEN_HEIGHT = 800;
    private final float SCREEN_WIDTH = 600;

    private Loop loop;
    private Graphics graphics;
    private TouchListener touchListener;

    private Display display;
    private Point point;
    private Bitmap sizeDisplay;
    private Scene scene;

    private float sceneWidth;
    private float sceneHeight;

    private boolean onPause = false;
    private boolean onResume = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        point = new Point();
        display = getWindowManager().getDefaultDisplay();
        display.getSize(point);

        sizeDisplay = Bitmap.createBitmap((int)SCREEN_WIDTH, (int)SCREEN_HEIGHT, Bitmap.Config.ARGB_8888);
        sceneWidth = SCREEN_WIDTH/point.x;
        sceneHeight = SCREEN_HEIGHT/point.y;

        loop = new Loop(this, sizeDisplay);
        graphics = new Graphics(getAssets(), sizeDisplay);
        touchListener = new TouchListener(loop, sceneWidth, sceneHeight);
        scene = getStartScene();
        setContentView(loop);

    }

    public Core(Context context) {
      //  getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        point = new Point();
        display = getWindowManager().getDefaultDisplay();
        display.getSize(point);

        sizeDisplay = Bitmap.createBitmap((int)SCREEN_WIDTH, (int)SCREEN_HEIGHT, Bitmap.Config.ARGB_8888);
        sceneWidth = SCREEN_WIDTH/point.x;
        sceneHeight = SCREEN_HEIGHT/point.y;

        loop = new Loop(this, sizeDisplay);
        graphics = new Graphics(getAssets(), sizeDisplay);
        touchListener = new TouchListener(loop, sceneWidth, sceneHeight);
        scene = getStartScene();
        setContentView(loop);
    }

    @Override
    protected void onResume() {
        super.onResume();
        scene.resume();
        loop.startGame();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scene.pause();
        loop.stopGame();
        onPause = true;
        if(isFinishing()){
            scene.dispose();
        }
    }

    public Graphics getGraphics(){
        return graphics;
    }

    public TouchListener getTouchListener(){
        return touchListener;
    }

    public void setScene(Scene scene){
        if(scene == null){
            throw new IllegalArgumentException("Невозможно загрузить сцену");
        }
        this.scene.pause();
        this.scene.dispose();
        scene.resume();
        scene.update();
        this.scene = scene;
    }

    public Scene getStartScene(){
        return scene;
    }

    public Scene getCurrentScene(){
        return scene;
    }

}
