package com.takusemba.multisnaprecyclerview;

/**
 * Listener to notify snapping
 **/
public interface OnSnapListener {

  /**
   * Called when RecyclerView is snapped
   *
   * @param position snapped position
   */
  void snapped(int position);
}
