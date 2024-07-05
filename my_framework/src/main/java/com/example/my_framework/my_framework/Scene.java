package com.example.my_framework.my_framework;

import android.util.Log;

public abstract class Scene  {
    private Core core;
    private int sceneHeight;
    private int sceneWidth;
    private Graphics graphics;

    public Scene(Core core) {         // Constructor
        this.core = core;
        Log.i(" ","Ширина экрана" + core.getGraphics().getWidth());
        sceneWidth = core.getGraphics().getWidth();
        sceneHeight = core.getGraphics().getHeight();
        graphics = core.getGraphics();

    }

    public abstract void update();

    public abstract void resume();

    public abstract void drawing();

    public abstract void dispose();

    public abstract void pause();

    public Graphics getGraphics() {
        return graphics;
    }
}
