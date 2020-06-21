package kr.ac.kpu.kimkt.sliceit.obj;

import android.graphics.Canvas;

import kr.ac.kpu.kimkt.sliceit.R;
import kr.ac.kpu.kimkt.sliceit.iface.GameObject;

public class Background implements GameObject {

    SharedBitmap bitmap ;
    public Background(){
        bitmap = SharedBitmap.load(R.mipmap.monoon1);
    }
    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        bitmap.draw(canvas,0,0);
    }

    @Override
    public void slice(Line line) {

    }
}
