package com.example.clock.models;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class RegPoly {

    private float width, height, length;

    private int size;

    private float x[], y[];

    private Canvas canvas;
    private Paint paint;


    public RegPoly(int size, float length, float width, float height, Canvas canvas, Paint paint) {

        this.size = size;
        this.length = length;
        this.width = width;
        this.height = height;

        this.canvas = canvas;
        this.paint = paint;

        x = new float[size];
        y = new float[size];

        for (int i = 0; i < size; i++) {

            x[i] = (float) (width + length * Math.cos(2 * Math.PI * i / size));
            y[i] = (float) (height + length * Math.sin(2 * Math.PI * i / size));
        }

    }

    public float getX(int index){return x[index % size];}
    public float getY(int index){return y[index % size];}

    public void drawRegPoly ()
    {
        for(int index = 0; index < size; index++)
        {
            this.canvas.drawLine(x[index], y[index], x[(index + 1) % size], y[(index + 1) % size], paint);
        }
    }

    public void drawPoints()
    {
        for(int index = 0; index < size; index++)
        {
            this.canvas.drawCircle(x[index], y[index], 4, paint);
        }
    }

    public void drawLines()
    {
        for(int index = 0; index < size; index++)
        {
            this.canvas.drawLine(x[index % size], y[index % size], x[index % size] + 10, y[index % size] - 3, paint);
        }
    }

    public void drawText()
    {
        for (int index = 0; index < size; index++ ) {
            String txt = String.valueOf(index + 1);
            paint.getTextBounds(txt, 0, txt.length(), new Rect());

            double angle = Math.PI / 6 * (index - 2);
            float x = (float) (width + Math.cos(angle) * length - new Rect().width() / 2);
            float y = (float) (height + Math.sin(angle) * length + new Rect().height() / 2);

            this.canvas.drawText(txt, x, y, paint);
        }
    }

    public void drawRadius(int index )
    {
        this.canvas.drawLine(width, height, x[index % size], y[index % size], paint);
    }

    public void  drawCircle() {
        this.canvas.drawCircle(width, height, length + 10, paint);
    }
}
