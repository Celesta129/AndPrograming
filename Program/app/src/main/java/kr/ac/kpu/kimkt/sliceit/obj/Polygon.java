package kr.ac.kpu.kimkt.sliceit.obj;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import java.util.ArrayList;


import kr.ac.kpu.kimkt.sliceit.PolyK;
import kr.ac.kpu.kimkt.sliceit.iface.GameObject;
import kr.ac.kpu.kimkt.sliceit.world.MainWorld;

public class Polygon implements GameObject {

    ArrayList<Float> points = new ArrayList<>();
    int color = Color.CYAN;
    Paint myPaint = new Paint();

    public Polygon(ArrayList<Float> p) {
        points.addAll(p);
        myPaint.setColor(color);
        myPaint.setStrokeWidth(5f);

    }

    public Polygon() {
        points.add(100.f);
        points.add(100.f);
        points.add(1000.f);
        points.add(100.f);
        points.add(1000.f);
        points.add(1500.f);
        points.add(100.f);
        points.add(1500.f);

        myPaint.setColor(color);
        myPaint.setStrokeWidth(5f);

    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        for (int i = 0; i < points.size() - 2; i += 2) {
            canvas.drawLine(points.get(i), points.get(i + 1),
                    points.get(i + 2), points.get(i + 3),
                    myPaint);
        }
        canvas.drawLine(
                points.get(points.size() - 2), points.get(points.size() - 1),
                points.get(0), points.get(1),
                myPaint);
    }

    @Override
    public void slice(Line line)
    {

        ArrayList<ArrayList<Float>> result = PolyK.Slice(points, line.x1, line.y1, line.x2, line.y2);
        for(int i = 0; i < result.size(); ++i)
        {
            MainWorld.get().add(MainWorld.Layer.polygon, new Polygon(result.get(i)));
        }
        MainWorld.get().remove(this);
    }
}
