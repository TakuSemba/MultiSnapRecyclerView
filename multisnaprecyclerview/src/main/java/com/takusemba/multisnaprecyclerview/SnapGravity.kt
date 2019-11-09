package com.takusemba.multisnaprecyclerview

/**
 * supported gravity
 */
enum class SnapGravity(val value: Int) {

  /**
   * gravity to center
   */
  CENTER(0) {

    override fun createLayoutPositionHelper(): LayoutPositionHelper {
      return CenterLayoutPositionHelper()
    }
  },

  /**
   * gravity to start (left or top)
   */
  START(1) {

    override fun createLayoutPositionHelper(): LayoutPositionHelper {
      return StartLayoutPositionHelper()
    }
  },

  /**
   * gravity to start (right or bottom)
   */
  END(2) {

    override fun createLayoutPositionHelper(): LayoutPositionHelper {
      return EndLayoutPositionHelper()
    }
  };

  abstract fun createLayoutPositionHelper(): LayoutPositionHelper

  companion object {

    fun valueOf(value: Int): SnapGravity {
      for (gravity in values()) {
        if (gravity.value == value) {
          return gravity
        }
      }
      throw IllegalArgumentException("no such enum object for the value: $value")
    }
  }
}
