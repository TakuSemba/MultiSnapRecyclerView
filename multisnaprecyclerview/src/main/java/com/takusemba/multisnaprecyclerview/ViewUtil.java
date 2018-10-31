package com.takusemba.multisnaprecyclerview;

import android.view.View;
import android.view.ViewGroup;

public class ViewUtil {
    public static float getLeftMargin(View view) {
        return ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).leftMargin;
    }

    public static float getRightMargin(View view) {
        return ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).rightMargin;
    }

    public static boolean isForwardedTargetView(View currentView, View targetView) {
        if (currentView.getX() > targetView.getX()) {
            return true;
        } else {
            return false;
        }
    }
}
