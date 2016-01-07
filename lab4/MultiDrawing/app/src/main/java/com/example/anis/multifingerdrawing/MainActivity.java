package com.example.anis.multifingerdrawing;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by anis on 10/26/15.
 */
public class MainActivity extends Activity {

    DrawingView dv ;
    private HashMap<Integer, Pointer> pointersMap;
    private ArrayList<Pointer> pointersList;
    //private Paint mPaint;
  //  private DrawingManager mDrawingManager=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dv = new DrawingView(this);
        pointersMap = new HashMap<>();
        pointersList= new ArrayList<>();
        pointersList.add(new Pointer(Color.GREEN));
        pointersList.add(new Pointer(Color.RED));
        pointersList.add(new Pointer(Color.BLUE));
        setContentView(dv);
    }

    public class DrawingView extends View {

        public int width;
        public  int height;
        private Bitmap mBitmap;
        private Canvas mCanvas;
        //private Path mPath;
        private Paint   mBitmapPaint;
        Context context;

        public DrawingView(Context c) {
            super(c);
            context=c;
            //mPath = new Path();
            mBitmapPaint = new Paint(Paint.DITHER_FLAG);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);

            mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            mCanvas = new Canvas(mBitmap);

        }
        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);

            for(Pointer pointer : pointersMap.values()){
                canvas.drawPath( pointer.path,  pointer.paint);
                //Log.d("onDrax", pointer.toString());
            }
        }

        //private float mX, mY;
        private static final float TOUCH_TOLERANCE = 4;

        private void touch_start(int id, float x, float y) {
            Pointer pointer = pointersList.remove(0);
            pointer.path.reset();
            pointer.path.moveTo(x, y);
            pointersMap.put(id,pointer);
            //Log.d("touchstart","pointer added");
        //  mX = x;
        // mY = y;
        }
        private void touch_move(int id, float x, float y) {
            //float dx = Math.abs(x - mX);
            //float dy = Math.abs(y - mY);
            //if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
                //mPath.quadTo(mX, mY, (x + mX)/2, (y + mY)/2);
                pointersMap.get(id).path.lineTo(x, y);
            //    mX = x;
            //    mY = y;
            //}
        }
        private void touch_up(int id) {
            //mPath.lineTo(mX, mY);
            // commit the path to our offscreen
            Pointer pointer = pointersMap.remove(id);
            mCanvas.drawPath(pointer.path,  pointer.paint);
            // kill this so we don't double draw
            pointer.path.reset();
            pointersList.add(pointer);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float x = event.getX();
            float y = event.getY();
            int id = event.getPointerId(event.getActionIndex());

            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    Log.d("onTouchEventDown", "current id "+ id);
                    touch_start(id, x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_MOVE:
                    Log.d("MOVE", "current id " + id);
                    for(int i=0; i<event.getPointerCount();i++ ) {
                        id = event.getPointerId(i);
                        x = event.getX(id);
                        y = event.getY(id);
                        touch_move(id, x, y);
                    }
                    invalidate();
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    Log.d("onTouchEventPointerDown", "current id "+ id);
                    touch_start(id, x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    touch_up(id);
                    invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    touch_up(id);
                    invalidate();
                    break;
            }
            return true;
        }
    }
}