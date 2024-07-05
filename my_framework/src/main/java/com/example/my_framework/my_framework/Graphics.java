package com.example.my_framework.my_framework;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;

import java.io.IOException;
import java.io.InputStream;

public class Graphics {
    private AssetManager assetManager;
    private Bitmap bitmap;
    private Canvas canvas;
    private Paint paint;
    private Bitmap textureGame;

    public Graphics(AssetManager assetManager, Bitmap bitmap) {
        this.assetManager = assetManager;
        this.bitmap = bitmap;
        this.canvas = new Canvas(bitmap);
        this.paint = new Paint();
    }

    public void clearScene(int colorRGB){
        canvas.drawRGB(colorRGB, colorRGB, colorRGB);
    }

    public void drawPixel(int x, int y, int color){
        paint.setColor(color);
        canvas.drawPoint(x, y, paint);
    }

    public void drawLine(int startX, int startY, int stopX, int stopY, int color){
        paint.setColor(color);
        canvas.drawLine(startX, startY, stopX, stopY, paint);
    }

    public void drawText(String text, int x, int y, int color, int size, Typeface font){
        paint.setColor(color);
        paint.setTextSize(size);
        paint.setTypeface(font);
        canvas.drawText(text, x, y, paint);
    }

    public void drawTexture(Bitmap textureGame, int x, int y){
        canvas.drawBitmap(textureGame, x, y, null);
    }

    public int getHeight(){
        return bitmap.getHeight();
    }

    public int getWidth(){
        return bitmap.getWidth();
    }

    public  Bitmap newTexture(String fileName){
        InputStream inputStream = null;
        try {
            inputStream = assetManager.open(fileName);
            textureGame = BitmapFactory.decodeStream(inputStream);
            if(textureGame == null){
                throw new RuntimeException("Невозможно загрузить Битмап");
            }
        }catch (IOException ex){
            ex.printStackTrace();
        }
        if(inputStream != null){
            try{
                inputStream.close();
            }catch (IOException ex){
                ex.printStackTrace();
            }
        }
        return textureGame;
    }

    public Bitmap newSprite(Bitmap textureAtlas, int x, int y, int spriteWidth, int spriteHeight){
        Bitmap newSprite = Bitmap.createBitmap(textureAtlas, x, y, spriteWidth, spriteHeight);
        return newSprite;
    }

}
