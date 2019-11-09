package com.takusemba.multisnaprecyclerview

/**
 * Supported gravity for snapping.
 */
enum class SnapGravity(val value: Int) {

  /**
   * gravity to center.
   */
  CENTER(0),

  /**
   * gravity to start (left or top).
   */
  START(1),

  /**
   * gravity to start (right or bottom).
   */
  END(2);

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
