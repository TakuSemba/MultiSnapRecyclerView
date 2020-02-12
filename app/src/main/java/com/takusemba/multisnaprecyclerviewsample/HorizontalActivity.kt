package com.takusemba.multisnaprecyclerviewsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView

class HorizontalActivity : AppCompatActivity() {

  companion object {

    private val SIMPLE_TITLES = arrayOf(
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

    val firstAdapter = HorizontalAdapter(SIMPLE_TITLES)
    val firstRecyclerView = findViewById<MultiSnapRecyclerView>(R.id.first_recycler_view)
    val firstManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    firstRecyclerView.layoutManager = firstManager
    firstRecyclerView.adapter = firstAdapter

    val secondAdapter = HorizontalAdapter(SIMPLE_TITLES)
    val secondRecyclerView = findViewById<MultiSnapRecyclerView>(R.id.second_recycler_view)
    val secondManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    secondRecyclerView.layoutManager = secondManager
    secondRecyclerView.adapter = secondAdapter

    val thirdAdapter = HorizontalAdapter(SIMPLE_TITLES)
    val thirdRecyclerView = findViewById<MultiSnapRecyclerView>(R.id.third_recycler_view)
    val thirdManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    thirdRecyclerView.layoutManager = thirdManager
    thirdRecyclerView.adapter = thirdAdapter
  }
}
