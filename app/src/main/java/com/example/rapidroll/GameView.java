package com.example.rapidroll;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.my_framework.my_framework.AnimationFW;
import com.example.my_framework.my_framework.Graphics;
import com.example.my_framework.my_framework.MusicFW;

import java.util.ArrayList;

public class GameView extends SurfaceView implements Runnable {

    public static int maxX = 20;
    public static int maxY = 28;
    public static float unitW;
    public static float unitH;
    private int count;
    private int spike_time;

    private boolean firstTime = true;
    private boolean gameRunning = true;
    public static Platform platform;
    private Spikes spikes;
    private Ball ball;
    private Thread gameThread = null;
    private Paint paint;
    private Canvas canvas;
    private MusicFW musicFW;
    private Graphics graphics;
    private AnimationFW animationFW;

    public static SurfaceHolder surfaceHolder;
    public static ArrayList<Platform> platforms = new ArrayList<>();
    public static ArrayList<Spikes> spikesArr = new ArrayList<>();
    private final int INTERVAL = 30;
    private int currentTime = 0;
    private int dist = 0;
    public static int lives;
    public static Heart heart;
    public static boolean isHeart;
    public static Bitmap explosionB;
    private Bitmap bitmap;
    private  int draw, upd;
    private int x, y;
    int countFrames, countEx;


    public GameView(Context context) {
        super(context);
        surfaceHolder = getHolder();
        paint = new Paint();
        isHeart = false;
        lives = 3;
        musicFW = GameActivity.getAudio().newMusic("music.1.ogg");
        musicFW .play(true, 1f);
        gameThread = new Thread(this);
        gameThread.start();

    }

    private void initAnimation() {
        animationFW = new AnimationFW(3, UtilResource.spriteExplosionArr.get(0),
                UtilResource.spriteExplosionArr.get(1), UtilResource.spriteExplosionArr.get(2));

    }

    @Override
    public void run() {
        while(gameRunning){
            addPlatforms();
            update();
            drawing();
            control();
            deletePlatforms();
            deleteSpikes();
            deleteHeart();
            if(GameActivity.getStatus()){
                closePlaying();
                spikesArr.clear();
                platforms.clear();
                return;
            }
        }
    }

    private void spriteExplosion(){
        UtilResource.spriteExplosionArr = new ArrayList<>();
        explosionB = BitmapFactory.decodeResource(getResources(), R.drawable.explosion1);
        explosionB = Bitmap.createScaledBitmap(explosionB, 2*(int)(GameView.unitW*0.7), (int)(GameView.unitH), false);
        UtilResource.spriteExplosionArr.add(explosionB);

        explosionB = BitmapFactory.decodeResource(getResources(), R.drawable.explosion2);
        explosionB = Bitmap.createScaledBitmap(explosionB, 2*(int)(GameView.unitW*0.7), (int)(GameView.unitH), false);
        UtilResource.spriteExplosionArr.add(explosionB);

        explosionB = BitmapFactory.decodeResource(getResources(), R.drawable.explosion3);
        explosionB = Bitmap.createScaledBitmap(explosionB, 2*(int)(GameView.unitW*0.7), (int)(GameView.unitH), false);
        UtilResource.spriteExplosionArr.add(explosionB);

    }
    private void closePlaying() {
        musicFW.dispose();
    }


    private void deleteHeart() {
        if(isHeart && heart.getY()-heart.getBitmapHeight()<=1.5){
            isHeart = false;
        }
    }

    private void deleteSpikes() {
        for (int i = 0; i < spikesArr.size() ; i++) {
            if(spikesArr.get(i).getY() <= 1.5)
                spikesArr.remove(i);
        }
    }

    private void deletePlatforms() {
        for (int i = 0; i < platforms.size() ; i++) {
            if(platforms.get(i).getY() <= 1.5)
                platforms.remove(i);
        }
    }

    private void addBall() {
        ball = new Ball(getContext());
    }

    private void control() {
        try {
            gameThread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void runAnimation() {
        canvas.drawBitmap(UtilResource.spriteExplosionArr.get(countFrames), x, y, paint);
        countFrames++;
        if(countFrames>=3){
            countFrames = 0;
        }
    }

    private void drawing() {
        if(surfaceHolder.getSurface().isValid()){
            if(firstTime){
                firstTime = false;
                unitW = surfaceHolder.getSurfaceFrame().width()/maxX;
                unitH = surfaceHolder.getSurfaceFrame().height()/maxY;
                addBall();
                bitmap = Bitmap.createBitmap((int)ball.getBitmapWidth(),
                        (int)ball.getBitmapHeight(), Bitmap.Config.ARGB_8888);
                graphics = new Graphics(GameActivity.assetManager, bitmap);
                spriteExplosion();
                initAnimation();
            }
            canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(getResources().getColor(R.color.myColor));
            if(countEx > 0){
                runAnimation();
                countEx--;
            }
            setLine();
            setParams();
            ball.drawing(paint, canvas);
            if(isHeart){
                heart.drawing(paint, canvas);
            }

            for (Platform platform: platforms) {
                platform.drawing(paint, canvas);
            }

            for (Spikes spikes: spikesArr) {
                spikes.drawing(paint, canvas);
            }
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void setParams() {
        if(ball.getSpeed()>0){
            dist += (int) (10*ball.getSpeed());
        }
        paint.setColor(Color.BLACK);
        paint.setTextSize(50);
        canvas.drawText("Дистанция: " + dist, 10, unitH, paint );
        canvas.drawText("Жизни: " + lives, (float)0.7*surfaceHolder.getSurfaceFrame().width(), unitH, paint );
    }


    private void setLine() {
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5);
        canvas.drawLine(0, (float)1.5*unitH, surfaceHolder.getSurfaceFrame().width(),
                (float)1.5*unitH, paint);
    }

    private void addPlatforms() {
        spike_time = UtilRandom.getGap(3, 5);
        if(currentTime > INTERVAL) {
            if(count>spike_time){
                spikes = new Spikes(getContext());
                spikesArr.add(spikes);
                count = 0;
                currentTime = 0;
            }
            else {

                platform = new Platform(getContext());
                platforms.add(platform);
                currentTime = 0;
                count++;
            }
        }else
            currentTime++;
    }

    private void update() {
        if(!firstTime){
            ball.update();
            int heartTime = UtilRandom.getGap(0, 8);

            if(ball.isHit()){
                GameActivity.vibrator.vibrate(100);
                x = (int)(ball.x*unitW);
                y = (int)(ball.y*unitH);
                countEx = 3;
                ball = new Ball(getContext());
            }

            for (Platform platform: platforms) {
                if(heartTime == 7 && !isHeart && platform.getY() == maxY){
                    heart = new Heart(getContext(), platform.getX(), platform.getY()-platform.getBitmapHeight()*0.9);
                    isHeart = true;
                }
                platform.update();
            }
            for (Spikes spikes: spikesArr) {
                spikes.update();
            }
            if(isHeart){
                heart.update();
            }
        }
    }
}
