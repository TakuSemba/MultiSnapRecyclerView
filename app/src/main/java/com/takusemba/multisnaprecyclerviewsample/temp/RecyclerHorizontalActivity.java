package com.takusemba.multisnaprecyclerviewsample.temp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;


import com.takusemba.multisnaprecyclerviewsample.R;


public class RecyclerHorizontalActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    // @BindView(R.id.recycler_view)
    // RecyclerView recyclerView;


    private HorizontalRecyclerViewAdapter mAdapter;

    private ArrayList<HorizontalAbstractModel> modelList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_horizontal);

        // ButterKnife.bind(this);
        findViews();
        setAdapter();


    }

    private void findViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    }


    private void setAdapter() {


        ArrayList<HorizontalAbstractModel> singleItemList = new ArrayList<>();

        singleItemList.add(new HorizontalAbstractModel("Android", "Hello " + " Android"));
        singleItemList.add(new HorizontalAbstractModel("Beta", "Hello " + " Beta"));
        singleItemList.add(new HorizontalAbstractModel("Cupcake", "Hello " + " Cupcake"));
        singleItemList.add(new HorizontalAbstractModel("Donut", "Hello " + " Donut"));
        singleItemList.add(new HorizontalAbstractModel("Eclair", "Hello " + " Eclair"));
        singleItemList.add(new HorizontalAbstractModel("Froyo", "Hello " + " Froyo"));
        singleItemList.add(new HorizontalAbstractModel("Gingerbread", "Hello " + " Gingerbread"));
        singleItemList.add(new HorizontalAbstractModel("Honeycomb", "Hello " + " Honeycomb"));
        singleItemList.add(new HorizontalAbstractModel("Ice Cream Sandwich", "Hello " + " Ice Cream Sandwich"));
        singleItemList.add(new HorizontalAbstractModel("Jelly Bean", "Hello " + " Jelly Bean"));
        singleItemList.add(new HorizontalAbstractModel("KitKat", "Hello " + " KitKat"));
        singleItemList.add(new HorizontalAbstractModel("Lollipop", "Hello " + " Lollipop"));
        singleItemList.add(new HorizontalAbstractModel("Marshmallow", "Hello " + " Marshmallow"));
        singleItemList.add(new HorizontalAbstractModel("Nougat", "Hello " + " Nougat"));
        singleItemList.add(new HorizontalAbstractModel("Android O", "Hello " + " Android O"));


        modelList.add(new HorizontalAbstractModel("Android", "Hello " + " Android", singleItemList));
        modelList.add(new HorizontalAbstractModel("Beta", "Hello " + " Beta", singleItemList));
        modelList.add(new HorizontalAbstractModel("Cupcake", "Hello " + " Cupcake", singleItemList));
        modelList.add(new HorizontalAbstractModel("Donut", "Hello " + " Donut", singleItemList));
        modelList.add(new HorizontalAbstractModel("Eclair", "Hello " + " Eclair", singleItemList));
        modelList.add(new HorizontalAbstractModel("Froyo", "Hello " + " Froyo", singleItemList));
        modelList.add(new HorizontalAbstractModel("Gingerbread", "Hello " + " Gingerbread", singleItemList));
        modelList.add(new HorizontalAbstractModel("Honeycomb", "Hello " + " Honeycomb", singleItemList));
        modelList.add(new HorizontalAbstractModel("Ice Cream Sandwich", "Hello " + " Ice Cream Sandwich", singleItemList));
        modelList.add(new HorizontalAbstractModel("Jelly Bean", "Hello " + " Jelly Bean", singleItemList));
        modelList.add(new HorizontalAbstractModel("KitKat", "Hello " + " KitKat", singleItemList));
        modelList.add(new HorizontalAbstractModel("Lollipop", "Hello " + " Lollipop", singleItemList));
        modelList.add(new HorizontalAbstractModel("Marshmallow", "Hello " + " Marshmallow", singleItemList));
        modelList.add(new HorizontalAbstractModel("Nougat", "Hello " + " Nougat", singleItemList));
        modelList.add(new HorizontalAbstractModel("Android O", "Hello " + " Android O", singleItemList));


        mAdapter = new HorizontalRecyclerViewAdapter(RecyclerHorizontalActivity.this, modelList);

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        recyclerView.setAdapter(mAdapter);



    }


}
