package com.example.rapidroll;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.my_framework.my_framework.Audio;


public class GameActivity extends AppCompatActivity implements View.OnTouchListener{
private static boolean isLeftPressed = false;
private static boolean isRightPressed = false;
private static boolean destroyed = false;
private Button btnLeft, btnRight;
public static Audio audio;
public static Vibrator vibrator;
public static AssetManager assetManager;



@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        audio = new Audio(this);
        assetManager = getAssets();
        GameView gameView = new GameView(this);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.gameLayout);
        linearLayout.addView(gameView);
        btnLeft = (Button)findViewById(R.id.btnLeft);
        btnRight = (Button)findViewById(R.id.btnRight);
        btnLeft.setOnTouchListener(this);
        btnRight.setOnTouchListener(this);
        destroyed = false;
        }


public static Audio getAudio() {
        return audio;
        }

@Override
protected void onDestroy() {
        super.onDestroy();
        destroyed = true;
        }

@Override
protected void onResume() {
        super.onResume();
        destroyed = false;
        }

@Override
public boolean onTouch(View v, MotionEvent event) {

synchronized (this) {
        switch (v.getId()) {
        case R.id.btnLeft:
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
        isLeftPressed = true;
        break;
        case MotionEvent.ACTION_UP:
        isLeftPressed = false;
        break;
        }
        break;
        case R.id.btnRight:
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
        isRightPressed = true;
        break;
        case MotionEvent.ACTION_UP:
        isRightPressed = false;
        break;
        }
        break;
        }
        }
        return true;
        }

public static boolean isIsLeftPressed() {
        return isLeftPressed;
        }

public static boolean isIsRightPressed() {
        return isRightPressed;
        }

public static boolean getStatus() {
        return destroyed;
        }
        }