package kr.ac.kpu.kimkt.sliceit.ui.view;

import android.app.Service;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import kr.ac.kpu.kimkt.sliceit.game.GameWorld;
import kr.ac.kpu.kimkt.sliceit.world.MainWorld;

public class GameView extends View {
    private static final String TAG = GameView.class.getSimpleName();

    private Rect mainRect;
    private Paint mainPaint;

    private GameWorld gameWorld;


    private boolean paused;

    public GameView(Context context) {
        super(context);
        init();
    }

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Service.WINDOW_SERVICE);
        Point size = new Point();
        wm.getDefaultDisplay().getSize(size);
        mainRect = new Rect(0,0, size.x, size.y);

        //SharedBitmap.setResources(getResources());
        mainPaint = new Paint();
        mainPaint.setColor(0xFFFFEEEE);


        gameWorld = MainWorld.get();
        gameWorld.setRect(mainRect);
        gameWorld.initResource(this);

        postFrameCallBack();
    }

    public void pause() {
        this.paused = true;
    }

    public void resume() {
        this.paused = false;
    }

    private void postFrameCallBack(){
        if(paused){
            return;
        }
        Choreographer.getInstance().postFrameCallback(new Choreographer.FrameCallback() {

            @Override
            public void doFrame(long frameTimeNanos) {
                update(frameTimeNanos);
                invalidate();


                postFrameCallBack();
            }
        });
    }
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(mainRect, mainPaint);
        gameWorld.draw(canvas);
    }

    public void update(long frameTimeNanos) {
        gameWorld.update(frameTimeNanos);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gameWorld.onTouchEvent(event);
    }
}
