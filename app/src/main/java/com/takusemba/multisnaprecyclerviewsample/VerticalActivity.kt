package com.takusemba.multisnaprecyclerviewsample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView

class VerticalActivity : AppCompatActivity() {

  companion object {
    private val TITLES = arrayOf(
        "Android",
        "Beta",
        "Cupcake",
        "Donut",
        "Eclair",
        "Froyo",
        "Gingerbread",
        "Honeycomb",
        "Ice Cream Sandwich",
        "Jelly Bean",
        "KitKat",
        "Lollipop",
        "Marshmallow",
        "Nougat",
        "Oreo"
    )
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_vertical)

    val adapter = VerticalAdapter(TITLES)
    val recyclerView = findViewById(R.id.recycler_view) as MultiSnapRecyclerView
    val manager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    recyclerView.layoutManager = manager
    recyclerView.adapter = adapter
  }
}
