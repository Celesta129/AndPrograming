package kr.ac.kpu.kimkt.sliceit.obj;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import kr.ac.kpu.kimkt.sliceit.iface.GameObject;

public class Line implements GameObject {
    float x1, x2, y1, y2;

    int color = Color.BLACK;
    Paint myPaint = new Paint();
    public enum point{
        X1, Y1, X2, Y2;
    }
    public Line(float x1, float y1, float x2, float y2){
        this.x1 = x1; this.y1 = y1; this.x2 = x2; this.y2 = y2;

        myPaint.setColor(color);
        myPaint.setStrokeWidth(3f);
    }
    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawLine(x1, y1,
                x2, y2,
                myPaint);
    }

    @Override
    public void slice(Line line) {

    }

    public void setPoints(point index, float value){
       switch (index){
           case X1:
               x1 = value;
           case X2:
               x2 = value;
           case Y1:
               y1 = value;
           case Y2:
               y2 = value;
       }
    }
}
