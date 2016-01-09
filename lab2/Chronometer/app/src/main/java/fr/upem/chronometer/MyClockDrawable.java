package fr.upem.chronometer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

/**
 * Created by Bastien on 09/01/2016.
 */
public class MyClockDrawable extends Drawable {
    private static double ANGLE = Math.toRadians(30);
    private float cx;
    private float cy;
    private final float radius;
    private final Paint paint;

    public MyClockDrawable(float radius, Paint paint) {
        this.radius = radius;
        this.paint = paint;
    }

    @Override
    public void draw(Canvas canvas) {
        paint.setColor(Color.BLACK);
        canvas.drawCircle(cx, cy, radius, paint);
        paint.setColor(Color.WHITE);
        for(int i=0; i<12; i++) {
            canvas.drawLine(cx, cy, (float) Math.cos(ANGLE*i), (float) Math.sin(ANGLE*i), paint);
        }
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

    public void setDim(int cx, int cy) {
        this.cx = cx;
        this.cy = cy;
    }
}
