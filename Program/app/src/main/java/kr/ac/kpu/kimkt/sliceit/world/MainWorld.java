package kr.ac.kpu.kimkt.sliceit.world;

import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.kpu.kimkt.sliceit.game.GameWorld;
import kr.ac.kpu.kimkt.sliceit.iface.GameObject;
import kr.ac.kpu.kimkt.sliceit.obj.Line;
import kr.ac.kpu.kimkt.sliceit.obj.Polygon;

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
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
            float x= event.getX(), y = event.getY();

            currLine = new Line(x,y,x,y);

            add(Layer.line, currLine);
        }
        else if(event.getAction() == MotionEvent.ACTION_MOVE)
        {
            currLine.setPoints(Line.point.X2, event.getX());
            currLine.setPoints(Line.point.Y2, event.getY());
        }
        else if(event.getAction() == MotionEvent.ACTION_UP)
        {
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
