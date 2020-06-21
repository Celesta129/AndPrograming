package kr.ac.kpu.kimkt.sliceit.world;

import android.media.SoundPool;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.kpu.kimkt.sliceit.R;
import kr.ac.kpu.kimkt.sliceit.game.GameWorld;
import kr.ac.kpu.kimkt.sliceit.iface.GameObject;
import kr.ac.kpu.kimkt.sliceit.obj.Background;
import kr.ac.kpu.kimkt.sliceit.obj.Line;
import kr.ac.kpu.kimkt.sliceit.obj.Polygon;
import kr.ac.kpu.kimkt.sliceit.res.SoundEffects;

public class MainWorld extends GameWorld {
    public enum Layer{
        bg, ui, polygon,line, COUNT
    }
    private static final String TAG = MainWorld.class.getSimpleName();

    Line currLine;

    public static void create() {
        if(singleton != null){
            Log.e(TAG, "Object already created");
        }
        singleton = new MainWorld();
    }
    public static MainWorld get() {
        return (MainWorld) singleton;
    }

    public void add(Layer layer, final GameObject obj){
        add(layer.ordinal(), obj);
    }

    @Override
    public void initObjects() {
        Polygon tmp = new Polygon();
        //ArrayList<Polygon> polygon = new ArrayList<>();
        add(Layer.polygon, tmp);

        Background bg = new Background();
        add(Layer.bg, bg);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
            Log.d(TAG, "Touch Down");
            float x= event.getX(), y = event.getY();
            currLine = new Line(x,y,x,y);

            Log.d(TAG, "Touch Move: x1: " + currLine.x1 + " y1: " + currLine.y1 +
                      " x2" + currLine.x2 + " y2: " + currLine.y2);
            add(Layer.line, currLine);
        }
        else if(event.getAction() == MotionEvent.ACTION_MOVE)
        {
            Log.d(TAG, "Touch Move: x2: " + currLine.x2 + " y2: " + currLine.y2);

            currLine.x2 = event.getX();
            currLine.y2 = event.getY();
        }
        else if(event.getAction() == MotionEvent.ACTION_UP)
        {
            Log.d(TAG, "Touch Up");
            for(GameObject objects: layers.get(Layer.polygon.ordinal())){
                objects.slice(currLine);

            }
            remove(currLine);
        }
        return true;
    }

    @Override
    public void update(long frameTimeNanos) {
        super.update(frameTimeNanos);
    }

    @Override
    protected int getLayerCount() {
        return Layer.COUNT.ordinal();
    }
}
