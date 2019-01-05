package com.takusemba.multisnaprecyclerviewsample

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class VerticalAdapter(
    private val titles: Array<String>
) : RecyclerView.Adapter<VerticalAdapter.ViewHolder>() {

  override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): VerticalAdapter.ViewHolder {
    val inflater = LayoutInflater.from(viewGroup.context)
    val view = inflater.inflate(R.layout.item_vertical, viewGroup, false)
    return VerticalAdapter.ViewHolder(view)
  }

  override fun onBindViewHolder(holder: VerticalAdapter.ViewHolder, position: Int) {
    val title = titles[position]
    val description = "Hello world, $title"
    holder.title.text = title
    holder.description.text = description
  }

  override fun getItemCount(): Int {
    return titles.size
  }

  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val title: TextView = itemView.findViewById(R.id.title) as TextView
    val description: TextView = itemView.findViewById(R.id.description) as TextView
  }
}