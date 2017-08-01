package com.takusemba.multisnaprecyclerviewsample;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView;
import com.takusemba.multisnaprecyclerview.OnSnapListener;

import java.util.ArrayList;


/**
 * A custom adapter to use with the RecyclerView widget.
 */
public class HorizontalRecyclerViewAdapter extends RecyclerView.Adapter<HorizontalRecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<HorizontalAbstractModel> modelList;

    private OnItemClickListener mItemClickListener;
    private OnMoreClickListener mMoreClickListener;

    public HorizontalRecyclerViewAdapter(Context context, ArrayList<HorizontalAbstractModel> modelList) {
        this.mContext = context;
        this.modelList = modelList;
    }

    public void updateList(ArrayList<HorizontalAbstractModel> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recycler_google_play, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        //Here you can fill your row view

        final HorizontalAbstractModel model = getItem(position);


        ArrayList<HorizontalAbstractModel> singleSectionItems = model.getSingleItemArrayList();

        holder.itemTxtTitle.setText(model.getTitle());

        HorizontalSingleItemListAdapter itemListDataAdapter = new HorizontalSingleItemListAdapter(mContext, singleSectionItems);

        holder.recyclerViewHorizontalList.setHasFixedSize(true);
        holder.recyclerViewHorizontalList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        holder.recyclerViewHorizontalList.setAdapter(itemListDataAdapter);
        holder.recyclerViewHorizontalList.setOnSnapListener(new OnSnapListener() {
            @Override
            public void snapped() {

            }
        });

//        new MultiSnapHelper(SnapGravity.CENTER, 1).attachToRecyclerView(holder.recyclerViewHorizontalList);


        holder.recyclerViewHorizontalList.setNestedScrollingEnabled(false);


        itemListDataAdapter.SetOnItemClickListener(new HorizontalSingleItemListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int itemPosition, HorizontalAbstractModel model) {

                mItemClickListener.onItemClick(view, position, itemPosition, model);

            }
        });

        holder.itemTxtMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mMoreClickListener.onMoreClick(view, position, model);


            }
        });


    }

    @Override
    public int getItemCount() {

        return modelList.size();
    }


    private HorizontalAbstractModel getItem(int position) {
        return modelList.get(position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public void SetOnMoreClickListener(final OnMoreClickListener onMoreClickListener) {
        this.mMoreClickListener = onMoreClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int absolutePosition, int relativePosition, HorizontalAbstractModel model);
    }


    public interface OnMoreClickListener {
        void onMoreClick(View view, int position, HorizontalAbstractModel model);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        protected MultiSnapRecyclerView recyclerViewHorizontalList;
        protected TextView itemTxtMore;
        private TextView itemTxtTitle;


        public ViewHolder(final View itemView) {
            super(itemView);

            // ButterKnife.bind(this, itemView);

            this.itemTxtTitle = (TextView) itemView.findViewById(R.id.item_txt_title);
            this.recyclerViewHorizontalList = (MultiSnapRecyclerView) itemView.findViewById(R.id.recycler_view_horizontal_list);
            this.itemTxtMore = (TextView) itemView.findViewById(R.id.item_txt_more);


        }
    }


}



