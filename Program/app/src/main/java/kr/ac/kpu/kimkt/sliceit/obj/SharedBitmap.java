package kr.ac.kpu.kimkt.sliceit.obj;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.HashMap;

import kr.ac.kpu.kimkt.sliceit.iface.GameObject;

public class BackGround implements GameObject {
    private static final String TAG = SharedBitmap.class.getSimpleName();
    private static Resources resources;
    private static HashMap<Integer, SharedBitmap> map = new HashMap<>();
    private static BitmapFactory.Options noScaleOption;

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(BitmapFactory.decodeFile(R.));
    }

    @Override
    public void slice(Line line) {

    }
}
