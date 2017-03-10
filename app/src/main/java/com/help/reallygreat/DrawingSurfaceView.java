package com.help.reallygreat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by mike on 10/03/17.
 */

public class DrawingSurfaceView extends SurfaceView {

    private SurfaceHolder surfaceHolder;
    private Paint drawPaint;
    private AnimateThread animateThread;

    int xPos = 50;
    int yPos = 50;
    int delX = 10;
    int delY = 10;
    int radius = 30;

    public DrawingSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupPaint();
        init();
    }

    private void init() {
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(new DrawingSurfaceCallback());

        animateThread = new AnimateThread(this);
    }

    private void setupPaint() {
        drawPaint = new Paint();
        drawPaint.setColor(Color.WHITE);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(5);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    private class DrawingSurfaceCallback implements SurfaceHolder.Callback {

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            Canvas canvas = holder.lockCanvas();
            drawThing(canvas);
            holder.unlockCanvasAndPost(canvas);

            animateThread.setRunning(true);
            animateThread.start();
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            boolean retry = true;

            animateThread.setRunning(false);
            while (retry) {
                try {
                    animateThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    protected void drawThing(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        canvas.drawCircle(xPos, yPos, radius, drawPaint);

        xPos += delX;
        if(delX > 0){
            if(xPos >= getWidth() - radius){
                delX *= -1;
            }
        }else{
            if(xPos <= 0 + radius){
                delX *= -1;
            }
        }

        yPos += delY;
        if(delY > 0){
            if(yPos >= getHeight() - radius){
                delY *= -1;
            }
        }else{
            if(yPos <= 0 + radius){
                delY *= -1;
            }
        }
    }
}
