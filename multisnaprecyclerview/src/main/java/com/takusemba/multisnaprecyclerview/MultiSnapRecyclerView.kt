package com.takusemba.multisnaprecyclerview

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView

/**
 * MultiSnapRecyclerView for multi interval snapping.
 * Note that only LinearLayoutManger is supported, and reverse layout is not supported.
 */
class MultiSnapRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : RecyclerView(context, attrs, defStyle) {

  private var multiSnapHelper: MultiSnapHelper? = null

  init {
    val a = context.obtainStyledAttributes(attrs, R.styleable.MultiSnapRecyclerView)

    val gravity: SnapGravity
    val interval: Int
    val speedMsPerInch: Float

    try {
      gravity = SnapGravity.valueOf(
          a.getInt(
              R.styleable.MultiSnapRecyclerView_msrv_gravity,
              MultiSnapHelper.DEFAULT_GRAVITY.value
          )
      )
      interval = a.getInteger(
          R.styleable.MultiSnapRecyclerView_msrv_interval,
          MultiSnapHelper.DEFAULT_INTERVAL
      )
      speedMsPerInch = a.getFloat(
          R.styleable.MultiSnapRecyclerView_msrv_speed_ms_per_inch,
          MultiSnapHelper.DEFAULT_SPEED_MS_PER_INCH
      )
    } finally {
      a.recycle()
    }

    multiSnapHelper = MultiSnapHelper(gravity, interval, speedMsPerInch)
    multiSnapHelper?.attachToRecyclerView(this)
  }

  /**
   * Sets [OnSnapListener] to [MultiSnapRecyclerView]
   */
  fun setOnSnapListener(listener: OnSnapListener) {
    multiSnapHelper?.setListener(listener)
  }
}
