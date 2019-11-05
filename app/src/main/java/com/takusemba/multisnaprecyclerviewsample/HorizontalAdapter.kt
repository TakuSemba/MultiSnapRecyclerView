package com.takusemba.multisnaprecyclerviewsample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.takusemba.multisnaprecyclerviewsample.HorizontalAdapter.ViewHolder

class HorizontalAdapter(
    private val titles: Array<String>
) : androidx.recyclerview.widget.RecyclerView.Adapter<ViewHolder>() {

  override fun onCreateViewHolder(
      viewGroup: ViewGroup,
      viewType: Int
  ): ViewHolder {
    val inflater = LayoutInflater.from(viewGroup.context)
    val view = inflater.inflate(R.layout.item_horizontal, viewGroup, false)
    return ViewHolder(view)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val title = titles[position]
    holder.title.text = title
  }

  override fun getItemCount(): Int {
    return titles.size
  }

  class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(
      itemView) {
    val title: TextView = itemView.findViewById(R.id.title) as TextView
  }
}