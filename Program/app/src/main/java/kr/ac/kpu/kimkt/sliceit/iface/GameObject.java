package kr.ac.kpu.kimkt.sliceit.iface;

import android.graphics.Canvas;

import kr.ac.kpu.kimkt.sliceit.obj.Line;

public interface GameObject {
    public void update();
    public void draw(Canvas canvas);
    public void slice(Line line);
}
