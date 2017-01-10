package com.anwesome.ui.concfillbuttonmodule;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by anweshmishra on 10/01/17.
 */
public class ConcFillButtonView extends View {
    private boolean shouldInvalidate = false;
    private int dir = 0,n=0;
    private ActionListener actionListener;

    public ActionListener getActionListener() {
        return actionListener;
    }

    public void setActionListener(ActionListener actionListener) {
        this.actionListener = actionListener;
    }

    private int concIndex = 0;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    public ConcFillButtonView(Context context) {
        super(context);
    }
    public void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        int w = canvas.getWidth(),h = canvas.getHeight();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(12);
        canvas.drawRect(new RectF(0,0,w,h),paint);


        Path outerPath = new Path(),innerPath = new Path();

        int r1 = w/3,r2 = w/4;
        for(int i=0;i<concIndex+1;i++) {
            float x = (float)(w/2+r1*Math.cos(i*10*Math.PI/180)),y = (float)(h/2+r1*Math.sin(i*10*Math.PI/180));
            float x1 = (float)(w/2+r2*Math.cos(i*10*Math.PI/180)),y1 = (float)(h/2+r2*Math.sin(i*10*Math.PI/180));
            if(i == 0){
                outerPath.moveTo(x,y);
                innerPath.moveTo(x1,y1);
            }
            else {
                outerPath.lineTo(x,y);
                innerPath.lineTo(x1,y1);
            }
        }
        paint.setColor(Color.parseColor(UIConstants.UI_COLOR));
        paint.setStrokeWidth(r1-r2);
        canvas.drawPath(outerPath,paint);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        canvas.drawPath(innerPath,paint);
        concIndex+=dir;
        if(concIndex<0) {
            concIndex = 0;
            shouldInvalidate = false;
            n=0;
        }
        else if(concIndex>UIConstants.CONC_LIMIT) {
            if(n == 0 && actionListener!=null) {
                actionListener.onAction(this);
            }
            concIndex = UIConstants.CONC_LIMIT;
            n++;
        }
        if(shouldInvalidate) {
            try {
                Thread.sleep(UIConstants.UI_DELAY);
                invalidate();
            }
            catch (Exception ex) {

            }
        }
    }
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                dir = Math.abs(dir);
                if(!shouldInvalidate) {
                    shouldInvalidate = true;
                    postInvalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                dir = -1;

                break;
            default:
                break;
        }
        return true;
    }
}
