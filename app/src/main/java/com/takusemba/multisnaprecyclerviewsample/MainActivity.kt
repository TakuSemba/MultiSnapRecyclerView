package com.takusemba.multisnaprecyclerviewsample

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    findViewById(R.id.horizontal).setOnClickListener {
      startActivity(Intent(this@MainActivity, HorizontalActivity::class.java))
    }

    findViewById(R.id.vertical).setOnClickListener {
      startActivity(Intent(this@MainActivity, VerticalActivity::class.java))
    }
  }
}
