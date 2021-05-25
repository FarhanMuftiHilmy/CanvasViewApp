package com.rechit.canvasviewapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;

public class MyCanvasView extends View {

    public static int BRUSH_SIZE = 10;
    public static final int DEFAULT_COLOR = Color.RED;

    private int currentColor;
    private int strokeWidth;

    private Paint mPaint;
    private Path mPath;
    private int mDrawColor;
    private int mBackgroundColor;
    private Canvas mCanvas;
    private Bitmap mBitmap;
    private Rect mFrame;

    private ArrayList<Draw> paths = new ArrayList<>();

    public MyCanvasView(Context context) {
        this(context, null);
    }

    public MyCanvasView(Context context, AttributeSet attributeSet){
        super(context);
        mBackgroundColor = ResourcesCompat.getColor(getResources(), R.color.teal_200, null);
        //mDrawColor = ResourcesCompat.getColor(getResources(), R.color.purple_200, null);


        mPath = new Path();
        mPaint = new Paint();
        currentColor = DEFAULT_COLOR;
        strokeWidth = BRUSH_SIZE;
        mPaint.setColor(DEFAULT_COLOR);
        mPaint.setAntiAlias(true); //memperhalus
        mPaint.setDither(true); //presisi
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND); // ujung lukisan
        mPaint.setStrokeWidth(12);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mCanvas.drawColor(mBackgroundColor);




        int inset = 30;
        mFrame = new Rect(inset, inset, w-inset, h-inset);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (Draw draw : paths) {

            mPaint.setColor(draw.color); // WRONG
            mPaint.setStrokeWidth(draw.strokeWidth);
            mPaint.setMaskFilter(null);

            mCanvas.drawPath(draw.path, mPaint);

        }

        canvas.drawBitmap(mBitmap, 0, 0, null);
        canvas.drawRect(mFrame, mPaint);
    }

    private float mX, mY; //untk menyimpan lokasi terakhir x dan y
    private static final float TOUCH_TOLERANCE = 4; //toleransi jarak muncul gambar

    private void touchStart(float x, float y){
        Draw draw = new Draw(currentColor, strokeWidth, mPath);
        paths.add(draw);

        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    private void touchMove(float x, float y){
        float dx = Math.abs(x-mX);
        float dy = Math.abs(y-mY);
        if(dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE){
            mPath.quadTo(mX, mY, (x+mX)/2, (y+mY)/2);
            mX = x;
            mY = y;
            //mCanvas.drawPath(mPath, mPaint);
        }
    }

    private void touchUp(){
        mPath.reset();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                touchStart(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(x, y);
                invalidate(); // untuk merefresh tampilan di layar
                break;
            case MotionEvent.ACTION_UP:
                touchUp();
                break;
            default:
        }

        return true;
    }


    public void setColor (int color) {

        currentColor = color;

    }

    public void setStrokeWidth (int width) {

        strokeWidth = width;

    }

}
