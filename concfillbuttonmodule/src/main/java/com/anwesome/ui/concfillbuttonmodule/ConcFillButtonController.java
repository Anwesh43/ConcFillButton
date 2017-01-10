package com.anwesome.ui.concfillbuttonmodule;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.hardware.display.DisplayManager;
import android.view.Display;
import android.view.ViewGroup;

/**
 * Created by anweshmishra on 10/01/17.
 */
public class ConcFillButtonController {
    private Activity activity;
    private Display display;
    private int w,h;
    public ConcFillButtonController(Activity activity) {
        this.activity = activity;
        DisplayManager manager = (DisplayManager)activity.getSystemService(Context.DISPLAY_SERVICE);
        display = manager.getDisplay(0);
        if(display!=null) {
            Point size = new Point();
            display.getRealSize(size);
            if(size!=null) {
                w = size.x;
                h = size.y;
            }
        }
    }
    public void addButton(float... coordinates) {

        ConcFillButtonView buttonView = new ConcFillButtonView(activity.getApplicationContext());
        if(coordinates.length == 2) {
            buttonView.setX(coordinates[0]);
            buttonView.setY(coordinates[1]);
        }
        activity.addContentView(buttonView,new ViewGroup.LayoutParams(w/3,w/3));
    }
    public void addButton(ActionListener actionListener,float... coordinates) {
        ConcFillButtonView buttonView = new ConcFillButtonView(activity.getApplicationContext());
        buttonView.setActionListener(actionListener);
        if(coordinates.length == 2) {
            buttonView.setX(coordinates[0]);
            buttonView.setY(coordinates[1]);
        }
        activity.addContentView(buttonView,new ViewGroup.LayoutParams(w/3,w/3));
    }
}
