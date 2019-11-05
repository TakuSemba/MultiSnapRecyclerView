package com.takusemba.multisnaprecyclerviewsample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class VerticalAdapter(
    private val titles: Array<String>
) : androidx.recyclerview.widget.RecyclerView.Adapter<VerticalAdapter.ViewHolder>() {

  override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
    val inflater = LayoutInflater.from(viewGroup.context)
    val view = inflater.inflate(R.layout.item_vertical, viewGroup, false)
    return ViewHolder(view)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val title = titles[position]
    val description = "Hello world, $title"
    holder.title.text = title
    holder.description.text = description
  }

  override fun getItemCount(): Int {
    return titles.size
  }

  class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(
      itemView) {
    val title: TextView = itemView.findViewById(R.id.title) as TextView
    val description: TextView = itemView.findViewById(R.id.description) as TextView
  }
}