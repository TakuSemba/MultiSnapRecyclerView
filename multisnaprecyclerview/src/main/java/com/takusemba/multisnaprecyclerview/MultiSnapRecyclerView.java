package com.takusemba.multisnaprecyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by takusemba on 2017/07/29.
 */

public class MultiSnapRecyclerView extends RecyclerView {

    private SnapGravity gravity;
    private int snapCount;
    private OnSnapListener snapListener;
    private OnScrollListener onScrollListener;

    /**
     * keeps track of the state of RecyclerView
     */
    private int state = RecyclerView.SCROLL_STATE_IDLE;

    public MultiSnapRecyclerView(Context context) {
        this(context, null);
    }

    public MultiSnapRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultiSnapRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MultiSnapRecyclerView);
        gravity = SnapGravity.valueOf(a.getInt(R.styleable.MultiSnapRecyclerView_msrv_gravity, 0));
        snapCount = a.getInteger(R.styleable.MultiSnapRecyclerView_msrv_snap_count, 1);
        a.recycle();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setOnFlingListener(null);
        if (!(getLayoutManager() instanceof LinearLayoutManager)) {
            throw new IllegalArgumentException("other than LinearLayoutManger is not supported");
        }
        removeOnScrollListener(onScrollListener);
        onScrollListener = new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (state == RecyclerView.SCROLL_STATE_DRAGGING && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (snapListener != null) snapListener.snapped();
                }
                if (state == RecyclerView.SCROLL_STATE_SETTLING && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (snapListener != null) snapListener.snapped();
                }
                state = newState;
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        };
        addOnScrollListener(onScrollListener);
        new MultiSnapHelper(gravity, snapCount).attachToRecyclerView(MultiSnapRecyclerView.this);
    }

    /**
     * sets Snap Listener to RecyclerView
     *
     * @param listener OnSnapListener of MultiSnapRecyclerView
     */
    public void setOnSnapListener(@NonNull final OnSnapListener listener) {
        snapListener = listener;
    }
}
