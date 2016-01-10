package fr.upem.chronometer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

import java.util.concurrent.TimeUnit;

/**
 * Created by Bastien on 09/01/2016.
 */
public class MyClockDrawable extends Drawable {
    private static double ANGLE = Math.toRadians(6);
    private float cx;
    private float cy;
    private final Paint paint = new Paint();

    private long elapsedTime;

    @Override
    public void draw(Canvas canvas) {
        float radius = Math.min(cx, cy);

        // dessin du cadran de l'horloge
        paint.setColor(Color.BLACK);
        canvas.drawCircle(cx, cy, radius, paint);

        paint.setColor(Color.LTGRAY);
        canvas.drawCircle(cx, cy, radius - 5, paint);

        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5);
        for(int i=0; i<60; i++) {
            float xAngle = (float) Math.cos(ANGLE*i);
            float yAngle = (float) Math.sin(ANGLE*i);
            if(i%5 == 0) {
                canvas.drawLine(cx + xAngle * (radius - 40), cy + yAngle * (radius - 40), cx + xAngle * radius, cy + yAngle * radius, paint);
            } else {
                canvas.drawLine(cx + xAngle * (radius - 20), cy + yAngle * (radius - 20), cx + xAngle * radius, cy + yAngle * radius, paint);
            }
        }

        // dessin des aiguilles
        paint.setColor(Color.RED);
        long milli = TimeUnit.MILLISECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS);
        double secondsAngle = Math.toRadians(milli/1000 * 360/60 - 90);
        double minutesAngle = Math.toRadians(milli/(1000*60) * 360/60 - 90);
        canvas.drawLine(cx, cy, cx+(float)Math.cos(secondsAngle)*(radius-5), cy+(float)Math.sin(secondsAngle)*(radius-5), paint);
        canvas.drawLine(cx, cy, cx+(float)Math.cos(minutesAngle)*(radius*2/3), cy+(float)Math.sin(minutesAngle)*(radius*2/3), paint);
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return 0;
    }

    public void setDim(int cx, int cy, long elapsedTime) {
        this.cx = cx;
        this.cy = cy;
        this.elapsedTime = elapsedTime;
    }
}
