package com.takusemba.multisnaprecyclerviewsample;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by takusemba on 2017/08/03.
 */

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.ViewHolder> implements View.OnClickListener {

    private String[] titles;
    private OnItemListener onItemListener;

    @Override
    public void onClick(View v) {
        if (onItemListener != null) {
            onItemListener.onItemClicked(v, (int) v.getTag());
        }
    }

    interface OnItemListener {
        void onItemClicked(View view, int position);
    }

    public HorizontalAdapter(String[] titles) {
        this.titles = titles;
    }

    public void setOnItemListener(OnItemListener onItemListener) {
        this.onItemListener = onItemListener;
    }

    @Override
    public HorizontalAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_horizontal, viewGroup, false);
        return new HorizontalAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HorizontalAdapter.ViewHolder holder, int position) {
        String title = titles[position];
        holder.title.setText(title);
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        ViewHolder(final View itemView) {
            super(itemView);
            this.title = (TextView) itemView.findViewById(R.id.title);
        }
    }
}