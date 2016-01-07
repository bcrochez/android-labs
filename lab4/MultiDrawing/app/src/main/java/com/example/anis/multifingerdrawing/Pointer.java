package com.example.anis.multifingerdrawing;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

/**
 * Created by anis on 10/27/15.
 */
public class Pointer {
    Paint paint;
    Path path;
    private int numPointer;
    private static int nbPointer = 0;
    public Pointer(int color){
        path = new Path();
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(12);
        numPointer = nbPointer;
        nbPointer++;

    }
    @Override
    public String toString(){
        return "pointer num  " + numPointer;
    }

}
