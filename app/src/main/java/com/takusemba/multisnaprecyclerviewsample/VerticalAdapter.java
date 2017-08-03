package com.takusemba.multisnaprecyclerviewsample;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by takusemba on 2017/08/03.
 */

public class VerticalAdapter extends RecyclerView.Adapter<VerticalAdapter.ViewHolder> {

    private String[] titles;

    public VerticalAdapter(String[] titles) {
        this.titles = titles;
    }

    @Override
    public VerticalAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_vertical, viewGroup, false);
        return new VerticalAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VerticalAdapter.ViewHolder holder, int position) {
        String title = titles[position];
        String description = "Hello world, " + title;
        holder.title.setText(title);
        holder.description.setText(description);
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView description;

        ViewHolder(final View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.title);
            this.description = itemView.findViewById(R.id.description);
        }
    }
}