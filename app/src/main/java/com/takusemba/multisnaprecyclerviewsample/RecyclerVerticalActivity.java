package com.takusemba.multisnaprecyclerviewsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;


public class RecyclerVerticalActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    // @BindView(R.id.recycler_view)
    // RecyclerView recyclerView;


    private VerticalRecyclerViewAdapter mAdapter;

    private ArrayList<VerticalAbstractModel> modelList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_vertical);

        // ButterKnife.bind(this);
        findViews();
        setAdapter();


    }

    private void findViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    }


    private void setAdapter() {


        modelList.add(new VerticalAbstractModel("Android", "Hello " + " Android"));
        modelList.add(new VerticalAbstractModel("Beta", "Hello " + " Beta"));
        modelList.add(new VerticalAbstractModel("Cupcake", "Hello " + " Cupcake"));
        modelList.add(new VerticalAbstractModel("Donut", "Hello " + " Donut"));
        modelList.add(new VerticalAbstractModel("Eclair", "Hello " + " Eclair"));
        modelList.add(new VerticalAbstractModel("Froyo", "Hello " + " Froyo"));
        modelList.add(new VerticalAbstractModel("Gingerbread", "Hello " + " Gingerbread"));
        modelList.add(new VerticalAbstractModel("Honeycomb", "Hello " + " Honeycomb"));
        modelList.add(new VerticalAbstractModel("Ice Cream Sandwich", "Hello " + " Ice Cream Sandwich"));
        modelList.add(new VerticalAbstractModel("Jelly Bean", "Hello " + " Jelly Bean"));
        modelList.add(new VerticalAbstractModel("KitKat", "Hello " + " KitKat"));
        modelList.add(new VerticalAbstractModel("Lollipop", "Hello " + " Lollipop"));
        modelList.add(new VerticalAbstractModel("Marshmallow", "Hello " + " Marshmallow"));
        modelList.add(new VerticalAbstractModel("Nougat", "Hello " + " Nougat"));
        modelList.add(new VerticalAbstractModel("Android O", "Hello " + " Android O"));


        mAdapter = new VerticalRecyclerViewAdapter(RecyclerVerticalActivity.this, modelList);

        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(mAdapter);


        mAdapter.SetOnItemClickListener(new VerticalRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, VerticalAbstractModel model) {

                //handle item click events here
                Toast.makeText(RecyclerVerticalActivity.this, "Hey " + model.getTitle(), Toast.LENGTH_SHORT).show();


            }
        });


    }


}
