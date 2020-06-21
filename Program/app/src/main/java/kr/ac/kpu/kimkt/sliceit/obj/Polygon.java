package kr.ac.kpu.kimkt.sliceit.obj;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import java.util.ArrayList;

import kr.ac.kpu.kimkt.sliceit.iface.GameObject;

public class Polygon implements GameObject {

    ArrayList<Float> points = new ArrayList<>();
    int color = Color.CYAN;
    Paint myPaint = new Paint();
    public Polygon(ArrayList<Float> p){
        points.addAll(p);
        myPaint.setColor(color);
        myPaint.setStrokeWidth(5f);

    }
    public Polygon(){
        points.add(50.f);
        points.add(50.f);
        points.add(150.f);
        points.add(50.f);
        points.add(200.f);
        points.add(200.f);

        myPaint.setColor(color);
        myPaint.setStrokeWidth(5f);

    }
    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        for(int i = 0; i < points.size()-2; i+=2){
            canvas.drawLine(points.get(i),points.get(i+1),
                    points.get(i+2),points.get(i+3),
                    myPaint);
        }
        canvas.drawLine(
                points.get(points.size() - 2),points.get(points.size() - 1),
                points.get(0), points.get(1),
                myPaint);
    }
}
