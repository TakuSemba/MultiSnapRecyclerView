package com.takusemba.multisnaprecyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

/**
 * MultiSnapRecyclerView Note that only LinearLayoutManger is supported, and reverse layout is not
 * supported.
 **/
public class MultiSnapRecyclerView extends RecyclerView {

  private MultiSnapHelper multiSnapHelper;

  public MultiSnapRecyclerView(Context context) {
    this(context, null);
  }

  public MultiSnapRecyclerView(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public MultiSnapRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MultiSnapRecyclerView);
    SnapGravity gravity =
        SnapGravity.valueOf(a.getInt(R.styleable.MultiSnapRecyclerView_msrv_gravity, 0));
    final int snapCount = a.getInteger(R.styleable.MultiSnapRecyclerView_msrv_snap_count, 1);
    final float millisecondsPerInch =
        a.getFloat(R.styleable.MultiSnapRecyclerView_msrv_ms_per_inch, 100f);
    a.recycle();
    multiSnapHelper = new MultiSnapHelper(gravity, snapCount, new LinearSmoothScroller(context) {

      @Override
      protected void onTargetFound(View targetView, RecyclerView.State state, Action action) {
        int[] snapDistances =
            multiSnapHelper.calculateDistanceToFinalSnap(getLayoutManager(), targetView);
        final int dx = snapDistances[0];
        final int dy = snapDistances[1];
        final int time = calculateTimeForDeceleration(Math.max(Math.abs(dx), Math.abs(dy)));
        if (time > 0) {
          action.update(dx, dy, time, mDecelerateInterpolator);
        }
      }

      @Override protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
        return millisecondsPerInch / displayMetrics.densityDpi;
      }
    });
  }

  @Override protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    setOnFlingListener(null);
    multiSnapHelper.attachToRecyclerView(MultiSnapRecyclerView.this);
  }

  /**
   * sets Snap Listener to RecyclerView
   *
   * @param listener OnSnapListener of MultiSnapRecyclerView
   */
  public void setOnSnapListener(@NonNull final OnSnapListener listener) {
    multiSnapHelper.setListener(listener);
  }
}
