package com.example.clock.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.clock.models.RegPoly;

import java.util.Date;

public class ClockSurfaceView extends SurfaceView implements Runnable {

    private Context context;
    private float length;
    private Thread animThread = null;
    private SurfaceHolder holder = null;
    private boolean running;

    public ClockSurfaceView(Context context, float length) {
        super(context);

        this.context = context;
        this.length = length;

        this.animThread = new Thread(this);
        this.animThread.start();

        this.running = true;
        this.holder = getHolder();
    }

    @Override
    public void run() {

        Date date = new Date();
        int sec = date.getSeconds();
        int min = date.getMinutes();
        int hour = date.getHours();

        while (this.running) {

            // check surfaceview
            if (this.holder.getSurface().isValid()) {

                drawClock(sec, min, hour);

                try {

                    Thread.sleep(1000);

                    sec++;
                    min++;
                    hour++;

                } catch (Exception e) { }
            }

        }

    }

    public void onClockPause() {

        this.running = false;

        boolean reentry = true;

        while (reentry) {

            try {
                this.animThread.join();
                reentry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void onClockResume() {

        this.running = true;
        this.animThread = new Thread(this);
        this.animThread.start();

    }

    private void drawClock(int sec, int min, int hour) {

        // get the canvas from holder and start drawing
        Canvas canvas = this.holder.lockCanvas();
        Paint paint = new Paint();

        // draw background
        paint.setColor(Color.rgb(119, 119, 119));
        canvas.drawPaint(paint);

        // draw clock body

        paint.setShader(new RadialGradient(getWidth() / 2, getHeight() /2,  length, new int[] {Color.rgb(255, 152, 0), Color.rgb(255, 235, 59), Color.rgb(255, 152, 0)}, null, Shader.TileMode.MIRROR));
        paint.setStyle(Paint.Style.FILL_AND_STROKE);

        RegPoly clockBody = new RegPoly(1, length, getWidth() / 2, getHeight() / 2, canvas, paint);
        clockBody.drawCircle();

        // draw clock frame

        paint.reset();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setAntiAlias(true);

        RegPoly clockFrame = new RegPoly(60, length, getWidth() / 2, getHeight() / 2, canvas, paint);
        clockFrame.drawCircle();

        // draw clock center

        paint.reset();
        paint.setStyle(Paint.Style.FILL);
        RegPoly clockCenter = new RegPoly(0, 12, getWidth() / 2, getHeight() / 2, canvas, paint);
        clockCenter.drawCircle();

        // draw clock seconds marks

        paint.reset();
        paint.setColor(Color.rgb(119, 119, 119));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);

        RegPoly clockSecondsMarks = new RegPoly(60, length - 10, getWidth() / 2, getHeight() / 2, canvas, paint);
        clockSecondsMarks.drawPoints();

        // draw clock hours marks

        paint.reset();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setTextSize(40);

        RegPoly clockHoursMarks = new RegPoly(12, length - 70, getWidth() / 2, getHeight() / 2, canvas, paint);
        clockHoursMarks.drawText();

        // draw clock seconds hand

        paint.reset();
        paint.setColor(Color.rgb(255, 7, 58));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);

        RegPoly clockSecondsHand = new RegPoly(60, length - 50, getWidth() / 2, getHeight() / 2, canvas, paint);
        clockSecondsHand.drawRadius(sec + 45);

        // draw clock minutes hand

        paint.reset();
        paint.setColor(Color.rgb(27, 3, 163));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);

        RegPoly clockMinutesHand = new RegPoly(3600, length - 60, getWidth() / 2, getHeight() / 2, canvas, paint);
        clockMinutesHand.drawRadius(min + 45);

        // draw clock hours hand

        paint.reset();
        paint.setColor(Color.rgb(57, 255 ,20));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);

        RegPoly clockHoursHand = new RegPoly(43200, length - 200, getWidth() / 2, getHeight() / 2, canvas, paint);
        clockHoursHand.drawRadius(hour + 45);

        this.holder.unlockCanvasAndPost(canvas);
    }
}
