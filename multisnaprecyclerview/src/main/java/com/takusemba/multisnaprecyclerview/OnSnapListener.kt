package com.takusemba.multisnaprecyclerview

/**
 * Listener to notify snapping
 */
interface OnSnapListener {

  /**
   * Called when RecyclerView is snapped
   *
   * @param position snapped position
   */
  fun snapped(position: Int)
}
