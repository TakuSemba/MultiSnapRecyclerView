package com.takusemba.multisnaprecyclerview

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView

/**
 * MultiSnapRecyclerView Note that only LinearLayoutManger is supported, and reverse layout is not
 * supported.
 */
class MultiSnapRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : RecyclerView(context, attrs, defStyle) {

  private var multiSnapHelper: MultiSnapHelper? = null

  private val gravity: SnapGravity
  private val snapCount: Int
  private val millisecondsPerInch: Float

  init {
    val a = context.obtainStyledAttributes(attrs, R.styleable.MultiSnapRecyclerView)
    gravity = SnapGravity.valueOf(a.getInt(R.styleable.MultiSnapRecyclerView_msrv_gravity, 0))
    snapCount = a.getInteger(R.styleable.MultiSnapRecyclerView_msrv_snap_count, 1)
    millisecondsPerInch = a.getFloat(R.styleable.MultiSnapRecyclerView_msrv_ms_per_inch, 100f)
    a.recycle()

    multiSnapHelper = MultiSnapHelper(gravity, snapCount, millisecondsPerInch)
    multiSnapHelper?.attachToRecyclerView(this)
  }

  /**
   * sets Snap Listener to RecyclerView
   *
   * @param listener OnSnapListener of MultiSnapRecyclerView
   */
  fun setOnSnapListener(listener: OnSnapListener) {
    multiSnapHelper?.setListener(listener)
  }
}
