package com.takusemba.multisnaprecyclerviewsample

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class HorizontalAdapter(
    private val titles: Array<String>
) : RecyclerView.Adapter<HorizontalAdapter.ViewHolder>() {

  override fun onCreateViewHolder(
      viewGroup: ViewGroup,
      viewType: Int
  ): HorizontalAdapter.ViewHolder {
    val inflater = LayoutInflater.from(viewGroup.context)
    val view = inflater.inflate(R.layout.item_horizontal, viewGroup, false)
    return HorizontalAdapter.ViewHolder(view)
  }

  override fun onBindViewHolder(holder: HorizontalAdapter.ViewHolder, position: Int) {
    val title = titles[position]
    holder.title.text = title
  }

  override fun getItemCount(): Int {
    return titles.size
  }

  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val title: TextView = itemView.findViewById(R.id.title) as TextView
  }
}