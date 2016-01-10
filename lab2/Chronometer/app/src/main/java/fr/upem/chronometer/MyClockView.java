package fr.upem.chronometer;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

public class MyClockView extends View {

    private long elapsedTime = 0;
    private MyClockDrawable mcd;

    public MyClockView(Context context) {
        super(context);
        init();
    }

    public MyClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyClockView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void setElapsedTime(long et) {
        elapsedTime = et;
    }

    private void init() {
        // create the drawable clock
        mcd = new MyClockDrawable();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // allocations per draw cycle.
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;

        // draw the clock
        mcd.setDim(contentWidth / 2, contentHeight / 2, elapsedTime);
        mcd.draw(canvas);
    }

}