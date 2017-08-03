package com.takusemba.multisnaprecyclerviewsample.temp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.takusemba.multisnaprecyclerviewsample.R;

import java.util.ArrayList;


public class HorizontalSingleItemListAdapter extends RecyclerView.Adapter<HorizontalSingleItemListAdapter.SingleItemRowHolder> {

    private ArrayList<HorizontalAbstractModel> itemsList;
    private Context mContext;
    private OnItemClickListener mItemClickListener;

    public HorizontalSingleItemListAdapter(Context context, ArrayList<HorizontalAbstractModel> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recycler_item_google_play, viewGroup, false);

        return new SingleItemRowHolder(view);
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, int i) {

        HorizontalAbstractModel singleItem = itemsList.get(i);

        holder.itemCardTxtTitle.setText(singleItem.getTitle());


       /* Glide.with(mContext)
                .load(feedItem.getImageURL())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .error(R.drawable.bg)
                .into(feedListRowHolder.thumbView);*/
    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int itemPosition, HorizontalAbstractModel model);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView itemCardTxtTitle;

        protected ImageView itemCardImg;


        public SingleItemRowHolder(View view) {
            super(view);

            this.itemCardTxtTitle = (TextView) view.findViewById(R.id.item_card_txt_title);
            this.itemCardImg = (ImageView) view.findViewById(R.id.item_card_img);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    mItemClickListener.onItemClick(v, getAdapterPosition(), itemsList.get(getAdapterPosition()));

                }
            });


        }

    }

}
