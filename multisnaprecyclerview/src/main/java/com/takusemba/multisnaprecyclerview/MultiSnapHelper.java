package com.takusemba.multisnaprecyclerview;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.View;

/**
 * Target
 *
 * @author takusemba
 * @since 30/07/2017
 **/
class MultiSnapHelper extends SnapHelper {

    private BaseSnapHelperDelegator snapHelper;

    /**
     * Constructor
     *
     * @param gravity gravity to which the RecyclerView snaps
     * @param snapCount the number of items to scroll over
     */
    MultiSnapHelper(SnapGravity gravity, int snapCount) {
        switch (gravity) {
            case CENTER:
                snapHelper = new CenterSnapHelperDelegator(snapCount);
                break;
            case START:
                snapHelper = new StartSnapHelperDelegator(snapCount);
                break;
            case END:
                snapHelper = new EndSnapHelperDelegator(snapCount);
                break;
            default:
                throw new IllegalArgumentException("not supported gravity");
        }
    }

    @Nullable
    @Override
    public int[] calculateDistanceToFinalSnap(@NonNull RecyclerView.LayoutManager layoutManager, @NonNull View view) {
        return snapHelper.calculateDistanceToFinalSnap(layoutManager, view);
    }

    @Nullable
    @Override
    public View findSnapView(RecyclerView.LayoutManager layoutManager) {
        return snapHelper.findSnapView(layoutManager);
    }

    @Override
    public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
        return snapHelper.findTargetSnapPosition(layoutManager, velocityX, velocityY);
    }

    void setListener(OnSnapListener listener){
        snapHelper.setListener(listener);
    }
}
