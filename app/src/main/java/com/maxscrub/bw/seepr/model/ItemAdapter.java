package com.maxscrub.bw.seepr.model;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maxscrub.bw.seepr.BuildConfig;
import com.maxscrub.bw.seepr.DiffActivity;
import com.maxscrub.bw.seepr.R;

import java.util.List;

import timber.log.Timber;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private Context context;
    private List<Item> itemResponses;

    public ItemAdapter(Context applicationContext, List<Item> itemList) {
        this.context = applicationContext;
        this.itemResponses = itemList;
    }

    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemAdapter.ViewHolder viewHolder, int position) {
        viewHolder.prTitle.setText(itemResponses.get(position).getPrTitle());
    }

    @Override
    public int getItemCount() {
        return itemResponses.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView prTitle;

        public ViewHolder(View view) {
            super(view);
            prTitle = view.findViewById(R.id.prTitle);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Item clickedData = itemResponses.get(position);
                        Intent intent = new Intent(context, DiffActivity.class);
                        intent.putExtra("pullTitle", itemResponses.get(position).getPrTitle());
                        intent.putExtra("diffUrl", itemResponses.get(position).getDiffUrl());

                        if (BuildConfig.DEBUG) {
                            Timber.d("PullTitle = %s, DiffUrl = %s",
                                    itemResponses.get(position).getPrTitle(),
                                    itemResponses.get(position).getDiffUrl());
                        }

                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                        if (BuildConfig.DEBUG) {
                            Timber.d("ItemAdapter onClick, start diffActivity - %s", clickedData.getPrTitle());
                        }
                    }
                }
            });
        }

    }
}
