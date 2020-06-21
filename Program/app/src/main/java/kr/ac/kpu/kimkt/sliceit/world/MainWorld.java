package kr.ac.kpu.kimkt.sliceit.world;

import android.util.Log;

import java.util.ArrayList;

import kr.ac.kpu.kimkt.sliceit.game.GameWorld;
import kr.ac.kpu.kimkt.sliceit.iface.GameObject;
import kr.ac.kpu.kimkt.sliceit.obj.Polygon;

public class MainWorld extends GameWorld {
    public enum Layer{
        bg, ui, polygon, COUNT
    }
    private static final String TAG = MainWorld.class.getSimpleName();

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
    public void update(long frameTimeNanos) {
        super.update(frameTimeNanos);
    }

    @Override
    protected int getLayerCount() {
        return Layer.COUNT.ordinal();
    }
}
