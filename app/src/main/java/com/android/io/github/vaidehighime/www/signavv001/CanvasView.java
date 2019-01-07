package com.android.io.github.vaidehighime.www.signavv001;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CanvasView extends View
{

    private final Paint paint;
    private final Path path;
    private float x;
    private float y;
    private Canvas canvas;
    private static final float TOLERANCE=4;
    private final CoordinateFactory cf=new CoordinateFactory();



    public CanvasView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.path=new Path();
        this.paint=new Paint();
        this.paint.setAntiAlias(true);
        this.paint.setColor(Color.BLACK);
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setStrokeJoin(Paint.Join.ROUND);
        this.paint.setStrokeWidth(4f);

    }

    @Override
    protected void onSizeChanged(int w,int h,int oldw,int oldh)
    {
        super.onSizeChanged(w,h,oldw,oldh);
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        this.canvas=new Canvas(bitmap);
    }

    private void startTouch(float x, float y)
    {
        this.path.moveTo(x,y);
        this.x=x;
        this.y=y;
    }

    private void moveTouch(float x, float y)
    {
        float dx=Math.abs(x-this.x);
        float dy=Math.abs(y-this.y);

        if(dx>= TOLERANCE || dy >= TOLERANCE)
        {

            this.path.quadTo(this.x,this.y,(this.x+x)/2,(this.y+y)/2);
            this.x=x;
            this.y=y;
        }

    }

    public void clearCanvas()
    {
        this.path.reset();
        invalidate();
    }

    private void upTouch()
    {
        this.path.lineTo(this.x,this.y);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        float x=event.getX();
        float y=event.getY();
        Coordinates c=new Coordinates();
        c.setXY(x,y);
        this.cf.pushCoordinates(c);
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                startTouch(x,y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                moveTouch(x,y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                upTouch();
                invalidate();
                break;
        }
        return(true);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        canvas.drawPath(path,paint);
    }

    public StringBuilder ProcessCanvas(Context context)
    {
        File tempFile;
        tempFile=this.cf.saveToFile(context);
        String strLine;
        StringBuilder text = new StringBuilder();
        try
        {
            FileReader fReader = new FileReader(tempFile);
            BufferedReader bReader = new BufferedReader(fReader);
            while( (strLine=bReader.readLine()) != null  )
            {
                text.append(strLine).append("\n");
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return (text);
    }

}