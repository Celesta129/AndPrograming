package kr.ac.kpu.kimkt.sliceit.game;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

import kr.ac.kpu.kimkt.sliceit.iface.GameObject;

public abstract class GameWorld {
    private static final String TAG = GameWorld.class.getSimpleName();

    protected static GameWorld singleton;
    protected View view;
    protected Rect rect;

    protected long frameTimeNanos;
    protected long timeDiffNanos;

    public boolean onTouchEvent(MotionEvent event) { return false; }

    protected ArrayList<ArrayList<GameObject>> layers;
    protected ArrayList<GameObject> trash = new ArrayList<>();

    public static GameWorld get() {
        if (singleton == null) {
            //singleton = new GameWorld();
            Log.e(TAG, "GameWorld subclass not created");
        }
        return singleton;
    }


    protected GameWorld() {
    }
    public ArrayList<GameObject> objectsAt(int index) {
        return layers.get(index);
    }
    public void initResource(View view) {
        this.view = view;
        initLayers();
        initObjects();
    }
    public void initObjects() {
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }

    public void draw(Canvas canvas) {
        for(ArrayList<GameObject> objects : layers){
            for (GameObject o : objects) {
                o.draw(canvas);
            }
        }

    }

    protected void initLayers() {
        layers = new ArrayList<>();
        int layerCount = getLayerCount();
        for(int i = 0 ; i < layerCount; i++){
            ArrayList<GameObject> layer = new ArrayList<>();
            layers.add(layer);
        }
    }

    abstract protected int getLayerCount();

    public Resources getResources(){
        return view.getResources();
    }

    public int getLeft() {
        return rect.left;
    }

    public int getRight() {
        return rect.right;
    }

    public int getTop() {
        return rect.top;
    }

    public int getBottom() {
        return rect.bottom;
    }


    public Context getContext() {
        return view.getContext();
    }

    public void pause() {

    }

    public void resume() {

    }
}
