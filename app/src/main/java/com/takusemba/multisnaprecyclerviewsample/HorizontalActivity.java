package com.takusemba.multisnaprecyclerviewsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView;

/**
 * Created by takusemba on 2017/08/03.
 */

public class HorizontalActivity extends AppCompatActivity implements HorizontalAdapter.OnItemListener {
    MultiSnapRecyclerView secondRecyclerView;
    String[] titles = {
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
            "Oreo",
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal);

        HorizontalAdapter firstAdapter = new HorizontalAdapter(titles);
        MultiSnapRecyclerView firstRecyclerView = (MultiSnapRecyclerView) findViewById(R.id.first_recycler_view);
        LinearLayoutManager firstManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        firstRecyclerView.setLayoutManager(firstManager);
        firstRecyclerView.setAdapter(firstAdapter);

        HorizontalAdapter secondAdapter = new HorizontalAdapter(titles);
        secondRecyclerView = (MultiSnapRecyclerView) findViewById(R.id.second_recycler_view);
        LinearLayoutManager secondManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        secondRecyclerView.setLayoutManager(secondManager);
        secondRecyclerView.setAdapter(secondAdapter);
        secondAdapter.setOnItemListener(this);

        HorizontalAdapter thirdAdapter = new HorizontalAdapter(titles);
        MultiSnapRecyclerView thirdRecyclerView = (MultiSnapRecyclerView) findViewById(R.id.third_recycler_view);
        LinearLayoutManager thirdManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        thirdRecyclerView.setLayoutManager(thirdManager);
        thirdRecyclerView.setAdapter(thirdAdapter);
    }

    @Override
    public void onItemClicked(View view, int position) {
        Toast.makeText(this, "Item's clicked with position " + position, Toast.LENGTH_SHORT).show();
        secondRecyclerView.snapToPosition(view);
    }
}
