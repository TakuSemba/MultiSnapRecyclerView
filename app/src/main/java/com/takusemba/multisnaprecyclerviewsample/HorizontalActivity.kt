package com.takusemba.multisnaprecyclerviewsample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView

class HorizontalActivity : AppCompatActivity() {

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
    setContentView(R.layout.activity_horizontal)

    val firstAdapter = HorizontalAdapter(TITLES)
    val firstRecyclerView = findViewById(R.id.first_recycler_view) as MultiSnapRecyclerView
    val firstManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    firstRecyclerView.layoutManager = firstManager
    firstRecyclerView.adapter = firstAdapter

    val secondAdapter = HorizontalAdapter(TITLES)
    val secondRecyclerView = findViewById(R.id.second_recycler_view) as MultiSnapRecyclerView
    val secondManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    secondRecyclerView.layoutManager = secondManager
    secondRecyclerView.adapter = secondAdapter

    val thirdAdapter = HorizontalAdapter(TITLES)
    val thirdRecyclerView = findViewById(R.id.third_recycler_view) as MultiSnapRecyclerView
    val thirdManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    thirdRecyclerView.layoutManager = thirdManager
    thirdRecyclerView.adapter = thirdAdapter
  }
}
