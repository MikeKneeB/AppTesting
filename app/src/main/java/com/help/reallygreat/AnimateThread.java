package com.help.reallygreat;

import android.graphics.Canvas;

/**
 * Created by mike on 10/03/17.
 */

public class AnimateThread extends Thread {

    private DrawingSurfaceView drawingSurfaceView;
    private boolean running = false;

    public AnimateThread(DrawingSurfaceView view) {
        drawingSurfaceView = view;
    }

    public void setRunning(Boolean run) {
        running = run;
    }

    @Override
    public void run() {
        while(running) {
            Canvas canvas = drawingSurfaceView.getHolder().lockCanvas();

            if (canvas != null) {
                synchronized (drawingSurfaceView.getHolder()) {
                    drawingSurfaceView.drawThing(canvas);
                }

                drawingSurfaceView.getHolder().unlockCanvasAndPost(canvas);
            }

            try {
                sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
